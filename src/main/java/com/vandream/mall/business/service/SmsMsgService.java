package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.SmsMsgException;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 18:55
 * Description:
 */
public interface SmsMsgService {
    Boolean sendSmsCode(String phoneNumber,String templateCode,String operationType)throws SmsMsgException;
}
