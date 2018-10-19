package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.FindContractException;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/30 9:50
 */
public interface ConfirmationContractService {

    String confirmationContract(String userId, String username, String contractId, String operatorType) throws Exception;
}
