package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.ValueSetLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface ValueSetLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    ValueSetLineDTO selectByPrimaryKey(String valuesetLineId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<ValueSetLineDTO> findListByValueSetHeadId(String valueSetCode);
}