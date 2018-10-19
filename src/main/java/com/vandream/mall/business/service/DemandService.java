package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.DemandException;
import com.vandream.mall.business.vo.demand.*;
import java.util.List;

/**
 * @author dingjie
 * @date 2018/3/28
 * @time 18:16
 * Description:
 */
public interface DemandService {
    /**
     * 发布采购需求
     * @param demandHeadVO
     * @return
     * @throws DemandException
     */
    Boolean addDemandInfo(DemandHeadVO demandHeadVO) throws DemandException;
    DemandResponseVO findDemandList(DemandRequestVO demandRequestVO)throws DemandException;
    List<DemandSolutionVO> findSchemeList(String userId, String demandId)throws DemandException;
    Boolean updateSchemeStatus(DemandStatusVO demandStatusVO) throws DemandException;
    DemandDetailVO getDemandInfo(String userId, String demandId)throws DemandException;
}