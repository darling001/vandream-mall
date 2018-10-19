package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 21:19
 */
@Data
public class BillingInformationVO extends BaseVO{
    private String receiptsExplain;
    private Long startDate;
    private String limitDay;
    private BigDecimal proportion;
    private BigDecimal amount;
}
