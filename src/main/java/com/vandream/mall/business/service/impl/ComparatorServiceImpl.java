package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.AccountDAO;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.service.ComparatorService;
import com.vandream.mall.business.vo.ComparatorItemVO;
import com.vandream.mall.business.vo.ComparatorSpecVO;
import com.vandream.mall.business.vo.ComparatorVO;
import com.vandream.mall.business.vo.search.SearchItemAggVO;
import com.vandream.mall.business.vo.search.SearchItemLineAggVO;
import com.vandream.mall.business.vo.search.SearchItemPictureVO;
import com.vandream.mall.business.vo.search.SearchItemSpecVO;
import com.vandream.mall.commons.constant.ElasticsearchConstant;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import javax.annotation.Resource;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("comparatorService")
public class ComparatorServiceImpl implements ComparatorService {

    private static final String ITEM_ID_SPEC_MAP_KEY = "itemId";
    private static final String VALUE_SPEC_MAP_KEY = "value";

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
        generalFields.add("SPU_ID");

        generalFields.add("cmc_item_line.ITEM_LINE_ID");
        generalFields.add("cmc_item_line.SALE_PRICE1");
        generalFields.add("cmc_item_line.SALE_PRICE2");
        generalFields.add("cmc_item_line.SALE_PRICE3");
        generalFields.add("cmc_item_line.SUPPLIER_ID");
        generalFields.add("cmc_item_line.AREA_NAME");
        generalFields.add("cmc_item_line.AREA_CODE");
        generalFields.add("cmc_item_line.MIN_ORDER_NUM");

        generalFields.add("aus_at_picture_list.PIC_EX_FILEID");

        generalFields.add("aus_at_picture_list.PIC_EX_FILEID");

