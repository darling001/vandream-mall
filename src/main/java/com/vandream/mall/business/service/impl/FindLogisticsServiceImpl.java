package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.dao.FindLogisticsDAO;
import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.service.FindLogisticsService;
import com.vandream.mall.business.vo.buyerContract.FindContractListVO;
import com.vandream.mall.business.vo.buyerContract.FindLogisticsListVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 10:02
 * 发货单物流信息列表
 */
@Service("findLogisticsService")
public class FindLogisticsServiceImpl implements FindLogisticsService {

    private static final Logger logger = LoggerFactory.getLogger(FindLogisticsServiceImpl.class);

    @Resource
    private FindLogisticsDAO findLogisticsDAO;

    @Override
    public FindLogisticsListVO findLogisticsList(String userId, String deliverInfoId) throws FindContractException {
        if (StringUtil.isBlank(userId) || StringUtil.isBlank(deliverInfoId)) {
            logger.error("参数为空");
            throw new FindContractException(ResultStatusConstant.CONTRACT_PARAMETERS_INTRODUCE_INTO_NULL);
        }
        FindLogisticsListVO findLogisticsListVO =new FindLogisticsListVO();
        try {
               findLogisticsListVO = findLogisticsDAO.findLogisticsList(userId, deliverInfoId);
        }catch (Exception e){
            logger.error("=====>数据库查询异常");
            throw new FindContractException(ResultStatusConstant.QUERY_DATABASE_ERROR);
        }
        return findLogisticsListVO;
    }
}
