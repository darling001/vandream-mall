package com.vandream.mall.business.vo;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/4/10
 * Time: 14:46
 * Description:锁货参数实体
 */
@Data
@Setter
@Getter
public class SalesReservationInfoVO extends BaseVO{

    /** 用户id **/
    @NotBlank(message = "用户id为空")
    private String userId;

    /** 供方id **/
    @NotBlank(message = "供方id不能为空")
    private String supplierId;

    /** 锁货单id **/
    @NotBlank(message = "锁货单id不能为空")
    private String salesReservationId;
    
}
