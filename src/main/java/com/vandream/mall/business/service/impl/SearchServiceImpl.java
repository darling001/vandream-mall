package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.AccountDAO;
import com.vandream.mall.business.dao.CategoryFieldAggDAO;
import com.vandream.mall.business.dao.ProductCategoryDAO;
import com.vandream.mall.business.domain.CategoryFieldAgg;
import com.vandream.mall.business.dto.CategoryAggDTO;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.business.service.SearchService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.business.vo.search.*;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.filter.FilterAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("searchService")
public class SearchServiceImpl implements SearchService {

    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    private static final String VALUE_SET_CODE_UNIT_TYPE = "tlerp.ausbs.unitType";


    private static final String ES_INDEX_GMS = "gms";
    private static final String ES_TYPE_ITEM_AGG = "item_agg";
    private static final String ES_ANALYZER_WHITESPACE = "whitespace";
    private static final Integer DEFAULT_PAGE_SIZE = 15;
    /**
     * 正常 (产品)
     */
    private static final String SEARCH_TYPE_NORMAL = "NORMAL";
    /**
     * 品牌
     */
    private static final String SEARCH_TYPE_BRAND = "BRAND";
    /**
     * 类目
     */
    private static final String SEARCH_TYPE_CATEGORY = "CATEGORY";

    /**
     * 搜索模式 求和模式
     */
    private static final String SCORE_MODE_SUM = "sum";
    /**
     * 由于无相关性的分值默认为 1 ，设置权重分最小值为 10
     */
    private static final Float MIN_SCORE = 10.0F;
    /**
     * order by
     */
    private static final String ORDER_FIELD_SALE_PRICE = "salePrice";

    private static final String ORDER_BY_ASC = "ASC";
    private static final String ORDER_BY_DESC = "DESC";
    /**
     * 类目聚合数据条数
     */
    private static final int CATEGORY_AGG_SIZE = 49;
    /**
     * 默认选择{}区域供应商
     */
    private static final String SUPPLIER_DEFAULT_AREA = "浙江";
    /**
     * 通用字段定义
     */
    private static Set<String> generalFields = new HashSet<>();
    private static String[] generalFieldArray = null;

    private static String loginLevel = "00";

    static {
        generalFields.add("cmc_item_line.STATUS");
        generalFields.add("cmc_item_line.DISPLAY_FLAG");

        generalFields.add("ITEM_ID");
        generalFields.add("ITEM_CODE");
        generalFields.add("ITEM_NAME");
        generalFields.add("ITEM_SHORTNAME");
        generalFields.add("PRIMARY_UNIT_CODE");
        generalFields.add("SECONDARY_UNIT_CODE");
        generalFields.add("SPEC_CONTENTS");
        generalFields.add("CATEGORY_ID");
        generalFields.add("SPU_ID");

        generalFields.add("cmc_item_line.ITEM_ID");
        generalFields.add("cmc_item_line.ITEM_LINE_ID");
        generalFields.add("cmc_item_line.SALE_PRICE1");
        generalFields.add("cmc_item_line.SALE_PRICE2");
        generalFields.add("cmc_item_line.SALE_PRICE3");
        generalFields.add("cmc_item_line.SUPPLIER_ID");
        generalFields.add("cmc_item_line.AREA_NAME");
        generalFields.add("cmc_item_line.AREA_CODE");

        generalFields.add("aus_at_picture_list.BILL_TYPE");
        generalFields.add("aus_at_picture_list.PIC_EX_FILEID");
        generalFields.add("aus_at_picture_list.PIC_ORDER");
        generalFields.add("aus_at_picture_list.PIC_STATUS");

        generalFieldArray = generalFields.toArray(new String[generalFields.size()]);
    }

    @Resource
    private TransportClient transportClient;
    @Resource
    private CategoryFieldAggDAO categoryFieldAggDAO;
    @Resource
    private ValueSetService valueSetService;
    @Resource
    private ProductCategoryDAO productCategoryDAO;

    @Resource
    private AccountDAO accountDAO;

    private static List removeDuplicate(List list) {
        if (list != null && list.size() > 0) {
            HashSet hashSet = new HashSet<>(list);
            list.clear();
            list.addAll(hashSet);
            return list;
        } else {
            return new ArrayList();
        }

    }

