package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractHeadDTO;

import java.util.List;
import java.util.Map;

import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Li Jie & Shi Feng
 */
@Mapper
public interface PurchaseContractHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractHeadDTO selectByPrimaryKey(String purchaseContractHeadId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<PurchaseContractHeadDTO> findOrderList(Map<String, Object> paramMap);
}