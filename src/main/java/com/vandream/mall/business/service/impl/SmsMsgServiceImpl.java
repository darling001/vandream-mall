package com.vandream.mall.business.service.impl;

import com.alibaba.druid.sql.ast.SQLPartitionValue;
import com.google.common.collect.Maps;
import com.vandream.mall.business.dto.BxApiResult;
import com.vandream.mall.business.dto.SmsMsgDTO;
import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SmsMsgException;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.service.SmsMsgService;
import com.vandream.mall.commons.constant.RedisKeyConstant;
import com.vandream.mall.commons.constant.ResultStatusConstant;
import com.vandream.mall.commons.service.ApiExecutorBxService;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.RegexUtil;
import com.vandream.mall.commons.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

/**
 * @author dingjie
 * @date 2018/3/20
 * @time 18:56
 * Description:
 */
@Service("smsMsgService")
public class SmsMsgServiceImpl implements SmsMsgService {
    private static final Logger logger = LoggerFactory.getLogger(SmsMsgServiceImpl.class);
    @Autowired
    private RedisService redisService;
    @Autowired
    private ApiExecutorBxService apiExecutorBxService;
    @Resource
    private RedisTemplate redisTemplate;
    private static final String BUSINESS_LIMIT_CONTROL="isv.BUSINESS_LIMIT_CONTROL";
    //private  final static String SMS_MSG_TEMP="SMS_126700017";
    @Override
    public Boolean sendSmsCode(String phoneNumber,String templateCode,String operationType) throws SmsMsgException {
        //校验手机号是否为空
        if(StringUtil.isBlank(phoneNumber)||StringUtil.isBlank(templateCode)||StringUtil.isBlank(operationType)){
            throw new SmsMsgException(ResultStatusConstant.SMS_MESSAGE_PHONE_CAN_NOT_NULL);
        }
        //校验手机号码格式
        if (!RegexUtil.isChinaMobilePhone(phoneNumber)) {
            //手机号格式异常
            throw new SmsMsgException(ResultStatusConstant.SMS_MESSAGE_PHONE_NOT_MATCH);
        }
        //查询redis中是否存在当前手机号的value,判断验证码时间 间隔
      String redisKey=  RedisKeyConstant.PHONE_CODE_OVER+phoneNumber;
        if (redisTemplate.hasKey(redisKey)) {
           throw new SmsMsgException(ResultStatusConstant.SMS_MESSAGE_SENT_ERROR);
        }
        boolean resultFlag=false;
        String smsMsgStr="";
        try {
            Map<String,String> msgCodeMap=new HashMap<String,String>(5);
            long randomCode=getRandomId();
            long outTime=300;
            SmsMsgDTO smsMsgDto=new SmsMsgDTO();
            smsMsgDto.setPhoneNumbers(phoneNumber);
            Map map = Maps.newConcurrentMap();
            map.put("code",randomCode);
            map.put("outTime",outTime);
            smsMsgDto.setParams(map);
            smsMsgDto.setTemplateCode(templateCode);
            //调用api发送短信到用户手机并接收返回值用于判断
            smsMsgStr=apiExecutorBxService.sendSmsCode(smsMsgDto);
            BxApiResult bxApiResult= JSONUtil.toBean(smsMsgStr,BxApiResult.class);
            if(null!=bxApiResult){
                if("1".equals(bxApiResult.getStatus().toString())){
                    resultFlag=true;
                    //获取redis中手机历史验证码map
                    if(redisTemplate.hasKey(operationType+phoneNumber)){
                        msgCodeMap= (Map<String,String>)redisTemplate.opsForHash().entries(operationType+phoneNumber);
                    }
                    Long currentTime=System.currentTimeMillis();
                    msgCodeMap.put(Long.toString(randomCode),Long.toString(currentTime));
                    //获取当前验证码的系统时间
                    redisTemplate.opsForHash().putAll(operationType+phoneNumber,msgCodeMap);
                    redisTemplate.expire(operationType+phoneNumber,outTime,TimeUnit.SECONDS);
                    //设置短信验证间隔时间
                    redisTemplate.opsForValue().set(RedisKeyConstant.PHONE_CODE_OVER+phoneNumber,phoneNumber,88,TimeUnit.SECONDS);
                }else if(BUSINESS_LIMIT_CONTROL.equals(bxApiResult.getTipsCode().toString())){
                    throw new SmsMsgException(ResultStatusConstant.SMS_MESSAGE_SENT_OFTEN_ERROR);
                }
            }
        }catch(SmsMsgException se){
            logger.error("=====SmsMsgException====sendSmsCode={}",se.toString());
            throw se;
        } catch (Exception e) {
            logger.error("=========sendSmsCode={}",e.toString());
            throw new SmsMsgException(ResultStatusConstant.SMS_MESSAGE_SEND_FAILED);
        }

        return resultFlag;
    }
    public long getRandomId(){
        long num=0;
        try {
            long l=(int) Math.round(Math.random() * (999999-100000) +100000);
            num=l;
            if(Long.toString(l).length()!=6){
               return getRandomId();
            }
        } catch (Exception e) {
            logger.error("=========getRandomId={}",e.toString());
        }
        return num;
    }
}
