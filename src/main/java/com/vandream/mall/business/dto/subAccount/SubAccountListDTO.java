package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 14:13
 * Description:
 */
@Data
@Setter
@Getter
public class SubAccountListDTO extends BaseDTO {

    /** 子账户id **/
    private String accountId;

    /** 子账户姓名 **/
    private String accoutName;

    /** 角色id **/
    private String roleId;

    /** 角色姓名 **/
    private String roleName;

    /** 子账户手机号 **/
    private String phoneNumber;

    /** 账号状态;1、停用;2、启用;3、注销; **/
    private String status;

}
