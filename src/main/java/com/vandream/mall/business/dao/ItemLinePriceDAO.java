package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.ItemLinePriceDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/11 14:25
 */
@Component
public interface ItemLinePriceDAO {
    /**
     *
     * @param itemLineId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    ItemLinePriceDTO itemLinePrice(@Param("itemLineId") String itemLineId);
}
