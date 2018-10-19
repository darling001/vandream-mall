package com.vandream;

import com.vandream.mall.business.execption.BusinessException;
import com.vandream.mall.business.execption.SecurityInfoException;
import com.vandream.mall.business.service.SecurityService;
import com.vandream.mall.business.service.VerifyCodeService;
import com.vandream.mall.business.vo.UserInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/19 16:58
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class SecurityApplicationTests {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private VerifyCodeService verifyCodeService;

    @Test
    public void getInfo(){
        String userId = "1";
        UserInfoVO userInfo = null;
        try {
            userInfo = securityService.getUserInfoByUserId(userId);
        } catch (SecurityInfoException e) {
            e.printStackTrace();
        }
        System.out.println(userInfo);
    }

    @Test
    public void getVerifyCode(){
       String phoneNumber = "18767160092";
       String verifyCode = "123456";

        try {
            verifyCodeService.checkVerifyCode(phoneNumber,verifyCode,null);
        } catch (SecurityInfoException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getModifyPassword() throws SecurityInfoException {
        String userId = "1";
        String password = "23344";
        String confirmPwd = "23344";
        try {
            securityService.modifyPassword(userId,password,confirmPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getModifyPhone() throws SecurityInfoException {
        String userId = "06db13c2c491f4937e1989ba05deb325";
        String password = "18518774873";
        String verifyCode = "";
        try {
            securityService.modifyPhone(userId,password,verifyCode);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
