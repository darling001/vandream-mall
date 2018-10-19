package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.dao.FavoritesDAO;
import com.vandream.mall.business.dao.ValueSetLineDAO;
import com.vandream.mall.business.dto.ValueSetLineDTO;
import com.vandream.mall.business.execption.FavoritesException;
import com.vandream.mall.business.service.FavoritesListByUserIdService;
import com.vandream.mall.business.service.UserIdLevelService;
import com.vandream.mall.business.vo.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetRequestBuilder;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 17:47
 */
@Service(value = "favoritesListByUserIdService")
public class FavoritesListByUserIdServiceImpl implements FavoritesListByUserIdService {
    private static final Logger logger = LoggerFactory.getLogger(FavoritesListByUserIdServiceImpl.class);
    /**获取商品单位结构集的常量**/
    private static final String UNIT_TYPE_NAME = "tlerp.ausbs.unitType";
    @Autowired
    private TransportClient transportClient;
    @Autowired
    private FavoritesDAO favoritesDao;
    @Autowired
    private UserIdLevelService userIdLevelService;
    @Resource
    private ValueSetLineDAO valueSetLineDAO;

    @Override
    public List<FavorityItemDataVO> getFavoritesListByUserId(String userId) throws
            FavoritesException {
        if (null == userId) {
            logger.debug("用户Id为空", userId);
            throw new FavoritesException(ResultStatusConstant.GET_COLLECTION_LIST_ERROR);
        }
        //获取商品单位结果集
        List<ValueSetLineDTO> unitTypeList = valueSetLineDAO.findListByValueSetHeadId(UNIT_TYPE_NAME);
        Map<String, String> unitTypeMap = new HashMap<>();
        for (ValueSetLineDTO valueSetLineDTO : unitTypeList) {
            unitTypeMap.put(valueSetLineDTO.getValueCode(), valueSetLineDTO.getValueName());
        }
        //以三级类目为唯一标识的收藏列表集合
        List<FavorityItemDataVO> favorityItemDataList = new ArrayList<>();
        //通过userId 查询出所有的itemId
        List<FavoritesVO> itemIdList = favoritesDao.selectSkuId(userId);
        String companyLevel = userIdLevelService.getUserIdLevelService(userId);
        if (StringUtil.isBlank(companyLevel)) {
            //没取到level显示普价
            companyLevel = "00";
        }
        //定义hashSet<String>
        Set<String> itemIdSet = new HashSet<>();
        Map<String, LinkedList<String>> itemMapLine = new HashMap<>(16);
        Map<String, FavoritesVO> itemLineMap = new HashMap<>(16);
        for (FavoritesVO favoritesEntity : itemIdList) {
            String itemId = favoritesEntity.getItemId();
            String itemLineId = favoritesEntity.getItemLineId();
            itemLineMap.put(itemLineId, favoritesEntity);
            itemIdSet.add(itemId);
            if (!itemMapLine.containsKey(itemId)) {
                itemMapLine.put(itemId, new LinkedList<>());
            }
            itemMapLine.get(itemId).add(itemLineId);
        }
        //通过es获取itemId的结果集
        MultiGetRequestBuilder multiGetRequestBuilder = transportClient.prepareMultiGet()
                .add("gms", "item_agg", itemIdSet);
        MultiGetResponse multiGetResponses = null;
        try {
            multiGetResponses = multiGetRequestBuilder.get();
        } catch (Exception e) {
            return favorityItemDataList;
        }
        //key:三级类目品类ID， Value:当前品类的品类信息，该品类下的商品信息
        Map<String, FavorityItemDataVO> categoryIdFavorityItemData = new HashMap<>(16);

        for (MultiGetItemResponse itemResponse : multiGetResponses) {
            // FavorityItemData favorityItemData = new FavorityItemData();
            GetResponse response = itemResponse.getResponse();
            if (response.isExists()) {
                Map<String, Object> map = response.getSource();
                String itemdDetailNotes = JSONUtil.toJson(map.get("cmc_category"));
                //itemCategroy获取完成
                List<FavorityItemCategoryVO> categoryList = JSONUtil.toList(itemdDetailNotes,
                        FavorityItemCategoryVO.class);
                String categoryId = categoryList.get(2).getId();
                //获取面包线
                if (!categoryIdFavorityItemData.containsKey(categoryId)) {
                    categoryIdFavorityItemData.put(categoryId, new FavorityItemDataVO());
                    categoryIdFavorityItemData.get(categoryId).addCategory(categoryList.get(0)
                            .getName());
                    categoryIdFavorityItemData.get(categoryId).addCategory(categoryList.get(1)
                            .getName());
                    categoryIdFavorityItemData.get(categoryId).addCategory(categoryList.get(2)
                            .getName());
                }
                //商品名称
                String itemName = (String) map.get("ITEM_NAME");
                //商品编码
                String itemCode = (String) map.get("ITEM_CODE");
                //单位
                String primaryUnitCode = "";
                String primaryUnitName = "";
                if (map.get("PRIMARY_UNIT_CODE") != null) {
                    primaryUnitCode = (String) map.get("PRIMARY_UNIT_CODE");
                    primaryUnitName = unitTypeMap.get(primaryUnitCode);
                }
                //商品属性
                List<Map<String, String>> specContentList = (List<Map<String, String>>) map.get
                        ("SPEC_CONTENTS");
                List<FavorityItemFieldVO> itemFieldList = new ArrayList<FavorityItemFieldVO>();
                if (ObjectUtil.isNotEmpty(specContentList)) {
                    for (int i = 0; i < specContentList.size(); i++) {
                        Map specContent = specContentList.get(i);
                        String priceFlag = specContent.get("PRICE_FLAG") != null ? specContent
                                .get("PRICE_FLAG").toString() : "";
                        String key = null;
                        if (null != specContent.get("ATTRIBUTE_NAME")) {
                            key = specContent.get("ATTRIBUTE_NAME").toString();
                        }
                        String value = null;
                        if (null != specContent.get("ATTRIBUTE_VALUE")) {
                            value = specContent.get("ATTRIBUTE_VALUE").toString();
                        }
                        FavorityItemFieldVO favorityItemFieldVO = new FavorityItemFieldVO();
                        if ("" != priceFlag && null != key && null != value) {
                            favorityItemFieldVO.setKey(key);
                            favorityItemFieldVO.setValue(value);
                            favorityItemFieldVO.setPriceFlag(priceFlag);
                            itemFieldList.add(favorityItemFieldVO);
                        }
                    }
                }
                List<String> itemLindIdList = itemMapLine.get(map.get("ITEM_ID"));
                if (itemLindIdList != null) {
                    for (String itemLineId : itemLindIdList) {
                        JSONArray itemLines = JSONObject.parseArray(JSONUtil.toJson(map.get
                                ("cmc_item_line")));
                        if (itemLines == null) {
                            break;
                        }
                        for (int i = 0; i < itemLines.size(); i++) {
                            JSONObject itemObj = itemLines.getJSONObject(i);
                            if (itemLineId.equals(itemObj.getString("ITEM_LINE_ID"))) {
                                Map<String, Double> typePriceMap = new HashMap<>(16);
                                //SALE_PRICE1是普价
                                typePriceMap.put("00", itemObj.getDouble("SALE_PRICE1"));
                                //高级会员价
                                typePriceMap.put("10", itemObj.getDouble("SALE_PRICE2"));
                                //VIP会员价
                                typePriceMap.put("20", itemObj.getDouble("SALE_PRICE3"));
                                String status = itemObj.getString("STATUS");
                                String itemId = itemObj.getString("ITEM_ID");
                                BigDecimal minOrderNum = itemObj.getBigDecimal("MIN_ORDER_NUM");
                                FavoritesVO favoritesEntity = itemLineMap.get(itemLineId);
                                FavorityItemVO favorityItemDTO = new FavorityItemVO();
                                favorityItemDTO.setId(favoritesEntity.getId());
                                favorityItemDTO.setItemLineId(itemLineId);
                                favorityItemDTO.setName(itemName);
                                favorityItemDTO.setItemId(itemId);
                                favorityItemDTO.setItemNum(itemCode);
                                favorityItemDTO.setMinOrderNum(minOrderNum);
                                favorityItemDTO.setItemStaus(status);
                                favorityItemDTO.setStatus(favoritesEntity.getStatus());
                                favorityItemDTO.setCount(favoritesEntity.getNumber());
                                favorityItemDTO.setPrice(typePriceMap.get("00"));
                                if (typePriceMap.get(companyLevel) == null || typePriceMap.get
                                        (companyLevel) <= 0) {
                                    favorityItemDTO.setMemberprice(-1);
                                } else {
                                    favorityItemDTO.setMemberprice(typePriceMap.get(companyLevel));
                                }
                                favorityItemDTO.setIsAdd(favoritesEntity.getIsAdd());
                                favorityItemDTO.setUnit(primaryUnitName);
                                favorityItemDTO.setFavorityItemFieldVOList(itemFieldList);
                                categoryIdFavorityItemData.get(categoryId).addItem(favorityItemDTO);
                            }
                        }
                    }
                }
            }
        }
        favorityItemDataList = new ArrayList(categoryIdFavorityItemData.values());
        return favorityItemDataList;
    }
}
