package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseSettlementHeadDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie & Shi Feng
 */
public interface PurchaseSettlementHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseSettlementHeadDTO selectByPrimaryKey(String purchaseSettlementHeadId);
}