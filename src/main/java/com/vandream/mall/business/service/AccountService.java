package com.vandream.mall.business.service;

import com.vandream.mall.business.vo.LoginVO;
import java.lang.reflect.InvocationTargetException;

/**
 * @author Li Jie
 */
public interface AccountService {
    /**
     * 手机号校验
     *
     * @param phoneNumber
     * @return
     */
    String verifyPhone(String phoneNumber) throws InvocationTargetException;

    /**
     * 用户注册
     *
     * @param phoneNumber
     * @param verifyCode
     * @param password
     * @param confirmPwd
     * @return
     */
    String register(String phoneNumber, String verifyCode, String password, String
            confirmPwd) throws Exception;

    /**
     * 用户登录
     *
     * @param phoneNumber
     * @param password
     * @param type
     * @return
     */
    LoginVO login(String phoneNumber, String password, Integer type) throws
            InvocationTargetException;

    /**
     * 根据token 获取对应用户的信息
     * @param token
     * @return
     * @throws InvocationTargetException
     */
    LoginVO getUserInfo(String token) throws InvocationTargetException;

    /**
     * 查询当前用户认证状态
     * @param userId
     * @return
     */
    String getAccountFlagByUserId(String userId)throws InvocationTargetException;

    void logout(String token);

    /**
     * 验证手机号是否已注册过(用于修改密码时验证)
     * @param phoneNumber
     * @return
     * @throws InvocationTargetException
     */
    boolean verifyPhoneExist(String phoneNumber)throws InvocationTargetException;

}
