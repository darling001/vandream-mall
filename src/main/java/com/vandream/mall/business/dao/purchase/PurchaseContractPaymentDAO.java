package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractPaymentDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

import java.util.List;

/**
 * @author Li Jie & Shi Feng
 */
public interface PurchaseContractPaymentDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractPaymentDTO selectByPrimaryKey(String contractPaymentId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<PurchaseContractPaymentDTO> findListByHeadId(String purchaseContractHeadId);
}