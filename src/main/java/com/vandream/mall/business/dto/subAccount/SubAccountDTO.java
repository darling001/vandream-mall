package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.vo.subAccount.RoleVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/5/25
 * @time 10:29
 * @description
 */
@Data
public class SubAccountDTO {
    /** 子账户id */
    private String accountId;
    /** 父账号id */
    private String parentAccountId;
    /** 手机号 */
    private String accountMobile;
    /** 注册时保留的手机号 */
    private String accountRegisterMobile;
    /** 姓名 */
    private String accountName;
    /** 邮箱 */
    private String accountEmail;
    /** 职务 */
    private String accountDuty;
    /** 账号状态；1、停用；2、启用；3、注销；*/
    private String accountStatus;
    /** 账号密码 */
    private String accountPwd;
    /** 账户类型，00：未明确；10：供方管理员；11：供方子账号；20：需方管理员；21：需方子账号；30：供需管理员；31：供需子账号； */
    private String accountType;
    /** 账号标识，1、注册未用户；2、待认证用户；3、已认证用户 */
    private String accountFlag;
    /** 跟进标识，00：关闭；10：正常 */
    private String trackFlag;
    /** 所在公司 */
    private String companyId;
    /** 创建人 */
    private String createUserId;
    /** 创建人姓名 */
    private String createUserName;
    /** 创建时间 */
    private Date createDate;
    /** 组织机构ID */
    private String orgId;
    /** 账套id */
    private String bookId;
    /** 集团ID */
    private String groupId;
    /** 角色列表 */
    private List<RoleDTO> roleList;
}
