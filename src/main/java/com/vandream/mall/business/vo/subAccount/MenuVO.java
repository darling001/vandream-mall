package com.vandream.mall.business.vo.subAccount;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;

import java.util.*;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:38
 * @description
 */
@Data
public class MenuVO extends BaseVO{
    /** 菜单id */
    private String menuId;
    /** 菜单名称 */
    private String name;
    /** 父菜单id */
    private String parentId;
    /** 访问地址 */
    private String url;
    /** 菜单顺序 */
    private String order;
    /** 创建人 */
    private String createUserId;
    /** 创建人姓名 */
    private String createUserName;
    /** 创建时间 */
    private Date createTime;
    /** 是否勾选 */
    private String selected;

    private String roleId;
    /** 下级功能 */
    private List<MenuVO> menuList;
}
