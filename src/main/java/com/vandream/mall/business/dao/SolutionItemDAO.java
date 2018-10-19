package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.solution.SolutionItemDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;

/**
 * @author Li Jie
 */
public interface SolutionItemDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionItemDTO selectByPrimaryKey(String solutionItemId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionItemDTO> findItemInfoList(List<String> idList);
}