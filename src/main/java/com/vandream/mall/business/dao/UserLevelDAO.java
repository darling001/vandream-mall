package com.vandream.mall.business.dao;

import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/9 11:22
 */
@Component
public interface UserLevelDAO {
       /**
        *
        * @param userId
        * @return
        */
       @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
       String getUserLevel(@Param("userId") String userId);
}
