package com.vandream.mall.business.vo;

import java.io.Serializable;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/6 17:26
 */
public class OrderVO implements Serializable {
    private static final long serialVersionUID = -8579654846927417712L;
    private Long id;
    private Long orderNo;
    private String address;
    private Long leadTime;
    private String userId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getLeadTime() {
        return leadTime;
    }

    public void setLeadTime(Long leadTime) {
        this.leadTime = leadTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", orderNo=" + orderNo +
                ", address='" + address + '\'' +
                ", leadTime=" + leadTime +
                ", userId='" + userId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
