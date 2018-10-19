package com.vandream.mall.business.service.impl;

import com.vandream.mall.business.constant.OperationType;
import com.vandream.mall.business.dao.AccountDAO;
import com.vandream.mall.business.dao.UserInfoDAO;
import com.vandream.mall.business.domain.AccountDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.UserInfoDTO;
import com.vandream.mall.business.dto.UserInfoPassDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SecurityInfoException;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.service.SecurityService;
import com.vandream.mall.business.service.VerifyCodeService;
import com.vandream.mall.business.vo.UserInfoVO;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.PasswordUtils;
import com.vandream.mall.commons.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;


/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/19 16:20
 */
@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private UserInfoDAO userInfoDao;

    @Autowired
    private ApiExecutorBxService apiExecutorBxService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountDAO accountDAO;

    @Resource
    private VerifyCodeService verifyCodeService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

    @Override
    public UserInfoVO getUserInfoByUserId(String userId) throws SecurityInfoException {
        if (StringUtils.isBlank(userId)) {
            logger.info("用户名为空值", userId);
            throw new SecurityInfoException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
        }

        UserInfoVO userInfoVo = null;
        try {
            userInfoVo = userInfoDao.getUserInfoByUserId(userId);
            String companyName = userInfoDao.getCompanyName(userInfoVo.getCompanyId());
            userInfoVo.setNickName(companyName);
        } catch (Exception e) {
            logger.info("获取用户信息数据错误");
            throw new SecurityInfoException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
        }
        return userInfoVo;
    }

    /**
     * @param phoneNumber
     * @param password
     * @param confirmPwd
     * @throws SecurityInfoException 修改密码
     */
    @Override
    public void modifyPassword(String phoneNumber, String password, String confirmPwd) throws SecurityInfoException, NoSuchAlgorithmException, BusinessException {
        if (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPwd)) {
            throw new SecurityInfoException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
        }
        //根据手机号获取账户信息
        AccountDTO accountDTO = accountDAO.findByPhone(phoneNumber);
        String userId = null;
        if (accountDTO == null) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_NOT_FOUND);
        }
        //获取账户id
        userId = accountDTO.getAccountId();

        if (!password.equals(confirmPwd)) {
            logger.info("两次密码不一致");
            throw new SecurityInfoException(ResultStatusConstant.USER_PASSWORD_INCONSISTENCY_ERROR);
        }
        UserInfoVO userInfoVo = null;
        try {
            userInfoVo = userInfoDao.getUserInfoByUserId(userId);
        } catch (Exception e) {
            throw new SecurityInfoException(ResultStatusConstant.RETURN_USER_INFORMATION_ERROR);
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setOperatorUserId(userId);
        userInfoDTO.setOperatorUserName(userInfoVo.getPhoneNumber());
        userInfoDTO.setAccountId(userId);
        String passwordSalt = PasswordUtils.MD5Hex(password, PasswordUtils.SALT_VANDREAM);
        userInfoDTO.setAccountNewPwd(passwordSalt);
        try {
            String modifyPasswordReturnResult = apiExecutorBxService.modifyPassword(userInfoDTO);
            if (null != modifyPasswordReturnResult) {
                BxApiResult bxApiResult = JSONUtil.toBean(modifyPasswordReturnResult, BxApiResult.class);
                if (0 == bxApiResult.getStatus()) {
                    logger.info("修改密码调用接口返回数据错误");
                    throw new SecurityInfoException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                }
            }
        } catch (Exception e) {
            throw new SecurityInfoException(ResultStatusConstant.CALLING_INTERFACE_EXCEPTION);
        }

    }

    /**
     * @param userId
     * @param phoneNumber
     * @throws SecurityInfoException 修改手机号
     */
    @Override
    public void modifyPhone(String userId, String phoneNumber, String verifyCode) throws InvocationTargetException {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(phoneNumber)) {
            throw new SecurityInfoException(ResultStatusConstant.OBTAINING_USER_INFORMATION_ERROR);
        }
        String phoneFlag = accountService.verifyPhone(phoneNumber);
        if (StringUtils.isNotBlank(phoneFlag)) {
            verifyCodeService.checkVerifyCode(phoneNumber, verifyCode, OperationType.OPE_MODIFYPHONE);
            UserInfoPassDTO userInfoPassDTO = new UserInfoPassDTO();
            userInfoPassDTO.setAccountId(userId);
            userInfoPassDTO.setAccountMobile(phoneNumber);
            userInfoPassDTO.setOperatorUserId(userId);
            userInfoPassDTO.setOperatorUserName(phoneNumber);
            try {
                String modifyPhoneReturnResult = apiExecutorBxService.modifyPhone(userInfoPassDTO);
                if (null != modifyPhoneReturnResult) {
                    BxApiResult bxApiResult = JSONUtil.toBean(modifyPhoneReturnResult, BxApiResult.class);
                    if (0 == bxApiResult.getStatus()) {
                        logger.info("修改密码调用接口返回数据错误", bxApiResult.getStatus());
                        throw new SecurityInfoException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
                    }
                }
            } catch (Exception e) {
                throw new SecurityInfoException(ResultStatusConstant.REMOTE_INTERFACE_CALL_FAILURE);
            }
        }
    }
}
