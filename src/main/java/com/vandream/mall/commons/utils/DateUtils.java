/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/abs/toubao">JeeSite</a> All rights
 * reserved.
 */
package com.vandream.mall.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 *
 * @author Li Jie
 * @version 2017年9月5日14:34:10
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    /**
     * The number of milliseconds per day
     */
    public static final Long DAY_MILLIS = 24L * 3600 * 1000;
    public static final String DEFAULT_DATE_FMT = "yyyy-MM-dd HH:mm:ss";
    public static final String BX_DATE_FMT = "yyyyMMddHHmmss";


    private static String[] parsePatterns = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd " +
            "HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd",
            "yyyy.MM.dd HH:mm:ss",
            "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDate() {
        return getCurrentDate("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 日期型字符串转化为日期 格式 { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy.MM.dd",
     * "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString(), parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换日期字符串为 <code>java.util.Date</code>对象 允许格式 { "yyyy-MM-dd", "yyyy-MM-dd
     * HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss",
     * "yyyy/MM/dd HH:mm", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     *
     * @param dateStr
     * @return <code>java.util.Date</code>
     */
    public static Date parseDate(String dateStr) {
        if (StringUtil.isBlank(dateStr)) {
            return null;
        }
        try {
            return parseDate(dateStr, parsePatterns);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 转换日期字符串为 <code>java.util.Date</code>对象
     *
     * @param dateStr
     * @param pattern 字符串日期格式
     * @return <code>java.util.Date</code>
     * @throws ParseException
     */
    public static Date parseDate(String dateStr, String pattern) {
        try {
            if (StringUtil.isBlank(dateStr)) {
                return null;
            } else if (StringUtil.isBlank(pattern)) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化当前日期字符串 格式（yyyy-MM-dd HH:mm:ss） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param pattern 默认格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getCurrentDate(String pattern) {
        if (StringUtil.isBlank(pattern)) {
            pattern = "yyyy-MM-dd HH:mm:ss";
        }
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 格式化日期字符串 默认格式（yyyy-MM-dd HH:mm:ss） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param dateStr
     * @param pattern 默认格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String formatDate(String dateStr, String pattern) {
        if (StringUtil.isBlank(dateStr)) {
            return "";
        }
        String formatDate = "";
        if (StringUtil.isNotBlank(pattern)) {
            formatDate = new SimpleDateFormat(pattern).format(parseDate(dateStr));
        } else {
            formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(parseDate(dateStr));
        }
        return formatDate;
    }

    /**
     * 格式化日期字符串 默认格式（yyyy-MM-dd HH:mm:ss） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     *
     * @param date
     * @param pattern 默认格式（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        String formatDate = null;
        if (StringUtil.isNotEmpty(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
        }
        return formatDate;
    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期 <code>Locale.UK</code> 默认格式（yyyy-MM-dd）pattern可以为："y M d H h m s
     * E"的任意组合
     */
    public static String formatDateUK(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        String formatDate = null;
        if (StringUtil.isNotEmpty(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern.toString(), Locale.UK);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss", Locale.UK);
        }
        return formatDate;
    }

    /**
     * 格式化日期 <code>Locale.UK</code> 默认格式（yyyy-MM-dd） pattern可以为："y M d H h m s
     * E"的任意组合
     *
     * @param date
     * @param pattern
     * @return resultString.toUpperCase()
     */
    public static String formatDateUK2UC(Date date, String pattern) {
        return formatDateUK(date, pattern).toUpperCase();
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 *
                1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 根据年龄获取距离date的年龄的距离的日子
     *
     * @param date 可以为空，为空默认是今天
     * @param age
     * @return
     */
    public static String getDateStrFormAge(Date date, int age) {
        if (date == null) {
            date = new Date();
        }
        return getDateStrFromOneDay(date, Calendar.YEAR, age, parsePatterns[0]);
    }

    /**
     * @param date
     * @param type    Calendar.YEAR / Calendar.MONTH / Calendar.DAY_OF_MONTH
     * @param range
     * @param pattern
     * @return
     */
    public static String getDateStrFromOneDay(Date date, int type, int range, String pattern) {

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(type, range);
        return DateFormatUtils.format(c, pattern);
    }

    /**
     * Get the number of days between two dates
     *
     * @param startDateStr    start date String
     * @param startDateFormat start date format
     * @param endDateStr      end date String
     * @param endDateFormat   end date format
     * @return
     * @throws ParseException
     */
    public static Long getBetweenDays(String startDateStr, String startDateFormat, String
            endDateStr,
                                      String endDateFormat) throws ParseException {
        SimpleDateFormat sdf_start = new SimpleDateFormat(startDateFormat);
        SimpleDateFormat sdf_end = new SimpleDateFormat(endDateFormat);
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(sdf_start.parse(startDateStr));
        endCalendar.setTime(sdf_end.parse(endDateStr));
        Long startDateMillis = startCalendar.getTimeInMillis();
        Long endDateMillis = endCalendar.getTimeInMillis();
        Long diffDateMillis = endDateMillis - startDateMillis;
        if (diffDateMillis < 0) {
            return 0L;
        }
        return diffDateMillis / DAY_MILLIS;
    }

    /**
     * Get the number of days between two dates
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static Long getBetweenDays(Date startDate, Date endDate) throws ParseException {
        Calendar startCalendar = Calendar.getInstance();
        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(startDate);
        endCalendar.setTime(endDate);
        Long startDateMillis = startCalendar.getTimeInMillis();
        Long endDateMillis = endCalendar.getTimeInMillis();
        Long diffDateMillis = endDateMillis - startDateMillis;
        if (diffDateMillis < 0) {
            return 0L;
        }
        return diffDateMillis / DAY_MILLIS;
    }

    /**
     * Validation of date legitimacy in string of dates
     *
     * @param dateStr Date String
     * @param format  date format,for example "yyyyMMdd"
     * @return Successful return true, Otherwise it returns false,The date and
     * format does not match also returns false
     * @throws ParseException
     * @author Li Jie
     */
    public static boolean verifyDate(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = dateFormat.parse(dateStr);
        if (dateStr.equals(dateFormat.format(date))) {
            return true;
        } else {
            return false;
        }
        // 正则校验
        // Pattern pattern =
        // Pattern.compile("([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9
        // ]{3})"
        // +
        // "(((0[13578]|1[02])(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)(0[1-9]|[12][0-9]|30))"
        // + "|(02(0[1-9]|[1][0-9]|2[0-9])))");

    }

    /**
     * Get the number of days in February of the year
     *
     * @param year
     * @return February days number
     * @author Li Jie
     */
    public static Integer getFebruaryDays(Integer year) {
        int days = 0;
        // 判断平年闰年
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            // 闰年
            days = 29;
        } else {
            days = 28;
        }
        return days;
    }

    /**
     * According year Gets the number of days specified for the month
     *
     * @param month
     * @param year
     * @return The number of days in the specified month
     * @author Li Jie
     */
    public static Integer getMonthDays(int year, int month) {
        int monthDay = 0;
        if (month == 2) {
            // 判断平年闰年
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                // 闰年
                monthDay = 29;
            } else {
                monthDay = 28;
            }
        } else if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month ==
                10 || month == 12) {
            monthDay = 31;
        } else if (month == 4 || month == 6 || month == 9 || month == 11) {
            monthDay = 30;
        }
        return monthDay;
    }

    /**
     * Generate a full year Date parameter
     *
     * @param year
     * @return List
     * @throws ParseException
     * @author Li Jie
     */
    public static List<Integer> generateFullYearDayArgsList(Integer year, String format) throws
            ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        // 初始化第一天
        Date date = dateFormat.parse(String.valueOf(year) + "0101");
        List<Integer> resultList = new ArrayList<>();
        // 不包含二月的每年天数
        Long days = 337L;
        days = days + getFebruaryDays(year);
        // 遍历取得整年日期天的参数
        while (days > 0) {
            resultList.add(Integer.valueOf(dateFormat.format(date)));
            // TODO 待优化
            date = addDays(date, 1);
            days--;
        }
        return resultList;
    }

    /**
     * Generate a full month Date parameter
     *
     * @param year
     * @return List
     * @throws ParseException
     * @author Li Jie
     */
    public static List<String> generateFullMonthDaysList(Integer year, Integer month, String format)
            throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Long days = Long.valueOf(getMonthDays(year, month));
        Date date = null;
        // 初始化第一天
        if (month < 10) {
            date = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(year) + "0" + String
                    .valueOf(month) + "01");
        } else {
            date = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(year) + String.valueOf
                    (month) + "01");
        }
        List<String> resultList = new ArrayList<String>();
        while (days > 0) {
            resultList.add(dateFormat.format(date));
            date = addDays(date, 1);
            days--;
        }
        return resultList;
    }

    /**
     * 获取两天之间的日期参数列表
     *
     * @param startDateStr
     * @param startDateFormat
     * @param endDateStr
     * @param endDateFormat
     * @param resultPattern
     * @param includeLastDay  是否包含最后一天
     * @return
     * @throws ParseException
     */
    public static List<Integer> generateBetweenDayArgsList(String startDateStr, String
            startDateFormat,
                                                           String endDateStr, String
                                                                   endDateFormat, String
                                                                   resultPattern, Boolean
                                                                   includeLastDay)
            throws ParseException {

        Date startDate = parseDate(startDateStr, startDateFormat);
        Date endDate = parseDate(endDateStr, endDateFormat);
        Long days = 0L;
        if (includeLastDay) {
            days = getBetweenDays(startDate, endDate);
        } else {
            endDate = addDays(endDate, -1);
            days = getBetweenDays(startDate, endDate);
        }
        List<Integer> resultList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(resultPattern);
        while (days >= 0) {
            resultList.add(Integer.valueOf(sdf.format(startDate)));
            startDate = addDays(startDate, 1);
            days--;
        }
        return resultList;
    }

    /**
     * 获取两天之间的日期参数列表
     *
     * @param startDate
     * @param endDate
     * @param resultPattern
     * @param includeLastDay 是否包含最后一天
     * @return
     * @throws ParseException
     */
    public static List<Integer> generateBetweenDaysList(Date startDate, Date endDate, String
            resultPattern, Boolean includeLastDay) throws
            ParseException {
        Long days = 0L;
        if (includeLastDay) {
            days = getBetweenDays(startDate, endDate);
        } else {
            endDate = addDays(endDate, -1);
            days = getBetweenDays(startDate, endDate);
        }
        List<Integer> resultList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(resultPattern);
        while (days >= 0) {
            resultList.add(Integer.valueOf(sdf.format(startDate)));
            startDate = addDays(startDate, 1);
            days--;
        }
        return resultList;
    }

    /**
     * generated Before the start date
     *
     * @param startDate
     * @param days
     * @param resultPattern
     * @return
     */
    public static List<String> generateBeforeDaysList(Date startDate, Integer days, String
            resultPattern) {
        List<String> resultList = new ArrayList<String>();
        SimpleDateFormat resultDateFormat = new SimpleDateFormat(resultPattern);
        while (days > 0) {
            resultList.add(resultDateFormat.format(startDate));
            startDate = addDays(startDate, -1);
            days--;
        }
        return resultList;
    }

    /**
     * generated after the start date
     *
     * @param startDate
     * @param days
     * @param resultPattern
     * @return
     */
    public static List<String> generateAfterDaysList(Date startDate, Integer days, String
            resultPattern) {
        List<String> resultList = new ArrayList<String>();
        SimpleDateFormat resultDateFormat = new SimpleDateFormat(resultPattern);
        while (days > 0) {
            resultList.add(resultDateFormat.format(startDate));
            startDate = addDays(startDate, 1);
            days--;
        }
        return resultList;
    }

    public static boolean compareTo(Date dateA, Date dateB) {
        if (dateA.compareTo(dateB) == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        try {
            System.out.println("--------------");
            List<String> afterList = generateAfterDaysList(new Date(), 3, "yyyyMMdd");
            System.out.println("afterList :" + Arrays.toString(afterList.toArray()));
            List<String> beforeList = generateBeforeDaysList(new Date(), 3, "yyyyMMdd");
            System.out.println("beforeList" + Arrays.toString(beforeList.toArray()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * format date and Locale.UK
     *
     * @param date
     * @return dd/MMMMM/yyyy
     */
    public static String formatDateUK(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy", Locale.UK);

        return sdf.format(date);
    }
}
