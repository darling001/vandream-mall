package com.vandream.mall.business.dao;


import com.vandream.mall.business.dto.solution.SolutionSupplierDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * @author Li Jie
 */
public interface SolutionSupplierDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionSupplierDTO selectByPrimaryKey(String solutionSupplierId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionSupplierDTO> findSolutionSupplierList(@Param("solutionSupplierId") String solutionSupplierId, @Param("supplierId")
            String supplierId);
}