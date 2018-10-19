package com.vandream.mall.business.dto.subAccount;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/25
 * Time: 15:28
 * Description:
 */
@Data
public class UpdateSubAccountDTO extends BaseDTO {

    /** 操作类型：DISABLE-停用、ENABLED启用、CANCELLED注销 **/
    private String operatorType;

    /** 来源类别(10 商城) **/
    private String fromType;

    /** 登录账号ID **/
    private String loginUserId;

    /** 登录账号名称 **/
    private String loginUserName;

    /** 账号ID列表 **/
    private List<String> accountIdList;

}
