package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.CategoryAggDTO;
import com.vandream.mall.business.dto.ProductCategoryDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie
 */
public interface ProductCategoryDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    ProductCategoryDTO selectByPrimaryKey(String categoryId);

    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    CategoryAggDTO findFullCategory(String categoryId);
}