package com.vandream.mall.business.dto.subAccount;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:36
 * @description
 */
public class RoleDTO {
    /** 角色id */
    private String roleId;
    /** 角色姓名 */
    private String roleName;
    /** 角色类型 */
    private Integer roleType;
    /** 1-供方角色，2-需方角色 */
    private Integer roleFlag;
    /** 功能列表 */
    private List<MenuDTO> menuList;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getRoleFlag() {
        return roleFlag;
    }

    public void setRoleFlag(Integer roleFlag) {
        this.roleFlag = roleFlag;
    }

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        if(menuList != null  && menuList.size()  == 0) {
            menuList = null;
        } else {
            this.menuList = menuList;
        }
    }
}


