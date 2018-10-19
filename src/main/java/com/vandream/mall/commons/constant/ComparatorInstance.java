package com.vandream.mall.commons.constant;

import com.vandream.mall.commons.utils.ObjectUtil;
import com.vandream.mall.commons.utils.StringUtil;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * @author Li Jie
 */
public class ComparatorInstance {
    /**
     * 排序用Collator实例  按汉字首字母排序
     */
    public static final Comparator<Object> INITIALS_COMPARATOR_CHINESE = Collator.getInstance(Locale
            .CHINA);
    /**
     * 根据字符串中数字的大小进行排序，无数字按首字母排序
     */
    public static final Comparator<String> STRING_NUMBER_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
            if (StringUtil.isBlank(o1) || StringUtil.isBlank(o1)) {
                return 0;
            }
            if (NumberUtils.isCreatable(o1) && NumberUtils.isCreatable(o2)) {
                final Double aDouble = NumberUtils.createDouble(o1);
                final Double bDouble = NumberUtils.createDouble(o2);
                return Double.compare(aDouble, bDouble);
            } else {
                // 判断字符串中是否包含数字 并提取第一个连续数字
                String o1Number = StringUtil.getFirstDigit(o1);
                String o2Number = StringUtil.getFirstDigit(o2);
                if (NumberUtils.isCreatable(o1Number) && NumberUtils.isCreatable(o2Number)) {
                    final Double aDouble = NumberUtils.createDouble(o1Number);
                    final Double bDouble = NumberUtils.createDouble(o2Number);
                    return Double.compare(aDouble, bDouble);
                }
            }
            return INITIALS_COMPARATOR_CHINESE.compare(o1, o2);
        }
    };
    /**
     * 根据字符串中数字的大小进行排序，无数字按首字母排序
     */
    public static final Comparator<String> STRING_HASH_COMPARATOR = new Comparator<String>() {
        @Override
        public int compare(final String o1, final String o2) {
            if (StringUtil.isBlank(o1) || StringUtil.isBlank(o1)) {
                return 0;
            }
            if (ObjectUtil.isNotEmpty(o1) && ObjectUtil.isNotEmpty(o2)) {
                int i = o1.hashCode();
                int n = o2.hashCode();
                return Integer.compare(i, n);
            }
            return INITIALS_COMPARATOR_CHINESE.compare(o1, o2);
        }
    };
}
