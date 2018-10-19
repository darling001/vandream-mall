package com.vandream;

import com.vandream.mall.business.dao.SubAccountDAO;
import com.vandream.mall.business.dto.subAccount.RoleDTO;
import com.vandream.mall.business.service.AccountService;
import com.vandream.mall.business.service.SubAccountService;
import com.vandream.mall.business.vo.LoginVO;
import com.vandream.mall.business.vo.subAccount.RoleVO;
import com.vandream.mall.business.vo.subAccount.SubAccVO;
import com.vandream.mall.business.vo.subAccount.SubAccountVO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/5/31
 * @time 12:52
 * @description
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubAccountServiceTest {

    @Autowired
    private SubAccountService subAccountService;

    @Autowired
    private SubAccountDAO subAccountDAO;

    @Autowired
    private AccountService accountService;

    /**
     * Dao层： 根据账号id查询角色和菜单权限列表
     */
    @Test
    public void testGetRoleListByAccountId() {
        List<RoleDTO> roleList = subAccountDAO.getRoleListByAccountId("03951ccf50aeb45f48d36cb6b5c3c754", "00",1);
        System.out.println("roleList = " + JSONUtil.toJson(roleList));
    }

    /**
     * Service层： 根据账户id查询角色和菜单权限列表
     *
     * @throws Exception
     */
    @Test
    public void testFindRoleAndMenuListByAccountId() throws Exception {
        SubAccountVO subAccountVO = subAccountService.findRoleAndMenuListByAccountId("e5fd82a27b41fcdbb5ece7ca7e086a40");
        System.out.println("subAccountVoJSON ========== " + JSONUtil.toJson(subAccountVO));
    }

    /**
     * Service层： 测试查询角色和菜单列表
     */
    @Test
    public void testFindAllRoleAndMenuList() throws Exception {
        List<RoleVO> roleVOList = subAccountService.findAllRoleAndMenuList("20",1);
        System.out.println("roleVOList = " + JSONUtil.toJson(roleVOList));
    }

    /**
     * Service层： 测试添加子账户
     *
     * @throws Exception
     */
    @Test
    public void testSaveSubAccount() throws Exception {
        SubAccVO subAccVO = new SubAccVO();
        subAccVO.setParentAccountId("7bb10197f131eb0c6bcd37a7f67c693a");
        subAccVO.setParentAccountName("杭萧");
        subAccVO.setAccountName("万郡");
        subAccVO.setAccountName("锋宝");
        subAccVO.setAccountMobile("15005000082");
        subAccVO.setVerifyCode("888888");
        subAccVO.setRoleId("1");
        String s = subAccountService.saveSubAccount(subAccVO);
        System.out.println("s ================== " + s);
    }

    /**
     * 测试子账户登录
     *
     * @throws InvocationTargetException
     */
    @Test
    public void testLogin() throws InvocationTargetException {
        LoginVO loginVoInfo = accountService.login("13822223456", "888888", 1);
        System.out.println("loginVoInfo ======== " + JSONUtil.toJson(loginVoInfo));
    }

}
