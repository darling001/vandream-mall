package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.ValueSetHeadDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

public interface ValueSetHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    ValueSetHeadDTO selectByPrimaryKey(String valuesetHeadId);
}