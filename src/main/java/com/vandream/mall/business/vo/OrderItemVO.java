package com.vandream.mall.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/7 9:06
 */
public class OrderItemVO implements Serializable {
    private static final long serialVersionUID = -8579654846927417712L;
    private Long id;
    private Long orderItemId;
    private Long orderId;
    private String itemId;
    private String itemLineId;
    private String itemUnit;
    private BigDecimal itemPrice;
    private Integer itemNum;
    private OrderVO orderEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemLineId() {
        return itemLineId;
    }

    public void setItemLineId(String itemLineId) {
        this.itemLineId = itemLineId;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemNum() {
        return itemNum;
    }

    public void setItemNum(Integer itemNum) {
        this.itemNum = itemNum;
    }

    public OrderVO getOrderEntity() {
        return orderEntity;
    }

    public void setOrderEntity(OrderVO orderEntity) {
        this.orderEntity = orderEntity;
    }

}
