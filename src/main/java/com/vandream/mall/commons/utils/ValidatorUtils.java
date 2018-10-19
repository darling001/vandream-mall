package com.vandream.mall.commons.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Hibernate validator工具类
 *
 * @author Li Jie
 */
public class ValidatorUtils<T> {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /**
     * 验证器方法
     *
     * @param obj           被验证对象
     * @param propertyNames 可选变长参数  指定参与验证的属性字段
     * @param <T>
     * @return {@code Map<String, Object>}
     * <br> key -> propertyName
     * <br> value -> message
     */
    public static <T> Map<String, Object> validation(T obj, String... propertyNames) {

        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> validatedSet = new HashSet<>();
        if (propertyNames == null||propertyNames.length==0) {
            validatedSet = validator.validate(obj);
        } else {
            for (String property : propertyNames) {
                validatedSet.addAll(validator.validateProperty(obj, property));
            }
        }
        Map<String, Object> result = new HashMap<>(validatedSet.size());
        for (ConstraintViolation<T> constraintViolation : validatedSet) {
            result.put(constraintViolation.getPropertyPath().toString(), constraintViolation
                    .getMessage());
        }
        return result;
    }
}
