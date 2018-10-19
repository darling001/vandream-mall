package com.vandream.mall.business.dto.item;

import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyuhong
 * @date 2018/3/8
 * @time 13:44
 * @description
 */
@Data
public class ItemAttributeDTO implements Comparable<ItemAttributeDTO> {

    /**
     * 计价参数名称
     */
    private String name;

    /**
     * 计价参数值集合
     */
    private List<ItemAttributeValueDTO> values = new ArrayList<>();

    /**
     * 计价参数名称序号
     */
    private String orderSort;

    public void addValues(ItemAttributeValueDTO itemAttributeValueDTO) {
        this.values.add(itemAttributeValueDTO);
    }

    @Override
    public int compareTo(ItemAttributeDTO o) {
        if (StringUtil.isBlank(this.orderSort) || StringUtil.isBlank(o.orderSort)) {
            //根据字符串中数字的大小进行排序，无数字按首字母排序
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.name, o.name);
        } else {
            //根据排序字段排序
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.orderSort, o.orderSort);
        }
    }
}
