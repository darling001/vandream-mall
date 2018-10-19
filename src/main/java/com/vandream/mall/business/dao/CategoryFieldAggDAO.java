package com.vandream.mall.business.dao;

import com.vandream.mall.business.domain.CategoryFieldAgg;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;

/**
 * @author Li Jie
 */
public interface CategoryFieldAggDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<CategoryFieldAgg> findListByCategoryId(String categoryId);
}
