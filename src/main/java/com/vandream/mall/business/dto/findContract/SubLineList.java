package com.vandream.mall.business.dto.findContract;

import com.vandream.mall.business.vo.base.BaseVO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/4/9 19:31
 */
@Data
@Setter
@Getter
public class SubLineList extends BaseVO implements Serializable {
    private static final long serialVersionUID = -6746631287741026263L;
    private String deliveryLineId;
    private String deliveryLineCode;
    private String deliveryHeadId;
    private BigDecimal receiptQuantity;
    private BigDecimal realQuantity;
}
