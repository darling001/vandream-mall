package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.SecurityInfoException;

/**
 * Create by shkstart on 2018/3/19.
 */
public interface VerifyCodeService {
    void checkVerifyCode(String phoneNumber, String verifyCode,String operationType) throws SecurityInfoException;
}
