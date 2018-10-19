package com.vandream.mall.business.dao.delivery;

import com.vandream.mall.business.dto.delivery.DeliveryLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie
 */
public interface DeliveryLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryLineDTO selectByPrimaryKey(String deliveryLineId);
}