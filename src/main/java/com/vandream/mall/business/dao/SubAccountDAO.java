package com.vandream.mall.business.dao;

import com.vandream.mall.business.dto.subAccount.*;
import com.vandream.mall.business.vo.subAccount.FindSubAccountVO;
import com.vandream.mall.business.vo.subAccount.SubAccountVO;
import com.vandream.mall.business.vo.subAccount.SubAccountsVO;
import com.vandream.mall.commons.annotation.DataSourceTarget;
import com.vandream.mall.commons.config.DataSourceKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 11:39
 * @description
 */
@Component
public interface SubAccountDAO {

    /**
     * 查询子账户列表
     *
     * @param findSubAccountDTO
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<SubAccountListDTO> findSubAccountList(FindSubAccountDTO findSubAccountDTO);

    /**
     * 查询子账户的id，父id和账户类型的基本信息
     *
     * @param accountId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    SubAccountDTO getAccount(String accountId);

    /**
     * 查询当前账户下的角色列表
     *
     * @param accountId
     * @param accountType
     * @param roleType
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<RoleDTO> getRoleListByAccountId(String accountId, String accountType, Integer roleType);

    /**
     * 获取当前角色下的菜单列表
     *
     * @param roleFlag
     * @param roleType
     * @param roleId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<MenuDTO> findRootMenuList(Integer roleFlag, Integer roleType, String roleId);

    /**
     * 获取一级菜单对应的子菜单列表
     *
     * @param parentId
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<MenuDTO> findSubMenuList(@Param("parentId") String parentId, @Param("roleId") String roleId);

    /**
     * 添加子账户时查询手机是否已存在一级以及状态使用情况
     *
     * @param phoneNumber
     * @return
     */
    @DataSourceTarget(DataSourceKey.DATABASE_MEMBER)
    List<SubAccountDTO> getSubAccount(String phoneNumber);

}
