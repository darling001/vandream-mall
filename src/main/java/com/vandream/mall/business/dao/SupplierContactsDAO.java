package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.SupplierContactsDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie
 */
public interface SupplierContactsDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    SupplierContactsDTO selectByPrimaryKey(String supplierContactsId);
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    SupplierContactsDTO findBySupplierId(String supplierId);
}