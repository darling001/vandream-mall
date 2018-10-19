package com.vandream;

import com.vandream.mall.business.execption.SubAccountException;
import com.vandream.mall.business.service.SubAccountService;
import com.vandream.mall.business.vo.subAccount.FindSubAccountVO;
import com.vandream.mall.business.vo.subAccount.SubAccountsVO;
import com.vandream.mall.business.vo.subAccount.UpdatePermissionVO;
import com.vandream.mall.business.vo.subAccount.UpdateSubAccountVO;
import com.vandream.mall.commons.utils.JSONUtil;
import org.hibernate.validator.constraints.NotBlank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 16:59
 * Description:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SubAccountTest {

    @Autowired
    private SubAccountService subAccountService;

    @Test
    public void findSubAccountList() throws SubAccountException {
        String parentAccountId = "b82c139479865718a4dffa78f5661ecb";
        FindSubAccountVO findSubAccountVO = new FindSubAccountVO();
        findSubAccountVO.setParentAccountId(parentAccountId);
        SubAccountsVO subAccountsVO = subAccountService.findSubAccountList(findSubAccountVO);
        System.out.println("==========================subAccountsVO = " + JSONUtil.toJSON
                (subAccountsVO, SubAccountsVO.class));
    }

    @Test
    public void updateSubAccount() throws SubAccountException {
        String accountId = "6e5dd862d55daf3f7079025abc7aeeb7";
        String password = "1234522";
        String confirmPwd = "1234522";
        String status = "1";
        String parentAccountId = "00";
        String parentAccountName ="国青";
        UpdateSubAccountVO updateSubAccountVO = new UpdateSubAccountVO();
        updateSubAccountVO.setAccountId(accountId);
//        updateSubAccountVO.setConfirmPwd(confirmPwd);
//        updateSubAccountVO.setPassword(password);
        updateSubAccountVO.setStatus(status);
        updateSubAccountVO.setParentAccoutName(parentAccountName);
        updateSubAccountVO.setParentAccountId(parentAccountId);
        String message = subAccountService.updateSubAccount(updateSubAccountVO);
        System.out.println(message);
    }

    @Test
    public void updatePermission() throws SubAccountException{
        String accountId = "25ac909fc6e806af0f1d6c5feff98db0";
        String accountName = "测试1";
        String parentAccountId = "00";
        String parentAccountName = "国青";
        String roleId = "02c1a0796e3911e896da525400c89a91";
        String roleName = "战略";
        Object infPermissionInfoList = "[{\"rootId\": \"3\",\"menuId\": \"1\"}, {\"rootId\": \"3\",\"menuId\": \"1\"}]";
        UpdatePermissionVO updatePermissionVO = new UpdatePermissionVO();
        updatePermissionVO.setAccountId(accountId);
        updatePermissionVO.setAccountName(accountName);
//        updatePermissionVO.setInfPermissionInfoList(infPermissionInfoList);
        updatePermissionVO.setParentAccountId(parentAccountId);
        updatePermissionVO.setParentAccountName(parentAccountName);
        updatePermissionVO.setRoleId(roleId);
        updatePermissionVO.setRoleName(roleName);
        subAccountService.updatePermission(updatePermissionVO);
    }

}
