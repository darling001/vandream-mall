package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractCompanyDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Li Jie
 */
@Mapper
public interface PurchaseContractCompanyDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractCompanyDTO selectByPrimaryKey(String contractCompanyId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractCompanyDTO selectByPurchaseContractHeadIdM(String purchaseContractHeadId);
}