package com.vandream.mall.business.service;

import com.vandream.mall.business.execption.SubAccountException;
import com.vandream.mall.business.vo.subAccount.*;

import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:27
 * @description 子账户
 */
public interface SubAccountService {

    /**
     * 根据账户id查询角色和菜单权限列表
     * @param accountId
     * @return
     * @throws Exception
     */
    SubAccountVO findRoleAndMenuListByAccountId(String accountId) throws Exception;

    /**
     * 查询角色和菜单权限列表(用于添加、修改子账户)
     * @param accountType
     * @param roleType
     * @return
     * @throws Exception
     */
    List<RoleVO> findAllRoleAndMenuList(String accountType, Integer roleType) throws Exception;

    /**
     * 添加子账户
     * @param subAccVO
     * @return
     * @throws Exception
     */
    String saveSubAccount(SubAccVO subAccVO) throws Exception;

    /**
     * 获取企业子账户列表
     * @param findSubAccountVO
     * @return 企业子账户列表
     * @throws SubAccountException
     */
    SubAccountsVO findSubAccountList(FindSubAccountVO findSubAccountVO) throws SubAccountException;

    /**
     * 修改子账户信息
     * @param updateSubAccountVO
     * @return success:修改子账户信息成功;failure:修改子账户状态失败
     * @throws SubAccountException
     */
    String updateSubAccount(UpdateSubAccountVO updateSubAccountVO) throws SubAccountException;

    /**
     * 修改子账户权限信息
     * @param updatePermissionVO
     * @return success:修改子账户权限信息成功;failure:修改子账户权限信息失败
     * @throws SubAccountException
     */
    String updatePermission(UpdatePermissionVO updatePermissionVO)throws SubAccountException;

}
