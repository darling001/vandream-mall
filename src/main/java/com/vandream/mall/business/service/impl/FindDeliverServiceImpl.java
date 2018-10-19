package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.FindDeliverDAO;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.service.FindDeliverService;
import com.vandream.mall.business.vo.buyerContract.FindDeliverInfoVO;
import com.vandream.mall.business.vo.buyerContract.FindDeliverNoticeVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.StringUtil;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 16:22
 *
 * 发货通知单信息列表和发货通知列表
 */
@Service("findDeliverService")
public class FindDeliverServiceImpl implements FindDeliverService{

    private static final Logger logger = LoggerFactory.getLogger(FindDeliverServiceImpl.class);
    @Resource
    private FindDeliverDAO findDeliverDAO;

    @Override
    public List<FindDeliverNoticeVO> findDeliverNoticeList(String userId, String companyId, String contractId) throws FindContractException {

        if(StringUtil.isBlank(userId) || StringUtil.isBlank(contractId)){
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant.CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        List<FindDeliverNoticeVO> findDeliverNoticeList = null;
        try {
            findDeliverNoticeList = findDeliverDAO.findDeliverNoticeList(userId, companyId, contractId);
        }catch (Exception e){
            logger.error("=====>数据库查询异常");
            throw new FindContractException(ResultStatusConstant.QUERY_DATABASE_ERROR);
        }

        return findDeliverNoticeList;
    }

    /**
     *
     * @param userId
     * @param companyId
     * @param contractId
     * @return
     */
    @Override
    public List<FindDeliverInfoVO> findDeliverInfoList(String userId, String companyId, String contractId) throws FindContractException {
        if(StringUtil.isBlank(userId) || StringUtil.isBlank(contractId)){
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant.CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        List<FindDeliverInfoVO> findDeliverInfoList = findDeliverDAO.findDeliverInfoList(userId, companyId, contractId);
        return findDeliverInfoList;
    }
}
