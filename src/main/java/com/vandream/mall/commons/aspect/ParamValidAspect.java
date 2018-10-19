package com.vandream.mall.commons.aspect;

import com.vandream.mall.business.vo.base.BaseVO;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/17
 * Time: 15:38
 * Description: Aspect--参数校验
 */
@Aspect
@Component
public class ParamValidAspect {


    @Autowired
    Validator validator;

    @Pointcut("execution( * com.vandream.mall.business.service..*.*(..))")
    public  void serviceAspect(){

    }

    @Before("serviceAspect()")
    public void paramsValid(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        for (Object object: args) {
            if(object instanceof BaseVO){
                validateWithException(validator, object);
            }
        }
    }

    /**
     * 服务端参数有效性验证
     * @param object 验证的实体对象
     * @return 验证成功：返回true；严重失败：将错误信息添加到 flash message 中
     */
    @SuppressWarnings("rawtypes")
    public static void validateWithException(Validator validator, Object object)
            throws ConstraintViolationException {
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object);
        if (!constraintViolations.isEmpty()) {
            StringBuffer sb=new StringBuffer();
            for (ConstraintViolation constraintViolation : constraintViolations) {
                sb.append(constraintViolation.getMessage());
                throw new RuntimeException(sb.toString());
            }
        }
    }
}
