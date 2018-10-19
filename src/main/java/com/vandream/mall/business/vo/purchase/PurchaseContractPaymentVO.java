package com.vandream.mall.business.vo.purchase;

import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.annotation.FieldAlias;
import java.math.BigDecimal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class PurchaseContractPaymentVO extends BaseVO {

    private String purchaseContractHeadId;
    /**
     * 起计日期
     */
    @FieldAlias("beginDate")
    private Long startDate;
    /**
     * 金额占比
     */
    @FieldAlias("amountRate")
    private BigDecimal proportion;
    /**
     * 金额
     */
    private BigDecimal amount;
    /**
     * 收款期限
     */
    @FieldAlias("paymentDays")
    private String limitDay;
    /**
     * 收款说明
     */
    @FieldAlias("paymentRemark")
    private String receiptsExplain;

}