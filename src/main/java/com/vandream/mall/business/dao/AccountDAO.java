package com.vandream.mall.business.dao;

import com.vandream.mall.business.domain.AccountDTO;
import com.vandream.mall.business.dto.LoginDTO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author Li Jie
 */
@Component
public interface AccountDAO {
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    AccountDTO selectByPrimaryKey(String id);
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    int verifyPhoneExist(String phone);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    int verifyLogin(AccountDTO accountDTO);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    LoginDTO findByLogin(String phone);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    LoginDTO findById(String userId);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    AccountDTO findByPhone(String phone);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    String  getAccountFlagByUserId(@Param("userId") String userId);

    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    int verifyPhoneIsExist(String phone);

}
