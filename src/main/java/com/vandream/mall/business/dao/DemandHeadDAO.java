package com.vandream.mall.business.dao;


import com.vandream.mall.business.dto.demand.DemandDetailDTO;
import com.vandream.mall.business.dto.demand.DemandHeadDTO;
import com.vandream.mall.business.vo.demand.DemandBillVO;
import com.vandream.mall.business.vo.demand.DemandRequestVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface DemandHeadDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandBillVO> findDemandList(DemandRequestVO demandRequestVO);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DemandHeadDTO selectByPrimaryKey(String demandId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DemandDetailDTO selectDetailByDemandId(DemandRequestVO demandRequestVO);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandBillVO> selectToBeConfirmed(DemandRequestVO demandRequestVO);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    String getOrderCodeById(String orderId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandBillVO> selectDemandSolutionList(DemandRequestVO demandRequestVO);
}