package com.vandream.mall.commons.annotation;



import com.vandream.mall.commons.config.DataSourceKey;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wangchengli
 * @version 1.0
 * @date 2018-03-23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSourceTarget {

    DataSourceKey value() default DataSourceKey.DATABASE_BUSINESS;	//默认主表

}
