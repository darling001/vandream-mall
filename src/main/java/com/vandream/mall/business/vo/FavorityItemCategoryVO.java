package com.vandream.mall.business.vo;

import com.google.gson.annotations.SerializedName;

/**
 * @Author: zhaopanfeng
 * @Date: 2018/3/8 17:13
 */
public class FavorityItemCategoryVO {
    @SerializedName("CATEGORY_NAME")
    private String name;
    @SerializedName("CATEGORY_ID")
    private String id;
    @SerializedName("CATEGORY_CODE")
    private String code;
    @SerializedName("CATEGORY_LEVEL")
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "FavorityItemCategory{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
