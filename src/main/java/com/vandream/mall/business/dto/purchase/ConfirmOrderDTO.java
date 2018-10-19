package com.vandream.mall.business.dto.purchase;

import com.vandream.mall.business.dto.BaseDTO;
import com.vandream.mall.commons.annotation.FieldAlias;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
public class ConfirmOrderDTO extends BaseDTO {
    /**
     * 用户id
     */
    @FieldAlias("userId")
    private String accountId;
    /**
     *当前用户名
     */
    @FieldAlias("userName")
    private String accountName;
    /**
     *采购订单id
     */
    @FieldAlias("purchaseContractHeadId")
    private String purchaseContractHeadId;
    /**
     *操作类型
     */
    @FieldAlias("operatorType")
    private String operatorType;
    /**
     * 采购操作记录备注
     */
    @FieldAlias("recordMark")
    private String recordMark;

}
