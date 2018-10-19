package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.solution.SolutionItemLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;

/**
 * @author Li Jie
 */
public interface SolutionItemLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionItemLineDTO selectByPrimaryKey(String solutionItemLineId);
}