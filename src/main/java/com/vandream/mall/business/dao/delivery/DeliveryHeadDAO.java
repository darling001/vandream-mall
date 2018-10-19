package com.vandream.mall.business.dao.delivery;

import com.vandream.mall.business.dto.delivery.DeliveryHeadDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;

/**
 * @author Li Jie
 */
public interface DeliveryHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DeliveryHeadDTO selectByPrimaryKey(String deliveryHeadId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DeliveryHeadDTO> findInvoiceList(String userId,String purchaseContractHeadId);
}