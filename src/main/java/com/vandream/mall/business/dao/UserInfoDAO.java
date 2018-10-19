package com.vandream.mall.business.dao;

import com.vandream.mall.business.vo.UserInfoVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/19 16:52
 */
@Component
public interface UserInfoDAO {
    /**
     *
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    UserInfoVO getUserInfoByUserId(@Param("userId") String userId);

    /**
     *
     * @param userId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    String getCompanyName(@Param("userId") String userId);
}
