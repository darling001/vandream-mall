package com.vandream.mall.business.dao.delivery;

import com.vandream.mall.business.dto.delivery.DeliveryReturnDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie
 */
public interface DeliveryReturnDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryReturnDTO selectByPrimaryKey(String deliveryReturnId);
}