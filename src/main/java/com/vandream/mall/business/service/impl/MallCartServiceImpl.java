package com.vandream.mall.business.service.impl;


import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.dto.mallCart.*;
import com.vandream.mall.business.dto.snapshot.ItemSnapshotVersionDTO;
import com.vandream.mall.business.execption.MallCartException;
import com.vandream.mall.business.service.MallCartService;
import com.vandream.mall.business.service.SnapshotService;
import com.vandream.mall.business.service.UserIdLevelService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.business.vo.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.JSONUtil;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;

import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dingjie
 * @date 2018/3/5
 * @time 10:54
 * Description:
 */
@Service(value = "mallCartService")
public class MallCartServiceImpl implements MallCartService {

    private static final Logger logger = LoggerFactory.getLogger(MallCartServiceImpl.class);
    /** 最大商品数量 */
    private static final int MAX_ITEM_NUM=200;
    @Autowired
    private MallCartDAO mallCartDAO;
    @Autowired
    private TransportClient transportClient;
    @Autowired
    private FavoritesDAO favoritesDao;
    @Resource
    private ValueSetLineDAO valueSetLineDAO;
    @Autowired
    private UserIdLevelService userIdLevelService;
    private final Integer MAX_NUM = 99999;
    /**
     * 商品图片类型
     */
    private final String BILL_TYPE = "cmcItem";
    @Autowired
    private ValueSetService valueSetService;
    @Autowired
    private OrderDAO orderDAO;
    @Resource
    private SnapshotDAO snapshotDAO;
    @Resource
    private SnapshotService snapshotService;
    /**
     * 添加商品 到购物车
     *
     * @param count
     * @param areaCode
     * @param userId
     * @return
     */
    @Override
    public int addCart(String itemId, String areaCode, String userId, BigDecimal count) throws MallCartException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(itemId)) {
            throw new MallCartException(ResultStatusConstant.CART_PARAM_CAN_NOT_NULL);
        }
        if (ObjectUtil.isNotEmpty(count)&&(count.toString().contains(".") || new BigDecimal(count.intValue()).compareTo(count) != 0)) {
            throw new MallCartException(ResultStatusConstant.CART_THE_QUANTITY_OF_GOODS_CANNOT_BE_DECIMAL);
        }
        if (ObjectUtil.isNotEmpty(count)&&count.intValue() > MAX_NUM) {
            throw new MallCartException(ResultStatusConstant.CART_GOODS_NUMBER_IS_BEYOND_THE_MAX_LIMIT);
        }
        ItemSnapshotVersionDTO itemSnapshotDTO  = null;

        MallCartDTO mallCartDTO = new MallCartDTO();
        try {
            itemSnapshotDTO = snapshotDAO.getCartSnapshot(itemId,areaCode);
        } catch (Exception e) {
            logger.error("=====getCartSnapshot异常:"+e.getMessage());
            throw new MallCartException(ResultStatusConstant.CART_ITEM_CAN_NOT_FOUND);
        }
