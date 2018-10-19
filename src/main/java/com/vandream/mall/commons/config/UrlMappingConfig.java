package com.vandream.mall.commons.config;

import com.vandream.mall.commons.utils.ApiMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Oakley(fangchao)
 * @Date 2018-04-11 14:33
 */
@Component
@PropertySource("classpath:urlMapping.properties")
@ConfigurationProperties
public class UrlMappingConfig {
    private static final Logger logger = LoggerFactory.getLogger(UrlMappingConfig.class);
    public  Map<String,String> devMap = new HashMap<>();
    public  Map<String,String> testMap = new HashMap<>();
    public  Map<String,String> prevMap = new HashMap<>();
    public  Map<String,String> prodMap = new HashMap<>();


    @Bean
    @Profile("test")
    public ApiMapping apiMappingTest() {
        return new ApiMapping(testMap) ;
    }

    @Bean
    @Profile("dev")
    public ApiMapping apiMappingDev() {
        return new ApiMapping(devMap);
    }

    @Bean
    @Profile("prev")
    public ApiMapping apiMappingPrev() {
        return new ApiMapping(prevMap);
    }

    @Bean
    @Profile("prod")
    public ApiMapping apiMappingProd() {
        return new ApiMapping(prodMap);
    }

    @Bean
    @Profile("prow")
    public ApiMapping apiMappingProw() {
        logger.error("prodMap =", prodMap);
        return new ApiMapping(prodMap);
    }

    public Map<String, String> getDevMap() {
        return devMap;
    }

    public void setDevMap(Map<String, String> devMap) {
        this.devMap = devMap;
    }

    public Map<String, String> getTestMap() {
        return testMap;
    }

    public void setTestMap(Map<String, String> testMap) {
        this.testMap = testMap;
    }

    public Map<String, String> getPrevMap() {
        return prevMap;
    }

    public void setPrevMap(Map<String, String> prevMap) {
        this.prevMap = prevMap;
    }

    public Map<String, String> getProdMap() {
        return prodMap;
    }

    public void setProdMap(Map<String, String> prodMap) {
        this.prodMap = prodMap;
    }
}
