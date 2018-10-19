package com.vandream.mall.business.dto.item;

import com.google.gson.annotations.SerializedName;
import com.itextpdf.text.pdf.AcroFields;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.StringUtil;
import lombok.Data;

/**
 * @author liuyuhong
 * @date 2018/4/23
 * @time 11:30
 * @description
 */
@Data
public class ItemDescDTO implements Comparable<ItemDescDTO> {
    /**
     * 商品描述序号
     */
    @SerializedName("ORDER_SORT")
    private String orderSort;
    /**
     * 商品描述标题
     */
    @SerializedName("DESC_TITLE")
    private String descTitle;
    /**
     * 商品描述文本
     */
    @SerializedName("DESC_TEXT")
    private String descText;

    @Override
    public int compareTo(ItemDescDTO o) {
        if (StringUtil.isBlank(this.orderSort) || StringUtil.isBlank(o.orderSort)) {
            //描述没有排序字段按照首字母排序
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.descTitle, o.descTitle);
        } else {
            //按照排序字段排序
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.orderSort, o.orderSort);
        }
    }
}
