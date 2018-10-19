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
 * Time: 14:01
 * Description: 获取企业子账户列表传入参数
 */
@Data
@Getter
@Setter
public class FindSubAccountVO extends BaseVO{

    /** 子账户状态 **/
    private String status;

    /** 主账户id **/
    @NotBlank(message = "主账户id不能为空")
    private String parentAccountId;

    /** 单页记录数 **/
    private Integer pageSize = 10;

    /** 当前页码 **/
    private Integer pageNo = 1;

}
