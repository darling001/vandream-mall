package com.vandream.mall.business.dao;


import com.vandream.mall.business.vo.CityListVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 20:07
 */
@Component
public interface CityListDAO {
       /**
        *
        * @return
        */
       @DataSourceTarget(DataSourceKey.DATABASE_VANDREAM_MALL)
       List<CityListVO> getCity();
}
