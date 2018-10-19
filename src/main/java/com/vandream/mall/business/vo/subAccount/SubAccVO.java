package com.vandream.mall.business.vo.subAccount;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author liuyuhong
 * @date 2018/5/29
 * @time 12:46
 * @description
 */
@Data
public class SubAccVO extends BaseVO {
    @NotBlank(message = "主账户id不能为空")
    private String parentAccountId;
    @NotBlank(message = "主账户名称不能为空")
    private String parentAccountName;
    @NotBlank(message = "账户名称不能为空")
    private String accountName;
    @NotBlank(message = "手机号码不能为空")
    private String accountMobile;
    @NotBlank(message = "短信验证码不能为空")
    private String verifyCode;
    @NotBlank(message = "角色id不能为空")
    private String roleId;

}
