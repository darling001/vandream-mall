package com.vandream.mall.business.dto.item;

import com.vandream.mall.commons.constant.ComparatorInstance;
import lombok.Data;

import java.util.Set;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 13:44
 * @description
 */
@Data
public class ItemAttributeValueDTO implements Comparable<ItemAttributeValueDTO>{

    /** 计价参数值 */
    private String name;

    /** 存入ITEM_LINE_ID */
    private Set<String> values;

    /** 是否选中 */
    private boolean isSelected;

    /** 是否可选 */
    private boolean enable;

    @Override
    public int compareTo(ItemAttributeValueDTO o) {
        return ComparatorInstance.STRING_HASH_COMPARATOR.compare(this.name, o.getName());
    }
}
