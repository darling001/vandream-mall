package com.vandream.mall.commons.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Li Jie
 */
public class StringUtil extends StringUtils {
    private static final String UPPER_NULL = "NULL";
    private static final String LOWER_NULL = "null";

    /**
     * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
     * <p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isBlank(null)      = true
     * StringUtil.isBlank("NULL")    = true
     * StringUtil.isBlank("null")    = true
     * StringUtil.isBlank("")        = true
     * StringUtil.isBlank(" ")       = true
     * StringUtil.isBlank("bob")     = false
     * StringUtil.isBlank("  bob  ") = false
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isBlank(String) to isBlank(CharSequence)
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if (cs == null || (strLen = cs.length()) == 0) {
            return true;
        }
        if (UPPER_NULL.equals(cs) || LOWER_NULL.equals(cs)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a CharSequence is not empty (""), not null and not whitespace only.</p>
     * <p>
     * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
     * <p>
     * <pre>
     * StringUtil.isNotBlank(null)      = false
     * StringUtil.isNotBlank("NULL")    = false
     * StringUtil.isNotBlank("null")    = false
     * StringUtil.isNotBlank("")        = false
     * StringUtil.isNotBlank(" ")       = false
     * StringUtil.isNotBlank("bob")     = true
     * StringUtil.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is
     * not empty and not null and not whitespace only
     * @since 2.0
     * @since 3.0 Changed signature from isNotBlank(String) to isNotBlank(CharSequence)
     */
    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    /**
     * <p>Checks if a String {@code str} contains Unicode digits,
     * if yes then  return first  digits in {@code str} and return it as a String.</p>
     * <p>
     * <p>An empty ("") String will be returned if no digits found in {@code str}.</p>
     * <p>
     * <pre>
     * StringUtil.getFirstDigits(null)  = null
     * StringUtil.getFirstDigits("")    = ""
     * StringUtil.getFirstDigits("abc") = ""
     * StringUtil.getFirstDigits("1000$") = "1000"
     * StringUtil.getFirstDigits("1123~45") = "123"
     * StringUtil.getFirstDigits("0.125mm") = "0.125"
     * StringUtil.getFirstDigits("(541) 754-3010") = "541"
     * StringUtil.getFirstDigits("\u0967\u0968\u0969") = "\u0967\u0968\u0969"
     * </pre>
     *
     * @param str the String to extract digits from, may be null
     * @return String with only digits,
     * or an empty ("") String if no digits found,
     * or {@code null} String if {@code str} is null
     * @since 3.6
     */
    public static String getFirstDigit(final String str) {
        if (isBlank(str)) {
            return str;
        }
        if(str.contains("\\u")){
            return "";
        }
        final int sz = str.length();
        final StringBuilder strDigits = new StringBuilder(sz);
        boolean flag = false;
        for (int i = 0; i < sz; i++) {
            final char tempChar = str.charAt(i);
            if (Character.isDigit(tempChar)) {
                flag = true;
                strDigits.append(tempChar);
            } else {
                if (flag) {
                    if('.'==tempChar){
                        strDigits.append(tempChar);
                    }else{
                        break;
                    }
                }
            }
        }
        return strDigits.toString();
    }

}
