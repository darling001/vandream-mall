package com.vandream.mall.business.dto.order;

import com.vandream.mall.business.dto.BaseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author dingjie
 * @date 2018/5/14
 * @time 16:13
 * Description:
 */
@Data
@Getter
@Setter
public class OrderItemDTO extends BaseDTO{
    /**
     * 订单标识id
     */
    private Long id;
    /**
     * 订单主表id
     */
    private Long orderId;
    /**
     * 商品id
     */
    private String itemId;
    /**
     * 供应商品id
     */
    private String itemLineId;
    /**
     * 商品单位
     */
    private String itemUnit;
    /**
     * 商品价格
     */
    private BigDecimal itemPrice;
    /**
     * 商品数量
     */
    private BigDecimal itemNum;
    /**
     * 订单明细创建时间
     */
    private Long createTime;
    /**
     * 修改时间
     */
    private Long modifyTime;
}
