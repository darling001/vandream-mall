package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SecurityInfoException;
import com.vandream.mall.business.vo.UserInfoVO;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

/**
 * Create by shkstart on 2018/3/19.
 */
public interface SecurityService {
    /**
     *
     * @param userId
     * @return
     * @throws SecurityInfoException
     */
    UserInfoVO getUserInfoByUserId(String userId) throws SecurityInfoException;

    /**
     *
     * @param phoneNumber
     * @param password
     * @param confirmPwd
     */
    void modifyPassword(String phoneNumber, String password, String confirmPwd) throws SecurityInfoException, NoSuchAlgorithmException, BusinessException;

    /**
     *
     * @param userId
     * @param phoneNumber
     */
    void modifyPhone(String userId, String phoneNumber,String verifyCode) throws InvocationTargetException;

}
