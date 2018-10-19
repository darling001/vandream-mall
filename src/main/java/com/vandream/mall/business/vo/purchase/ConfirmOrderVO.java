package com.vandream.mall.business.vo.purchase;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created with IntelliJ IDEA
 *
 * @author : ShiFeng
 * @date : 2018/4/8
 * @time : 20:55
 * Description:
 * 供方--采购订单确认
 */
@Data
@Getter
@Setter
public class ConfirmOrderVO extends BaseVO {
    @NotBlank(message = "用户id不能为空！")
    private String userId;
    @NotBlank(message = "用户名不能为空！")
    private String userName;
    @NotBlank(message = "采购订单id不能为空！")
    private String purchaseContractHeadId;
    @NotBlank(message = "操作类型不能为空！")
    private String operatorType;
    private String recordMark;
    @NotBlank(message = "供应商Id不能为空！")
    private String supplierId;

}