        generalFieldArray = generalFields.toArray(new String[generalFields.size()]);
    }

    @Resource
    private TransportClient transportClient;
    @Resource
    private AccountDAO accountDAO;


    @Override
    public List<SearchItemAggVO> findSpuItemList(String spuId) throws InvocationTargetException {
        if (StringUtil.isBlank(spuId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch
                (ElasticsearchConstant.ES_INDEX_GMS);

        searchRequestBuilder.setTypes(ElasticsearchConstant.ES_TYPE_ITEM_AGG);
        searchRequestBuilder.setExplain(true);
        //设置返回条数限制
        searchRequestBuilder.setFrom(0).setSize(49);

        Set<String> fields = new HashSet<>();
        fields.add("cmc_item_line.STATUS");
        fields.add("cmc_item_line.DISPLAY_FLAG");

        fields.add("ITEM_ID");
        fields.add("ITEM_NAME");
        fields.add("ITEM_SHORTNAME");
        fields.add("SPU_ID");

        fields.add("aus_at_picture_list.PIC_EX_FILEID");

        searchRequestBuilder.setFetchSource(fields.toArray(new String[fields.size()]), null);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //是否上架
        boolQuery.must(QueryBuilders.termQuery("ITEM_STATUS", 40).boost(0F));
        boolQuery.must(QueryBuilders.termQuery("cmc_item_line.STATUS", 40).boost(0F));
        //是否展示
        boolQuery.must(QueryBuilders.matchQuery("cmc_item_line.DISPLAY_FLAG", "Y").boost(0F));
        boolQuery.should(QueryBuilders.termQuery("aus_at_picture_list.PIC_STATUS", 40).boost(0F));

        boolQuery.must(QueryBuilders.termQuery("SPU_ID", spuId));
        SearchResponse searchResponse = searchRequestBuilder.setQuery(boolQuery).execute()
                .actionGet();
        List<Map<String, Object>> sourceList = new ArrayList<>();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            sourceList.add(searchHit.getSource());
        }
        String listJson = JSON.toJSONString(sourceList);
        List<SearchItemAggVO> searchItemAggVOS = JSON.parseArray(listJson, SearchItemAggVO.class);
        for (SearchItemAggVO itemAggVO : searchItemAggVOS) {
            //设置图片url
            List<SearchItemPictureVO> pictureList = itemAggVO.getPictureList();
            if (ObjectUtil.isNotEmpty(pictureList)) {
                String pictureUrl = pictureList.get(0).getPicExFileId();
                itemAggVO.setPictureUrl(pictureUrl);
                itemAggVO.setPictureList(null);
            }
        }

        return searchItemAggVOS;
    }

    @Override
    public ComparatorVO findListItemInfo(Object itemIdList,String userId) throws
            InvocationTargetException {
        if (ObjectUtil.isEmpty(itemIdList)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        ComparatorVO result = new ComparatorVO();
        String idListJson = JSON.toJSONString(itemIdList);
        List<String> idList = JSON.parseArray(idListJson, String.class);

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

        SearchRequestBuilder searchRequestBuilder = transportClient.prepareSearch
                (ElasticsearchConstant.ES_INDEX_GMS);

        searchRequestBuilder.setTypes(ElasticsearchConstant.ES_TYPE_ITEM_AGG);
        searchRequestBuilder.setExplain(false);
        searchRequestBuilder.setFrom(0);

        searchRequestBuilder.setFetchSource(generalFieldArray, null);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        //是否上架
        boolQuery.must(QueryBuilders.termQuery("ITEM_STATUS", 40).boost(0F));
        boolQuery.must(QueryBuilders.termQuery("cmc_item_line.STATUS", 40).boost(0F));
        //是否展示
        boolQuery.must(QueryBuilders.matchQuery("cmc_item_line.DISPLAY_FLAG", "Y").boost(0F));
        boolQuery.should(QueryBuilders.termQuery("aus_at_picture_list.PIC_STATUS", 40).boost(0F));
        //图片类型 商品图片
        boolQuery.should(QueryBuilders.termQuery("aus_at_picture_list.BILL_TYPE",
                BusinessType.CMC_ITEM).boost(0F));

        boolQuery.must(QueryBuilders.termsQuery("ITEM_ID", idList.toArray(new String[idList.size
                ()])));
        SearchResponse searchResponse = searchRequestBuilder.setQuery(boolQuery).execute()
                .actionGet();
        List<Map<String, Object>> sourceList = new ArrayList<>();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            sourceList.add(searchHit.getSource());
        }


        String itemListJson = JSON.toJSONString(sourceList);
        List<ComparatorItemVO> itemList = JSON.parseArray(itemListJson, ComparatorItemVO
                .class);
        if (ObjectUtil.isNotEmpty(itemList)) {
            processSettingItemPriceAndMiniOrderNum(itemList);
        }

        Map<String, ComparatorSpecVO> itemSpecVOMap = new HashMap<>(itemList.size());
        for (ComparatorItemVO itemAggVO : itemList) {

            //设置图片url
            List<SearchItemPictureVO> pictureList = itemAggVO.getPictureList();
            if (ObjectUtil.isNotEmpty(pictureList)) {
                String pictureUrl = pictureList.get(0).getPicExFileId();
                itemAggVO.setPictureUrl(pictureUrl);
                itemAggVO.setPictureList(null);
            }

            List<SearchItemLineAggVO> itemLine = itemAggVO.getItemLine();
            itemAggVO.setItemLine(null);
            StringBuffer areaName = new StringBuffer();
            for (SearchItemLineAggVO searchItemLineAggVO : itemLine) {
                areaName.append(searchItemLineAggVO.getAreaName());
                areaName.append(",");
            }
            if (ObjectUtil.isNotEmpty(areaName)) {
                itemAggVO.setSaleArea(areaName.substring(0, areaName.length() - 1));
            }

            //封装item技术参数
            String specListJson = itemAggVO.getSpecListJson();

            if (StringUtil.isNotBlank(specListJson)) {
                List<SearchItemSpecVO> searchItemSpecList = JSON.parseArray(specListJson,
                        SearchItemSpecVO.class);
                for (SearchItemSpecVO searchItemSpecVO : searchItemSpecList) {
                    ComparatorSpecVO comparatorSpecVO = itemSpecVOMap.get(searchItemSpecVO
                            .getName());
                    if (ObjectUtil.isEmpty(comparatorSpecVO)) {
                        comparatorSpecVO = new ComparatorSpecVO(searchItemSpecVO.getName());
                    }
                    List<Map<String, String>> specValues = comparatorSpecVO.getSpecMap();
                    if (ObjectUtil.isEmpty(specValues)) {
                        specValues = new ArrayList<>();
                    }
                    Map<String, String> specMap = new HashMap<>(2);
                    specMap.put(ITEM_ID_SPEC_MAP_KEY, itemAggVO.getItemId());
                    specMap.put(VALUE_SPEC_MAP_KEY, searchItemSpecVO.getValue());

                    //封装收敛
                    specValues.add(specMap);
                    comparatorSpecVO.setSpecMap(specValues);
                    itemSpecVOMap.put(searchItemSpecVO.getName(), comparatorSpecVO);

                }
            }
            itemAggVO.setSpecListJson(null);
        }
        List<ComparatorSpecVO> specList = new ArrayList<>(itemSpecVOMap.values());
        for (ComparatorSpecVO comparatorSpecVO : specList) {
            List<Map<String, String>> specValues = comparatorSpecVO.getSpecMap();
            if (ObjectUtil.isNotEmpty(specValues)) {
                Set<String> specValueSet = new HashSet<>();
                for (Map<String, String> specMap : specValues) {
                    String specValue = specMap.get(VALUE_SPEC_MAP_KEY);
                    if (ObjectUtil.isNotEmpty(specValueSet)) {
                        if (!specValueSet.contains(specValue)) {
                            comparatorSpecVO.setIdentical(false);
                        }
                    }
                    specValueSet.add(specValue);
                }
            }
        }
        result.setItemList(itemList);
        result.setSpecList(specList);
        return result;
    }

    private List<ComparatorItemVO> processSettingItemPriceAndMiniOrderNum(List<ComparatorItemVO>
                                                                                  itemVOList) {
        // -1 代表议价
        BigDecimal negativeOne = new BigDecimal("-1").setScale(2, RoundingMode.HALF_UP);

        for (ComparatorItemVO itemAggVO : itemVOList) {
            List<SearchItemLineAggVO> itemLineList = itemAggVO.getItemLine();
            if (ObjectUtil.isNotEmpty(itemLineList)) {
                //商品普价列表
                List<BigDecimal> itemPriceList = new ArrayList<>(itemLineList.size());
                List<BigDecimal> miniOrderNumList = new ArrayList<>(itemLineList.size());
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
                    if (ObjectUtil.isNotEmpty(itemLine.getMiniOrderNum())) {
                        miniOrderNumList.add(itemLine.getMiniOrderNum());
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
                if (ObjectUtil.isNotEmpty(miniOrderNumList)) {
                    itemAggVO.setMinOrderNum(Collections.min(miniOrderNumList));
                    itemAggVO.setMaxOrderNum(Collections.max(miniOrderNumList));
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
        return itemVOList;
    }
}
