package com.vandream;

import com.vandream.mall.commons.config.RedisProperties;
import com.vandream.mall.commons.config.UrlMappingConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.vandream.mall.business.dao")
@SpringBootApplication()
@EnableConfigurationProperties({RedisProperties.class, UrlMappingConfig.class})
@EnableTransactionManagement
@EnableScheduling
public class VandreamMallApplication {

    public static void main(String[] args) {
        SpringApplication.run(VandreamMallApplication.class, args);
    }
}
