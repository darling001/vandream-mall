package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseSettlementLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie & Shi Feng
 */
public interface PurchaseSettlementLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseSettlementLineDTO selectByPrimaryKey(String purchaseSettlementLineId);
}