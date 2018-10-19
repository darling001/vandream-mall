package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.BuryingPointRecordDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author yu yingxing
 * @date 2018/6/29
 * @time 10:38
 * @description
 */
@Component
public interface BuryingPointRecordDao {


    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    Integer maxRecordCode();

    @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
    void batchInsertRecord(List<BuryingPointRecordDTO> list);


}
