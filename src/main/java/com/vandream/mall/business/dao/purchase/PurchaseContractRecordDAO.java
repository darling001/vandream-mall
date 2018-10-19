package com.vandream.mall.business.dao.purchase;

import com.vandream.mall.business.dto.purchase.PurchaseContractRecordDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;

/**
 * @author Li Jie & Shi Feng
 */
public interface PurchaseContractRecordDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractRecordDTO selectByPrimaryKey(String purchaseContractRecordId);

    /**
     * 根据采购订单id和操作类型查询
     * @param purchaseContractHeadId
     * @param operatorType
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    PurchaseContractRecordDTO selectByHeadIdAndType(@Param("purchaseContractHeadId") String
                                                            purchaseContractHeadId, @Param
                                                            ("operatorType") String operatorType);
}