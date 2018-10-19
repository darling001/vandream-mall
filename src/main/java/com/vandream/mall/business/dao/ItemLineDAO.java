package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.item.ItemLineDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

/**
 * @author dingjie
 * @date 2018/5/14
 * @time 17:35
 * Description:
 */
@Component
public interface ItemLineDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    ItemLineDTO getItemLineByItemLineId(String itemLineId);
}
