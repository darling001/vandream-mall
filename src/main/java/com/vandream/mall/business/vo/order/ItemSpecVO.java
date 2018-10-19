package com.vandream.mall.business.vo.order;

import com.alibaba.fastjson.annotation.JSONField;
import com.vandream.mall.business.vo.base.BaseVO;
import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.StringUtil;
import lombok.Data;

/**
 * Created with IntelliJ IDEA
 *
 * @author : liguoqing
 * @date : 2018/7/10
 * Time: 10:09
 * Description:
 */
@Data
public class ItemSpecVO extends BaseVO implements Comparable<ItemSpecVO>{
    /*参数名称*/
    @JSONField(name = "ATTRIBUTE_NAME")
    private String attributeName;
    /*参数值*/
    @JSONField(name = "ATTRIBUTE_VALUE")
    private String attributeValue;
    /*参数值*/
    @JSONField(name = "ORDER_SORT")
    private String orderSort;
   

    @Override
    public int compareTo(ItemSpecVO o) {
        if (StringUtil.isBlank(this.orderSort) || StringUtil.isBlank(o.orderSort)) {
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.attributeName, o.attributeName);
        }else{
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(this.orderSort, o.orderSort);
        }
    }
}
