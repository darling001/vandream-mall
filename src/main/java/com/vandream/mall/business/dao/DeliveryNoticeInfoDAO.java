package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.buyerContract.DeliveryInfoVO;
import com.vandream.mall.business.vo.buyerContract.DeliveryNoticeInfoVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/4 13:23
 */
@Component
public interface DeliveryNoticeInfoDAO {
    /**
     * 发货通知单详情
     * @param userId
     * @param deliveryNoticeId
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryNoticeInfoVO getDeliveryNoticeInfo(@Param("userId")String userId, @Param("deliveryNoticeId")String deliveryNoticeId);
}
