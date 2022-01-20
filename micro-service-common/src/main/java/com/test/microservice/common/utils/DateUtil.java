package com.test.microservice.common.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @description: 日期工具类
 * @author: Zhaotianyi
 * @time: 2021/11/15 15:37
 */
public class DateUtil {
    public static final String PATTERN_DIGIT = "yyyyMMddHHmmss";

    public static final String PATTERN_YYYYMMDD = "yyyy-MM-dd";

    public static final String PATTERN_YYYYMMDDHHMM = "yyyyMMddHHmm";

    public static final String PATTERN_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static final String PATTERN_DEFAULT_HOUR = "yyyy-MM-dd HH:mm";

    public static final SimpleDateFormat FORMAT = new SimpleDateFormat(PATTERN_DEFAULT);

    public static final SimpleDateFormat HOURFORMAT = new SimpleDateFormat(PATTERN_DEFAULT_HOUR);

    public static final SimpleDateFormat DAYFORMAT = new SimpleDateFormat(PATTERN_YYYYMMDD);

    public static String format(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getNowDate() {
        Calendar cal = Calendar.getInstance();
        return cal.getTime();
    }

    public static String getSerialDate() {
        synchronized (FORMAT) {
            FORMAT.applyPattern(PATTERN_DIGIT);
            return FORMAT.format(getNowDate());
        }
    }

    /**
     * 获取当前时间的昨天
     *
     * @param format
     * @return
     */
    public static Date getNowYesterday(String format) {
        SimpleDateFormat fm = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取当前时间的明天
     *
     * @param format
     * @return
     */
    public static Date getNowTomorrow(String format) {
        SimpleDateFormat fm = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当前时间的昨天 返回 00:00:00 至23:59:59 时间段的字符串
     *
     * @return
     */
    public static String[] getNowYesterdayString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        String startTime = format.format(DateUtil.getNowYesterday(DateUtil.PATTERN_YYYYMMDD)) + " 00:00:00";
        String endTime = format.format(DateUtil.getNowYesterday(DateUtil.PATTERN_YYYYMMDD)) + " 23:59:59";
        String[] array = new String[2];
        array[0] = startTime;
        array[1] = endTime;
        return array;
    }

    /**
     * 获取当前时间的时间段 返回 00:00:00 至23:59:59 时间段的字符串
     *
     * @return
     */
    public static String[] getNowString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        String date = format.format(new Date());
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        String[] array = new String[2];
        array[0] = startTime;
        array[1] = endTime;
        return array;
    }

    /**
     * 获取当前时间的前5分钟 至23:59:59 时间段的字符串
     *
     * @return
     */
    public static String[] getBeforeMinutesString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        String date = format.format(new Date());
        Calendar beforeTime = Calendar.getInstance();
        beforeTime.add(Calendar.MINUTE, -5);
        synchronized (FORMAT) {
            String startTime = FORMAT.format(beforeTime.getTime());
            String endTime = date + " 23:59:59";
            String[] array = new String[2];
            array[0] = startTime;
            array[1] = endTime;
            return array;
        }
    }

