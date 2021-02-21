package com.vivi.array;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@Slf4j
public class DateUtils {

    // 年月日格式
    public static final String sdf = "yyyy-MM-dd";
    // 年月日格式
    public static final String sdfYmd = "yyyyMMdd";
    //  年月日时分秒格式
    public static final String sdfHms = "yyyy-MM-dd HH:mm:ss";
    //  年月日时分格式
    public static final String sdfHm = "yyyy-MM-dd HH:mm";

    public static final long MILLISECOND_OF_DAY = 24 * 60 * 60 * 1000;

    /**
     * 获取指定时间的日期
     * @param hourNumber
     * @return
     */
    public static Date getNextTime(Integer hourNumber){
        Calendar cl = Calendar.getInstance(Locale.getDefault());
        cl.setTime(new Date());
        cl.set(Calendar.HOUR_OF_DAY, cl.get(Calendar.HOUR_OF_DAY) + hourNumber);
        return cl.getTime();
    }

    /**
     * Desc: 获取给定日期的下一天
     * @param currentDate
     * @return
     */
    public static Date getNextDay(Date currentDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }


    /**
     * Desc: 修改日期
     * @param date 日期
     * @param sdf 变化的天数
     * @return
     */
    public static Date parseToDate(String date, String sdf){
        if (StringUtils.isEmpty(date)){
            return null;
        }
        try {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(sdf);
            return sdfFormat.parse(date);
        } catch (Exception e) {
            log.error("parseToDate error", e);
        }
        return null;
    }

    public static String parseToString(Date date, String sdf){
        try {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(sdf);
            return sdfFormat.format(date);
        } catch (Exception e) {
            log.error("parseToString error", e);
        }
        return StringUtils.EMPTY;
    }

    /**
     * Desc: 修改日期
     * @param date 日期
     * @param days 变化的天数
     * @return
     */
    public static Date changeDayForDate(Date date, int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }

    /**
     * Desc: 返回时间差. font: HH:MM
     * @param startTime
     * @param endTime
     * @return
     */
    public static String caulateDelayTime(Date startTime, Date endTime, long timeGap) {
        if (startTime == null || endTime == null) {
            return StringUtils.EMPTY;
        }
        long mills = endTime.getTime() - startTime.getTime() - timeGap;
        if (mills < 0) {
            return StringUtils.EMPTY;
        }
        int totalSecond = (int)mills / 1000 / 60;
        int hour = totalSecond / 60;
        int second = totalSecond % 60;
        StringBuilder sb = new StringBuilder();
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        sb.append(":");
        if (second < 10) {
            sb.append("0");
        }
        sb.append(second);
        return sb.toString();
    }

    /**
     * Desc: 年月日转换为时分秒
     * @param date
     * @param days 增加的天数
     * @return
     */
    public static String ymsTOhms(String date, int days){
        String hms = null;
        try {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(sdf);
            Date yms = sdfFormat.parse(date);
            if (days != 0) {
                yms = changeDayForDate(yms, days);
            }
            SimpleDateFormat sdfHmsFormat = new SimpleDateFormat(sdfHms);
            hms = sdfHmsFormat.format(yms);
        } catch (ParseException e) {
            return StringUtils.EMPTY;
        }
        return hms;
    }


    /**
     * Desc: 计算时差
     * @return
     */
    public static boolean calculateTimeGap(Date start, Date end) {
        long startTime = start.getTime();
        long endTime = end.getTime();
        long gap = startTime - endTime;
        if (gap < 0) {
            return false;
        }

        return true;
    }

    /**
     * Desc: 计算天数
     * @return
     */
    public static long calculateDays(Date start, Date end) {
        long startTime = start.getTime();
        long endTime = end.getTime();
        long gap = startTime - endTime;
        if (gap < 0) {
            return 0;
        }
        long days = gap / MILLISECOND_OF_DAY;
        return days;
    }

    /**
     * Desc: 计算相差日期
     * @return
     */
    public static long calculateDate(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);

        Calendar endCalendar = Calendar.getInstance();
        startCalendar.setTime(end);

        long startTime = startCalendar.getTime().getTime();
        long endTime = endCalendar.getTime().getTime();
        long gap = startTime - endTime;
        if (gap < 0) {
            return 0;
        }
        long days = gap / MILLISECOND_OF_DAY;
        return days;
    }

    /**
     * Desc: 判断距离当前是否正好一整年
     * @return
     */
    public static Integer timePassOneYear(Date date) {
        Calendar oldCalendar = Calendar.getInstance();
        oldCalendar.setTime(date);
        Calendar currentCalendar = Calendar.getInstance();
        Integer oldYear = oldCalendar.get(Calendar.YEAR);
        Integer currentYear = currentCalendar.get(Calendar.YEAR);
        // 年份不符合
        if (oldYear >= currentYear) {
            return 0;
        }
        Integer oldMonth = oldCalendar.get(Calendar.MONTH);
        Integer currentMonth = currentCalendar.get(Calendar.MONTH);
        Integer oldDay = oldCalendar.get(Calendar.DAY_OF_MONTH);
        Integer currentDay = currentCalendar.get(Calendar.DAY_OF_MONTH);
        if (!oldMonth.equals(currentMonth)){
            return 0;
        }
        if (oldDay == 29 && currentDay == 28) {
            return currentYear - oldYear;
        }
        if (!oldDay.equals(currentDay) || oldDay == 29) {
            return 0;
        }
        return currentYear - oldYear;
    }
}

