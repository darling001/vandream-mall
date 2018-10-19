package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.vo.buyerContract.FindContractListVO;
import com.vandream.mall.business.vo.buyerContract.FindLogisticsListVO;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 9:49
 */
public interface FindLogisticsService {
    /**
     * @param userId
     * @param deliverInfoId
     * @return
     */
    FindLogisticsListVO findLogisticsList(String userId, String deliverInfoId) throws FindContractException;
}
