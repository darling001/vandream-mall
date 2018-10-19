package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.delivery.DeliveryDetailDTO;
import com.vandream.mall.business.dto.delivery.DeliveryInfoListDTO;
import com.vandream.mall.business.dto.delivery.DeliveryRequestDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeDetailDTO;
import com.vandream.mall.business.dto.delivery.notice.DeliveryNoticeListDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/4/3
 * Time: 15:23
 * Description: 发货管理
 */
@Component
public interface DeliveryDAO {

    /**
     * 获取发货管理列表
     *
     * @param deliveryRequestDTO
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DeliveryNoticeListDTO> findDeliveryNoticeList(DeliveryRequestDTO deliveryRequestDTO);

    /**
     * 获取供方发货通知单详情
     *
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryNoticeDetailDTO getSupplierDeliveryNoticeInfo(@Param("deliveryNoticeId") String deliveryNoticeId);

    /**
     * 获取供方发货单详情
     *
     * @param deliveryNoticeId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryDetailDTO getDeliveryItem(@Param("deliveryNoticeId") String deliveryNoticeId);

    /**
     * 获取已发货商品详情
     *
     * @param deliveryHeadId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryDetailDTO getDeliveryInfo(@Param("deliveryHeadId") String deliveryHeadId);

    /**
     * 获取供方发货单列表
     *
     * @param deliveryNoticeId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DeliveryInfoListDTO> findDeliveryList(@Param("userId") String userId, @Param("deliveryNoticeId") String deliveryNoticeId);
}
