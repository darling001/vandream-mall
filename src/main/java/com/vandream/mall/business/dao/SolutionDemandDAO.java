package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.solution.SolutionDemandDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author Li Jie
 */
public interface SolutionDemandDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionDemandDTO selectByPrimaryKey(String solutionDemandId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionDemandDTO> findListBySolutionId(String solutionId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionDemandDTO> findSolutionItemInfoList(@Param("solutionId") String solutionId,
                                                     @Param("supplierId")
                                                             String supplierId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionDemandDTO> findSolutionSupplierItemInfoList(@Param("solutionSupplierId") String solutionSupplierId,
                                                     @Param("supplierId") String supplierId);

}