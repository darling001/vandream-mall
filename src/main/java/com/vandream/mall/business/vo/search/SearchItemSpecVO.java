package com.vandream.mall.business.vo.search;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.StringUtil;
import java.io.Serializable;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Li Jie
 */
@Data
@Getter
@Setter
public class SearchItemSpecVO implements Serializable, Comparable<SearchItemSpecVO> {
    private static final long serialVersionUID = 1679860908362920360L;
    @JSONField(name = "ATTRIBUTE_NAME")
    private String name;
    @JSONField(name = "ATTRIBUTE_VALUE")
    private String value;
    @JSONField(name = "PRICE_FLAG")
    private String priceFlag;
    @JSONField(name = "ORDER_SORT")
    private String orderSort;

    @Override
    public int compareTo(SearchItemSpecVO o) {
        if (StringUtil.isBlank(this.orderSort) || StringUtil.isBlank(o.orderSort)) {
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.name, o.name);
        } else {
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.orderSort, o.orderSort);
        }

    }
}
