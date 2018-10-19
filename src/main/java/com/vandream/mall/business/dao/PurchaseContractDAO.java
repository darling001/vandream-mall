package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.delivery.DeliveryGoodsDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeGoodsDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 19:54
 * Description: 供方发货通知单详情中的商品信息
 */
@Component
public interface PurchaseContractDAO {

    /**
     * 获取供方发货通知单详情中的商品信息
     *
     * @param deliveryNoticeId
     * @return
     */
    List<DeliveryNoticeGoodsDTO> getDeliveryNoticeGoodsInfo(@Param("deliveryNoticeId") String deliveryNoticeId);

    /**
     * 获取供方发货单详情中的商品信息
     *
     * @param deliveryNoticeId
     * @return
     */
    List<DeliveryGoodsDTO> getDeliveryGoodsInfo(@Param("deliveryNoticeId") String deliveryNoticeId);

    /**
     * 获取供方已发货的商品信息
     *
     * @param deliveryHeadId
     * @return
     */
    List<DeliveryGoodsDTO> getDeliverySendedGoodsInfo(@Param("deliveryHeadId") String deliveryHeadId);
}
