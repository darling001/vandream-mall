package com.vandream.mall.business.service.impl;


import com.vandream.mall.business.dao.UserLevelDAO;
import com.vandream.mall.business.service.UserIdLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/9 12:
 * 获取用户级别公共类
 */
@Service(value = "userIdLevelService")
public class UserIdLevelServiceImpl implements UserIdLevelService {

    @Autowired
    private UserLevelDAO userLevelMapper;

    @Override
    public String getUserIdLevelService(String userId) {
        return userLevelMapper.getUserLevel(userId);
    }
}
