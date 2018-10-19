package com.vandream.mall.business.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.vandream.mall.business.constant.OperationType;
import com.vandream.mall.business.dao.AccountDAO;
import com.vandream.mall.business.dao.SubAccountDAO;
import com.vandream.mall.business.domain.AccountDTO;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.business.dto.subAccount.MenuDTO;
import com.vandream.mall.business.dto.subAccount.RoleDTO;
import com.vandream.mall.business.dto.subAccount.SubAccountDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.LoginTimeExpireException;
import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.service.VerifyCodeService;
import com.vandream.mall.business.vo.LoginVO;
import com.vandream.mall.commons.constant.RedisKeyConstant;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.*;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.security.auth.login.AccountException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Li Jie
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AccountDAO accountDAO;

    @Resource
    private SubAccountDAO subAccountDAO;

    @Resource
    private VerifyCodeService verifyCodeService;

    @Resource
    private ApiExecutorBxService apiExecutorBxService;

    @Override
    public String verifyPhone(String phoneNumber) throws InvocationTargetException {

        //校验
        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }

        //查重
        int phoneExist = accountDAO.verifyPhoneExist(phoneNumber);
        if (phoneExist == 0) {
            return "此手机号可以注册!";
        } else {
            //此手机号已经被注册或者停用
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_ALREADY_REGISTERED);
        }
    }

    @Override
    public String register(String phoneNumber, String verifyCode, String password,
                           String confirmPwd) throws InvocationTargetException {

        if (StringUtil.isBlank(phoneNumber) || StringUtil.isBlank(verifyCode)
                || StringUtil.isBlank(password) || StringUtil.isBlank(confirmPwd)) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_REGISTER_PARAM_ERROR);
        }
        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }
        if (StringUtil.isBlank(verifyCode)) {
            throw new BusinessException(ResultStatusConstant.VERIFY_CODE_NOT_NULL);
        }
        verifyPhone(phoneNumber);
        //校验验证码
        verifyCodeService.checkVerifyCode(phoneNumber, verifyCode,OperationType.OPE_REGIST);
        //校验两次密码
        if (StringUtil.isNotBlank(password) && StringUtil.isNotBlank(confirmPwd)) {
            if (!password.equals(confirmPwd)) {
                throw new BusinessException(ResultStatusConstant
                        .ACCOUNT_REGISTER_PASSWORD_DIFFER);
            }
        } else {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_REGISTER_PARAM_ERROR);
        }

        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setAccountMobile(phoneNumber);

        String register = null;
        try {
            //密码加密
            String md5Pwd = PasswordUtils.MD5Hex(password, PasswordUtils.SALT_VANDREAM);

            loginDTO.setAccountPwd(md5Pwd);
            //调用宝信API
            register = apiExecutorBxService.register(loginDTO);
        } catch (Exception e) {
            e.printStackTrace();
            throw new SystemException();
        }
        if (StringUtil.isNotBlank(register)) {
            BxApiResult bxApiResult = JSON.parseObject(register, BxApiResult.class);
            //返回对应状态
            if (bxApiResult == null) {
                throw new BusinessException(ResultStatusConstant
                        .ACCOUNT_REGISTER_FAILED);
            }
            if (bxApiResult.getStatus() == 1) {
                return "注册成功!";
            } else {
                BusinessException businessException = new BusinessException(ResultStatusConstant
                        .REMOTE_INTERFACE_CALL_FAILURE);
                businessException.setMessage(bxApiResult.getMessage());
                throw businessException;
            }
        } else {
            throw new BusinessException(ResultStatusConstant.SERVER_ERROR);
        }
    }

    @Override
    public LoginVO login(String phoneNumber, String password, Integer type) throws
            InvocationTargetException {

        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }
        LoginVO loginVO = null;
        //查询该用户记录
        LoginDTO loginDTO = null;
        try {
            loginDTO = accountDAO.findByLogin(phoneNumber);
        } catch (Exception e) {
            logger.error("====login："+e.getMessage());
            throw new BusinessException(ResultStatusConstant.ACCOUNT_STATUS_EXCEPTION);
        }
        if (type == 0) {
            //密码登录
            if (StringUtil.isBlank(password)) {
                throw new BusinessException(ResultStatusConstant.ACCOUNT_LOGIN_PASSWORD_EMPTY);
            } else {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountMobile(phoneNumber);
                try {
                    password = PasswordUtils.MD5Hex(password, PasswordUtils.SALT_VANDREAM);
                } catch (NoSuchAlgorithmException e) {
                    throw new BusinessException(ResultStatusConstant.SERVER_ERROR);
                }
                accountDTO.setAccountPwd(password);
                int verifyLogin = accountDAO.verifyLogin(accountDTO);
                if (verifyLogin != 1) {
                    throw new BusinessException(ResultStatusConstant.ACCOUNT_LOGIN_PHONE_OR_PASSWORD_EMPTY);
                }
            }
        } else {
            //验证码登录
            //验证手机号是否为平台账号
            AccountDTO accountDTO = accountDAO.findByPhone(phoneNumber);
            if (accountDTO == null) {
                throw new BusinessException(ResultStatusConstant
                        .ACCOUNT_NOT_FOUND);
            }
            //校验验证码
            verifyCodeService.checkVerifyCode(phoneNumber, password, OperationType.OPE_LOGIN);
        }

        if (loginDTO == null) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_DATA_EXCEPTION);
        }
        /**
         * COMPANY_FLAG : 1,供方；2，需方；3，两者都是
         * 当crm_company表中COMPANY_FLAG等于3，即两者都是（既是供方又是需方）的情况下，逻辑处理
         */
        if ("3".equals(loginDTO.getCompanyFlag())) {
            if (StringUtil.isNotBlank(loginDTO.getSupplierId()) &&
                    StringUtil.isBlank(loginDTO.getCustomerId())) {
                //供方，需方ID为空且供方ID不为空
                loginDTO.setCompanyFlag("1");
            } else if (StringUtil.isNotBlank(loginDTO.getCustomerId()) &&
                    StringUtil.isBlank(loginDTO.getSupplierId())) {
                //需方,需方ID不为空且供方ID为空
                loginDTO.setCompanyFlag("2");
            } else {
                logger.error("companyFlag为:"+loginDTO.getCompanyFlag()+"，供需方ID数据异常，一条记录只能存在其一");
                throw new BusinessException(ResultStatusConstant.ACCOUNT_DATA_EXCEPTION);
            }
        }
        //登录的是子账户则返回菜单列表
        if (StringUtil.isNotBlank(loginDTO.getParentAccountId()) || "11".equals(loginDTO.getAccountType()) || "21".equals(loginDTO.getAccountType())) {
            //查询当前子账户下账户信息
            String accountId = loginDTO.getAccountId();
            SubAccountDTO accountDTO = subAccountDAO.getAccount(accountId);
            //获取角色列表（包含菜单列表）
            List<RoleDTO> roleDTOList = accountDTO.getRoleList();
            if (ObjectUtil.isNotEmpty(roleDTOList)) {
                for (RoleDTO roleDTO : roleDTOList) {
                    //获取一级菜单集合
                    List<MenuDTO> menuList = roleDTO.getMenuList();
                    if (ObjectUtil.isNotEmpty(menuList)) {
                        for (MenuDTO menuDTO : menuList) {
                            //获取二级菜单集合
                            List<MenuDTO> secondMenuList = menuDTO.getMenuList();
                            if (ObjectUtil.isNotEmpty(secondMenuList)) {
                                for (MenuDTO secondMenuDTO : secondMenuList) {
                                    //获取三级菜单集合
                                    List<MenuDTO> thirdMenuList = secondMenuDTO.getMenuList();
                                    if (ObjectUtil.isNotEmpty(thirdMenuList)) {
                                        for (MenuDTO menu : thirdMenuList) {
                                            String selected = menu.getSelected();
                                            if ("1".equals(selected)) {
                                                secondMenuDTO.setSelected("1");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    loginDTO.setMenuList(menuList);
                }
            }
        }

        //转换为LoginVO转换传给前台
        loginVO = ObjectUtil.transfer(loginDTO, LoginVO.class);
        if (ObjectUtil.isEmpty(loginVO.getUserName())) {
            loginVO.setUserName(loginVO.getPhoneNumber());
        }

        //生成加密token
        HashMap<String, String> tokenMap = new HashMap<>(3);
        tokenMap.put("userId", loginVO.getUserId() + "");
        tokenMap.put("username", loginVO.getUserName());
        long lastAccessTime = System.currentTimeMillis();
        tokenMap.put("lastLoginTime", String.valueOf(lastAccessTime));
        String tokenJson = JSONObject.toJSONString(tokenMap);
        String token = null;
        try {
            //生成 用户token
            token = AES.encrypt(tokenJson, AES.KEY);
        } catch (Exception e) {
            throw new SystemException(ResultStatusConstant.CREATE_USER_TOKEN_ERROR);
        }
        if (StringUtil.isBlank(loginVO.getCustomerLevel())) {
            loginVO.setCustomerLevel("00");
        }

        //token
        loginVO.setToken(token);
        redisTemplate.opsForValue().set(token, tokenMap, 30, TimeUnit.MINUTES);
        //清理验证码
        redisTemplate.delete(phoneNumber);
        return loginVO;
    }

    private Boolean verifyRepeatSubmit(String phoneNumber) throws
            InvocationTargetException {
        String redisKey = phoneNumber + RedisKeyConstant.ACCOUNT_REPEAT_SUBMIT;

        if (redisTemplate.hasKey(redisKey)) {
            Integer repeatCount = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (repeatCount >= 3) {
                throw new BusinessException(ResultStatusConstant
                        .ACCOUNT_REPEAT_SUBMIT_COUNT_EXCEEDS_LIMIT);
            }
            redisTemplate.opsForValue().set(redisKey, (repeatCount + 1));
        } else {
            redisTemplate.opsForValue().set(redisKey, 1);
        }
        return true;
    }

    @Override
    public LoginVO getUserInfo(String token) throws InvocationTargetException {
        if (StringUtil.isBlank(token)) {
            throw new BusinessException(ResultStatusConstant.INPUT_PARAM_ERROR);
        }
        //从redis取回 tokenMap
        HashMap<String, String> tokenMap = (HashMap<String, String>) redisTemplate
                .opsForValue().get(token);
        if (ObjectUtil.isEmpty(tokenMap)) {
            throw new LoginTimeExpireException(ResultStatusConstant.LOGIN_TIME_EXPIRE);
        }
        String userId = tokenMap.get("userId");
        //查询该用户记录
        LoginDTO loginDTO = accountDAO.findById(userId);
        if (loginDTO == null) {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_DATA_EXCEPTION);
        }
        LoginVO loginVO = null;
        loginVO = ObjectUtil.transfer(loginDTO, LoginVO.class);
        if (ObjectUtil.isEmpty(loginVO.getUserName())) {
            loginVO.setUserName(loginVO.getPhoneNumber());
        }
        loginVO.setToken(token);
        return loginVO;
    }

    @Override
    public String getAccountFlagByUserId(String userId)throws InvocationTargetException {
        String accountFlagByUserId =null;
        try {
            accountFlagByUserId=accountDAO.getAccountFlagByUserId(userId);
            if(StringUtil.isBlank(accountFlagByUserId)){
                throw new BusinessException(ResultStatusConstant.ACCOUNT_STATUS_EXCEPTION);
            }
        } catch (Exception e) {
            logger.error("=========getAccountFlagByUserId查询失败"+e.getMessage());
            throw new BusinessException(ResultStatusConstant.FAIL_TO_GET_ACCOUNT);
        }

        return accountFlagByUserId;
    }

    /**
     * 退出登录
     *
     * @param token
     */
    @Override
    public void logout(String token) {
        redisTemplate.delete(token);
    }

    /**
     * 验证手机号是否已注册过(用于修改密码时验证)
     *
     * @param phoneNumber
     * @return
     * @throws InvocationTargetException
     */
    @Override
    public boolean verifyPhoneExist(String phoneNumber) throws InvocationTargetException {
        //校验
        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            throw new BusinessException(ResultStatusConstant.ACCOUNT_PHONE_FORMAT_ERROR);
        }
        //查重
        int phoneExist = accountDAO.verifyPhoneIsExist(phoneNumber);
        if (phoneExist == 1) {
            return true;
        } else {
            throw new BusinessException(ResultStatusConstant.ACCOUNT_NOT_FOUND);
        }
    }

}
