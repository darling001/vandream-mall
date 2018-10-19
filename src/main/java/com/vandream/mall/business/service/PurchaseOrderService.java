package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.base.DataListVO;
import com.vandream.mall.business.vo.delivery.DeliveryVO;
import com.vandream.mall.business.vo.purchase.ConfirmOrderVO;
import com.vandream.mall.business.vo.purchase.OrderInfoVO;
import com.vandream.mall.business.vo.purchase.PurchaseOrderListVO;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author shifeng
 */
public interface PurchaseOrderService {
    /**
     * 订单列表
     *
     * @param userId
     * @param companyId
     * @param keyword
     * @param status
     * @param startTime
     * @param endTime
     * @param pageSize
     * @param pageNo
     * @return
     * @throws InvocationTargetException
     */
    DataListVO<PurchaseOrderListVO> findOrderList(String userId, String companyId, String supplierId, String
            keyword, String status, Long startTime, Long endTime, Integer pageSize, Integer
                                                          pageNo) throws
            InvocationTargetException;

    /**
     * 发货单列表信息
     *
     * @param userId
     * @param purchaseContractHeadId
     * @return
     * @throws InvocationTargetException
     */
    List<DeliveryVO> findInvoiceList(String userId, String purchaseContractHeadId) throws
            InvocationTargetException;

    /**
     * 订单详情
     *
     * @param userId
     * @param purchaseContractHeadId
     * @return
     * @throws InvocationTargetException
     */
    OrderInfoVO getOrderInfo(String userId, String purchaseContractHeadId) throws
            InvocationTargetException;

    String confirmOrder(ConfirmOrderVO confirmOrderVO) throws Exception;
}
