package com.vandream.mall.business.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/8 16:46
 */
public class FavorityItemVO {
    private int id;
    private String itemLineId;
    private String itemId;
    private String name;
    private String itemNum;
    private double price;
    private double memberprice;
    private BigDecimal minOrderNum;
    private BigDecimal count;
    private Byte isAdd;
    private byte status;
    private String itemStaus;
    private String unit;
    private List<FavorityItemFieldVO> favorityItemFieldVOList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemLineId() {
        return itemLineId;
    }

    public void setItemLineId(String itemLineId) {
        this.itemLineId = itemLineId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getMemberprice() {
        return memberprice;
    }

    public void setMemberprice(double memberprice) {
        this.memberprice = memberprice;
    }

    public BigDecimal getMinOrderNum() {
        return minOrderNum;
    }

    public void setMinOrderNum(BigDecimal minOrderNum) {
        this.minOrderNum = minOrderNum;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }

    public Byte getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Byte isAdd) {
        this.isAdd = isAdd;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public String getItemStaus() {
        return itemStaus;
    }

    public void setItemStaus(String itemStaus) {
        this.itemStaus = itemStaus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<FavorityItemFieldVO> getFavorityItemFieldVOList() {
        return favorityItemFieldVOList;
    }

    public void setFavorityItemFieldVOList(List<FavorityItemFieldVO> favorityItemFieldVOList) {
        this.favorityItemFieldVOList = favorityItemFieldVOList;
    }
}
