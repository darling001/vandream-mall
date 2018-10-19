package com.vandream;

import com.alibaba.fastjson.JSON;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.vo.LoginVO;
import java.lang.reflect.InvocationTargetException;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Li Jie
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AccountDTOServiceTests {
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private AccountService accountService;
    /**
     * 手机号校验
     *
     * @return
     */
    @Test
    public void verifyPhone() throws InvocationTargetException {
        String phoneNumber="18767264563";
        accountService.verifyPhone(phoneNumber);

    }

    /**
     * 用户注册
     *
     * @return
     */
    @Test
    public void register() throws InvocationTargetException {
        String phoneNumber = "18767264563";
        String verifyCode = "";
        String password = "";
        String confirmPwd = "";

    }
    @Test
    public void getUserInfo() throws InvocationTargetException {
        LoginVO userInfo = accountService.getUserInfo
                ("KeUy8uY4fhaM22khM+UqwxG15" +
                        "/vNUHixyvLx4WSddT2GhXHxBdjJWmNf5Iw7gpTQ1pRuEvMjFJRQVm5H4JcrHM67DmXffNYtIU3sJfCBy/m+5da/CFpD4jnEMaYPZBpfZTROLPzhEh2l18BRCK3CaQ==");
        System.out.println(JSON.toJSONString(userInfo));


    }

    /**
     * 用户登录
     *
     * @return
     */
    @Test
    public void login() throws
            InvocationTargetException {
        String phoneNumber = "18767264563";
        String password = "18767264563";
        Integer type = 0;
        accountService.login(phoneNumber,password,type);

    }

    @Test
    public  void testGetAccountFlag(){
//        System.out.println("test:"+accountService.getAccountFlagByUserId("f516c9bb9a2e84cb7f6ce65135cb1d54"));
    }

}
