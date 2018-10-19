package com.vandream.mall.business.dto.subAccount;

import lombok.Data;

/**
 * @author liuyuhong
 * @date 2018/5/30
 * @time 10:15
 * @description
 */
@Data
public class SaveSubAccountDTO {
    /** 来源类别(10 商城) */
    private String fromType;
    /** 登录账号ID */
    private String loginUserId;
    /** 登录账号名称 */
    private String loginUserName;
    /** 手机号 */
    private String accountMobile;
    /** 姓名 */
    private String accountName;
    /** 父账号id */
    private String parentAccountId;
    /** 角色id */
    private String roleId;
}
