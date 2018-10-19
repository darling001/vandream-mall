package com.vandream.mall.business.dao;


import com.vandream.mall.business.dto.demand.DemandSolutionDTO;
import com.vandream.mall.business.dto.solution.SolutionHeadDTO;
import com.vandream.mall.business.dto.solution.SolutionSupplierDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Li Jie
 */
@Component
@Mapper
public interface SolutionHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionHeadDTO selectByPrimaryKey(String solutionId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandSolutionDTO> getDemandSolutionList(String userId,String demandId);


    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionHeadDTO selectBySolutionId(@Param("solutionId") String solutionId,@Param("supplierId") String
            supplierId);

    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionHeadDTO> findSolutionList(Map<String, Object> paraMap);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<SolutionHeadDTO> findSolutionSupplierList(Map<String, Object> paraMap);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    SolutionHeadDTO selectBySupplierSolutionId(@Param("solutionSupplierId") String solutionSupplierId,@Param("supplierId") String
            supplierId);
}