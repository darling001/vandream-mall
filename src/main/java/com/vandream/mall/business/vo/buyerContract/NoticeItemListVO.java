package com.vandream.mall.business.vo.buyerContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/3 19:54
 */
@Data
public class NoticeItemListVO extends BaseVO{
    private String itemName;
    private String brandName;
    private String parameters;
    private String unit;
    private BigDecimal contractTotal;
    private BigDecimal noticeNumber;
}
