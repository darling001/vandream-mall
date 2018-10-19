package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 20:29
 */
@Data
public class InfoItemListVO extends BaseVO{
    private String deliveryLineId;
    private String deliveryLineCode;
    private String itemName;
    private String brandName;
    private String parameters;
    private String unit;
    private BigDecimal contractTotal;
    private BigDecimal noticeQuantity;
    private BigDecimal realQuantity;
    private BigDecimal receiptQuantity;
}
