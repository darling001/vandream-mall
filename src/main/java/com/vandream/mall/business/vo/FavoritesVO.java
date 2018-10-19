package com.vandream.mall.business.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/5 13:57
 */
public class FavoritesVO implements Serializable {

    private static final long serialVersionUID = -583148513239537623L;
    private Integer id;
    private String itemId;
    private String itemLineId;
    private String spuId;
    private String  userId;
    private Byte status;
    private Byte isAdd;
    private BigDecimal number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsAdd() {
        return isAdd;
    }

    public void setIsAdd(Byte isAdd) {
        this.isAdd = isAdd;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }
}
