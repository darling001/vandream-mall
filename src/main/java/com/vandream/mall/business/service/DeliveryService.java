package com.vandream.mall.business.service;


import com.vandream.mall.business.execption.DeliveryException;
import com.vandream.mall.business.vo.DeliveryDetailInfoVO;
import com.vandream.mall.business.vo.delivery.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:37
 * Description: 发货管理
 */
public interface DeliveryService {

    /**
     * 获取发货管理列表
     *
     * @param requestDTO
     * @return 发货管理列表
     */
    DeliveryListVO findDeliveryNoticeList(DeliveryRequestVO requestDTO) throws DeliveryException;

    /**
     * 获取供方发货通知单详情
     *
     * @param userId           用户账号id--对应数据库的account_id
     * @param deliveryNoticeId 发货通知单id
     * @return 供方发货通知单详情页面
     * @throws DeliveryException
     */
    DeliveryNoticeDetailVO getSupplierDeliveryNoticeInfo(String userId, String deliveryNoticeId) throws DeliveryException;

    /**
     * 获取供方发货单详情
     *
     * @param userId           用户账号id--对应数据库的account_id
     * @param deliveryNoticeId 发货通知单id
     * @param supplierId       供应商id
     * @return 供方发货单详情页面
     * @throws DeliveryException
     */
    DeliveryDetailVO getDeliveryItem(String userId, String deliveryNoticeId, String supplierId) throws DeliveryException;

    /**
     * 获取已发货订单商品详情
     *
     * @param userId
     * @param userName
     * @param deliveryHeadId
     * @param companyId
     * @param companyName
     * @return
     * @throws DeliveryException
     */
    DeliveryDetailInfoVO getDeliveryInfo(String userId, String userName, String deliveryHeadId, String deliveryNoticeId, String companyId, String companyName) throws DeliveryException;

    /**
     * 提交发货信息
     *
     * @param deliverySubmitVO
     * @return operateResult：true-->发货成功;false-->发货失败
     * @throws DeliveryException
     */
    String submitDeliveryInfo(DeliverySubmitVO deliverySubmitVO) throws DeliveryException;

    /**
     * 获取供方发货单列表
     *
     * @param userId           用户账号id--对应数据库的account_id
     * @param deliveryNoticeId 发货通知单id
     * @return 供方发货单列表
     */
    List<DeliveryInfoListVO> findDeliveryList(String userId, String deliveryNoticeId) throws DeliveryException;
}
