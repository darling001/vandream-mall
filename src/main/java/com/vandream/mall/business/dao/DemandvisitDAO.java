package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.demand.DemandvisitDTO;

public interface DemandvisitDAO {
    int deleteByPrimaryKey(String demandVisitId);

    int insert(DemandvisitDTO record);

    int insertSelective(DemandvisitDTO record);

    DemandvisitDTO selectByPrimaryKey(String demandVisitId);

    int updateByPrimaryKeySelective(DemandvisitDTO record);

    int updateByPrimaryKey(DemandvisitDTO record);
}