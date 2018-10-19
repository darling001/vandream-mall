package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.demand.DemandLineDTO;
import com.vandream.mall.business.dto.demand.DemandLineDetailDTO;
import com.vandream.mall.business.vo.demand.DemandVisitLogVO;
import com.vandream.mall.business.vo.demand.DemandRequestVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface DemandLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    DemandLineDTO selectByPrimaryKey(String demandLineId);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandLineDTO> findItemList(Map<String, Object> paraMap);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandLineDetailDTO> getDemandLineList(DemandRequestVO demandRequestVO);
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<DemandVisitLogVO> getdemandVisitLogs(DemandRequestVO demandRequestVO);
}