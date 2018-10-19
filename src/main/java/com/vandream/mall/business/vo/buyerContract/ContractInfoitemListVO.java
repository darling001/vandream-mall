package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import java.math.BigDecimal;
import lombok.Data;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 20:44
 */
@Data
public class ContractInfoitemListVO extends BaseVO {
    private String contractId;
    private String itemId;
    private String itemName;
    private String brandName;
    private String paramters;
    private BigDecimal quantity;
    private String unit;
    private BigDecimal unitPrice;
    private String taxRate;
    private BigDecimal anountPrice;
    private Long expectDate;
    private Boolean isChanged;
    private String itemVersion;
    private String orderItemVersion;
    private String itemLineId;



}
