package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.UserLevelDAO;
import com.vandream.mall.business.dao.ItemDAO;
import com.vandream.mall.business.dto.item.*;
import com.vandream.mall.business.dto.mallCart.GoodsParams;
import com.vandream.mall.business.execption.ItemDetailException;
import com.vandream.mall.business.service.AreaService;
import com.vandream.mall.business.service.ElasticsearchService;
import com.vandream.mall.business.service.ItemDetailService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liuyuhong
 * @date 2018/3/6
 * @time 13:29
 * @description
 */
@Service(value = "itemDetailService")
public class ItemDetailServiceImpl implements ItemDetailService {

    private static final Logger logger = LoggerFactory.getLogger(ItemDetailServiceImpl.class);

    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserLevelDAO userLevelDAO;

    @Autowired
    private AreaService areaService;

    @Resource
    private ValueSetService valueSetService;

    @Resource
    private ItemDAO itemDAO;

    /**
     * @param userId
     * @param areaCode
     * @param itemId
     * @return
     */
    @Override
    public ItemInfoDTO getItemBaseInfo(String userId, String itemId, String areaCode) throws ItemDetailException {

        if (StringUtil.isBlank(itemId)) {
            logger.info("itemId=" + itemId);
            throw new ItemDetailException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        ItemInfoDTO itemInfoDTO = new ItemInfoDTO();

        /* 存入面包屑 */
        CrumbsDTO crumbsDTO = new CrumbsDTO();

        /* 商品信息 */
        ItemDTO itemDTO = new ItemDTO();

        /* 用户会员级别 */
        String customerLevel = userLevelDAO.getUserLevel(userId);

        /* 存放供货区域 */
        List<AreaDTO> areaDtoList = new ArrayList<>();

        /* 存入当前商品的最小起订量 */
        BigDecimal minOrderNum = null;

        /* 销售价格 */
        BigDecimal currentPrice = null;
        /* 会员价格 */
        BigDecimal memberPrice = null;

        /* 存储当前供货区域与ItemLineId对应关系 */
        Map<String, Set<String>> currentAreaCodeLineIdMap = new HashMap<>();

        /* 商品属性(GoodsParams)与ItemLineId对应关系 */
        Map<GoodsParams, Set<String>> goodsParamsItemLineIdMap = new HashMap<>();

        /* ItemId与GoodsParams Name的关系 */
        Map<String, Set<String>> itemIdGoodsParamsNameMap = new HashMap<>();

        /* ItemId与ItemCode的关系 */
        Map<String, String> itemIdItemCodeMap = new HashMap<>();

        /* ItemId与ItemLineId的关系 */
        Map<String, Set<String>> itemIdItemLineIdsMap = new HashMap<>();

        /* 查询当前item对应的SPU */
        String spuId = null;
        Map<String, Object> spuMap = elasticsearchService.searchDataById("gms", "item_agg", itemId, null);
        if (ObjectUtil.isEmpty(spuMap)) {
            logger.info("itemId=" + itemId);
            throw new ItemDetailException(ResultStatusConstant.ITEM_ID_NON_EXISTENT);
        }
        spuId = (String) spuMap.get("SPU_ID");
        /* 设置当前item默认供货区域 */
        if (StringUtil.isBlank(areaCode)) {
            List<Map<String, Object>> itemLineMapList = (List) spuMap.get("cmc_item_line");
            int defaultAreaCodeIndex = 0;
            if (ObjectUtil.isEmpty(itemLineMapList)) {
                throw new ItemDetailException(ResultStatusConstant.ITEM_LINE_NON_EXISTENT);
            }
            for (int i = 0; i < itemLineMapList.size(); i++) {
                if ("3301".equals(itemLineMapList.get(i).get("AREA_CODE"))) {
                    defaultAreaCodeIndex = i;
                    break;
                }
            }
            areaCode = (String) itemLineMapList.get(defaultAreaCodeIndex).get("AREA_CODE");
        }

        /* 查询spu下满足item和item_line都是已上架状态下的item集合 */
        String matchStr = "SPU_ID=" + spuId + ",ITEM_STATUS=40";
        List<Map<String, Object>> itemMapList = elasticsearchService.searchListData("gms", "item_agg", 1000, null, matchStr);

        /* 区域模板编码与名称映射关系 */
        Map<String, AreaDTO> areaRangeMap = areaService.getAreaRangeMap();

        /* 商品计量单位中英文映射关系 */
        Map<String, String> itemUnitTypeMap = valueSetService.getItemUnitTypeNameMap();

        /* 存储图片列表 */
        List<ImageDTO> imageList = new ArrayList<>();

        /* 存储是计价参数的规格参数 */
        List<GoodsParams> currentItemGoodsParams = new ArrayList<>();

        /* 存储品牌id对应的提示 */
        List<String> tipList = new ArrayList<>();

        /** 处理Item相应的商品属性 **/
        for (Map itemMap : itemMapList) {
            /** 获取面包屑类目 */
            if (itemMap.containsKey("cmc_category")) {
                List<Map> categoryMapList = (List<Map>) itemMap.get("cmc_category");
                if (ObjectUtil.isNotEmpty(categoryMapList)) {
                    for (Map map : categoryMapList) {
                        HashMap<String, String> categoryMap = new HashMap<>();
                        Object categoryId = map.get("CATEGORY_ID");
                        Object categoryName = map.get("CATEGORY_NAME");
                        Object categoryLevel = map.get("CATEGORY_LEVEL");
                        int levelInt = 0;
                        if (ObjectUtil.isNotEmpty(categoryLevel)) {
                            levelInt = Integer.parseInt(categoryLevel.toString());
                        }
                        categoryMap.put("categoryId", (categoryId == null) ? null : categoryId.toString());
                        categoryMap.put("categoryName", (categoryName == null) ? null : categoryName.toString());
                        if (1 == levelInt) {
                            crumbsDTO.setLevelOne(categoryMap);
                        }
                        if (2 == levelInt) {
                            crumbsDTO.setLevelTwo(categoryMap);
                        }
                        if (3 == levelInt) {
                            crumbsDTO.setLevelThree(categoryMap);
                        }
                    }
                } else {
                    logger.info("categoryMapList=" + categoryMapList);
                }
            }
            /* 当前的商品id */
            String currentItemId = itemMap.get("ITEM_ID") == null ? null : itemMap.get("ITEM_ID").toString();

            /* 组装商品基本属性 */
            if (itemId.equals(currentItemId)) {
                /** 商品名称 */
                itemDTO.setName(itemMap.get("ITEM_NAME") == null ? null : itemMap.get("ITEM_NAME").toString());
                /* 商品标题 */
                itemDTO.setTitle(itemMap.get("ITEM_SHORTNAME") == null ? null : itemMap.get("ITEM_SHORTNAME").toString());
                /* 优先显示商品标题 */
                String itemShortName = String.valueOf(itemMap.get("ITEM_SHORTNAME"));
                if (StringUtil.isNotBlank(itemShortName)) {
                    itemDTO.setName(itemShortName);
                }
                /* 设置商品编码 */
                itemDTO.setItemNum(itemMap.get("ITEM_CODE") == null ? null : itemMap.get("ITEM_CODE").toString());
                /* 设置当前itemId */
                itemDTO.setItemId(currentItemId);
                /* 存储商品图片列表 */
                String pictureType = "cmcItem";
                List<Map<String, Object>> tempImageList = (List) itemMap.get("aus_at_picture_list");
                if (itemMap.containsKey("aus_at_picture_list")) {
                    getPictureList(pictureType, tempImageList, imageList);
                }
                Collections.sort(imageList, Comparator.comparing(ImageDTO::getPicOrder));
                itemDTO.setImageList(imageList);
                /* 商品的计量单位 */
                String primaryUnitCode = itemMap.get("PRIMARY_UNIT_CODE") == null ? null : itemMap.get("PRIMARY_UNIT_CODE").toString();
                itemDTO.setUnit(itemUnitTypeMap.get(primaryUnitCode));
                /* 商品品牌 */
                Map<String, Object> cmcBrandMap = (HashMap<String, Object>) itemMap.get("cmc_brand");
                String brandId = null;
                if (ObjectUtil.isNotEmpty(cmcBrandMap)) {
                    /* 获取商品品牌id */
                    brandId = (String) cmcBrandMap.get("BRAND_ID");
                }
                /* 查询品牌对应的提示*/
                if (StringUtil.isNotBlank(brandId)) {
                    tipList = itemDAO.findTipList(brandId);
                }
                itemDTO.setTipList(tipList);
            }

            /* 是否为标品 */
            itemDTO.setStandardFlag("Y");
            if (itemMap.containsKey("STANDARD_FLAG")) {
                Object standardFlag = itemMap.get("STANDARD_FLAG");
                itemDTO.setStandardFlag((standardFlag != null) ? standardFlag.toString() : "N");
            }

            /* 存放当前Item的cmcItemLine中itemId集合 */
            Set<String> itemLineIdSet = new HashSet<>();
            if (itemMap.containsKey("cmc_item_line")) {
                /* 获取当前item下itemLine集合 */
                Object itemLineObj = itemMap.get("cmc_item_line");
                if (ObjectUtil.isNotEmpty(itemLineObj)) {
                    List<ItemLineDTO> itemLineList = JSONUtil.toList(JSONUtil.toJson(itemLineObj), ItemLineDTO.class);
                    for (ItemLineDTO itemLineDto : itemLineList) {
                        String itemLineStatus = itemLineDto.getStatus();
                        /* 商品itemLine状态为40（已上架）*/
                        if ("40".equals(itemLineStatus)) {
                            itemLineIdSet.add(itemLineDto.getItemId());
                            String currentAreaCode = itemLineDto.getAreaCode();
                            /* 当前item下每个cmcItemLine中itemId */
                            Set<String> itemLineIds = new HashSet<>();
                            itemLineIds.add(itemLineDto.getItemId());
                            /* 是否包含当前区域 */
                            if (currentAreaCodeLineIdMap.containsKey(currentAreaCode)) {
                                currentAreaCodeLineIdMap.get(currentAreaCode).addAll(itemLineIds);
                            } else {
                                currentAreaCodeLineIdMap.put(currentAreaCode, itemLineIds);
                            }
                            AreaDTO areaDTO = new AreaDTO();
                            /* 入参区域和当前item中itemLine的区域相同，则设置默认 */
                            if (areaCode.equals(currentAreaCode)) {
                                areaDTO.setDefault(true);
                            }
                            if (itemId.equals(itemLineDto.getItemId()) && areaCode.equals(currentAreaCode)) {
                                /* 最小起订量 */
                                minOrderNum = itemLineDto.getMinOrderNum();
                                /* 获取当前用户价格和对应的会员价格 */
                                currentPrice = itemLineDto.getSalePrice1();
                                memberPrice = getMemberPrice(userId, itemLineDto);
                            }
                            /* 处理供货区域 */
                            /* 设置供应商区域编码和模板名称 */
                            areaDTO.setAreaCode(currentAreaCode);
                            AreaDTO area = areaRangeMap.get(currentAreaCode);
                            if (ObjectUtil.isEmpty(area)) {
                                //获取区域模板对象异常
                                throw new ItemDetailException(ResultStatusConstant.GET_AREA_RANGE_NAME_ERROR);
                            }
                            if ("40".equals(area.getAreaStatus())) {
                                areaDTO.setAreaName(area.getAreaRangeName());
                            }
                            /* 设置供应商id */
                            areaDTO.addItemId(itemLineDto.getItemId());
                            /* 区域列表中去重处理 */
                            if (StringUtil.isNotBlank(currentAreaCode)) {
                                if (areaDtoList.contains(areaDTO)) {
                                    int i = areaDtoList.indexOf(areaDTO);
                                    areaDtoList.get(i).addItemId(itemLineDto.getItemId());
                                } else {
                                    areaDtoList.add(areaDTO);
                                }
                            }
                        }
                    }
                }
            }
            List<GoodsParams> goodParamsList = new ArrayList<>();
            if (itemMap.containsKey("SPEC_CONTENTS")) {
                /* 获取所有规格参数(包含计价和技术参数) */
                Object specObject = itemMap.get("SPEC_CONTENTS");
                if (ObjectUtil.isNotEmpty(specObject)) {
                    List<GoodsParams> cGoodsParamsList = JSONUtil.toList(JSON.toJSONString(specObject), GoodsParams.class);
                    /* 处理数据中缺失规格参数值的情况 */
                    for (GoodsParams goodsParams : cGoodsParamsList) {
                        if (StringUtil.isBlank(goodsParams.getAttributeValue())) {
                            goodsParams.setAttributeValue("无（" + itemMap.get("ITEM_CODE").toString() + "）");
                        }
                        if (!goodParamsList.contains(goodsParams)) {
                            goodParamsList.add(goodsParams);
                        }
                    }
                    for (GoodsParams goodsParams : goodParamsList) {
                        /* 存储是计价参数的规格参数 */
                        if (("Y").equals(goodsParams.getPriceFlag())) {
                            if (!goodsParamsItemLineIdMap.containsKey(goodsParams)) {
                                goodsParamsItemLineIdMap.put(goodsParams, new HashSet<>());
                            }
                            List<GoodsParams> cuGoodsParamsList = new ArrayList<>(goodsParamsItemLineIdMap.keySet());
                            GoodsParams currentGoodsParams = cuGoodsParamsList.get(cuGoodsParamsList.indexOf(goodsParams));
                            currentItemGoodsParams.add(currentGoodsParams);
                            if (itemId.equals(currentItemId)) {
                                /** 设置计价参数为选中的 */
                                currentGoodsParams.setSelected(Boolean.TRUE);
                            }
                            goodsParamsItemLineIdMap.get(goodsParams).addAll(itemLineIdSet);
                            currentGoodsParams.addItemId(currentItemId);
                            if (!itemIdGoodsParamsNameMap.containsKey(currentItemId)) {
                                itemIdGoodsParamsNameMap.put(currentItemId, new HashSet<>());
                            }
                            itemIdGoodsParamsNameMap.get(currentItemId).add(goodsParams.getAttributeName());
                            itemIdItemCodeMap.put(currentItemId, itemMap.get("ITEM_CODE").toString());
                            if (!itemIdItemLineIdsMap.containsKey(currentItemId)) {
                                itemIdItemLineIdsMap.put(currentItemId, itemLineIdSet);
                            } else {
                                itemIdItemLineIdsMap.get(currentItemId).addAll(itemLineIdSet);
                            }
                        }
                    }
                }
            }
        }

        /*** 处理数据中同时缺失属性名和值的情况 ***/
        for (Map.Entry<String, Set<String>> e : itemIdGoodsParamsNameMap.entrySet()) {
            Set<GoodsParams> goodsParamsSet = new HashSet<>(goodsParamsItemLineIdMap.keySet());
            for (GoodsParams gp : goodsParamsSet) {
                if (!e.getValue().contains(gp.getAttributeName())) {
                    GoodsParams goodsParams = new GoodsParams();
                    goodsParams.setAttributeName(gp.getAttributeName());
                    goodsParams.setAttributeValue("无（" + itemIdItemCodeMap.get(e.getKey()) + "）");
                    goodsParams.setPriceFlag(gp.getPriceFlag());
                    goodsParams.addItemId(e.getKey());
                    if (itemId.equals(e.getKey())) {
                        goodsParams.setSelected(true);
                    }
                    if (!goodsParamsItemLineIdMap.containsKey(goodsParams)) {
                        goodsParamsItemLineIdMap.put(goodsParams, new HashSet<>());
                    }
                    goodsParamsItemLineIdMap.get(goodsParams).addAll(itemIdItemLineIdsMap.get(e.getKey()));
                    currentItemGoodsParams.add(goodsParams);
                }
            }
        }

        /** 求供应商交集 */
        /**
         * 取出前当供应商的ItemLineIds
         **/
        Set<String> itemLineIdsSet = currentAreaCodeLineIdMap.get(areaCode);
        /* 存储计价参数对应的itemId */
        Set<String> currentItemGoodsParamsItems = new HashSet<>();
        for (GoodsParams goodsParams : currentItemGoodsParams) {
            currentItemGoodsParamsItems.addAll(goodsParams.getItemIds());
        }
        for (Map.Entry<GoodsParams, Set<String>> entry : goodsParamsItemLineIdMap.entrySet()) {
            /* 存储每个计价参数对应的itemLineId */
            Set<String> itemLineIds = new HashSet<>();
            /* 存储每个计价参数对应的itemId */
            Set<String> itemIds = new HashSet<>();
            itemLineIds.addAll(entry.getValue());
            itemIds.addAll(currentItemGoodsParamsItems);
            if (null != itemLineIdsSet) {
                itemLineIds.retainAll(itemLineIdsSet);
            }
            int itemLineIdsSize = itemLineIds.size();
            itemIds.retainAll(entry.getKey().getItemIds());
            int itemIdsSize = itemIds.size();
            if (itemLineIdsSize > 0 && itemIdsSize > 0) {
                /* 设置计价参数可选 */
                entry.getKey().setEnable(true);
            }
        }

        List<ItemAttributeDTO> itemAttributeDTOList = new ArrayList<>();
        for (Map.Entry<GoodsParams, Set<String>> entry : goodsParamsItemLineIdMap.entrySet()) {
            GoodsParams goodsParams = entry.getKey();
            /** 组装商品定价属性对象 */
            ItemAttributeValueDTO itemAttributeValueDTO = new ItemAttributeValueDTO();
            itemAttributeValueDTO.setName(goodsParams.getAttributeValue());
            itemAttributeValueDTO.setSelected(goodsParams.isSelected());
            itemAttributeValueDTO.setValues(goodsParams.getItemIds());
            itemAttributeValueDTO.setEnable(goodsParams.isEnable());

            ItemAttributeDTO itemAttributeDTO = new ItemAttributeDTO();
            itemAttributeDTO.setName(entry.getKey().getAttributeName());
            itemAttributeDTO.setOrderSort(entry.getKey().getOrderSort());

            itemAttributeDTO.addValues(itemAttributeValueDTO);

            int index = -1;
            boolean flag = false;
            for (ItemAttributeDTO itemAttribute : itemAttributeDTOList) {
                if (itemAttribute.getName().equals(goodsParams.getAttributeName())) {
                    flag = true;
                }
                index += 1;
                if (flag) {
                    break;
                }
            }
            if (flag) {
                itemAttributeDTOList.get(index).addValues(itemAttributeValueDTO);
            } else {
                itemAttributeDTOList.add(itemAttributeDTO);
            }
        }
        itemDTO.setArea(areaDtoList);
        if (null == currentPrice || BigDecimal.ZERO.compareTo(currentPrice) == 0) {
            currentPrice = new BigDecimal(-1);
        }

        /* 计价参数名称排序 */
        if (ObjectUtil.isNotEmpty(itemAttributeDTOList)) {
            Collections.sort(itemAttributeDTOList);
        }
        /* 计价参数值排序 */
        for (ItemAttributeDTO itemAttributeDTO : itemAttributeDTOList) {
            List<ItemAttributeValueDTO> itemAttributeValues = itemAttributeDTO.getValues();
            Collections.sort(itemAttributeValues);
        }
        itemDTO.setPrice(currentPrice);
        itemDTO.setMemberPrice(memberPrice);
        itemDTO.setMinQuantity(minOrderNum);
        itemDTO.setAttributes(itemAttributeDTOList);
        itemInfoDTO.setCustomerLevel(customerLevel);
        itemInfoDTO.setItemInfo(itemDTO);
        itemInfoDTO.setCrumbs(crumbsDTO);
        return itemInfoDTO;
    }

    /**
     * 获取图片列表
     */
    private void getPictureList(String pictureType, List<Map<String, Object>> tempImageList, List<ImageDTO> imageList) {
        if (ObjectUtil.isNotEmpty(tempImageList)) {
            for (Map imageMap : tempImageList) {
                ImageDTO imageDTO = new ImageDTO();
                Object pic = imageMap.get("PIC_EX_FILEID");
                Object bType = imageMap.get("BILL_TYPE");
                /* 图片状态 40：有效状态 */
                Object picStatus = imageMap.get("PIC_STATUS");
                Object pictureOrder = imageMap.get("PIC_ORDER");
                Integer picOrder = Double.valueOf(String.valueOf(pictureOrder)).intValue();
                if (ObjectUtil.isNotEmpty(pic) && ObjectUtil.isNotEmpty(bType)) {
                    String picExFileid = pic.toString();
                    String billType = bType.toString();
                    if ("40".equals(picStatus) && pictureType.equals(billType)) {
                        imageDTO.setPicExFileid(picExFileid);
                        imageDTO.setPicOrder(picOrder);
                        imageDTO.setBillType(billType);
                        imageList.add(imageDTO);
                    }
                }
            }
        }
    }

    /**
     * 获取会员价格
     *
     * @param userId
     * @param itemLineDto
     * @return
     */
    private BigDecimal getMemberPrice(String userId, ItemLineDTO itemLineDto) {
        BigDecimal currentPrice;
        if (StringUtil.isBlank(userId)) {
            return null;
        }
        String level = userLevelDAO.getUserLevel(userId);
        if (StringUtil.isBlank(level)) {
            level = "00";
        }
        switch (level) {
            case "00":
                currentPrice = itemLineDto.getSalePrice1();
                break;
            case "10":
                /** 高级会员售价 */
                currentPrice = itemLineDto.getSalePrice2();
                break;
            case "20":
                /** VIP会员售价 */
                currentPrice = itemLineDto.getSalePrice3();
                break;
            default:
                currentPrice = itemLineDto.getSalePrice1();
                break;
        }
        if (null == currentPrice || BigDecimal.ZERO.compareTo(currentPrice) == 0) {
            return new BigDecimal(-1);
        }
        return currentPrice;
    }

    /**
     * 获取商品技术参数
     *
     * @param itemId
     * @return
     */
    @Override
    public List getSkuAttributeList(String itemId) throws ItemDetailException {
        Map<String, Object> itemMap = getItemMapById(itemId);
        List goodsParamsList = new ArrayList<>();
        String itemSkuAttrStr = "无商品技术参数";
        try {
            if (itemMap.containsKey("SPEC_CONTENTS") && itemMap.containsKey("ITEM_ID")) {
                /* 取出当前ITEM对应的商品属性定义 */
                Object itemSkuAttributeObj = itemMap.get("SPEC_CONTENTS");
                Object itemIdObj = itemMap.get("ITEM_ID");
                if (ObjectUtil.isNotEmpty(itemIdObj)) {
                    String itemSkuAttributeStr = (itemSkuAttributeObj != null) ? JSON.toJSONString(itemSkuAttributeObj) : itemSkuAttrStr;
                    String itemIdStr = itemIdObj.toString();
                    if (itemSkuAttrStr.equals(itemSkuAttributeStr)) {
                        /* 无商品技术参数 */
                        goodsParamsList.add(itemSkuAttrStr);
                    } else {
                        if (StringUtil.isNotBlank(itemSkuAttributeStr)) {
                            List<GoodsParams> tempGoodParamsList = JSONUtil.toList(itemSkuAttributeStr, GoodsParams.class);
                            /* 取出所有商品的技术参数属性 */
                            for (GoodsParams goodsParams : tempGoodParamsList) {
                                /* 获取商品的技术参数 */
                                if (("N").equals(goodsParams.getPriceFlag())) {
                                    goodsParams.addItemId(itemIdStr);
                                    goodsParamsList.add(goodsParams);
                                }
                            }
                            /* 对技术参数排序 */
                            if (ObjectUtil.isNotEmpty(goodsParamsList)) {
                                Collections.sort(goodsParamsList);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("获取商品技术参数异常！", e.getMessage(), e);
            throw new ItemDetailException(ResultStatusConstant.GET_ITEM_SKUATTRIBUTE_FAILED);
        }
        return goodsParamsList;
    }

    /**
     * 获取商品详情描述
     *
     * @param itemId
     * @return
     */
    @Override
    public ItemDetailDTO getItemInfoDetail(String itemId) throws ItemDetailException {
        Map<String, Object> itemMap = getItemMapById(itemId);
        ItemDetailDTO itemDetailDTO = new ItemDetailDTO();
        List itemDescDTOList = new ArrayList<>();
        try {
            /** 获取商品详情描述的文档内容 */
            if (itemMap.containsKey("cmc_item_desc")) {
                List<Map<String, Object>> itemDescMapList = (List) itemMap.get("cmc_item_desc");
                if (ObjectUtil.isNotEmpty(itemDescMapList)) {
                    for (Map map : itemDescMapList) {
                        ItemDescDTO itemDescDTO = new ItemDescDTO();
                        String orderSort = (String) map.get("ORDER_SORT");
                        String descTitle = (String) map.get("DESC_TITLE");
                        String descText = (String) map.get("DESC_TEXT");
                        itemDescDTO.setOrderSort(orderSort);
                        itemDescDTO.setDescTitle(descTitle);
                        itemDescDTO.setDescText(descText);
                        itemDescDTOList.add(itemDescDTO);
                    }
                }
            }
            /** 对描述标题和文字描述排序 */
            if (ObjectUtil.isNotEmpty(itemDescDTOList)) {
                Collections.sort(itemDescDTOList);
            }
            /* 获取商品详情描述图片列表 */
            List<Map<String, Object>> tempImageList = (List) itemMap.get("aus_at_picture_list");
            List<ImageDTO> imageDTOList = new ArrayList<>();
            String pictureType = "cmcItemDesc";
            if (itemMap.containsKey("aus_at_picture_list")) {
                getPictureList(pictureType, tempImageList, imageDTOList);
            }
            /** 对详情描述图片排序 */
            if (ObjectUtil.isNotEmpty(imageDTOList)) {
                Collections.sort(imageDTOList);
            }
            itemDetailDTO.setItemDescDTOList(itemDescDTOList);
            itemDetailDTO.setImageDTOList(imageDTOList);
        } catch (Exception e) {
            logger.error("获取商品详情描述异常！", e.getMessage(), e);
            throw new ItemDetailException(ResultStatusConstant.GET_ITEM_DETAIL_FAILED);
        }
        return itemDetailDTO;
    }

    /**
     * 判断itemId是否存在
     *
     * @param itemId
     * @return
     * @throws ItemDetailException
     */
    private Map<String, Object> getItemMapById(String itemId) throws ItemDetailException {

        if (StringUtil.isBlank(itemId)) {
            logger.info("itemId=" + itemId);
            throw new ItemDetailException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        Map<String, Object> itemMap = elasticsearchService.searchDataById("gms", "item_agg", itemId, null);
        if (ObjectUtil.isEmpty(itemMap)) {
            throw new ItemDetailException(ResultStatusConstant.ITEM_ID_NON_EXISTENT);
        }
        return itemMap;
    }

}