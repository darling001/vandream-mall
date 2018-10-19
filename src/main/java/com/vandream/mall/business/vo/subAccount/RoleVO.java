package com.vandream.mall.business.vo.subAccount;

import com.vandream.mall.business.dto.subAccount.MenuDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:36
 * @description
 */
@Data
public class RoleVO extends BaseVO {
    /** 角色id */
    private String roleId;
    /** 角色姓名 */
    private String roleName;
    /** 角色类型 */
    private Integer roleType;
    /** 1-供方角色，2-需方角色 */
    private Integer roleFlag;
    /** 功能列表 */
    private List<MenuVO> menuList;

}
