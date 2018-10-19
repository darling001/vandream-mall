package com.vandream.mall.business.dto.order;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author dingjie
 * @date 2018/7/10
 * @time 13:58
 * Description:
 */
@Data
@Getter
@Setter
public class OrderSOMItemLineDTO {
    private String itemLineId;
    private String saleQuantity;
}
