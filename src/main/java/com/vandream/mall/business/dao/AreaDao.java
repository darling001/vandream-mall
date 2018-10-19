package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.item.AreaDTO;
import com.vandream.mall.business.vo.AreaVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author dingjie
 * @date 2018/3/21
 * @time 15:19
 * Description:
 */
@Component
public interface AreaDao {

    /**
     * 查看省市区 列表
     * @param
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    public List<AreaVO> getAreaList();

    /**
     * 获取区域对象列表
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_BUSINESS)
    List<AreaDTO> findAreaRangeList();
}
