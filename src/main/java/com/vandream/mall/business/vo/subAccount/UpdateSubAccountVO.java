package com.vandream.mall.business.vo.subAccount;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 15:28
 * Description:
 */
@Data
public class UpdateSubAccountVO extends BaseVO {

    /** 子账户Id **/
    @NotBlank(message = "子账户Id不能为空")
    private String accountId;

    /** 主账户Id **/
    @NotBlank(message = "主账户Id不能为空")
    private String parentAccountId;

    /** 主账户名称 **/
    @NotBlank(message = "主账户名称不能为空")
    private String parentAccoutName;

    /** 账户密码 **/
    private String password;

    /** 确认密码 **/
    private String confirmPwd;

    /** 状态 **/
    private String status;
}
