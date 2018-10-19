package com.vandream.mall.business.service;

import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeGoodsDTO;
import com.vandream.mall.business.execption.PurchaseContractException;
import com.vandream.mall.business.vo.delivery.DeliveryGoodsVO;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 20:21
 * Description: 供方发货通知单详情中的商品信息
 */
public interface PurchaseContractService {

    /**
     * 获取供方发货通知单详情中的商品信息
     *
     * @param deliveryNoticeId 发货通知单id
     * @return 发货通知单的商品信息列表
     * @throws PurchaseContractException
     */
    List<DeliveryNoticeGoodsDTO> getDeliveryNoticeGoodsInfo(String deliveryNoticeId) throws PurchaseContractException;

    /**
     * 获取供方发货单详情中的商品信息
     *
     * @param deliveryNoticeId 发货通知单id
     * @return 发货单的商品信息列表
     * @throws PurchaseContractException
     */
    List<DeliveryGoodsVO> getDeliveryGoodsInfo(String deliveryNoticeId) throws PurchaseContractException;

}
