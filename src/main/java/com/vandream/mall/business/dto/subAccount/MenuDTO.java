package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.subAccount.MenuVO;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.StringUtil;
import lombok.Data;

import java.util.*;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:38
 * @description
 */
@Data
public class MenuDTO extends BaseDTO  implements Comparable<MenuDTO> {
    /**
     * 菜单id
     */
    private String menuId;
    /**
     * 菜单名称
     */
    private String name;
    /**
     * 父菜单id
     */
    private String parentId;
    /**
     * 访问地址
     */
    private String url;
    /**
     * 菜单顺序
     */
    private String order;
    /**
     * 创建人
     */
    private String createUserId;
    /**
     * 创建人姓名
     */
    private String createUserName;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否勾选
     */
    private String selected;

    private String roleId;

    /**
     * 下级功能
     */
    private List<MenuDTO> menuList;

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        if (menuList != null && menuList.size() == 0) {
            menuList = null;
        } else {
            this.menuList = menuList;
        }
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    @Override
    public int compareTo(MenuDTO o) {
        //根据排序字段排序
        return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.order, o.order);
    }
}