    /**
     * 本周至现在的时间传
     *
     * @return
     */
    public static String[] getThisWeekString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar cal = Calendar.getInstance();
        Date date = new Date();
        cal.setTime(new Date());
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        // 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int day = cal.get(Calendar.DAY_OF_WEEK);
        // 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String weekMonday = format.format(cal.getTime()) + " 00:00:00";
        String[] array = new String[2];
        array[0] = weekMonday;
        array[1] = FORMAT.format(date);
        return array;
    }

    /**
     * 获取昨天的时间段 返回 00:00:00 至23:59:59 时间段的字符串
     *
     * @return
     */
    public static String[] getYesTodayString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        String date = format.format(calendar.getTime());
        String startTime = date + " 00:00:00";
        String endTime = date + " 23:59:59";
        String[] array = new String[2];
        array[0] = startTime;
        array[1] = endTime;
        return array;
    }

    /**
     * 获取上周 周一到周末时间区间
     *
     * @return
     */
    public static String[] getLastWeekString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar cal = Calendar.getInstance();
        int n = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (n == 0) {
            n = 7;
        }
        cal.add(Calendar.DATE, -(7 + (n - 1)));
        String weekStart = format.format(cal.getTime()) + " 00:00:00";
        cal.add(Calendar.DATE, 6);
        String weekEnd = format.format(cal.getTime()) + " 23:59:59";
        String[] array = new String[2];
        array[0] = weekStart;
        array[1] = weekEnd;
        return array;
    }

    /**
     * 取得上个月的时间范围
     *
     * @return
     */
    public static String[] getLastMonthString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        // 获取前月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = format.format(calendar.getTime());
        String[] array = new String[3];
        array[0] = firstDay + " 00:00:00";
        array[1] = lastDay + " 23:59:59";
        array[2] = String.valueOf(calendar.get(Calendar.DATE));
        return array;
    }

    /**
     * 取得本月的时间范围
     *
     * @return
     */
    public static String[] getThisMonthString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        // 获取前月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDay = format.format(calendar.getTime());
        String[] array = new String[3];
        array[0] = firstDay + " 00:00:00";
        array[1] = lastDay + " 23:59:59";
        array[2] = String.valueOf(calendar.get(Calendar.DATE));
        return array;
    }

    public static String[] getMonthString(String queryMonth) {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        Date queryDate = new Date();
        try {
            queryDate = format.parse(queryMonth + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(queryDate);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        // 获取前月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String lastDay = format.format(calendar.getTime());
        String[] array = new String[3];
        array[0] = firstDay + " 00:00:00";
        array[1] = lastDay + " 23:59:59";
        array[2] = String.valueOf(calendar.get(Calendar.DATE));
        return array;
    }

    /**
     * 取得本月至现在的时间范围
     *
     * @return
     */
    public static String[] getThisMonthToNowString() {
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        String firstDay = format.format(calendar.getTime());
        // 获取前月的最后一天
        String[] array = new String[2];
        array[0] = firstDay + " 00:00:00";
        array[1] = FORMAT.format(new Date());
        return array;
    }

    /**
     * 取得明天凌晨时间
     *
     * @return
     */
    public static Date getNowTomorrowTime() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 1);
        return cal.getTime();
    }

    /**
     * 计算两个时间相差多少分钟
     *
     * @param endDate
     * @param beginDate
     * @return
     */
    public static long getDatePoor(Date endDate, Date beginDate) {
        long nm = 1000 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - beginDate.getTime();
        // 计算差多少分钟
        return diff / nm;
    }

    /**
     * 计算两个时间相差多少秒
     *
     * @param endDate
     * @param beginDate
     * @return
     */
    public static long getDatePoorSecond(Date endDate, Date beginDate) {
        long nm = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - beginDate.getTime();
        // 计算差多少秒
        return diff / nm;
    }

    /**
     * 判断一个时间是否在时间区间内
     *
     * @param happenDate
     * @param beginDate
     * @param endDate
     * @return
     */
    public static boolean isBetweenSection(Date happenDate, Date beginDate, Date endDate) {
        if (happenDate.getTime() == beginDate.getTime() || happenDate.getTime() == endDate.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(happenDate);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginDate);

        Calendar end = Calendar.getInstance();
        end.setTime(endDate);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 组合时间
     *
     * @param dateString
     * @param endTomorrow 是否是次日 0不是 1 是
     * @return
     */
    public static Date getCommbineDate(Date date, String dateString, Short endTomorrow) {
        if (endTomorrow != 0) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DAY_OF_MONTH, 1);
            date = cal.getTime();
        }
        SimpleDateFormat sdf = new SimpleDateFormat(PATTERN_YYYYMMDD);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(sdf.format(date)).append(" ").append(dateString);
        try {
            return HOURFORMAT.parse(stringBuffer.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 秒数转化为多少天、小时、分钟
     *
     * @param mss
     * @return
     */
    public static String formatDateTime(long mss) {

        String dateTimes = null;
        if (mss == 0) {
            dateTimes = "0分钟";
        }
        if (mss < 60) {
            // 不足一分钟的按一分钟算
            dateTimes = 1 + "分钟";
        }
        long days = mss / (60 * 60 * 24);
        long hours = (mss % (60 * 60 * 24)) / (60 * 60);
        long minutes = (mss % (60 * 60)) / 60;
        if (days > 0) {
            dateTimes = days + "天" + hours + "小时" + minutes + "分钟";
        } else if (hours > 0) {
            dateTimes = hours + "小时" + minutes + "分钟";
        } else if (minutes > 0) {
            dateTimes = minutes + "分钟";
        }

        return dateTimes;
    }

    public static List<String> getMonthDateList(String queryMonth) {
        List<String> result = new ArrayList<String>();
        SimpleDateFormat format = new SimpleDateFormat(DateUtil.PATTERN_YYYYMMDD);
        Calendar calendar = Calendar.getInstance();

        Date queryDate = new Date();
        try {
            queryDate = format.parse(queryMonth + "-01");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(queryDate);
        // 获取前月的最后一天
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        Date lastDay = calendar.getTime();

        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        for (int i = 1; i <= 31; i++) {
            Date date = calendar.getTime();
            if (date.before(lastDay)) {
                result.add(format.format(date));
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        }
        return result;
    }

    public static List<Map<String, String>> getStartTimeAndEndTime(String date, String startTime, String endTime, int type) {
        List<Map<String, String>> list = Lists.newArrayList();
        String[] strings = date.split(",");
        for (String string : strings) {
            if (type == 0) {
                Map<String, String> map = Maps.newHashMap();
                map.put("startTime", string.concat(" " + startTime));
                map.put("endTime", string.concat(" " + endTime));
                list.add(map);
            } else {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                Date d = null;
                try {
                    d = df.parse(string + " 00:00");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d.setTime(d.getTime() + 24 * 60 * 60 * 1000);
                Map<String, String> map = Maps.newHashMap();
                map.put("startTime", string.concat(" " + startTime));
                map.put("endTime", DAYFORMAT.format(d).concat(" " + endTime));
                list.add(map);
            }
        }
        return list;
    }

    /**
     * @param @param  date
     * @param @return
     * @return String
     * @description: 获取本周周一周日日期
     */
    public static String getTimeInterval(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        synchronized (FORMAT) {
            String imptimeBegin = DAYFORMAT.format(cal.getTime());
            cal.add(Calendar.DATE, 6);
            String imptimeEnd = DAYFORMAT.format(cal.getTime());
            return imptimeBegin + "," + imptimeEnd;
        }
    }

    /**
     * @param @param  intervals
     * @param @return
     * @return List<String>
     * @description: 获取某个日期过去n天日期集合
     */
    public static List<String> getPastDate(String nowDay, int intervals) {
        List<String> pastDaysList = Lists.newArrayList();
        for (int i = 0; i < intervals; i++) {
            Calendar calendar = Calendar.getInstance();
            try {
                calendar.setTime(DAYFORMAT.parse(nowDay));
            } catch (ParseException e) {
            }
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - i);
            Date today = calendar.getTime();
            pastDaysList.add(DAYFORMAT.format(today));
        }
        return pastDaysList;
    }

    /**
     * 判断当前时间是否在时间段中
     *
     * @param beginDate
     * @param endDate
     * @return 0 已经结束 1 还未开始 2正在进行
     */
    public static Short getDateStatus(Date beginDate, Date endDate) {
        LocalDate beginDateTime = LocalDateTime.ofInstant(beginDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate endDateTime = LocalDateTime.ofInstant(endDate.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate time = LocalDate.now();
        if (time.isAfter(endDateTime)) {
            // 已经结束
            return 0;
        }
        if (time.isBefore(beginDateTime)) {
            // 还未开始
            return 1;
        }
        boolean condition1 = time.isAfter(beginDateTime) && time.isBefore(endDateTime);
        boolean condition2 = time.isEqual(endDateTime) || time.isEqual(beginDateTime);
        if (condition1 || condition2) {
            // 正在进行
            return 2;
        }
        return null;
    }

    /**
     * 根据开始日期、结束日期，返回有序日期key
     * 按月：2019-01 2019-02...
     * 按天：2019-01-01 2019-01-02...
     *
     * @param beginDate
     * @param endDate
     * @param dateType
     * @return
     */
    public static Set<String> getKeySet(String beginDate, String endDate, String dateType) {
        Set<String> set = Sets.newLinkedHashSet();
        LocalDate ld1 = LocalDate.parse(beginDate);
        LocalDate ld2 = LocalDate.parse(endDate);
        boolean bl = true;
        if ("m".equals(dateType)) {
            do {
                String key = ld1.getYear() + "-" + ld1.getMonthValue();
                if (ld1.getMonthValue() < 10) {
                    key = ld1.getYear() + "-0" + ld1.getMonthValue();
                }
                set.add(key);
                ld1 = ld1.plusMonths(1);
                bl = !ld1.minusDays(ld1.getDayOfMonth() - 1).isAfter(ld2);
            } while (bl);
        } else {
            do {
                set.add(ld1.toString());
                ld1 = ld1.plusDays(1);
                bl = !ld1.isAfter(ld2);
            } while (bl);

        }
        return set;
    }


    /**
     * @param @return
     * @return String[]
     * @description: TODO
     */
    public static String[] getLast12Months() {
        String[] last12Months = new String[12];
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1);
            last12Months[11 - i] = cal.get(Calendar.YEAR) + "-" + fillZero((cal.get(Calendar.MONTH) + 1) + "");
        }
        return last12Months;
    }

    public static String[] getLastDays(int day) {
        String[] last30Days = new String[day];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calc = Calendar.getInstance();
        for (int i = 0; i < day; i++) {
            calc.setTime(new Date());
            calc.add(calc.DATE, -i);
            Date minDate = calc.getTime();
            last30Days[i] = sdf.format(minDate);
        }
        Collections.reverse(Arrays.asList(last30Days));
        return last30Days;
    }


    private static String fillZero(String month) {
        if (month.length() == 1) {
            return "0".concat(month);
        }
        return month;
    }

}
