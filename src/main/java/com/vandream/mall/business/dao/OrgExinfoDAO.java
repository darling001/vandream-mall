package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.OrgExinfo;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

public interface OrgExinfoDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    OrgExinfo selectByPrimaryKey(String orgExinfoId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    OrgExinfo findVandreamCompanyInfo();
}