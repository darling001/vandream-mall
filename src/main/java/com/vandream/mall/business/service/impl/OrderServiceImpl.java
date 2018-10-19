package com.vandream.mall.business.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.vandream.mall.business.constant.BusinessType;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.domain.Attachment;
import com.vandream.mall.business.dto.AddOrderReturnInformationDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.business.dto.delivery.DeliverySubLineDTO;
import com.vandream.mall.business.dto.item.ItemLineDTO;
import com.vandream.mall.business.dto.order.*;
import com.vandream.mall.business.dto.demand.SubLineDTO;
import com.vandream.mall.business.dto.findContract.AddOrderDTO;
import com.vandream.mall.business.execption.*;
import com.vandream.mall.business.service.AddressService;
import com.vandream.mall.business.vo.*;
import com.vandream.mall.business.dto.mallCart.MallCartDTO;
import com.vandream.mall.business.dto.mallCart.SearchEsReturnData;
import com.vandream.mall.business.service.OrderService;
import com.vandream.mall.business.vo.buyerContract.ContractInfoVO;
import com.vandream.mall.business.vo.order.*;
import com.vandream.mall.business.vo.search.SearchItemAggVO;
import com.vandream.mall.commons.constant.ElasticsearchConstant;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/7 10:16
 */
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
    /** 最大商品数量 */
    private static final int MAX_ITEM_NUM=200;
    @Autowired
    private FavoritesDAO favoritesDao;

    @Autowired
    private TransportClient transportClient;
    @Autowired
    private MallCartDAO mallCartDAO;
    @Autowired
    private ApiExecutorBxService apiExecutorBxService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private DemandHeadDAO demandHeadDAO;
    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private ItemLineDAO itemLineDAO;
    @Autowired
    private UserLevelDAO userLevelDAO;
    @Resource
    private ContractInfoDAO contractInfoDAO;
    @Resource
    private AccountDAO accountDAO;
    @Resource
    private UserInfoDAO userInfoDAO;

    @Override
    public AddOrderReturnInformationDTO addOrder(AddOrderVO addOrderVO) throws Exception {
        Map<String, Object> validation = ValidatorUtils.validation(addOrderVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            logger.info("==================》参数传入错误");
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .ORDER_PARAM_CAN_NOT_NULL);
            addOrderException.setMessage(JSON.toJSONString(validation));
            throw addOrderException;
        }

        if (addOrderVO.getAddress() == null) {
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .INFORMATION_CAN_NOT_EMPTY);
            addOrderException.setMessage("详细地址不能为空");
            throw addOrderException;
        }
        if (addOrderVO.getContactName() == null) {
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .INFORMATION_CAN_NOT_EMPTY);
            addOrderException.setMessage("收货人姓名不能为空");
            throw addOrderException;
        }
        if (addOrderVO.getContactPhone() == null) {
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .INFORMATION_CAN_NOT_EMPTY);
            addOrderException.setMessage("收货人联系方式不能为空");
            throw addOrderException;
        }
        if (addOrderVO.getLeadTime() == null) {
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .INFORMATION_CAN_NOT_EMPTY);
            addOrderException.setMessage("交付时间不能为空");
            throw addOrderException;
        }
        if (!RegexUtil.isChinaMobilePhone(addOrderVO.getContactPhone())) {
            throw new AddOrderException(ResultStatusConstant.SMS_MESSAGE_PHONE_NOT_MATCH);
        }
        try {
            checkAccount(addOrderVO.getUserId());
        } catch (AddOrderException e) {
            throw e;
        }
        AddOrderDTO addOrderDTO = new AddOrderDTO();
        addOrderDTO.setCustomerId(addOrderVO.getCustomerId());
        addOrderDTO.setCustomerCode(addOrderVO.getCustomerCode());
        addOrderDTO.setCustomerName(addOrderVO.getCustomerName());
        addOrderDTO.setDemandResume("商城下单");
        addOrderDTO.setDemandType("10");
        addOrderDTO.setFromType("10");
        addOrderDTO.setDemandAccountId(addOrderVO.getUserId());
        addOrderDTO.setDemandAccountName(addOrderVO.getUserName());
        addOrderDTO.setCurrencyCode("CNY");
        addOrderDTO.setSiteCountryCode(addOrderVO.getCountryCode());
        addOrderDTO.setSiteCountryName(addOrderVO.getCountryName());
        addOrderDTO.setSiteRegionCode(addOrderVO.getProvinceCode());
        addOrderDTO.setSiteRegionName(addOrderVO.getProvinceName());
        addOrderDTO.setSiteCityCode(addOrderVO.getCityCode());
        addOrderDTO.setSiteCityName(addOrderVO.getCityName());
        addOrderDTO.setSiteCountyCode(addOrderVO.getAreaId());
        addOrderDTO.setSiteCountyName(addOrderVO.getAreaName());
        StringBuffer sbf = new StringBuffer();
        sbf.append(addOrderVO.getAddress()).append("(" + addOrderVO.getContactName() + " 收)").append(
                " " + addOrderVO.getContactPhone());
        addOrderDTO.setCustomerConsigneetName(addOrderVO.getContactName());
        addOrderDTO.setCustomerConsigneetPhone( addOrderVO.getContactPhone());
        addOrderDTO.setCustomerSiteArea(sbf.toString());
        addOrderDTO.setAccountId(addOrderVO.getUserId());
        addOrderDTO.setAccountName(addOrderVO.getUserName());
        addOrderDTO.setOperatorUserId(addOrderVO.getUserId());
        addOrderDTO.setOperatorUserName(addOrderVO.getUserName());
        addOrderDTO.setBookCode("1000");
        addOrderDTO.setBusinessType("psdDemand");
        addOrderDTO.setDeliveryPeriodStart((DateUtils.formatDate(
                new java.util.Date(System.currentTimeMillis()), "yyyyMMdd")));
        addOrderDTO.setDeliveryPeriodEnd((DateUtils.formatDate(
                new java.util.Date(addOrderVO.getLeadTime()), "yyyyMMdd")));
        addOrderDTO.setDemandPhone(addOrderVO.getContactPhone());
        addOrderDTO.setDemandContactsRole(addOrderVO.getContactName());
        List<SubLineDTO> subLines = new ArrayList<>();
        SubLineDTO subLineDTO = new SubLineDTO();
        String itemLineId = favoritesDao.selectItemLineId(addOrderVO.getItemId(), addOrderVO.getAreaCode());
        subLineDTO.setItemId(addOrderVO.getItemId());
        subLineDTO.setItemLineCode(addOrderVO.getAreaCode());
        subLineDTO.setItemLineId(itemLineId);
        subLineDTO.setItemType("STOCK");
        BigDecimal num = addOrderVO.getNumber();
        boolean flagt = num.toString().contains(".");
        if (flagt) {
            throw new FindContractException(ResultStatusConstant.NO_DECIMAL_INPUT_ALLOWED);
        }
        int nums = num.intValue();
        Boolean flags = (99999 < nums);
        if (flags) {
            throw new FavoritesException(ResultStatusConstant.ORDER_NUMBER_MAXIMUM);
        }
        subLineDTO.setQuantity(nums);
        subLines.add(subLineDTO);
        addOrderDTO.setSubLineList(subLines);
        String resultStr = apiExecutorBxService.addDemandInfo(addOrderDTO);
        BxApiResult bxApiResult = JSONUtil.toBean(resultStr, BxApiResult.class);
        if (null != bxApiResult) {
            if ("0".equals(bxApiResult.getStatus().toString())) {
                logger.error("调用接口返回数据错误");
                FindContractException findContractException = new FindContractException(ResultStatusConstant
                        .CALLING_INTERFACE_EXCEPTION);
                findContractException.setMessage(bxApiResult.getMessage());
                throw findContractException;
            }
        }
        try {
            addressService.modifyAddress(addOrderVO.getUserId(), addOrderVO.getCompanyId(), null,
                    addOrderVO.getProvinceCode(), addOrderVO.getCityCode(),
                    addOrderVO.getAreaCode(), addOrderVO.getProvinceName(), addOrderVO.getCityName(), addOrderVO
                            .getAreaName(), addOrderVO.getAddress(),
                    addOrderVO.getPostCode(), addOrderVO.getContactName(), addOrderVO.getContactPhone(), "0",
                    addOrderVO.getCountryName(),
                    addOrderVO.getCountryCode());

        } catch (Exception e) {
           logger.info("地址修改失败");
        }
        try {
            String orderNo = bxApiResult.getPkId();
            GetRequestBuilder getRequestBuilder = transportClient.prepareGet("gms", "item_agg", addOrderVO.getItemId());
            GetResponse getResponse = getRequestBuilder.execute().actionGet();
            Map<String, Object> map = getResponse.getSource();
            AddOrderReturnInformationDTO addOrderReturnInformationDTO = new AddOrderReturnInformationDTO();
            Map searchMap = SearchEsReturnData.getCartDetail(transportClient, addOrderVO.getItemId(), getResultJson());
            String itemStr = searchMap.get("ITEM_NAME") + "(商品编码:" + map.get("ITEM_CODE") + ")";
            if (null != orderNo && !"".equals(orderNo)) {
                addOrderReturnInformationDTO.setOrderCode(demandHeadDAO.getOrderCodeById(orderNo));
            }
            addOrderReturnInformationDTO.setItemIdName(itemStr);
            addOrderReturnInformationDTO.setOrderId(orderNo);
            return addOrderReturnInformationDTO;
        } catch (Exception e) {
            throw new FindContractException(ResultStatusConstant.CALLING_INTERFACE_EXCEPTION);
        }

    }

    //获取地址信息
    public OrderDemandLineDTO getAdderss(BatchOrderVO batchOrderVO, OrderDemandLineDTO orderDemandLineDTO) {
        OrderDemandLineDTO demandLineDTO = new OrderDemandLineDTO();
        if (null != batchOrderVO) {
            demandLineDTO = orderDemandLineDTO;
            StringBuilder stringBuilder = new StringBuilder();
            String provinceName = batchOrderVO.getProvinceName();
            String cityName = batchOrderVO.getCityName();
            String countyName = batchOrderVO.getAreaName();
            String phone = batchOrderVO.getContactPhone();
            String userName = batchOrderVO.getContactName();
            String addressDetail = batchOrderVO.getAddress();
            stringBuilder.append(addressDetail).append("(" + userName + " 收)").append(phone);
            demandLineDTO.setCustomerConsigneetName(userName);
            demandLineDTO.setCustomerConsigneetPhone(phone);
            demandLineDTO.setSiteRegionCode(batchOrderVO.getProvinceCode());
            demandLineDTO.setSiteRegionName(provinceName);
            demandLineDTO.setSiteCityCode(batchOrderVO.getCityCode());
            demandLineDTO.setSiteCityName(cityName);
            demandLineDTO.setSiteCountyCode(batchOrderVO.getAreaId());
            demandLineDTO.setSiteCountyName(countyName);
            demandLineDTO.setCustomerSiteArea(stringBuilder.toString());
            demandLineDTO.setSiteCountryCode(batchOrderVO.getCountryCode());
            demandLineDTO.setSiteCountryName(batchOrderVO.getCountryName());
        }
        return demandLineDTO;
    }

    /**
     * 根据userId从mysql中拿到itemId,然后从 es数据获取商品信息
     *
     * @param batchOrderVO
     * @return
     */
    @Override
    public OrderResponseVO addBatchOrder(BatchOrderVO batchOrderVO) throws AddOrderException {
        Map<String, Object> validation = ValidatorUtils.validation(batchOrderVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            AddOrderException demandException = new AddOrderException(ResultStatusConstant
                    .ORDER_PARAM_CAN_NOT_NULL);
            demandException.setMessage(JSON.toJSONString(validation));
            throw demandException;
        }
        if (!RegexUtil.isChinaMobilePhone(batchOrderVO.getContactPhone())) {
            throw new AddOrderException(ResultStatusConstant.SMS_MESSAGE_PHONE_NOT_MATCH);
        }
        try {
            checkAccount(batchOrderVO.getUserId());
        } catch (AddOrderException e) {
            throw e;
        }
        //需求发布DTO
        OrderDemandLineDTO orderDemandLineDTO = new OrderDemandLineDTO();

        OrderResponseVO orderResponseVO = new OrderResponseVO();
        try {
            String userId = batchOrderVO.getUserId();
            //根据预订单Id查询购物车列表
            Map<String,Object> paramsMap=new HashMap<>(MAX_ITEM_NUM);
            String advanceOrderId=batchOrderVO.getAdvanceOrderId();
            paramsMap.put("userId",userId);
                List<String> orderItemLineList = orderDAO.getOrderItemDTOListByOrderId(advanceOrderId);
                if (ObjectUtil.isEmpty(orderItemLineList)) {
                    throw new AddOrderException(ResultStatusConstant.ADD_ORDER_PARAM_ERROR);
                }
                paramsMap.put("orderItemLineList",orderItemLineList);

            List<MallCartDTO> mallCartDTOList = mallCartDAO.getCartList(paramsMap);
            List<OrderItemLineDTO> orderItemLineDTOList = null;
            if (null == mallCartDTOList || mallCartDTOList.size() == 0) {
                throw new AddOrderException(ResultStatusConstant.CART_GOODS_CAN_NOT_NULL);
            }
            //vo传入参数转换发布信息dto
            orderDemandLineDTO = ObjectUtil.transfer(batchOrderVO, OrderDemandLineDTO.class);
            if (mallCartDTOList.size() > 0) {
                for (MallCartDTO mallDTO : mallCartDTOList) {
                    String skuId = mallDTO.getSkuId();
                    if (null != skuId) {
                        Map<String, Object> map = SearchEsReturnData.getCartDetail(transportClient, skuId,
                                "{\"TRADEMARK\":\"\" }");
                        Object obj = map.get("TRADEMARK");
                        String trademark = obj == null ? "" : obj.toString();
                        mallDTO.setBrand(trademark);
                    }
                }
            }

            orderItemLineDTOList = ObjectUtil.transfer(mallCartDTOList, OrderItemLineDTO.class);
            //添加地址信息
            orderDemandLineDTO = getAdderss(batchOrderVO, orderDemandLineDTO);
            orderDemandLineDTO.setSubLineList(orderItemLineDTOList);
            orderDemandLineDTO.setDeliveryPeriodStart((DateUtils.formatDate(
                    new java.util.Date(System.currentTimeMillis()), "yyyyMMdd")));
            orderDemandLineDTO.setDeliveryPeriodEnd((DateUtils.formatDate(
                    new java.util.Date(batchOrderVO.getLeadTime()), "yyyyMMdd")));
            orderDemandLineDTO.setAttachmentList(new ArrayList<Attachment>());
            //业务类型
            orderDemandLineDTO.setBusinessType(BusinessType.PSD_DEMAND);
            //账套code
            orderDemandLineDTO.setBookCode("1000");
            //来源类别(10 商城)
            orderDemandLineDTO.setFromType("10");
            //需求提出人
            orderDemandLineDTO.setOperatorUserId(batchOrderVO.getUserId());
            orderDemandLineDTO.setOperatorUserName(batchOrderVO.getUserName());
            orderDemandLineDTO.setAccountId(batchOrderVO.getUserId());
            orderDemandLineDTO.setAccountName(batchOrderVO.getUserName());
            //需求类型(10 简要需求)
            orderDemandLineDTO.setDemandType("10");
            //需求简述
            orderDemandLineDTO.setDemandResume("商城购物车批量下单生成需求单");
            //调用 api添加需求
            String resultStr = apiExecutorBxService.addDemandInfo(orderDemandLineDTO);
            BxApiResult bxApiResult = JSONUtil.toBean(resultStr, BxApiResult.class);
            if (null != bxApiResult) {
                if ("1".equals(bxApiResult.getStatus().toString())) {
                    //返回购物车商品名称+编码
                    List<String> itemList = new ArrayList<String>(MAX_ITEM_NUM);
                    String orderId = "";
                    orderId = bxApiResult.getPkId();
                    orderResponseVO.setOrderId(orderId);
                    if (null != orderId && !"".equals(orderId)) {
                        orderResponseVO.setOrderCode(demandHeadDAO.getOrderCodeById(orderId));
                    }
                    for (int i = 0; i < mallCartDTOList.size(); i++) {
                        MallCartDTO mallCartDTO = mallCartDTOList.get(i);
                        String responseStr = mallCartDTO.getItemName() + "(商品编码:" + mallCartDTO.getItemCode() + ")";
                        itemList.add(responseStr);
                    }
                    orderResponseVO.setItemList(itemList);
                    //mysql中取出order表中的orderNo,得到itemId，用以查询es中商品信息
                    //更改购物车内商品的状态;
                    int updCartCount = mallCartDAO.updateCartStatus(paramsMap);
                    //需求发布成功后添加地址信息到记录
                    try {
                        //添加地址,获取地址id
                        Boolean flag = addressService.modifyAddress(batchOrderVO.getUserId(), batchOrderVO
                                        .getCompanyId(), null, batchOrderVO.getProvinceCode(),
                                batchOrderVO.getCityCode(), batchOrderVO.getAreaId(), batchOrderVO.getProvinceName(),
                                batchOrderVO.getCityName(),
                                batchOrderVO.getAreaName(), batchOrderVO.getAddress(), batchOrderVO.getPostCode(),
                                batchOrderVO.getContactName(),
                                batchOrderVO.getContactPhone(), "0", batchOrderVO.getCountryName(), batchOrderVO
                                        .getCountryCode());
                    } catch (Exception e) {
                        logger.error("======购物车批量下单添加地址异常！！" + e.toString());
                    }
                    return orderResponseVO;
                } else {
                    //调用宝信接口失败
                    throw new AddOrderException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                }
            }

        } catch (Exception e) {
            throw new AddOrderException(ResultStatusConstant.ORDER_BATCH_INSERT_ERROR);
        }
        return orderResponseVO;
    }

    /**
     * 预订单
     */
    @Override
    public String createAdvanceOrder(String userId, List<String> itemLineIds) throws AddOrderException {
        /**
         * 判断itemLineId集合参数是否为空
         */
        if (StringUtil.isBlank(userId) && ObjectUtil.isEmpty(itemLineIds)) {
            throw new AddOrderException(ResultStatusConstant.ORDER_PARAM_CAN_NOT_NULL);
        }
        try {
            checkAccount(userId);
        } catch (AddOrderException e) {
            throw e;
        }
        List<String> itemLineIdCheck = new ArrayList<>(itemLineIds.size());
        //商品itemLine 集合
        List<ItemLineDTO> itemLineDTOList=new ArrayList<>(itemLineIds.size());
        for (String itemLineId : itemLineIds) {
            if (StringUtil.isNotBlank(itemLineId)) {
                ItemLineDTO itemLineDTO = itemLineDAO.getItemLineByItemLineId(itemLineId);
                //itemLine判断是否为下架状态（下架商品无法提交）
                if (ObjectUtil.isNotEmpty(itemLineDTO) && !"40".equals(itemLineDTO.getStatus())) {
                    throw new AddOrderException(ResultStatusConstant
                            .THE_GOODS_IN_THE_NEXT_SHELF_CAN_NOT_BE_SENT_DOWN);
                }
                itemLineIdCheck.add(itemLineId);
                itemLineDTOList.add(itemLineDTO);
            }
        }
        /**
         * 根据userId，itemLineIdCheck，itemIsOrdered(参数是否下单) 更新已下单商品状态
         */

        List<OrderDetailDTO> orderEntityList = new ArrayList<>(itemLineIds.size());
        List<OrderItemDTO> itemEntityList = new ArrayList<>(itemLineIds.size());
        if (ObjectUtil.isNotEmpty(itemLineIdCheck)) {
            try {

                Long orderId = 0L;
                //随机生成唯一订单编号
                long orderNo = System.currentTimeMillis() + getRandomId();
                //order主表新增商品记录数
                int insertOrderCount = 0;
                //order_item订单详情表新增商品记录数
                int insertItemCount = 0;
                //批量添加order表

                //生成预订单
                OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
                orderDetailDTO.setUserId(userId);
                orderDetailDTO.setOrderNo(orderNo);
                orderEntityList.add(orderDetailDTO);
                if (null != orderEntityList && orderEntityList.size() > 0) {
                    insertOrderCount = orderDAO.batchInsertOrderList(orderEntityList);
                    orderId = orderDAO.selectMallOrderByOrderNo(orderNo);
                }
                if (insertOrderCount <= 0) {
                    throw new AddOrderException(ResultStatusConstant.ADD_ORDER_PARAM_ERROR);
                }

                for (ItemLineDTO itemLineDTO : itemLineDTOList) {
                    String itemId = itemLineDTO.getItemId();
                    //预订单详情表
                    OrderItemDTO orderItemDTO = new OrderItemDTO();
                    orderItemDTO.setItemId(itemId);
                    orderItemDTO.setItemLineId(itemLineDTO.getItemLineId());
                    orderItemDTO.setOrderId(orderId);
                    itemEntityList.add(orderItemDTO);
                }
                //批量添加order-item表
                if (null != itemEntityList && itemEntityList.size() > 0) {
                    insertItemCount = orderDAO.batchInsertOrderItemList(itemEntityList);
                }
                //mysql中取出order表中的orderNo,得到itemId，用以查询es中商品信息
                if (insertItemCount > 0) {
                    return Long.toString(orderId);
                }
            } catch (Exception e) {
                logger.error("=========" + e.toString());
                throw new AddOrderException(ResultStatusConstant.ADD_ORDER_PARAM_ERROR);
            }

        }
        return null;
    }




    /**
     *
     * 获取商品匹配列表
     * @param orderItemRequestVO
     * @return
     * @throws BusinessException
     */
    @Override
    public OrderItemMatchVO findCurrentItemLineList(OrderItemRequestVO orderItemRequestVO) throws BusinessException {
        if (ObjectUtil.isEmpty(orderItemRequestVO.getProvinceCode())  || ObjectUtil.isEmpty(orderItemRequestVO.getProvinceName()) ||
                ObjectUtil.isEmpty(orderItemRequestVO.getCityCode()) || ObjectUtil.isEmpty(orderItemRequestVO.getCityName())
                || ObjectUtil.isEmpty(orderItemRequestVO.getCountyCode()) || ObjectUtil.isEmpty(orderItemRequestVO.getCountyName())
                || ObjectUtil.isEmpty(orderItemRequestVO.getItemList())) {
            logger.error("商品匹配参数为空=========" + JSONObject.toJSON(orderItemRequestVO));
            throw new BusinessException(ResultStatusConstant.ORDER_ITEM_MATCH_PARAM_CAN_NOT_NULL);
        }
        OrderItemMatchVO  orderItemMatchVO   =  new OrderItemMatchVO();
        try {
            List<ItemVO> itemVOList = JSONUtil.toList(JSON.toJSONString(orderItemRequestVO.getItemList()), ItemVO.class);
            if (checkItemEmpty(itemVOList)) {
                logger.error("商品匹配参数为空=========" + JSONObject.toJSON(itemVOList));
                throw new BusinessException(ResultStatusConstant.ORDER_ITEM_MATCH_PARAM_CAN_NOT_NULL);
            }
            Map<String, Object> itemDataMap = getItemMap(itemVOList);
            List<String> itemIds = new ArrayList<>(itemVOList.size());
            itemVOList.forEach(item -> itemIds.add(item.getItemId()));
            Set<String> arangeCodeMatchSet = orderDAO.findAreaRangeCode(orderItemRequestVO.getCityCode());
            if(ObjectUtil.isEmpty(arangeCodeMatchSet)){
                logger.error("匹配区域为空=========" + JSONObject.toJSON(arangeCodeMatchSet));
                throw new BusinessException(ResultStatusConstant.ORDER_ITEM_MATCH_PARAM_CAN_NOT_NULL);   
            }
            /*查询匹配的商品集合*/
            List<ItemMatchAggVO> itemMatchAggVOList = orderDAO.findItemMatchInfo(itemIds,arangeCodeMatchSet);
            /*获取会员等级*/
            String customerLevel = userLevelDAO.getUserLevel(orderItemRequestVO.getUserId());
            /*获取未匹配商品集合*/
            List<ItemMatchVO> matchVOList = formateItemMatchAggVO(itemMatchAggVOList, customerLevel, itemDataMap, arangeCodeMatchSet, itemIds);
           if(ObjectUtil.isNotEmpty(itemIds)) {
               List<ItemMatchVO> unMatchAggVOList = formateItemUnMatchAggVO(itemDataMap, itemIds);
               matchVOList.addAll(unMatchAggVOList);
           }
           /*计算总数量何总价格，议价或者未匹配不计入*/
            setTotalAmountAndCount(orderItemMatchVO, matchVOList);
            sortMatchList(matchVOList);
            orderItemMatchVO.setList(matchVOList);
        }catch(Exception  e){
            logger.error("商品匹配数据为空=========" + e.getMessage());
            e.printStackTrace();
        }
        return orderItemMatchVO;
    }
  

    /**
     * 匹配结果集转换
     * @return
     */
    private List<ItemMatchVO>  formateItemMatchAggVO(List<ItemMatchAggVO>  itemmatchAggVOs,String level,Map itemMap,Set rangCodeSet, List<String>  itemIds){
        List<ItemMatchVO>  itemMatchVOS  = new ArrayList<ItemMatchVO>();
        ItemMatchVO  itemMatchVO  = null;
        Integer  count  = 0;
        for(ItemMatchAggVO  itemMatchAggVO  : itemmatchAggVOs ){
            for(ItemLineAggVO  itemLineAggVO  : itemMatchAggVO.getItemLineList()){
                if(rangCodeSet.contains(itemLineAggVO.getAreaRangeCode())) {
                    itemIds.remove(itemMatchAggVO.getItemId());
                    itemMatchVO = new ItemMatchVO();
                    itemMatchVO.setItemName(itemMatchAggVO.getItemName());
                    itemMatchVO.setItemId(itemMatchAggVO.getItemId());
                    if(ObjectUtil.isNotEmpty(itemMatchAggVO.getSpecContents())) {
                        itemMatchVO.setItemSpecList(formateSpecContent(itemMatchAggVO.getSpecContents()));
                    }
                    count = itemMap.get(itemMatchAggVO.getItemId()) == null ? 0 : (Integer) itemMap.get(itemMatchAggVO.getItemId());
                    itemMatchVO.setCount(count);
                    itemMatchVO.setItemLineId(itemLineAggVO.getItemLineId());
                    itemMatchVO.setItemLineCode(itemLineAggVO.getItemLineCode());
                    itemMatchVO.setMatched(true);
                        switch (level) {
                            case "00":
                                itemMatchVO.setUnitPrice(itemLineAggVO.getSalePrice1());
                                break;
                            case "10":
                                /** 高级会员售价 */
                                itemMatchVO.setUnitPrice(itemLineAggVO.getSalePrice2());
                                break;
                            case "20":
                                /** VIP会员售价 */
                                itemMatchVO.setUnitPrice(itemLineAggVO.getSalePrice3());
                                break;
                            default:
                                itemMatchVO.setUnitPrice(itemLineAggVO.getSalePrice1());
                                break;
                        }
                    if(ObjectUtil.isEmpty(itemMatchVO.getUnitPrice())){
                        itemMatchVO.setUnitPrice(new BigDecimal("-1"));
                    }else if(itemMatchVO.getUnitPrice().compareTo(new BigDecimal("0")) ==0){
                        itemMatchVO.setUnitPrice(new BigDecimal("-1"));
                    }
                    itemMatchVOS.add(itemMatchVO);
                    break;
                }
              
            }

        }
        return itemMatchVOS;
    }


    /**
     * 未匹配结果集转换
     * @return
     */
    private List<ItemMatchVO>  formateItemUnMatchAggVO( Map itemMap,List<String>  itemIds){
        
        List<ItemMatchVO>  itemUnMatchVOS  = new ArrayList<ItemMatchVO>();
        Integer  count  = 0;
        ItemMatchVO  itemUnMatchVO  = null;
        List<ItemMatchAggVO>  itemMatchAggVOList    = orderDAO.findUnItemMatchInfo(itemIds);
        for(String itemId : itemIds){
            for(ItemMatchAggVO  itemMatchAggVO  : itemMatchAggVOList ){
                if(itemId.equals(itemMatchAggVO.getItemId())){
                    itemUnMatchVO  = new ItemMatchVO();
                    itemUnMatchVO.setItemName(itemMatchAggVO.getItemName());
                    itemUnMatchVO.setItemId(itemMatchAggVO.getItemId());
                    itemUnMatchVO.setMatched(false);
                    itemUnMatchVO.setUnitPrice(new BigDecimal("-1"));
                    if(ObjectUtil.isNotEmpty(itemMatchAggVO.getSpecContents())) {
                        itemUnMatchVO.setItemSpecList(formateSpecContent(itemMatchAggVO.getSpecContents()));
                    }
                    count = itemMap.get(itemMatchAggVO.getItemId()) == null ? 0 : (Integer) itemMap.get(itemMatchAggVO.getItemId());
                    itemUnMatchVO.setCount(count);
                    itemUnMatchVOS.add(itemUnMatchVO);
                    break;
                }
            }
        }
        
        return itemUnMatchVOS;
    }
    private boolean checkItemEmpty( List<ItemVO> itemVOList){
        for(ItemVO itemVO  : itemVOList){
            if(ObjectUtil.isEmpty(itemVO.getCount())  ||  ObjectUtil.isEmpty(itemVO.getItemId())){
                return true;
            }
        }
        return false;
    }
    public void checkAccount(String userId)throws AddOrderException{
        try {
            LoginDTO account = accountDAO.findById(userId);
            if(ObjectUtil.isEmpty(account)){
                throw new AddOrderException(ResultStatusConstant.ACCOUNT_DATA_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error("=======创建订单账号无效"+e.getMessage());
            throw new AddOrderException(ResultStatusConstant.ACCOUNT_DATA_EXCEPTION);
        }
    }

    /**
     *创建订单
     */
    @Override
    public SaleContractInfoVO createSalesOrder(SalesOrderVO salesOrderVO)throws AddOrderException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        Map<String, Object> validation = ValidatorUtils.validation(salesOrderVO);
        if (ObjectUtil.isNotEmpty(validation)) {
            AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                    .ORDER_PARAM_CAN_NOT_NULL);
            addOrderException.setMessage(JSON.toJSONString(validation));
            throw addOrderException;
        }
        try {
            checkAccount(salesOrderVO.getUserId());
        } catch (AddOrderException e) {
            throw e;
        }
        OrederItemDataDTO orederItemDataDTO=new OrederItemDataDTO();
        try {
            orederItemDataDTO = ObjectUtil.transfer(salesOrderVO, OrederItemDataDTO.class);
            orederItemDataDTO.setCusSiteCountryCode("CN");
            orederItemDataDTO.setCusSiteCountryName("中国");
            orederItemDataDTO.setFromType("MARKET");
            orederItemDataDTO.setExpectedReceiptDate(dateFormat.format(new Date(salesOrderVO.getDeliveryEndDate())));
            UserInfoVO userInfoVO = userInfoDAO.getUserInfoByUserId(salesOrderVO.getUserId());
            orederItemDataDTO.setOperatorUserId(userInfoVO.getUserId());
            orederItemDataDTO.setOperatorUserName(userInfoVO.getUserName());
        } catch (SystemException e) {
            logger.error("=======创建订单转换DTO异常:"+e.getMessage());
            throw new AddOrderException(ResultStatusConstant.PARAM_FORMAT_FAILURE);
        }
        BxApiResult bxApiResult=new BxApiResult();
        try {
            String resultStr = apiExecutorBxService.createSomOrder(orederItemDataDTO);
            if(StringUtil.isNotBlank(resultStr)){
                bxApiResult= JSONUtil.toBean(resultStr, BxApiResult.class);
            }else{
                throw new AddOrderException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        } catch (Exception e) {
            logger.error("====远程接口调用异常"+e.getMessage());
            throw new AddOrderException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        if (ObjectUtil.isNotEmpty(bxApiResult)) {
            if ("0".equals(bxApiResult.getStatus().toString())) {
                logger.error("调用接口返回数据错误");
                AddOrderException addOrderException = new AddOrderException(ResultStatusConstant
                        .CALLING_INTERFACE_EXCEPTION);
                addOrderException.setMessage(bxApiResult.getMessage());
                throw addOrderException;
            }else{
                List itemLineList = salesOrderVO.getItemLineList();
                try {
                    if(ObjectUtil.isNotEmpty(itemLineList)){
                        List<OrderSOMItemLineDTO> orderLineDTOS = JSONUtil.toList(itemLineList.toString(),OrderSOMItemLineDTO.class);
                        Map<String,Object> paramsMap=new HashMap<>();
                        paramsMap.put("userId",salesOrderVO.getUserId());
                        List<String> orderLineIds=new ArrayList<>();
                        for(int k=0;k<orderLineDTOS.size();k++){
                            OrderSOMItemLineDTO orderSOMItemLineDTO = orderLineDTOS.get(k);
                            String itemLineId = orderSOMItemLineDTO.getItemLineId();
                            if(StringUtil.isNotBlank(itemLineId)){
                                orderLineIds.add(itemLineId);
                            }
                        }
                        paramsMap.put("orderItemLineList",orderLineIds);
                        //更改购物车内商品的状态;
                        int updCartCount = mallCartDAO.updateCartStatus(paramsMap);
                    }
                } catch (Exception e) {
                    logger.error("=====订单创建成功,更改购物车内商品的状态失败:"+e.getMessage());
                }
                SaleContractInfoVO  saleContractInfoVO=new SaleContractInfoVO();
                //订单id
                String contractId=bxApiResult.getPkId();
                //查询订单详情
                String contractCode = contractInfoDAO.getContractCodeById(contractId);
                saleContractInfoVO.setSalesContractCode(contractCode);
                return saleContractInfoVO;
            }
        }else{
            throw new AddOrderException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
    }
    
    
    private List<ItemSpecVO>  formateSpecContent(String  contents ){
        List<ItemSpecVO>  itemSpecVOS  = new ArrayList<>();
        List<Map>  itemsMap  = JSONUtil.toList(contents, Map.class);
        for(Map  map   : itemsMap){
            ItemSpecVO   itemSpecVO   = new ItemSpecVO();
            itemSpecVO.setAttributeName((String)map.get("ATTRIBUTE_NAME"));
            itemSpecVO.setAttributeValue((String)map.get("ATTRIBUTE_VALUE"));
            itemSpecVO.setOrderSort((String)map.get("ORDER_SORT"));
            itemSpecVOS.add(itemSpecVO);
        }
        return itemSpecVOS;
    }


   
    

    
    
    private Map<String,Object>  getItemMap(List<ItemVO>  itemVOS){
        Map  itemMap = new HashMap<String,Object>();
        itemVOS.forEach(data -> itemMap.put(data.getItemId(),data.getCount()));
        return itemMap;
    }
   
    
   
    
   private  void   setTotalAmountAndCount (OrderItemMatchVO orderItemMatchVO,List<ItemMatchVO> itemList){
        if(ObjectUtil.isNotEmpty(itemList)) {
            BigDecimal totalAmount = new BigDecimal("0");
            Integer totalCount = 0;
            for (ItemMatchVO item : itemList) {
                if (item.isMatched() && !item.getUnitPrice().toString().equals("-1")) {
                    totalAmount = totalAmount.add(item.getUnitPrice().multiply(new BigDecimal(item.getCount())));
                    totalCount = totalCount + item.getCount();
                }
            }
            orderItemMatchVO.setTotalAmount(totalAmount);
            orderItemMatchVO.setTotalCount(totalCount);
        }else{
            orderItemMatchVO.setTotalCount(0);
            orderItemMatchVO.setTotalAmount(new BigDecimal("0"));
        }
   }     
   
    private    void  sortMatchList(  List<ItemMatchVO>  itemMatchVOS){
       for(ItemMatchVO  itemMatchVO  :  itemMatchVOS){
           if(ObjectUtil.isNotEmpty(itemMatchVO.getItemSpecList())){
           Collections.sort(itemMatchVO.getItemSpecList(), (o1, o2) -> {
               if(StringUtil.isEmpty(o2.getOrderSort())){ 
                   return  (o1.getAttributeName().compareTo(o2.getAttributeName())) ;  
               }else {
                   return (o1.getOrderSort().compareTo(o2.getOrderSort()));
               }});  
       }}
    }

    public long getRandomId() {
        long num = 0;
        try {
            long l = (int) Math.round(Math.random() * (99999 - 10000) + 10000);
            num = l;
        } catch (Exception e) {
            logger.error("====getRandomId={}", e.toString());
        }
        return num;
    }

    /**
     * 需要查询的商品信息 json格式
     *
     * @return
     */
    public String getResultJson() {
        String responseJson = "{" +
                "                \"ITEM_ID\":\"\",\n" +
                "                \"ITEM_CODE\":\"\",\n" +
                "                \"ITEM_NAME\":\"\",\n" +
                "                \"cmc_item_line\":[\n" +
                "                ]\n" +
                "            }";
        return responseJson;
    }

   
}
