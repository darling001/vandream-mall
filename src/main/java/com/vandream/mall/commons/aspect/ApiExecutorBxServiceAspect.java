package com.vandream.mall.commons.aspect;

import com.vandream.mall.business.execption.SystemException;
import com.vandream.mall.commons.utils.ApiMapping;
import com.vandream.mall.commons.utils.HttpRequestUtils;
import com.vandream.mall.commons.utils.JSONUtil;
import com.vandream.mall.commons.utils.ObjectUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/3/20
 * Time: 15:38
 * Description: 实体对象属性参数去空格
 */
@Aspect
@Component
public class ApiExecutorBxServiceAspect {
    private static final Logger logger = LoggerFactory.getLogger(ApiExecutorBxServiceAspect.class);

    @Autowired
    private ApiMapping apiMapping;

    @Pointcut("execution(public * com.vandream.mall.commons.service.ApiExecutorBxService.*(..))")
    public void urlAspect() {
    }


    @Around("urlAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Exception {
        Object ret = null;
        String classMethod = null;
        Object[] args = null;

        Signature signature = joinPoint.getSignature();
        //获取切点类与方法名
        classMethod = signature.getDeclaringTypeName() + "." + signature.getName();
        //获取切点类方法的参数
        args = joinPoint.getArgs();
        logger.info("method = {},args = {}", classMethod, JSONUtil.toJson(args));
        //通过apimapping获取映射的url地址
        Map<String, String> urlMap = this.apiMapping.getChainedUrlMap();
        try {
            if (ObjectUtil.isEmpty(urlMap)) {
                throw new SystemException("URL Map" +
                        "ping 未注入");
            }
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
        }


        if (!urlMap.containsKey(classMethod)) {
            return null;
        }
        if (null == args || args.length <= 0) {
            return null;
        }
        String apiUrl = urlMap.get(classMethod);
        logger.info("url = {}", apiUrl);
        //使用切点方法的参数转json并拼装http请求链接
        String dtoStr = JSONUtil.toJson(args[0]);
        ret = HttpRequestUtils.httpPost(apiUrl, dtoStr);
        logger.info("===========================ret = " + ret);
        return ret;
    }
}
