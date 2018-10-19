package com.vandream.mall.business.service.impl;


import com.vandream.mall.business.execption.SecurityInfoException;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.service.VerifyCodeService;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import javax.annotation.Resource;

import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/19 16:47
 * 手机验证码公共类
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeServiceImpl.class);
    /**
     * 开发环境
     */
    private static final String ENV_DEV = "dev";
    /**
     * 测试环境
     */
    private static final String ENV_TEST = "test";
    /**
     * 通用验证码
     */
    private static final String GENERAL_VERFIY_CODE = "888888";

    @Value("${spring.profiles.active}")
    private String environment;
    @Resource
    private RedisService redisService;
    @Resource
    private RedisTemplate redisTemplate;

    @Override
    public void checkVerifyCode(String phoneNumber, String verifyCode,String operationType) throws
            SecurityInfoException {
        if (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(verifyCode)|| StringUtil.isBlank(operationType)) {
            throw new SecurityInfoException(ResultStatusConstant
                    .USERNAME_VERY_CIPHER_INFORMATION_ERROR);
        }
        if (ENV_DEV.equalsIgnoreCase(environment) || ENV_TEST.equalsIgnoreCase(environment)) {
            if (GENERAL_VERFIY_CODE.equals(verifyCode)) {
                return;
            }
        }
        Boolean isCheck=false;
        Map<String,String> msgCodeMap=new HashMap<>(5);
        try {
            String opePhone=operationType+phoneNumber;
            if(redisTemplate.hasKey(operationType+phoneNumber)){
                msgCodeMap= (Map<String,String>)redisTemplate.opsForHash().entries(opePhone);
            }
            Map<String,String> newMsgCodeMap=msgCodeMap;
            if(ObjectUtil.isNotEmpty(msgCodeMap)){
                Set<String> codeSets = msgCodeMap.keySet();
                for (String code:codeSets) {
                    if (verifyCode.equals(code)) {
                        Long codeCurrentTime = Long.parseLong(msgCodeMap.get(code));
                        Long currentTime=System.currentTimeMillis();
                        //时间差
                        Long secTime=currentTime-codeCurrentTime;
                        if(secTime<5*60*1000){
                            isCheck=true;
                        }
                        redisTemplate.opsForHash().delete(opePhone,code);
                        break;
                    }
                }
//                redisTemplate.opsForHash().putAll(opePhone,newMsgCodeMap);
            }
            Long msgCodesize = redisTemplate.opsForHash().size(opePhone);
            if(msgCodesize.intValue()==0){
                redisTemplate.delete(opePhone);
            }
        } catch (Exception e) {
            throw new SecurityInfoException(ResultStatusConstant
                    .USER_AUTHENTICATION_CODE_INFORMATION);
        }

        if(!isCheck){
            throw new SecurityInfoException(ResultStatusConstant
                    .USER_VERIFY_CODE_INCONSISTENCY_ERROR);
        }
    }
}
