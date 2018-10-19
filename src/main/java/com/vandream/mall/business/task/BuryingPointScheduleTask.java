package com.vandream.mall.business.task;

import com.vandream.mall.business.service.BuryingPointService;
import com.vandream.mall.business.service.RedisService;
import com.vandream.mall.business.service.impl.BuryingPointServiceImpl;
import com.vandream.mall.commons.utils.DateUtils;
import com.vandream.mall.commons.utils.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 *
 * @author yu yingxing
 * @date 2018/6/29
 * @time 16:15
 * @description 埋点每日统计task
 */
@Component
@PropertySource("classpath:application.properties")
public class BuryingPointScheduleTask {

    private static final Logger logger = LoggerFactory.getLogger(BuryingPointScheduleTask.class);

    @Autowired
    private BuryingPointService buryingPointService;

    @Scheduled(cron = "${spring.schedule.buryingPoint.storage}" )
    public void reportCurrentTime() {
        try {
            buryingPointService.cycleRecordToSqlHandler();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("============失败！埋点每日统计入库。{}",e.getMessage(),e);
        }
    }
}
