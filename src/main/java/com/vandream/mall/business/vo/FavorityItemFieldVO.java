package com.vandream.mall.business.vo;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/8 16:59
 */
public class FavorityItemFieldVO {
    private String key;
    private String value;
    private String priceFlag;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(String priceFlag) {
        this.priceFlag = priceFlag;
    }

    @Override
    public String toString() {
        return "FavorityItemField{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", priceFlag='" + priceFlag + '\'' +
                '}';
    }
}
