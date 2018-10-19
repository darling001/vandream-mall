package com.vandream.mall.business.vo.subAccount;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;


/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 16:02
 * Description:
 */
@Data
public class UpdatePermissionVO extends BaseVO{

    /** 子账户Id **/
    @NotBlank(message = "子账户Id不能为空")
    private String accountId;

    /** 子账户名称 **/
    @NotBlank(message = "子账户名称不能为空")
    private String accountName;

    /** 主账户Id **/
    @NotBlank(message = "主账户Id不能为空")
    private String parentAccountId;

    /** 父账号名称 **/
    @NotBlank(message = "父账号名称不能为空")
    private String parentAccountName;

    /** 角色Id **/
    @NotBlank(message = "角色Id不能为空")
    private String roleId;

    /** 角色名称 **/
    @NotBlank(message = "角色名称不能为空")
    private String roleName;

    /** 选中的菜单列表 **/
    private Object infPermissionInfoList;

}
