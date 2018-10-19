package com.vandream.mall.commons.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * Multiple DataSource Configurer
 * @author wangchengli
 * @version 1.0
 * @date 2018-03-23
 */
@Configuration
public class DataSourceConfigurer {

    /**
     * business  Default DataSource
     *
     * @return data source
     */
    @Bean("business")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.business")
    public DataSource business() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * member DataSource.
     *
     * @return the data source
     */
    @Bean("member")
    @ConfigurationProperties(prefix = "spring.datasource.druid.member")
    public DataSource member() {
        return DruidDataSourceBuilder.create().build();
    }
    /**
     * vandream_mall DataSource.
     *
     * @return the data source
     */
    @Bean("vandream-mall")
    @ConfigurationProperties(prefix = "spring.datasource.druid.vandream-mall")
    public DataSource vandreamMall() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * vandream_goods DataSource.
     *
     * @return the data source
     */
    @Bean("product")
    @ConfigurationProperties(prefix = "spring.datasource.druid.product")
    public DataSource product() {
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * Dynamic data source.
     *
     * @return the data source
     */
    @Bean("dynamicDataSource")
    public DataSource dynamicDataSource() {
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        Map<Object, Object> dataSourceMap = new HashMap<>(4);
        System.out.println(business());
        dataSourceMap.put(DataSourceKey.DATABASE_BUSINESS.name(), business());
        dataSourceMap.put(DataSourceKey.DATABASE_MEMBER.name(), member());
        dataSourceMap.put(DataSourceKey.DATABASE_VANDREAM_MALL.name(), vandreamMall());
        dataSourceMap.put(DataSourceKey.DATABASE_PRODUCT.name(), product());

        // Set master datasource as default
        dynamicRoutingDataSource.setDefaultTargetDataSource(business());
        // Set master and slave datasource as target datasource
        dynamicRoutingDataSource.setTargetDataSources(dataSourceMap);

        // To put datasource keys into DataSourceContextHolder to judge if the datasource is exist
        DynamicDataSourceContextHolder.dataSourceKeys.addAll(dataSourceMap.keySet());

        // To put slave datasource keys into DataSourceContextHolder to load balance
        DynamicDataSourceContextHolder.slaveDataSourceKeys.addAll(dataSourceMap.keySet());
        DynamicDataSourceContextHolder.slaveDataSourceKeys.remove(DataSourceKey.DATABASE_BUSINESS.name());
        return dynamicRoutingDataSource;
    }


    @Bean
    @ConfigurationProperties(prefix = "mybatis")
    public SqlSessionFactoryBean sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        // Here is very important, if don't config this, will can't switch datasource
        // put all datasource into SqlSessionFactoryBean, then will autoconfig SqlSessionFactory
        sqlSessionFactoryBean.setDataSource(dynamicDataSource());
        return sqlSessionFactoryBean;
    }

    /**
     *
     * @return the platform transaction manager
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource());
    }
}