    /**
     * 搜索服务主流程
     *
     * @param keyWord
     * @param brandId
     * @param categoryId
     * @param type
     * @param spec
     * @param orderBy
     * @param sortBy
     * @param pageNum
     * @param pageSize
     * @return
     * @throws InvocationTargetException
     */
    @Override
    public SearchResultVO search(String keyWord, String brandId, String categoryId, String type,
                                 String spec, String orderBy, String sortBy, Integer pageNum,
                                 Integer pageSize, String userId) {

        if (StringUtil.isBlank(type)) {
            type = SEARCH_TYPE_NORMAL;
        }


        if (pageSize == null || pageSize == 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        if (pageNum == null || pageNum == 1) {
            pageNum = 0;
        } else {
            pageNum--;
        }
        if (StringUtil.isNotBlank(userId)) {
            LoginDTO loginDTO = accountDAO.findById(userId);
            if (ObjectUtil.isNotEmpty(loginDTO)) {
                if (StringUtil.isNotBlank(loginDTO.getAccountFlag()) && StringUtil.isNotBlank
                        (loginDTO
                                .getCustomerId()) && StringUtil.isNotBlank(loginDTO
                        .getCustomerLevel())) {
                    if ("3".equals(loginDTO.getAccountFlag())) {
                        loginLevel = loginDTO.getCustomerLevel();
                    }
                }
            }
        }
        /**
         * 搜索请求对象
         */
        SearchRequestBuilder searchRequestBuilder = getSearchRequestBuilder(orderBy,
                sortBy, pageNum, pageSize, 0F);
        /**
         * 搜索返回Response对象
         */
        SearchResponse searchResponse = null;
        BoolQueryBuilder boolQueryBuilder = null;
        //面包屑
        SearchCrumbsVO searchCrumbs = null;
        //类目聚合
        List<SearchCategoryAggVO> categoryAggList = null;
        //规格属性值聚合
        List<SearchSpecAggVO> parameters = null;
        //searchGroupByCategory
        SearchResponse categoryAggSearchResponse = null;



        // 搜索流程控制
        if (StringUtil.isBlank(keyWord) && StringUtil.isBlank(categoryId) && StringUtil.isBlank
                (brandId)) {
            //空搜索流程  无筛选条件
            boolQueryBuilder = getGeneralSearchQuery();
            logger.debug("GeneralSearchRequest: \n {}", searchRequestBuilder.toString());
        } else {
            if (StringUtil.isBlank(keyWord)) {
                if (StringUtil.isNotBlank(categoryId)) {
                    //类目Id 检索流程
                    boolQueryBuilder = getCategoryIdSearchQuery(categoryId, spec);
                    logger.debug("categoryIdSearchRequest: \n {}", searchRequestBuilder
                            .toString());
                } else {
                    if (StringUtil.isNotBlank(brandId)) {
                        //品牌ID检索
                        boolQueryBuilder = getBrandIdSearchQuery(brandId, spec);
                        logger.debug("brandIdSearchRequest: \n {}", searchRequestBuilder.toString
                                ());
                    }
                }
            } else {
                if (SEARCH_TYPE_BRAND.equalsIgnoreCase(type)) {
                    //品牌名称搜索流程
                    boolQueryBuilder = getBrandNameSearchQuery(keyWord, spec);
                    logger.debug("brandNameSearchRequest: \n {}", searchRequestBuilder.toString
                            ());
                } else {
                    //关键字搜索流程  联合类目与品牌Id
                    boolQueryBuilder = getKeyWordSearchQuery(keyWord, brandId, categoryId, spec);
                    logger.debug("keyWordSearchRequest: \n {}", searchRequestBuilder.toString());
                }
            }
        }

        categoryAggSearchResponse = searchGroupByCategory(boolQueryBuilder);
        searchCrumbs = getSearchCrumbs(categoryAggSearchResponse, categoryId, type);
        categoryAggList = getCategoryAgg(categoryAggSearchResponse);
        if (searchCrumbs.isLocated()) {
            parameters = searchItemSpecAggByQuery(boolQueryBuilder);
            boolQueryBuilder = setSpecFilterQuery(boolQueryBuilder, spec);
            categoryAggList = null;
        }
        searchRequestBuilder.setQuery(boolQueryBuilder);

        //preference参数  防止搜索结果排序不一致性问题
        String preferenceStr= JSON.toJSONString(searchRequestBuilder);
        searchRequestBuilder.setPreference(preferenceStr);

        logger.info("GeneralSearchRequest: \n {}", searchRequestBuilder.toString());
        searchResponse = searchRequestBuilder.execute().actionGet();
        logger.debug("#searchResponse#  \n{}", searchResponse.toString());

        SearchResultVO searchResultVO = assemblySearchResultVO(searchResponse, searchCrumbs,
                categoryAggList, parameters, pageNum);
        logger.debug("SearchResultVO: \n {}", JSONUtil.toJson(searchResultVO));
        return searchResultVO;
    }

    /**
     * 搜索结果VO对象总装
     *
     * @param searchResponse
     * @param crumbs
     * @param category
     * @param parameters
     * @param pageNum
     * @return
     */
    private SearchResultVO assemblySearchResultVO(SearchResponse searchResponse, SearchCrumbsVO
            crumbs, List<SearchCategoryAggVO> category, List<SearchSpecAggVO> parameters,
                                                  Integer pageNum) {
        List<Map<String, Object>> sourceList = getSourceList(searchResponse);
        long totalHits = searchResponse.getHits().getTotalHits();
        SearchResultVO result = new SearchResultVO((pageNum + 1), totalHits);
        //获取item价格区间聚合信息

        String itemListJson = JSON.toJSONString(sourceList);
        List<SearchItemAggVO> searchItemAggVOList = JSON.parseArray(itemListJson, SearchItemAggVO
                .class);
        //处理设置商品Min Max价格
        processSettingItemPrice(searchItemAggVOList);


        //取回商品单位值集
        Map<String, String> unitTypeMap = valueSetService.getItemUnitTypeNameMap();

        List<String> itemIdList = new ArrayList<>();

        for (SearchItemAggVO itemAggVO : searchItemAggVOList) {

            itemIdList.add(itemAggVO.getItemId());
            //规格参数转换
            String specJson = itemAggVO.getSpecListJson();
            List<SearchItemSpecVO> searchItemSpecVOList = null;
            try {
                searchItemSpecVOList = JSON.parseArray(specJson, SearchItemSpecVO
                        .class);
            } catch (Exception e) {
                logger.error("{}", e.getMessage(), e);
                logger.debug(specJson);
            }
            //item参数排序
            if (ObjectUtil.isNotEmpty(searchItemSpecVOList)) {
                Collections.sort(searchItemSpecVOList);
                itemAggVO.setSpecList(searchItemSpecVOList);
            }
            itemAggVO.setSpecListJson("");

            //设置供默认应商id
            List<SearchItemLineAggVO> itemLineAggVOList = itemAggVO.getItemLine();

            for (SearchItemLineAggVO itemLineAggVO : itemLineAggVOList) {
                String areaName = itemLineAggVO.getAreaName();
                if (StringUtil.isNotBlank(areaName)) {
                    if (areaName.contains(SUPPLIER_DEFAULT_AREA)) {
                        if (StringUtil.isNotBlank(itemLineAggVO.getSupplierId())) {
                            itemAggVO.setSupplierId(itemLineAggVO.getSupplierId());
                            break;
                        }
                    }
                }
            }
            if (StringUtil.isBlank(itemAggVO.getSupplierId())) {
                if (itemLineAggVOList != null && !itemLineAggVOList.isEmpty()) {
                    itemAggVO.setSupplierId(itemLineAggVOList.get(0).getSupplierId());
                }
            }
            //设置图片url
            List<SearchItemPictureVO> pictureList = itemAggVO.getPictureList();
            if (ObjectUtil.isNotEmpty(pictureList)) {
                //设置过滤条件
                List<SearchItemPictureVO> pictureVOList = pictureList.stream()
                        .filter(itemPictureVO -> SearchItemPictureVO.BILL_TYPE_ITEM.equals(itemPictureVO.getBillType()))
                        .filter(itemPictureVO -> SearchItemPictureVO.PIC_STATUS.equals(itemPictureVO.getPicStatus()))
                        .collect(Collectors.toList());
                //列表排序
                Collections.sort(pictureVOList, Comparator.comparing(SearchItemPictureVO::getPicOrder));
                //遍历筛选第一张图片
                if (ObjectUtil.isNotEmpty(pictureVOList)) {
                    String picExFileId = pictureVOList.get(0).getPicExFileId();
                    itemAggVO.setPictureUrl(picExFileId);
                    itemAggVO.setPictureList(null);
                }
            }
            // 设置单位名称
            itemAggVO.setUnitName(unitTypeMap.get(itemAggVO.getPrimaryUnitCode()));
        }
        result.setItemList(searchItemAggVOList);
        result.setParameters(parameters);
        result.setCategory(category);
        result.setCrumbs(crumbs);

        return result;
    }

    private List<SearchItemAggVO> processSettingItemPrice(List<SearchItemAggVO>
                                                                  searchItemAggVOList) {
        // -1 代表议价
        BigDecimal negativeOne = new BigDecimal("-1").setScale(2, RoundingMode.HALF_UP);

        for (SearchItemAggVO itemAggVO : searchItemAggVOList) {
            List<SearchItemLineAggVO> itemLineList = itemAggVO.getItemLine();
            if (ObjectUtil.isNotEmpty(itemLineList)) {
                //商品普价列表
                List<BigDecimal> itemPriceList = new ArrayList<>(itemLineList.size());
                //会员价列表
                List<BigDecimal> memberPriceList = new ArrayList<>(itemLineList.size());
                for (SearchItemLineAggVO itemLineAggVO : itemLineList) {
                    if (ObjectUtil.isEmpty(itemLineAggVO.getSalePrice1()) || BigDecimal.ZERO
                            .compareTo(itemLineAggVO.getSalePrice1()) == 0) {
                        itemAggVO.setMinPrice(negativeOne);
                        itemAggVO.setMaxPrice(negativeOne);
                    }
                    if (ObjectUtil.isEmpty(itemLineAggVO.getSalePrice2()) || BigDecimal.ZERO
                            .compareTo(itemLineAggVO.getSalePrice2()) == 0) {
                        itemAggVO.setMinMemberPrice(negativeOne);
                        itemAggVO.setMaxMemberPrice(negativeOne);
                    }
                    if (ObjectUtil.isEmpty(itemLineAggVO.getSalePrice3()) || BigDecimal.ZERO
                            .compareTo(itemLineAggVO.getSalePrice3()) == 0) {
                        itemAggVO.setMinMemberPrice(negativeOne);
                        itemAggVO.setMaxMemberPrice(negativeOne);
                    }

                }
                for (SearchItemLineAggVO itemLine : itemLineList) {
                    if (ObjectUtil.isEmpty(itemLine.getSalePrice1())) {
                        break;
                    }
                    itemPriceList.add(itemLine.getSalePrice1());
                    if ("10".equals(loginLevel)) {
                        memberPriceList.add(itemLine.getSalePrice2());
                    }
                    if ("20".equals(loginLevel)) {
                        memberPriceList.add(itemLine.getSalePrice3());
                    }

                    itemLine.setSalePrice1(null);
                    itemLine.setSalePrice2(null);
                    itemLine.setSalePrice3(null);
                }
                BigDecimal minPrice = null;
                BigDecimal maxPrice = null;
                if (ObjectUtil.isNotEmpty(itemPriceList)) {
                    minPrice = Collections.min(itemPriceList);
                    maxPrice = Collections.max(itemPriceList);
                }
                BigDecimal minMemberPrice = null;
                BigDecimal maxMemberPrice = null;
                if (ObjectUtil.isNotEmpty(memberPriceList)) {
                    minMemberPrice = Collections.min(memberPriceList);
                    maxMemberPrice = Collections.max(memberPriceList);
                }

                if (ObjectUtil.isEmpty(minPrice) || ObjectUtil.isEmpty(maxPrice)) {
                    itemAggVO.setMinPrice(negativeOne);
                    itemAggVO.setMaxPrice(negativeOne);
                } else if (BigDecimal.ZERO.compareTo(minPrice) == 0 || BigDecimal.ZERO.compareTo
                        (maxPrice) == 0) {
                    itemAggVO.setMinPrice(negativeOne);
                    itemAggVO.setMaxPrice(negativeOne);
                } else {
                    itemAggVO.setMinPrice(minPrice.setScale(2, RoundingMode.HALF_UP));
                    itemAggVO.setMaxPrice(maxPrice.setScale(2, RoundingMode.HALF_UP));
                }

                if (ObjectUtil.isEmpty(minMemberPrice) || ObjectUtil.isEmpty(maxMemberPrice)) {
                    itemAggVO.setMinMemberPrice(negativeOne);
                    itemAggVO.setMaxMemberPrice(negativeOne);
                } else if (BigDecimal.ZERO.compareTo(minMemberPrice) == 0 || BigDecimal.ZERO
                        .compareTo(maxMemberPrice) == 0) {
                    itemAggVO.setMinMemberPrice(negativeOne);
                    itemAggVO.setMaxMemberPrice(negativeOne);
                } else {
                    itemAggVO.setMinMemberPrice(minMemberPrice.setScale(2, RoundingMode.HALF_UP));
                    itemAggVO.setMaxMemberPrice(maxMemberPrice.setScale(2, RoundingMode.HALF_UP));
                }

            }

        }
        return searchItemAggVOList;
    }

    /**
     * 获取首页所需类目聚合信息
     *
     * @param searchResponse
     * @return
     */
    private List<SearchCategoryAggVO> getCategoryAgg(SearchResponse searchResponse) {

        List<SearchCategoryAggVO> categoryAggAllList = getCategoryAggAllList(searchResponse);
        categoryAggAllList = removeDuplicate(categoryAggAllList);
        logger.debug("CategoryAggSourceList \n {}", categoryAggAllList);
        if (ObjectUtil.isNotEmpty(categoryAggAllList)) {

            Map<String, List<SearchCategoryAggVO>> categoryOne = new HashMap<>(categoryAggAllList
                    .size());
            Map<String, List<SearchCategoryAggVO>> categoryTwo = new HashMap<>(categoryAggAllList
                    .size());
            Map<String, List<SearchCategoryAggVO>> categoryThree = new HashMap<>
                    (categoryAggAllList.size());
            //将类目信息按级别分组
            for (SearchCategoryAggVO categoryAggVO : categoryAggAllList) {
                String level = categoryAggVO.getCategoryLevel();
                if ("1".equals(level)) {

                    List<SearchCategoryAggVO> oneLevelCategoryList = categoryOne.get
                            ("categoryOne");
                    if (oneLevelCategoryList == null || oneLevelCategoryList.isEmpty
                            ()) {
                        oneLevelCategoryList = new ArrayList<>();
                    }
                    oneLevelCategoryList.add(categoryAggVO);
                    categoryOne.put("categoryOne", oneLevelCategoryList);

                } else if ("2".equals(level)) {
                    List<SearchCategoryAggVO> twoLevelCategoryList = categoryTwo.get
                            ("categoryTwo");

                    if (twoLevelCategoryList == null || twoLevelCategoryList.isEmpty
                            ()) {
                        twoLevelCategoryList = new ArrayList<>();
                    }
                    twoLevelCategoryList.add(categoryAggVO);
                    categoryTwo.put("categoryTwo", twoLevelCategoryList);
                } else if ("3".equals(level)) {
                    List<SearchCategoryAggVO> threeLevelCategoryList = categoryThree.get
                            ("categoryThree");
                    if (threeLevelCategoryList == null || threeLevelCategoryList
                            .isEmpty()) {
                        threeLevelCategoryList = new ArrayList<>();
                    }
                    threeLevelCategoryList.add(categoryAggVO);
                    categoryThree.put("categoryThree", threeLevelCategoryList);
                }

            }

            List<SearchCategoryAggVO> categoryOneList = categoryOne.get("categoryOne");
            List<SearchCategoryAggVO> categoryTwoList = categoryTwo.get("categoryTwo");
            List<SearchCategoryAggVO> categoryThreeList = categoryThree.get("categoryThree");
            //排序
            if (categoryOneList != null && !categoryOneList.isEmpty()) {
                Collections.sort(categoryOneList);
            }
            if (categoryTwoList != null && !categoryTwoList.isEmpty()) {
                Collections.sort(categoryTwoList);
            }
            if (categoryThreeList != null && !categoryThreeList.isEmpty()) {
                Collections.sort(categoryThreeList);
            }

            List<SearchCategoryAggVO> resultList = new ArrayList<>();
            resultList.addAll(categoryThreeList);
            resultList.addAll(categoryTwoList);
            resultList.addAll(categoryOneList);

            return resultList;
        } else {
            return new ArrayList<>();
        }

    }

    /**
     * 从数据表取回 商品基于类目的 规格属性聚合参数值
     *
     * @param categoryId
     * @return
     */
    private List<CategoryFieldAgg> getCategoryFieldAggList(String categoryId) {
        List<CategoryFieldAgg> categoryFieldAggList = categoryFieldAggDAO
                .findListByCategoryId(categoryId);
        logger.debug("CategoryFieldAggList \n {}", categoryFieldAggList);
        return categoryFieldAggList;
    }

    private BoolQueryBuilder getGeneralSearchQuery() {
        return getCommonSearchQuery();
    }

    /**
     * 获取主查询的query对象
     *
     * @param keyWord
     * @param spec
     * @return
     */
    private BoolQueryBuilder getKeyWordSearchQuery(String keyWord, String brandId, String
            categoryId, String spec) {
        BoolQueryBuilder boolQuery = getCommonSearchQuery();

        boolQuery.must(QueryBuilders.multiMatchQuery(keyWord, "ITEM_NAME", "ITEM_SHORTNAME",
                "SPEC_CONTENTS", "cmc_spu.SPU_NAME", "ITEM_DETAIL", "ITEM_DETAIL_NOTES")
                .boost(0F));

        if (StringUtil.isNotBlank(categoryId)) {
            boolQuery.must(QueryBuilders.termQuery("cmc_category.CATEGORY_ID", categoryId));
        }
        if (StringUtil.isNotBlank(brandId)) {
            boolQuery.must(QueryBuilders.termQuery("cmc_brand.BRAND_ID", brandId));
        }

        MultiMatchQueryBuilder itemNameMultiQuery = QueryBuilders.multiMatchQuery(keyWord,
                "ITEM_NAME", "ITEM_SHORTNAME").boost(100F);

        BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
        nestedBoolQuery.should(QueryBuilders.matchQuery
                ("SPEC_CONTENTS.ATTRIBUTE_NAME", keyWord));
        nestedBoolQuery.should(QueryBuilders.matchQuery("SPEC_CONTENTS.ATTRIBUTE_VALUE",
                keyWord));
        NestedQueryBuilder specContentsMatch = QueryBuilders.nestedQuery("SPEC_CONTENTS",
                nestedBoolQuery).boost(90F);

        //MatchQueryBuilder specContentsMatch = QueryBuilders.matchQuery("SPEC_CONTENTS", keyWord)
        //        .boost(90F);
        MatchQueryBuilder spuNameMatch = QueryBuilders.matchQuery("cmc_spu.SPU_NAME", keyWord)
                .boost(80F);
        MatchQueryBuilder categoryNameMatch = QueryBuilders.matchQuery("category.CATEGORY_NAME",
                keyWord)
                .boost(70F);

        MultiMatchQueryBuilder itemDetailMultiQuery = QueryBuilders.multiMatchQuery(keyWord,
                "ITEM_DETAIL", "ITEM_DETAIL_NOTES").boost(10F);


        boolQuery.should(itemNameMultiQuery);
        boolQuery.should(specContentsMatch);
        boolQuery.should(spuNameMatch);
        boolQuery.should(categoryNameMatch);
        boolQuery.should(itemDetailMultiQuery);


        return boolQuery;
    }

    /**
     * 获取公共的query对象
     *
     * @return
     */
    private BoolQueryBuilder getCommonSearchQuery() {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //是否上架
        boolQuery.must(QueryBuilders.termQuery("ITEM_STATUS", 40).boost(0F));
        boolQuery.must(QueryBuilders.termQuery("cmc_item_line.STATUS", 40).boost(0F));
        //是否展示
        boolQuery.must(QueryBuilders.matchQuery("cmc_item_line.DISPLAY_FLAG", "Y").boost(0F));
        //图片状态
        boolQuery.should(QueryBuilders.termQuery("aus_at_picture_list.PIC_STATUS", 40).boost(0F));

        return boolQuery;
    }


    /**
     * 获取公共的 SearchRequestBuilder
     *
     * @param orderBy
     * @param sortBy
     * @param pageNum
     * @param pageSize
     * @return
     */
    private SearchRequestBuilder getSearchRequestBuilder(String orderBy, String sortBy, int
            pageNum, int pageSize, float minScore) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(ES_INDEX_GMS);

        searchRequestBuilder.setTypes(ES_TYPE_ITEM_AGG);

        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        searchRequestBuilder.setMinScore(minScore);
        /** 需要显示的字段，逗号分隔（缺省为全部字段）**/


        //fields.add("aus_at_picture_list.PICTURE_ID");
        //fields.add("aus_at_picture_list.BILL_NO");
        //fields.add("aus_at_picture_list.BILL_TYPE");
        //fields.add("aus_at_picture_list.PIC_NAME");
        //fields.add("aus_at_picture_list.PIC_TYPE");
        //fields.add("aus_at_picture_list.FILE_PATH");
        //fields.add("aus_at_picture_list.PIC_SIZE");
        //fields.add("aus_at_picture_list.PIC_STORAGE_NAME");
        //fields.add("aus_at_picture_list.PIC_EX_FILEID");
        //fields.add("aus_at_picture_list.SMALL_SIZE_NAME");
        //fields.add("aus_at_picture_list.SMALL_SIZE_EX_FILE_ID");
        //fields.add("aus_at_picture_list.MIDDLE_SIZE_NAME");
        //fields.add("aus_at_picture_list.MIDDLE_SIZE_EX_FILE_ID");
        //fields.add("aus_at_picture_list.PIC_STATUS");

        searchRequestBuilder.setFetchSource(generalFieldArray, null);

        SortBuilder sortBuilder = getSortBuilder(orderBy, sortBy);


        if (sortBuilder != null) {
            searchRequestBuilder.addSort(sortBuilder);
        }
        // 分页应用
        searchRequestBuilder.setFrom(pageNum * pageSize).setSize(pageSize);
        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);

