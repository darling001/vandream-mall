package com.vandream.mall.business.dto.item;

import com.vandream.mall.commons.constant.ComparatorInstance;
import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import lombok.Data;

/**
 * @author liuyuhong
 * @date 2018/3/14
 * @time 17:11
 * @description
 */
@Data
public class ImageDTO implements Comparable<ImageDTO> {

    /**
     * 图片原图
     */
    private String picExFileid;

    /**
     * 图片类型 cmcItem:商品图片；cmcItemDesc:商品描述图片
     */
    private String billType;

    /**
     * 图片序号
     */
    private Integer picOrder;

    /**
     * 图片名称
     */
    private String picName;

    @Override
    public int compareTo(ImageDTO o) {
        if (ObjectUtil.isEmpty(this.picOrder) || ObjectUtil.isEmpty(o.picOrder)) {
            //根据字符串中数字的大小进行排序，无数字按首字母排序
            return ComparatorInstance.INITIALS_COMPARATOR_CHINESE.compare(this.picName, o.picName);
        } else {
            //按照排序字段排序
            return ComparatorInstance.STRING_NUMBER_COMPARATOR.compare(String.valueOf(this.picOrder), String.valueOf(o.picOrder));
        }
    }
}
