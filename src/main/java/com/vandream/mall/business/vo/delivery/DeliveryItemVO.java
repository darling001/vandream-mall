package com.vandream.mall.business.vo.delivery;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShenJiaQing
 * @date : 2018/5/31
 * Time: 17:37
 * Description:
 */
@Data
public class DeliveryItemVO extends BaseVO {

    /** 用户id **/
    @NotBlank(message = "用户id为空")
    private String userId;

    /** 发货通知单id **/
    @NotBlank(message = "发货通知单id不能为空")
    private String deliveryNoticeId;

    /** 供方id **/
    @NotBlank(message = "供方id不能为空")
    private String supplierId;
}
