package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.vo.buyerContract.*;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/28 19:12
 */
public interface FindContractService {

    /**
     * 导出订单pdf
     * @param userId
     * @param deliveryId
     */

    Map<String, String> exportContractPdf(String userId, String deliveryId) throws FindContractException,SystemException;
    /**
     *
     * @param companyId
     * @param keyword
     * @param validateStartDate
     * @param validateEndDate
     * @param pageSize
     * @param pageNo
     * @param contractStatus
     * @param paymentStatus
     * @param DelieveryStattus
     * @return
     * @throws FindContractException
     */
      FindContractListVO findContractList(String userId, String companyId, String keyword, Long validateStartDate, Long validateEndDate, int pageSize, int pageNo, String contractStatus, String paymentStatus, String DelieveryStattus) throws FindContractException;
    /**
     * 修改单据状态
     * @param billId
     * @param billType
     * @param companyId
     * @param companyName
     * @param userId
     * @param userName
     * @param operatorType
     */

    String updateBillStatus(String billId, String billType, String companyId, String companyName, String userId, String userName, String operatorType) throws Exception;

    /**
     * 销售合同凭证
     * @param userId
     * @param userName
     * @param contractId
     * @param fromType
     * @param bussinessType
     * @param attachmentList
     */
    String voucherUpload(String userId, String userName, String contractId, String fromType, String bussinessType, Object attachmentList) throws Exception;

    /**
     * 获取合同的对应的付款记录
     * @param userId
     * @param contractId
     *
     */
    FindPaymentHistoryVO findPaymentHistory(String userId,String contractId) throws FindContractException;

    /**
     * 获取发货通知单详情
     * @param userId
     * @param deliveryNoticeId
     */
    DeliveryNoticeInfoVO getDeliveryNoticeInfo(String userId, String deliveryNoticeId) throws FindContractException;

    /**
     * 获取发货单详情
     * @param userId
     * @param deliveryId
     */
    DeliveryInfoVO getDeliveryInfo(String userId, String deliveryId) throws FindContractException;

    /**
     * 发货单签收
     * @param userId
     * @param deliveryId
     * @param deliveryCode
     * @param userName
     * @param itemList
     * @param receiptInfo

     */
   String logisticsReceipt(String userId, String deliveryId, String deliveryCode, String userName,
                           Object itemList, String receiptInfo, List<Object> attachmentList, String bussinessType) throws Exception;

    /**
     *
     * @param userId
     * @param contractId
     * @return
     */

    ContractInfoVO getContractInfo(String userId, String contractId) throws FindContractException,SystemException;


    /**
     * 业务单据附件删除
     * @param billId
     * @param businessType
     * @param fromType
     * @param accountId
     * @param accountName
     * @param attachmentIdList
     * @return
     * @throws FindContractException
     */
    Boolean deleteAttachment(String billId,String businessType,String fromType,
                             String accountId,String accountName,String[] attachmentIdList) throws FindContractException;
}
