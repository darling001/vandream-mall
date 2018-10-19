package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vandream.mall.business.constant.AttachmentType;
import com.vandream.mall.business.dao.*;
import com.vandream.mall.business.dao.delivery.DeliveryHeadDAO;
import com.vandream.mall.business.dao.purchase.*;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.OrgExinfo;
import com.vandream.mall.business.dto.StaffDTO;
import com.vandream.mall.business.dto.SupplierContactsDTO;
import com.vandream.mall.business.dto.aus.AttachmentDTO;
import com.vandream.mall.business.dto.delivery.DeliveryHeadDTO;
import com.vandream.mall.business.dto.purchase.*;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.CompanyException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.PurchaseOrderService;
import com.vandream.mall.business.service.ValueSetService;
import com.vandream.mall.business.vo.AttachmentVO;
import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.delivery.DeliveryVO;
import com.vandream.mall.business.vo.purchase.*;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import com.vandream.mall.commons.utils.ValidatorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng & Li Jie
 * @date : 2018/4/4
 * @time : 17:41
 * Description:
 */
@Service(value = "purchaseOrderService")
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseOrderServiceImpl.class);

    private static final String VALUE_SET_CODE_UNIT_TYPE = "tlerp.ausbs.unitType";

    @Resource
    private PurchaseContractHeadDAO purchaseContractHeadDAO;

    @Resource
    private PurchaseContractLineDAO purchaseContractLineDAO;

    @Resource
    private PurchaseContractCompanyDAO purchaseContractCompanyDAO;

    @Resource
    private PurchaseContractRecordDAO purchaseContractRecordDAO;

    @Resource
    private PurchaseContractPaymentDAO purchaseContractPaymentDAO;

    @Resource
    private DeliveryHeadDAO deliveryHeadDAO;

    @Resource
    private StaffDAO staffDAO;

    @Resource
    private AttachmentDAO attachmentDAO;

    @Resource
    private ApiExecutorBxService apiExecutorBxService;

    @Resource
    private ValueSetService valueSetService;

    @Resource
    private OrgExinfoDAO orgExinfoDAO;

    @Resource
    private SupplierContactsDAO supplierContactsDAO;

    @Resource
    private ItemDAO itemDAO;

    @Override
    public DataListVO<PurchaseOrderListVO> findOrderList(String userId, String companyId, String supplierId, String
            keyword, String status, Long startTime, Long endTime, Integer pageSize, Integer pageNo)
            throws InvocationTargetException {
        //参数校验
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(companyId) || StringUtil.isBlank(supplierId)) {
            logger.info("入参userId={},入参companyId={}", userId, companyId, supplierId);
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (ObjectUtil.isNotEmpty(endTime)) {
            endTime += (1000 * 60 * 60 * 24);
        }
        //将参数封装进Map,作为查询条件
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId", userId);
        paramMap.put("companyId", companyId);
        paramMap.put("keyword", keyword);
        paramMap.put("status", status);
        paramMap.put("startTime", startTime);
        paramMap.put("endTime", endTime);
        paramMap.put("supplierId", supplierId);


        //处理分页参数，设置默认值
        if (pageNo == null || pageNo == 0) {
            pageNo = 1;
        }
        if (pageSize == null || pageSize == 0) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNo, pageSize);

        //查取采购订单列表
        List<PurchaseContractHeadDTO> purchaseOrderList = purchaseContractHeadDAO.findOrderList
                (paramMap);
        PageInfo<PurchaseContractHeadDTO> pageInfo = new PageInfo<>(purchaseOrderList);
        //DTO转VO
        List<PurchaseOrderListVO> purchaseOrderVOList = null;
        try {
            purchaseOrderVOList = ObjectUtil.transfer(purchaseOrderList, PurchaseOrderListVO.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new SystemException();
        }

        //取出purchaseContractHeadId作为清单数量、预交日期的查询条件
        List<String> purchaseContractHeadIdList = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(purchaseOrderVOList)) {
            for (PurchaseOrderListVO purchaseOrderListVO : purchaseOrderVOList) {
                if (purchaseOrderListVO.getOrderId() != null) {
                    purchaseContractHeadIdList.add(purchaseOrderListVO.getOrderId());
                }
                //查询此订单下是否存在发货单
                if (purchaseOrderListVO.getDeliveryHeadId() == null || "".equals(purchaseOrderListVO.getDeliveryHeadId())) {
                    purchaseOrderListVO.setIsExistDeliveryInfo(false);
                } else {
                    purchaseOrderListVO.setIsExistDeliveryInfo(true);
                }
            }
        }

        //查询清单数量(itemTotal)、预交日期（maxExpectedDelivery,minExpectedDelivery）
        if (ObjectUtil.isNotEmpty(purchaseContractHeadIdList)) {
            List<PurchaseContractLineDTO> orderAggInfoList = purchaseContractLineDAO
                    .findOrderAggInfo(purchaseContractHeadIdList);
            Map<String, PurchaseContractLineDTO> lineListMap = new HashMap<>(orderAggInfoList.size());
            for (PurchaseContractLineDTO purchaseContractLineDTO : orderAggInfoList) {
                lineListMap.put(purchaseContractLineDTO.getPurchaseContractHeadId(), purchaseContractLineDTO);
            }

            for (PurchaseOrderListVO purchaseOrderListVO : purchaseOrderVOList) {
                PurchaseContractLineDTO purchaseContractLineDTO = lineListMap.get(purchaseOrderListVO.getOrderId());
                if (purchaseContractLineDTO != null) {
                    purchaseOrderListVO.apply(purchaseContractLineDTO);
                }
            }

        }

        //组装VO数据
        DataListVO<PurchaseOrderListVO> dataListVO = new DataListVO(pageInfo);
        dataListVO.setList(purchaseOrderVOList);
        return dataListVO;
    }

    @Override
    public List<DeliveryVO> findInvoiceList(String userId, String purchaseContractHeadId) throws
            InvocationTargetException {
        if (StringUtil.isBlank(userId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (StringUtil.isBlank(purchaseContractHeadId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        List<DeliveryHeadDTO> invoiceList = deliveryHeadDAO.findInvoiceList(userId, purchaseContractHeadId);
        try {
            List<DeliveryVO> voList = ObjectUtil.transfer(invoiceList, DeliveryVO.class);
            return voList;
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new SystemException();
        }


    }

    @Override
    public OrderInfoVO getOrderInfo(String userId, String purchaseContractHeadId) throws
            InvocationTargetException {
        if (StringUtil.isBlank(userId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (StringUtil.isBlank(purchaseContractHeadId)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        OrderInfoVO orderInfoVO = null;
        //主信息查询
        PurchaseContractHeadDTO headDTO = purchaseContractHeadDAO
                .selectByPrimaryKey(purchaseContractHeadId);
        if (headDTO == null) {
            throw new BusinessException(ResultStatusConstant.DATA_READ_FAIL);
        }
        try {
            orderInfoVO = ObjectUtil.transfer(headDTO, OrderInfoVO.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new SystemException();
        }
        //合同内我方企业信息
        PurchaseContractCompanyDTO companyDTO = purchaseContractCompanyDAO
                .selectByPurchaseContractHeadIdM(purchaseContractHeadId);
        OrgExinfo vandreamCompanyInfo = orgExinfoDAO.findVandreamCompanyInfo();
        if (companyDTO != null) {
            //开票信息
            orderInfoVO.apply(companyDTO, vandreamCompanyInfo);
        }


        //招采信息
        if (StringUtil.isNotBlank(headDTO.getExtCol4())) {
            StaffDTO purchaser = staffDAO.findByStaffId(headDTO.getExtCol4());
            orderInfoVO.apply(purchaser);
        }

        //确认人信息
        PurchaseContractRecordDTO recordDTO = purchaseContractRecordDAO
                .selectByHeadIdAndType(purchaseContractHeadId, "50");
        if (recordDTO != null) {
            orderInfoVO.apply(recordDTO);
        }


        //itemList
        List<PurchaseContractLineDTO> itemDTOList = purchaseContractLineDAO.findListByHeadId
                (purchaseContractHeadId);
        //类目名称
        if (ObjectUtil.isNotEmpty(itemDTOList)) {
            orderInfoVO.setCategoryName(itemDTOList.get(0).getCategoryName());
        }
        //取回商品单位值集
        Map<String, String> unitTypeMap = valueSetService.getItemUnitTypeNameMap();

        List<OrderItemInfoVO> itemVOList = ObjectUtil.transfer(itemDTOList, OrderItemInfoVO
                .class);
        List<String> itemIdList = new ArrayList<>();
        for (OrderItemInfoVO orderItemInfoVO : itemVOList) {
            orderItemInfoVO.setUnit(unitTypeMap.get(orderItemInfoVO.getUnit()));
            itemIdList.add(orderItemInfoVO.getItemId());
        }
        ////取出item商品标记 是否标品
        //List<ItemDTO> itemStandardFlagList = itemDAO.queryStandardFlagByItemIdList(itemIdList);
        //Map<String, String> itemStandardFlagMap = new HashMap<>(itemStandardFlagList.size());
        ////数据结构转换
        //for (ItemDTO itemDTO : itemStandardFlagList) {
        //    itemStandardFlagMap.put(itemDTO.getItemId(), itemDTO.getStandardFlag());
        //}
        //
        //for (OrderItemInfoVO orderItemInfoVO : itemVOList) {
        //    String standardFlag = itemStandardFlagMap.get(orderItemInfoVO.getItemId());
        //    if (ObjectUtil.isEmpty(standardFlag)) {
        //        throw new BusinessException(ResultStatusConstant.DATA_NOT_FOUND.setDesc("商品数据异常!"));
        //    }
        //    orderItemInfoVO.setStandardFlag(standardFlag);
        //    if ("N".equals(standardFlag)) {
        //        orderInfoVO.setStandardFlag(standardFlag);
        //    }
        //}
        orderInfoVO.setItemList(itemVOList);


        //attachmentList contractList
        Map<String, Object> attachParaMap = new HashMap<>();
        attachParaMap.put("billNo", purchaseContractHeadId);
        List<AttachmentDTO> attachmentList = attachmentDAO.findByBillNo(attachParaMap);

        Map<String, List<AttachmentDTO>> attachmentListMap = new HashMap<>(2);
        for (AttachmentDTO attachmentDTO : attachmentList) {
            if (AttachmentType
                    .PURCHASE_CONTRACT_DOC.equalsIgnoreCase(attachmentDTO.getAttachmentType())) {
                List<AttachmentDTO> docList = attachmentListMap.get(AttachmentType
                        .PURCHASE_CONTRACT_DOC);
                if (docList == null || !docList.isEmpty()) {
                    docList = new ArrayList<>();
                }
                docList.add(attachmentDTO);
            }
            if (AttachmentType
                    .PURCHASE_CONTRACT_TEXT.equalsIgnoreCase(attachmentDTO.getAttachmentType())) {
                List<AttachmentDTO> textList = attachmentListMap.get(AttachmentType
                        .PURCHASE_CONTRACT_DOC);
                if (textList == null || !textList.isEmpty()) {
                    textList = new ArrayList<>();
                }
                textList.add(attachmentDTO);
            }
        }


        //附件和承诺列表
        List<AttachmentVO> docList = ObjectUtil.transfer(attachmentListMap.get(AttachmentType
                .PURCHASE_CONTRACT_DOC), AttachmentVO.class);
        orderInfoVO.setAttachmentList(docList);
        //合同附件列表
        List<AttachmentVO> textList = ObjectUtil.transfer(attachmentListMap.get(AttachmentType
                .PURCHASE_CONTRACT_TEXT), AttachmentVO.class);
        orderInfoVO.setContractList(textList);


        //billingInformation 采购付款明细
        List<PurchaseContractPaymentDTO> contractPaymentList = purchaseContractPaymentDAO
                .findListByHeadId(purchaseContractHeadId);
        List<PurchaseContractPaymentVO> contractPaymentVOList = ObjectUtil.transfer
                (contractPaymentList,
                        PurchaseContractPaymentVO.class);
        orderInfoVO.setBillingInformation(contractPaymentVOList);


        return orderInfoVO;
    }

    @Override
    public String confirmOrder(ConfirmOrderVO confirmOrderVO)
            throws InvocationTargetException {
        //参数校验
        Map<String, Object> validation = ValidatorUtils.validation(confirmOrderVO);
        if (validation != null && !validation.isEmpty()) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        if (!"25".equals(confirmOrderVO.getOperatorType())) {
            logger.info("operatorType={}", confirmOrderVO.getOperatorType());
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }

        //VO转DTO
        ConfirmOrderDTO confirmOrderDTO = null;
        try {
            confirmOrderDTO = ObjectUtil.transfer(confirmOrderVO, ConfirmOrderDTO.class);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }

        SupplierContactsDTO supplierContactsDTO = supplierContactsDAO.findBySupplierId
                (confirmOrderVO
                        .getSupplierId());
        confirmOrderDTO.setOperatorUserId(confirmOrderVO.getUserId());
        if (ObjectUtil.isEmpty(supplierContactsDTO)) {
            confirmOrderDTO.setOperatorUserName(confirmOrderVO.getUserName());
        } else {
            confirmOrderDTO.setOperatorUserName(supplierContactsDTO.getContactName());
        }


        //调用宝信接口
        String responseResult = null;
        try {
            responseResult = apiExecutorBxService.confirmOrder(confirmOrderDTO);
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        if (StringUtil.isBlank(responseResult)) {
            logger.info("确认订单调用错误", responseResult);
            throw new CompanyException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        BxApiResult bxApiResult = JSON.parseObject(responseResult, BxApiResult.class);

        if (bxApiResult.getStatus() == 1) {
            return "确认成功";
        } else {
            logger.info("返回参数Status={}", bxApiResult.getStatus());
            throw new BusinessException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
    }
}
