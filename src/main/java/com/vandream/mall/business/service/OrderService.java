package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.AddOrderReturnInformationDTO;
import com.vandream.mall.business.dto.order.OrderItemDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.vo.AddOrderVO;
import com.vandream.mall.business.vo.OrderResponseVO;
import com.vandream.mall.business.execption.AddOrderException;
import com.vandream.mall.business.vo.order.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/7 10:16
 */
public interface OrderService {

    /**
     *
     * @param addOrderVO
     * @return
     * @throws AddOrderException
     */
    AddOrderReturnInformationDTO addOrder(AddOrderVO addOrderVO) throws Exception;

    /**
     * 批量下单 返回商品参数
     * @param batchOrderVO
     * @return
     */
     OrderResponseVO addBatchOrder(BatchOrderVO batchOrderVO)throws AddOrderException;
    /**
     * 生成预订单advance order
     */
    String createAdvanceOrder(String userId,List<String> itemLineIds)throws AddOrderException;

     OrderItemMatchVO  findCurrentItemLineList(OrderItemRequestVO orderItemRequestVO) throws BusinessException;

    /**
     * 创建订单
     * @param salesOrderVO
     * @return
     * @throws AddOrderException
     */
    public SaleContractInfoVO createSalesOrder(SalesOrderVO salesOrderVO)throws AddOrderException;
}
