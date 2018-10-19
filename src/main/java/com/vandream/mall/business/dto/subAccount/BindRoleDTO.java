package com.vandream.mall.business.dto.subAccount;


import lombok.Data;

/**
 * @author liuyuhong
 * @date 2018/6/4
 * @time 19:25
 * @description
 */
@Data
public class BindRoleDTO {
    /** 来源类别(10 商城) */
    private String fromType;
    /** 角色id */
    private String roleId;
    /** 子账号ID */
    private String accountId;
    /** 登录账号ID */
    private String loginUserId;
}
