package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.item.AreaDTO;
import com.vandream.mall.business.dto.item.ItemDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author liuyuhong
 * @date 2018/5/29
 * @time 14:37
 * @description
 */
@Component
public interface ItemDAO {
    /**
     * 获取相应的区域模板名称
     *
     * @param areaCode
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    AreaDTO getAreaRangeName(String areaCode);

    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<ItemDTO> queryStandardFlagByItemIdList(@Param("list") List<String> itemIdList);

    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    String getBrandByItemId(@Param("itemId") String itemId);

    @DataSourceTarget(DataSourceKey.DATABASE_PRODUCT)
    List<String> findTipList(@Param("brandId") String brandId);
}
