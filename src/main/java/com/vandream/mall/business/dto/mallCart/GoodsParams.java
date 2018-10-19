package com.vandream.mall.business.dto.mallCart;

import com.google.gson.annotations.SerializedName;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author dingjie
 * @date 2018/3/8
 * @time 16:48
 * Description:
 */
public class GoodsParams implements Comparable<GoodsParams> {
    /**
     * 参数名
     */
    @SerializedName("ATTRIBUTE_NAME")
    private String attributeName;
    /**
     * 参数值
     */
    @SerializedName("ATTRIBUTE_VALUE")
    private String attributeValue;
    /**
     * 参数类型
     */
    @SerializedName("PRICE_FLAG")
    private String priceFlag;

    private Set<String> itemIds = new HashSet<>();

    /**
     * 是否选中
     */
    private boolean isSelected;
    /**
     * 是否可选
     */
    private boolean isEnable;

    /**
     * 属性名称序号
     */
    @SerializedName("ORDER_SORT")
    private String orderSort;

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(String attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getPriceFlag() {
        return priceFlag;
    }

    public void setPriceFlag(String priceFlag) {
        this.priceFlag = priceFlag;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void addItemId(String itemId) {
        this.itemIds.add(itemId);
    }

    public Set<String> getItemIds() {
        return itemIds;
    }

    public void setItemIds(Set<String> itemIds) {
        this.itemIds = itemIds;
    }

    public String getOrderSort() {
        return orderSort;
    }

    public void setOrderSort(String orderSort) {
        this.orderSort = orderSort;
    }

    @Override
    public boolean equals(Object o) {
        if (ObjectUtil.isEmpty(o)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        GoodsParams that = (GoodsParams) o;
        return priceFlag.equals(that.priceFlag) &&
                Objects.equals(attributeName, that.attributeName) &&
                Objects.equals(attributeValue, that.attributeValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attributeName, attributeValue, priceFlag);
    }

    @Override
    public String toString() {
        return "GoodsParams{" +
                "attributeName='" + attributeName + '\'' +
                ", attributeValue='" + attributeValue + '\'' +
                ", priceFlag=" + priceFlag +
                '}';
    }

    @Override
    public int compareTo(GoodsParams o) {
        if (StringUtil.isBlank(this.orderSort) || StringUtil.isBlank(o.orderSort)) {
            //根据字符串中数字的大小进行排序，无数字按首字母排序
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.attributeName, o.attributeName);
        } else {
            //根据排序字段排序
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.orderSort, o.orderSort);
        }
    }
}