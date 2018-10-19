package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractLineDTO;

import java.util.List;

import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Li Jie & Shi Feng
 */
@Mapper
public interface PurchaseContractLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractLineDTO selectByPrimaryKey(String purchaseContractLineId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<PurchaseContractLineDTO> findOrderAggInfo(@Param("purchaseContractHeadIdList") List<String> purchaseContractHeadIdList);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<PurchaseContractLineDTO> findListByHeadId(String purchaseContractHeadId);
}