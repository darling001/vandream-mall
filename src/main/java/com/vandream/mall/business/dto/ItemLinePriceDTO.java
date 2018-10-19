package com.vandream.mall.business.dto;

import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/11 14:20
 */
public class ItemLinePriceDTO {
    private String itemPriceId;
    private BigDecimal salePrivateOrdinary;
    private BigDecimal salePrivateSenior;
    private BigDecimal salePrivateVip;

    public String getItemPriceId() {
        return itemPriceId;
    }

    public void setItemPriceId(String itemPriceId) {
        this.itemPriceId = itemPriceId;
    }

    public BigDecimal getSalePrivateOrdinary() {
        return salePrivateOrdinary;
    }

    public void setSalePrivateOrdinary(BigDecimal salePrivateOrdinary) {
        this.salePrivateOrdinary = salePrivateOrdinary;
    }

    public BigDecimal getSalePrivateSenior() {
        return salePrivateSenior;
    }

    public void setSalePrivateSenior(BigDecimal salePrivateSenior) {
        this.salePrivateSenior = salePrivateSenior;
    }

    public BigDecimal getSalePrivateVip() {
        return salePrivateVip;
    }

    public void setSalePrivateVip(BigDecimal salePrivateVip) {
        this.salePrivateVip = salePrivateVip;
    }

    @Override
    public String toString() {
        return "ItemLinePrice{" +
                "itemPriceId='" + itemPriceId + '\'' +
                ", salePrivateOrdinary=" + salePrivateOrdinary +
                ", salePrivateSenior=" + salePrivateSenior +
                ", salePrivateVip=" + salePrivateVip +
                '}';
    }
}
