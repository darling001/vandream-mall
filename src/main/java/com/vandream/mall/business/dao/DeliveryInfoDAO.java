package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.DeliveryInfoVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import lombok.Data;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/8 17:53
 */
@Component
public interface DeliveryInfoDAO {
    /**
     * 发货单详情
     * @param userId
     * @param deliveryId
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryInfoVO getDeliveryInfo(@Param("userId") String userId, @Param("deliveryId") String deliveryId);
}