        return searchRequestBuilder;
    }

    /**
     * 添加 普通销售价格聚合查询条件
     *
     * @param searchRequestBuilder
     * @param pageSize
     * @return
     */
    private SearchRequestBuilder setAggSalePrice(SearchRequestBuilder searchRequestBuilder,
                                                 QueryBuilder queryBuilder, Integer
                                                         pageSize) {
        //默认情况下，search执行后，仅返回10条聚合结果 小于10条会出现和source 结果集不匹配的bug
        int aggsSize = 10;

        if (pageSize > 10) {
            aggsSize = pageSize;
        }
        AggregationBuilder
                aggregationBuilder = AggregationBuilders.terms("aggs_item_price_interval")
                .field("cmc_item_line.ITEM_ID").size(aggsSize);

        MaxBuilder maxPrice = AggregationBuilders.max("maxSalePrice1")
                .field("cmc_item_line.SALE_PRICE1");
        MinBuilder minPrice = AggregationBuilders.min("minSalePrice1")
                .field("cmc_item_line.SALE_PRICE1");

        FilterAggregationBuilder filterAggregationBuilder = AggregationBuilders.filter
                ("aggs_item_price_filter").filter(queryBuilder);
        filterAggregationBuilder.subAggregation(maxPrice).subAggregation(minPrice);

        aggregationBuilder.subAggregation(filterAggregationBuilder);
        searchRequestBuilder.addAggregation(aggregationBuilder);
        return searchRequestBuilder;
    }

    private BoolQueryBuilder setSpecFilterQuery(BoolQueryBuilder boolQuery, String spec) {
        if (StringUtil.isNotBlank(spec)) {


            HashMap<String, String> hashMap = null;

            try {
                hashMap = (HashMap<String, String>) JSON.parseObject(spec, new HashMap<String,
                        String>(1).getClass());
            } catch (Exception e) {
                logger.error("{}", e.getMessage(), e);
                hashMap = new HashMap<>(1);
            }
            hashMap.forEach((name, value) -> {
                BoolQueryBuilder nestedBoolQuery = QueryBuilders.boolQuery();
                nestedBoolQuery.must(QueryBuilders.termQuery
                        ("SPEC_CONTENTS.ATTRIBUTE_NAME", name));
                nestedBoolQuery.must(QueryBuilders.termQuery("SPEC_CONTENTS.ATTRIBUTE_VALUE",
                        value));
                boolQuery.must(QueryBuilders.nestedQuery("SPEC_CONTENTS", nestedBoolQuery));
            });
        }
        return boolQuery;
    }

    /**
     * 获取公用的 SortBuilder
     *
     * @param orderBy
     * @param sortBy
     * @return
     */
    private SortBuilder getSortBuilder(String orderBy, String
            sortBy) {
        SortBuilder sortBuilder = null;
        if (StringUtil.isNotBlank(orderBy)) {
            if (StringUtil.isBlank(sortBy)) {
                sortBy = ORDER_BY_ASC;
            }
            if (ORDER_FIELD_SALE_PRICE.equalsIgnoreCase(orderBy)) {
                orderBy = "cmc_item_line.SALE_PRICE1";

                if (ORDER_BY_ASC.equalsIgnoreCase(sortBy)) {
                    sortBuilder = SortBuilders.fieldSort(orderBy).order(SortOrder.ASC);

                } else if (ORDER_BY_DESC.equalsIgnoreCase(sortBy)) {
                    sortBuilder = SortBuilders.fieldSort(orderBy).order(SortOrder.DESC);
                }

            }
        }
        return sortBuilder;
    }

    /**
     * 基于三级类目id的分组查询
     *
     * @param queryBuilder
     * @return
     */
    private SearchResponse searchGroupByCategory(BoolQueryBuilder queryBuilder) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(ES_INDEX_GMS);

        searchRequestBuilder.setTypes(ES_TYPE_ITEM_AGG);
        Set<String> fields = new HashSet<>();
        fields.add("cmc_category.CATEGORY_ID");
        fields.add("cmc_category.CATEGORY_NAME");
        fields.add("cmc_category.CATEGORY_LEVEL");


        searchRequestBuilder.setFetchSource(fields.toArray(new String[fields.size()]), null);

        searchRequestBuilder.setFrom(0).setSize(10000);
        //searchRequestBuilder.setExplain(true);


        //AggregationBuilder
        //        aggregationBuilder = AggregationBuilders.terms("group_by_category")
        //        .field("CATEGORY_ID");


        searchRequestBuilder.setQuery(queryBuilder);
        logger.debug("groupByCategoryRequest : \n {}", searchRequestBuilder.toString());
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();
        logger.debug("groupByCategoryResponse : \n {}", searchResponse.toString());
        return searchResponse;
    }

    /**
     * 根据 groupByCategory 返回的信息组装面包屑数据
     *
     * @param searchResponse
     * @param categoryId
     * @return
     */
    private SearchCrumbsVO getSearchCrumbs(SearchResponse searchResponse, String categoryId, String
            type) {
        SearchCrumbsVO crumbs = new SearchCrumbsVO();
        List<SearchCategoryAggVO> categoryAggAllList = getCategoryAggAllList(searchResponse);
        categoryAggAllList = removeDuplicate(categoryAggAllList);

        if (categoryAggAllList.size() == 3 && StringUtil.isBlank(categoryId)) {
            //商品结果集定位到三级类目时 不是类目点击进来
            for (SearchCategoryAggVO categoryAggVO : categoryAggAllList) {
                if ("1".equals(categoryAggVO.getCategoryLevel())) {
                    crumbs.setLevelOne(categoryAggVO);
                } else if ("2".equals(categoryAggVO.getCategoryLevel())) {
                    crumbs.setLevelTwo(categoryAggVO);
                } else if ("3".equals(categoryAggVO.getCategoryLevel())) {
                    crumbs.setLevelThree(categoryAggVO);
                }
            }
        } else if (StringUtil.isNotBlank(categoryId)) {
            CategoryAggDTO fc = productCategoryDAO.findFullCategory(categoryId);
            //有类目Id 传入时  类目参与检索时的面包屑
            if (ObjectUtil.isNotEmpty(fc)) {
                crumbs.setLevelOne(new SearchCategoryAggVO(fc.getFid(), fc.getFname(),
                        fc.getFlevel()));
                crumbs.setLevelTwo(new SearchCategoryAggVO(fc.getSid(), fc.getSname(),
                        fc.getSlevel()));
                crumbs.setLevelThree(new SearchCategoryAggVO(fc.getTid(), fc.getTname(),
                        fc.getTlevel()));
            }
            if (categoryId.equals(fc.getFid())) {
                crumbs.setLevelTwo(null);
                crumbs.setLevelThree(null);
            } else if (categoryId.equals(fc.getSid())) {
                crumbs.setLevelThree(null);
            }
        }

        return crumbs;
    }

    private List<SearchCategoryAggVO> getCategoryAggAllList(SearchResponse searchResponse) {
        List<Map<String, Object>> sourceList = getSourceList(searchResponse);
        List<SearchCategoryAggVO> categoryAggAllList = new ArrayList<>();
        for (Map<String, Object> map : sourceList) {
            Object category = map.get("cmc_category");
            String categoryListJson = JSON.toJSONString(category);
            if (StringUtil.isNotBlank(categoryListJson)) {
                List<SearchCategoryAggVO> categoryAggList = JSON.parseArray(categoryListJson.trim(),
                        SearchCategoryAggVO.class);
                categoryAggAllList.addAll(categoryAggList);
            }
        }
        return categoryAggAllList;
    }

    private BoolQueryBuilder getCategoryIdSearchQuery(String categoryId, String spec) {
        BoolQueryBuilder boolQuery = getCommonSearchQuery();

        boolQuery.must(QueryBuilders.termQuery("cmc_category.CATEGORY_ID", categoryId));
        return boolQuery;
    }


    private BoolQueryBuilder getBrandIdSearchQuery(String brandId, String spec) {
        BoolQueryBuilder boolQuery = getCommonSearchQuery();

        boolQuery.must(QueryBuilders.termQuery("cmc_brand.BRAND_ID", brandId));
        return boolQuery;
    }

    private BoolQueryBuilder getBrandNameSearchQuery(String keyWord, String spec) {
        BoolQueryBuilder boolQuery = getCommonSearchQuery();

        boolQuery.must(QueryBuilders.matchQuery("cmc_brand.BRAND_NAME", keyWord));

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery();
        functionScoreQueryBuilder.add(QueryBuilders.matchQuery("cmc_brand.BRAND_NAME",
                keyWord).operator(MatchQueryBuilder.Operator.OR)
                , ScoreFunctionBuilders.weightFactorFunction(1000));
        return boolQuery;
    }

    /**
     * 根据 queryBuilder 取回所有item spec的信息
     *
     * @param queryBuilder
     * @return
     */
    private List<SearchSpecAggVO> searchItemSpecAggByQuery(BoolQueryBuilder queryBuilder) {
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch(ES_INDEX_GMS);

        searchRequestBuilder.setTypes(ES_TYPE_ITEM_AGG);

        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);
        searchRequestBuilder.setQuery(queryBuilder);
        searchRequestBuilder.setFetchSource("SPEC_CONTENTS", null);
        logger.debug("itemSpecAggSearchRequest \n {}", searchRequestBuilder);
        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        List<Map<String, Object>> sourceList = getSourceList(searchResponse);
        List<SearchItemSpecVO> itemSpecAllList = new ArrayList<>(sourceList.size());
        for (Map<String, Object> map : sourceList) {
            List<Map<String, Object>> specList = (List<Map<String, Object>>) map.get
                    ("SPEC_CONTENTS");
            String specListJson = JSON.toJSONString(specList);
            //尝试解析json结构
            try {
                List<SearchItemSpecVO> itemSpecList = JSON.parseArray(specListJson, SearchItemSpecVO
                        .class);
                itemSpecAllList.addAll(itemSpecList);
            } catch (Exception e) {
                continue;
            }
        }

        List<SearchSpecAggVO> specAggList = SearchSpecAggVO.transform(itemSpecAllList);
        logger.debug("specAggList \n{}", specAggList);
        //排序
        for (SearchSpecAggVO searchSpecAggVO : specAggList) {
            List<String> specValues = searchSpecAggVO.getSpecValues();
            Collections.sort(specValues, ComparatorInstance.STRING_HASH_COMPARATOR);
        }
        Collections.sort(specAggList);

        return specAggList;
    }

    private List<Map<String, Object>> getSourceList(SearchResponse searchResponse) {
        List<Map<String, Object>> sourceList = new ArrayList<>();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            sourceList.add(searchHit.getSource());
        }
        return sourceList;
    }

}
