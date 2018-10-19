package com.vandream.mall.commons.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author dingjie
 */
public class RegexUtil {
    /**
     * url验证
     */
    private static final String URL_REGEX = "^(([hH][tT]{2}[pP]|[hH][tT]{2}[pP][sS])?:\\/\\/)(www\\.)?[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:\\d+)*(\\/\\w+\\.\\w+)*([\\?&]\\w+=\\w*)*$";
    private static Pattern URL_PATTERN = Pattern.compile(URL_REGEX);
    /**
     * 国内手机号码 正则
     */
    private static final String MOBILE_REGEX="^(\\+|)(86|)[1][1-9][0-9]{9}$";
    private static Pattern CHINA_MOBILE_PATTERN = Pattern.compile(MOBILE_REGEX);


    public static boolean isUrl(String url) {
        Matcher matcher = URL_PATTERN.matcher(url);
       return matcher.matches();
    }

    /**
     * 校验字符串格式是否为国内的手机号
     * @param mobilePhone
     * @return
     */
    public static boolean isChinaMobilePhone(String mobilePhone){
        Matcher matcher = CHINA_MOBILE_PATTERN.matcher(mobilePhone);
        return matcher.matches();
    }
}
