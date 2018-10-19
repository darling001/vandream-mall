package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.FindContractException;
import com.vandream.mall.business.vo.buyerContract.FindDeliverInfoVO;
import com.vandream.mall.business.vo.buyerContract.FindDeliverNoticeVO;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/29 16:17
 */
public interface FindDeliverService {

    List<FindDeliverNoticeVO> findDeliverNoticeList(String userId, String companyId, String contractId) throws FindContractException;

    List<FindDeliverInfoVO> findDeliverInfoList(String userId, String companyId, String contractId) throws FindContractException;
}