//        String itemLineId = favoritesDao.selectItemLineId(itemId, areaCode);
        String itemLineId  =  null;
        if(ObjectUtil.isNotEmpty(itemSnapshotDTO)) {
            itemLineId =    itemSnapshotDTO.getItemLineId();
            String version=itemSnapshotDTO.getItemLineVersion();
            if(StringUtil.isBlank(version)){
                version="0";
            }
            mallCartDTO.setItemLineVersion(version);
        }
        if (StringUtil.isBlank(itemLineId)) {
            logger.info("==========itemLineId为空", itemLineId);
            throw new MallCartException(ResultStatusConstant.ITEM_LINE_INVALID);
        }
        //购物车有效商品总数
        int cartCount = 0;
        //加入购物车的状态
        char itemStatus = '1';
        char itemIsOrdered = '0';
        boolean addStatus = false;
        mallCartDTO.setSkuId(itemId);
        mallCartDTO.setItemNo(count);
        mallCartDTO.setItemLineId(itemLineId);
        mallCartDTO.setUserId(userId);
        mallCartDTO.setItemStatus(itemStatus);
        mallCartDTO.setItemIsOrdered(itemIsOrdered);
        MallCartDTO queryCart = mallCartDAO.selectMallCartByItemLineId(userId,itemLineId);
        try {
            if (ObjectUtil.isNotEmpty(queryCart) ) {
                MallCartDTO modifyCartDto = new MallCartDTO();
                BigDecimal totalCount=BigDecimal.ZERO;
                if(null==count){
                    count=getMinOrderNum(BigDecimal.ZERO, queryCart,itemId);
                }
                totalCount=count.add(queryCart.getItemNo());
                if(totalCount.intValue()>MAX_NUM){
                    totalCount=new BigDecimal(MAX_NUM);
                }
                modifyCartDto.setItemLineVersion(mallCartDTO.getItemLineVersion());
                modifyCartDto.setItemNo(totalCount);
                modifyCartDto.setId(queryCart.getId());
                modifyCartDto.setUserId(queryCart.getUserId());
                int modifyCount = mallCartDAO.modifyCart(modifyCartDto);
                if (modifyCount > 0) {
                    addStatus = true;
                }
            } else {
                Map<String, Object> itemMap = SearchEsReturnData.getCartDetail(transportClient, itemId,
                        getItemCodeJson());
                mallCartDTO.setItemName(null != itemMap.get("ITEM_NAME") ? itemMap.get("ITEM_NAME").toString() : "");
                mallCartDTO.setItemCode(null != itemMap.get("ITEM_CODE") ? itemMap.get("ITEM_CODE").toString() : "");
                Map<String, String> unitTypeMap = valueSetService.getItemUnitTypeNameMap();
                if (ObjectUtil.isNotEmpty(itemMap.get("PRIMARY_UNIT_CODE") )) {
                    String primaryUnitCode = (String) itemMap.get("PRIMARY_UNIT_CODE");
                  String primaryUnitName = unitTypeMap.get(primaryUnitCode);
                    mallCartDTO.setItemUnit(primaryUnitName);
                }
                count=count==null?BigDecimal.ZERO:count;
                BigDecimal minOrderNum = getMinOrderNum(count, mallCartDTO, itemId);
                if(count.compareTo(BigDecimal.ZERO)>0){
                    mallCartDTO.setItemNo(count);
                }else{
                    mallCartDTO.setItemNo(minOrderNum);
                }
                int insCount = mallCartDAO.addCart(mallCartDTO);

                if (insCount > 0) {
                    addStatus = true;
                }
            }
            if (addStatus) {
                cartCount = mallCartDAO.selectCartCountByUserId(userId);
            } else {
                throw new MallCartException(ResultStatusConstant.CART_INSERT_ERROR);
            }
        } catch (Exception e) {
            logger.error("=========收藏夹商品加入购物车失败！");
            throw new MallCartException(ResultStatusConstant.CART_INSERT_ERROR);
        }
        return cartCount;
    }

    /**
     * 移除购物车内商品
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    public int removeCart(long id, String userId) throws MallCartException {
        if (0 == id || StringUtil.isBlank(userId)) {
            throw new MallCartException(ResultStatusConstant.CART_PARAM_CAN_NOT_NULL);
        }
        //购物车商品总数
        int cartCount = 0;
        MallCartDTO mallCartDTO = new MallCartDTO();
        mallCartDTO.setUserId(userId);
        mallCartDTO.setId(id);
        //先验证商品是否在购物车中
        MallCartDTO queryCart = mallCartDAO.selectCartById(mallCartDTO);
        if (null == queryCart) {
            throw new MallCartException(ResultStatusConstant.CART_ITEM_CAN_NOT_FOUND);
        }
        try {
            int count = mallCartDAO.removeCart(queryCart);
            if (count > 0) {
                cartCount = mallCartDAO.selectCartCountByUserId(userId);
            } else {
                throw new MallCartException(ResultStatusConstant.CART_REMOVE_ERROR);
            }
        } catch (Exception e) {
            logger.error("======removeCart={}", e.toString());
            throw new MallCartException(ResultStatusConstant.CART_REMOVE_ERROR);
        }
        return cartCount;
    }


    /**
     * 获取图片列表
     */
    private Object getPictureList(String pictureType, List<Map<String, Object>> tempImageList) {
        if (ObjectUtil.isNotEmpty(tempImageList)) {
            for (Map imageMap : tempImageList) {
                Object pic = imageMap.get("PIC_EX_FILEID");
                Object bType = imageMap.get("BILL_TYPE");
                if (ObjectUtil.isNotEmpty(pic) && ObjectUtil.isNotEmpty(bType)) {
                    String billType = bType.toString();
                    if (pictureType.equals(billType)) {
                        return pic;
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据userId获取购物车内商品列表
     *
     * @param userId
     * @return
     */
    @Override
    public CartResponseDTO getCartList(String userId,String advanceOrderId) throws MallCartException {
        if (null == userId || "".equals(userId)) {
            throw new MallCartException(ResultStatusConstant.CART_PARAM_CAN_NOT_NULL);
        }
        Map<String,Object> paramsMap=new HashMap<>(MAX_ITEM_NUM);
        paramsMap.put("userId",userId);
        if(StringUtil.isNotBlank(advanceOrderId)){
            List<String> orderItemLineList = orderDAO.getOrderItemDTOListByOrderId(advanceOrderId);
            if (ObjectUtil.isEmpty(orderItemLineList)) {
                throw new MallCartException(ResultStatusConstant.ADD_ORDER_PARAM_ERROR);
            }
            paramsMap.put("orderItemLineList",orderItemLineList);
        }
        List<MallCartDTO> mallCartList = mallCartDAO.getCartList(paramsMap);
        List<GoodsDetailDTO> goodsDetailDTOList = new ArrayList<>(mallCartList.size());
        CartResponseDTO mallEntity = new CartResponseDTO();

        /**
         * 商品信息集合(包含面包屑) key:三级类目品类ID， Value:当前品类的品类信息，该品类下的商品信息
         */
        Map<String, CartItemLineListDTO> cartItemLineListDTOMap = new HashMap<>(mallCartList.size());

        List<CartItemLineListDTO> arrayList= new ArrayList(cartItemLineListDTOMap.size());
        //商品总数量
        BigDecimal totalCount = BigDecimal.ZERO;
        BigDecimal totalPrice = BigDecimal.ZERO;
        boolean isYiJia = false;
        if (null != mallCartList && mallCartList.size() > 0) {
            for (int i = 0; i < mallCartList.size(); i++) {
                GoodsDetailDTO goodsDetailDTO = new GoodsDetailDTO();
                MallCartDTO mallCartDTO = mallCartList.get(i);
                String skuId = mallCartDTO.getSkuId() != null ? mallCartDTO.getSkuId() : "";
                Map<String, Object> map;
                //商品单位
                String primaryUnitName = null;
                try {
                    map = SearchEsReturnData.getCartDetail(transportClient, skuId, getResultJson());
                    if (null == map || map.size() == 0) {
                        throw new MallCartException(ResultStatusConstant.CART_READ_LIST_ERROR);
                    }
                    Map<String, String> unitTypeMap = valueSetService.getItemUnitTypeNameMap();
                    if (map.get("PRIMARY_UNIT_CODE") != null) {
                        String primaryUnitCode = (String) map.get("PRIMARY_UNIT_CODE");
                        primaryUnitName = unitTypeMap.get(primaryUnitCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("=========es检索异常:"+e.toString());
                    throw new MallCartException(ResultStatusConstant.CART_READ_LIST_ERROR);
                }
                //获取商品的面包屑
                Object cmcCategory = map.get("cmc_category");
                String categoryId="";
                if (ObjectUtil.isNotEmpty(cmcCategory)) {
                    String itemdDetailNotes = JSONUtil.toJson(cmcCategory);
                    //itemCategroy获取完成
                    List<FavorityItemCategoryVO> categoryList = JSONUtil.toList(itemdDetailNotes,
                            FavorityItemCategoryVO.class);
                    categoryId = categoryList.get(2).getId();
                    //获取面包屑
                    if (!cartItemLineListDTOMap.containsKey(categoryId)) {
                        cartItemLineListDTOMap.put(categoryId, new CartItemLineListDTO());
                        cartItemLineListDTOMap.get(categoryId).addCategoryList(categoryList.get(0)
                                .getName());
                        cartItemLineListDTOMap.get(categoryId).addCategoryList(categoryList.get(1)
                                .getName());
                        cartItemLineListDTOMap.get(categoryId).addCategoryList(categoryList.get(2)
                                .getName());
                    }
                }
                //单个商品数量
                BigDecimal count = mallCartDTO.getItemNo();
                BigDecimal unitPrice = BigDecimal.ZERO;
                //单列商品参考价格
                BigDecimal subTotal = BigDecimal.ZERO;
                totalCount = totalCount.add(count);
                String goodStr = "";
                try {
                    map.put("number", count);
                    map.put("id", mallCartDTO.getId());
                    map.put("status", mallCartDTO.getItemStatus());
                    /*设置版本信息*/
                    map.put("versionStatus", "00");
                    map.put("itemId", mallCartDTO.getSkuId());
                    map.put("itemLineId", mallCartDTO.getItemLineId());
                    map.put("itemLineVersion",mallCartDTO.getItemLineVersion());
                    goodStr = JSON.toJSONString(map);
                    goodsDetailDTO = JSON.parseObject(goodStr, GoodsDetailDTO.class);

                    goodsDetailDTO.setUnitName(primaryUnitName);
                    //商品详细参数需要单独获取
                    String specContents = "";
                    if (null != goodsDetailDTO) {
                        specContents = null != goodsDetailDTO.getSpecContents() ? goodsDetailDTO.getSpecContents() : "";
                        //获取单价
                        String cmcStr = goodsDetailDTO.getCmcItemLine() != null ? goodsDetailDTO.getCmcItemLine() : "";
                        if (null == cmcStr || "".equals(cmcStr)) {
                            throw new MallCartException(ResultStatusConstant.CART_ITEM_CAN_NOT_FOUND);
                        }
                        List<CmcItemLineDTO> cmcItemLineList = JSONUtil.toList(cmcStr, CmcItemLineDTO.class);
                        if (null != cmcItemLineList && cmcItemLineList.size() > 0) {
                            //循环并判断查询 商品item_line_id与es结果,抽取相等的
                            for (CmcItemLineDTO cid : cmcItemLineList) {
                                Map<String, String> typePriceMap = new HashMap<>(16);
                                //SALE_PRICE1是普价
                                typePriceMap.put("00", cid.getSalePrice1());
                                //高级会员价
                                typePriceMap.put("10", cid.getSalePrice2());
                                //VIP会员价
                                typePriceMap.put("20", cid.getSalePrice3());

                                if (StringUtil.isNotBlank(cid.getItemLineId()) &&
                                        StringUtil.isNotBlank(mallCartDTO.getItemLineId())
                                        && mallCartDTO.getItemLineId().equals(cid.getItemLineId())) {
                                    String minOrderStr = (null == cid.getMinOrderNum() ||
                                            "".equals(cid.getMinOrderNum())) ? "1" : cid.getMinOrderNum();
                                    //设置最小起定量
                                    BigDecimal minOrderNum = new BigDecimal(minOrderStr);
                                    goodsDetailDTO.setMinOrderNum(minOrderNum);
                                    //取会员价格，如果会员等级为空或会员等级对应价格为空时，显示为议价
                                    String companyLevel = userIdLevelService.getUserIdLevelService(userId);
                                    companyLevel = companyLevel == null ? "00" : companyLevel;
                                    String salePrice = typePriceMap.get(companyLevel);
                                    if (null == salePrice || "".equals(salePrice) ||
                                            BigDecimal.ZERO.compareTo(new BigDecimal(salePrice)) == 0) {
                                        //会员对应价格为空时,使用议价
                                        isYiJia = true;
                                        salePrice = "-1";
                                    }
                                    //设置单价属性
                                    unitPrice = new BigDecimal(salePrice);
                                    goodsDetailDTO.setUnitPrice(unitPrice);
                                    //设置商品状态
                                    goodsDetailDTO.setItemStatus(cid.getItemStatus());
                                    goodsDetailDTO.setItemLineId(cid.getItemLineId());
                                }
                            }
                            goodsDetailDTO.setCmcItemLine("");

                        }
                        //判断商品价格是否议价
                        if (isYiJia) {
                            if (unitPrice.compareTo(BigDecimal.ZERO) <= 0) {
                                //如果单价为议价时，单行商品总价与所有商品总价都为议价
                                goodsDetailDTO.setSubTotal(new BigDecimal("-1"));
                            } else {
                                //计算单行商品总价
                                subTotal = count.multiply(unitPrice);
                                goodsDetailDTO.setSubTotal(subTotal);
                            }
                            totalPrice = new BigDecimal("-1");
                        } else {
                            //非议价商品
                            subTotal = count.multiply(unitPrice);
                            goodsDetailDTO.setSubTotal(subTotal);
                            //计算多种商品总价
                            totalPrice = totalPrice.add(count.multiply(unitPrice));
                        }
                        List<GoodsParams> goodsParamsList = new ArrayList<>();
                        if (null != specContents && !"".equals(specContents) && specContents.contains("[")) {
                            goodsParamsList = JSONUtil.toList(specContents, GoodsParams.class);
                            goodsDetailDTO.setParams(goodsParamsList);
                            goodsDetailDTO.setSpecContents("");
                        }
                        //获取商品图片地址
                        if (null != map.get("aus_at_picture_list")) {
                            List<Map<String, Object>> tempImageList = (List) map.get("aus_at_picture_list");
                            String picUrl = null;
                            picUrl =(String)getPictureList(BILL_TYPE, tempImageList);
                            goodsDetailDTO.setImagePath(picUrl);
                        } else {
                            goodsDetailDTO.setImagePath(null);
                        }
                        if (StringUtil.isNotBlank(categoryId)) {
                            cartItemLineListDTOMap.get(categoryId).addItems(goodsDetailDTO);
                        }
                    }
                } catch (Exception e) {
                    logger.error("======getCartList={}", e.toString());
                    throw new MallCartException(ResultStatusConstant.CART_INFO_READ_ERROR);
                }
            }
            //计算商品总数量与总价
            mallEntity.setCount(totalCount.longValue());
            if (isYiJia) {
                mallEntity.setTotalPrice(new BigDecimal("-1"));
            } else {
                mallEntity.setTotalPrice(totalPrice);
            }
          
            if(ObjectUtil.isNotEmpty(cartItemLineListDTOMap)&&cartItemLineListDTOMap.size()>0){
                arrayList=new ArrayList(cartItemLineListDTOMap.values());
                arrayList.forEach(cartItem->
                   checkItemChanged( cartItem.getItems())
                );
            }
            mallEntity.setCartItemLineListDTOList(arrayList);
        }else{
            mallEntity.setCartItemLineListDTOList(arrayList);
        }
        return mallEntity;
    }

    /**
     * 修改购物车内商品数量
     *
     * @param id
     * @param itemNo
     * @return
     */
    @Override
    public int modifyCart(long id, String userId, BigDecimal itemNo) throws MallCartException {
        if (0 == id || null == itemNo || null == userId || "".equals(userId)) {
            throw new MallCartException(ResultStatusConstant.CART_PARAM_CAN_NOT_NULL);
        }
        if (itemNo.intValue() <= 0) {
            throw new MallCartException(ResultStatusConstant.CART_THE_QUANTITY_OF_GOODS_CANNOT_BE_ZERO);
        }

        if (itemNo.toString().contains(".") || new BigDecimal(itemNo.intValue()).compareTo(itemNo) != 0) {
            throw new MallCartException(ResultStatusConstant.CART_THE_QUANTITY_OF_GOODS_CANNOT_BE_DECIMAL);
        }
        if (itemNo.intValue() > MAX_NUM) {
            throw new MallCartException(ResultStatusConstant.CART_GOODS_NUMBER_IS_BEYOND_THE_MAX_LIMIT);
        }
        int updCount = 0;
        MallCartDTO queryCart;
        MallCartDTO mallCartDTO = new MallCartDTO();
        try {
            mallCartDTO.setId(id);
            mallCartDTO.setItemNo(itemNo);
            mallCartDTO.setUserId(userId);
            //查询item_id并取es里的最小起定量判断是否符合
            queryCart = mallCartDAO.selectCartById(mallCartDTO);
            String skuId = queryCart.getSkuId();
            getMinOrderNum(itemNo, queryCart, skuId);
            mallCartDTO.setItemNo(itemNo);
        } catch (Exception e) {
            logger.error("=====modifyCart={}", e.toString());
            throw new MallCartException(ResultStatusConstant.CART_MODIFY_ERROR);
        }
        try {
            updCount = mallCartDAO.modifyCart(mallCartDTO);
        } catch (Exception e) {
            throw new MallCartException(ResultStatusConstant.CART_MODIFY_ERROR);
        }

        return updCount;
    }

    @Override
    public int selectCartCountByUserId(String userId) throws MallCartException{
        if (StringUtil.isBlank(userId)) {
            throw new MallCartException(ResultStatusConstant.CART_PARAM_CAN_NOT_NULL);
        }
        int cartCount=0;
        try {
            cartCount = mallCartDAO.selectCartCountByUserId(userId);
        } catch (Exception e) {
            logger.error("===============selectCartCountByUserId"+e.toString());
            throw new MallCartException(ResultStatusConstant.DATA_READ_FAIL);
        }
        return cartCount;
    }

    private BigDecimal getMinOrderNum(BigDecimal itemNo, MallCartDTO queryCart, String skuId) throws MallCartException {
        String cmcStr="";
        BigDecimal minOrderNum=new BigDecimal(1);
        Map<String, Object> map = SearchEsReturnData.getCartDetail(transportClient, skuId, "{\"cmc_item_line\":[]" +
                " }");
        cmcStr = JSON.toJSONString(map.get("cmc_item_line"));
        if (null == cmcStr || "".equals(cmcStr) || !cmcStr.contains("[")) {
            throw new MallCartException(ResultStatusConstant.CART_ITEM_CAN_NOT_FOUND);
        }
        List<CmcItemLineDTO> cmcItemLineList = JSONUtil.toList(cmcStr, CmcItemLineDTO.class);
        if (null != cmcItemLineList && cmcItemLineList.size() > 0) {
            for (CmcItemLineDTO cid : cmcItemLineList) {
                if (StringUtil.isNotBlank(cid.getItemLineId()) && StringUtil.isNotBlank(queryCart.getItemLineId()) &&
                        queryCart.getItemLineId().equals(cid.getItemLineId()) && null != cid.getMinOrderNum() &&
                        !"".equals(cid.getMinOrderNum())) {
                    minOrderNum = new BigDecimal(cid.getMinOrderNum());
                    if (itemNo.compareTo(BigDecimal.ZERO)>0&&itemNo.compareTo(minOrderNum) < 0) {
                        throw new MallCartException(ResultStatusConstant
                                .CART_MODIFY_CART_NUM_ERROR);
                    }
                }
            }
        }
        return minOrderNum;
    }

    public String getItemCodeJson() {
        String responseJson = "{" +
                "                \"ITEM_ID\":\"\",\n" +
                "                \"ITEM_CODE\":\"\",\n" +
                "                \"ITEM_NAME\":\"\",\"PRIMARY_UNIT_CODE\":\"\" }";
        return responseJson;
    }
    public String getResultJson() {
        String responseJson = "{" +
                "                \"ITEM_ID\":\"\",\n" +
                "                \"ITEM_CODE\":\"\",\n" +
                "                \"ITEM_NAME\":\"\",\"PRIMARY_UNIT_CODE\":\"\",\"cmc_item_line\":[]," +
                "                \"SPEC_CONTENTS\":[],\"aus_at_picture_list\":[],\"cmc_category\":[]\n" +
                "            }";
        return responseJson;
    }

    private void checkItemChanged(List<GoodsDetailDTO> itemInfo) {

        Set<String> itemLineIds = new HashSet<>();
        itemInfo.forEach(item -> {
            itemLineIds.add(item.getItemLineId());
        });
        Map itemLineMap = snapshotService.getItemLineMap(itemLineIds);
        for (GoodsDetailDTO lineDTO : itemInfo) {
            if (ObjectUtil.isNotEmpty(lineDTO.getItemLineVersion()) && !itemLineMap.get(lineDTO.getItemLineId()).equals(lineDTO.getItemLineVersion())) {
                lineDTO.setIsChangeType(1);
            }else{
                lineDTO.setIsChangeType(0);
            }
            
        }
    }

}
