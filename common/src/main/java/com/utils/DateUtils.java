package com.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.time.DateUtils.parseDate;

@Slf4j
public class DateUtils {
    // 年月日格式
    public static final String sdf = "yyyy-MM-dd";
    //  年月日时分秒格式
    public static final String sdfHms = "yyyy-MM-dd HH:mm:ss";
    //  年月日时分格式
    public static final String sdfHm = "yyyy-MM-dd HH:mm";

    public static final String SDFHM_SPLIT_SPRIT = "yyyy/MM/dd HH:mm";
    public static final String SDFHMS_SPLIT_SPRIT = "yyyy/MM/dd HH:mm:ss";

    private static final Long ONE_HOUR_TO_MILLS = 60 * 60 * 1000L;

    public static final long SECONDS = 1000;
    public static final long MINUTES = 60 * SECONDS;
    public static final long HOUR = 60 * MINUTES;
    public static final long DAY = 24 * HOUR;
    public static final String YMDHMS = "yyyy/MM/dd HH:mm:ss";
    public static final String YMDHMS_LINE = "yyyy-MM-dd HH:mm:ss";
    public static final String YMD = "yyyy/MM/dd";
    public static final String HMS = "HH:mm:ss";

    /**
     * Desc: 日期格式化,String类型的日期转换成固定格式的Date
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

    /**
     * Desc: 日期格式化,Date类型的日期转换成固定格式的String类型的日期
     * @param date 日期
     * @param sdf 变化的天数
     * @return
     */
    public static String parseToString(Date date, String sdf){
        try {
            SimpleDateFormat sdfFormat = new SimpleDateFormat(sdf);
            return sdfFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return StringUtils.EMPTY;
    }

    /**
     * Desc: 修改日期,日期加或减days天
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
     * parse异常抛出
     * @param date
     * @param format
     * @return
     */
    public static String parseDate(Date date, String format) {
        if (date == null) {
            return "";
        } else {
            SimpleDateFormat sm = new SimpleDateFormat(format);
            return sm.format(date);
        }
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
            SimpleDateFormat sdfFomat = new SimpleDateFormat(sdf);
            Date yms = sdfFomat.parse(date);
            if (days != 0) {
                yms = changeDayForDate(yms, days);
            }
            SimpleDateFormat sdfHmsFomat = new SimpleDateFormat(sdfHms);
            hms = sdfHmsFomat.format(yms);
        } catch (ParseException e) {
            return StringUtils.EMPTY;
        }
        return hms;
    }

    /**
     * 判断一个日期是否今天之前
     */
    public static boolean beforeNowDay(Date date) {
        date = clearHMS(date);
        Date now = new Date();
        now = clearHMS(now);
        return date.before(now);
    }

    public static Date clearHMS(Date paymentDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(paymentDate);

        try {
            return simpleDateFormat.parse(format);
        } catch (ParseException var4) {
            return paymentDate;
        }
    }

    /**
     * Desc: 时间格式转换
     * @param date
     * @param origin
     * @param target
     * @return
     */
    public static Date formatConvert(Date date, SimpleDateFormat origin, SimpleDateFormat target) {
        if (date == null) {
            return null;
        }
        String originDateStr = origin.format(date);
        Date targetDate = null;
        try {
            targetDate = target.parse(originDateStr);
        } catch (ParseException e) {
            log.error("DateUtil:formatConvert error.", e);
        }
        return targetDate;
    }


    /**
     * Desc: 舍弃时分秒
     * @param date
     * @return
     */
    public static Date  abandonHms(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * Desc: 返回时间差. font: HH:MM
     * @param startTime
     * @param endTime
     * @return
     */
    public static Long caulateTimeGap(Date startTime, Date endTime) {
        if (startTime == null || endTime == null) {
            return 0L;
        }
        long mills = endTime.getTime() - startTime.getTime();
        if (mills < 0) {
            return 0L;
        }
        return mills;
    }

    /**
     * Desc: 判断时间范围是否符合
     * @param mills
     * @param begin
     * @param end
     * @param timeUnit 时间单位
     * @return
     */
    public static boolean isGapBetween(Long mills, Long begin, Long end, TimeUnit timeUnit) {
        if (mills > timeUnit.toMillis(begin) && mills < timeUnit.toMillis(end)) {
            return true;
        }
        return false;
    }

    public static Long convertToMills(Long time, TimeUnit timeUnit) {
        return timeUnit.toMillis(time);
    }

    /**
     * 根据开始和截止日期获取日期集合
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static List<Date> handleCirculationDate(Date startDate, Date endDate) {
        List<Date> listDate = new ArrayList<>();
        if (startDate == null || endDate == null) {
            listDate.add(startDate);
            listDate.add(endDate);
            return listDate.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(startDate);
            while (calendar.getTime().before(endDate)) {
                listDate.add(calendar.getTime());
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
            listDate.add(endDate);
            return listDate;
        } catch (Exception e) {
            log.error("日期解析有误，startDate:{},endDate:{}", startDate, endDate, e);
            return Collections.emptyList();
        }
    }

    public static int minus(Date from, Date to) {
        return Math.abs(minusReal(from, to));
    }

    public static int minusReal(Date from, Date to) {
        long fromTime = clearHMS(from).getTime();
        long toTime = clearHMS(to).getTime();
        long diffTime = fromTime - toTime;
        double days = (double) diffTime / 8.64E7D;
        return (int) Math.ceil(days);
    }
    /**
     * 指定日期距今第几周
     *
     * @param date
     * @return
     */
    public static int getCurrentWeek(Date date) {
        if (beforeNowDay(date)) {
            int minus = minus(date, new Date());
            return minus % 7 == 0 ? (minus / 7) : (minus / 7) + 1;
        } else {
            log.error("超出当前日期,date: " + parseDate(date, YMDHMS));
            return 1;
        }
    }

    /**
     * 获取某月开始时间
     *
     * @param date
     * @return
     */
    public static Date getBeginDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        return getDayStartTime(c.getTime());
    }

    /**
     * 获取某月的结束时间
     *
     * @param date
     * @return
     */
    public static Date getEndDayOfMonth(Date date) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(date);
        int year = gc.get(Calendar.YEAR);

        gc.setTime(date);
        int month = gc.get(Calendar.MONTH) + 1;

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1);
        int day = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(year, month - 1, day);
        return getDayEndTime(calendar.getTime());
    }

    /**
     * 获取上月开始时间
     *
     * @param date
     * @return
     */
    public static Date getLastBeginDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return getDayStartTime(c.getTime());
    }

    /**
     * 获取传入日期的当天最后一秒
     */
    public static Date getDayEnd(Date date) {
        Calendar calendar = Calendar.getInstance();
        date = clearHMS(date);
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.SECOND, -1);
        return calendar.getTime();
    }

    /**
     * 获取上月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastMaxDayOfMonth(Date date) {
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH, 0);
        return getDayEnd(calendar2.getTime());
    }

    /**
     * 获取某天上周开始时间
     *
     * @param date
     * @return
     */
    public static Date getLastBeginDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek - 7);
        return getDayStartTime(cal.getTime());
    }

    /**
     * 获取某天上周结束时间
     *
     * @param date
     * @return
     */
    public static Date getLastMaxDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getLastBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEnd(weekEndSta);
    }

    //获取本周结束时间
    public static Date getEndDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(getBeginDayOfWeek(date));
        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date weekEndSta = cal.getTime();
        return getDayEndTime(weekEndSta);
    }

    //根据指定日期获取本周的开始时间
    public static Date getBeginDayOfWeek(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == 1) {
            dayofweek += 7;
        }
        cal.add(Calendar.DATE, 2 - dayofweek);
        return getDayStartTime(cal.getTime());
    }

    //获取某个日期的开始时间
    public static Timestamp getDayStartTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return new Timestamp(calendar.getTimeInMillis());

    }

    // 获取某个日期的结束时间
    public static Timestamp getDayEndTime(Date d) {
        Calendar calendar = Calendar.getInstance();
        if (null != d) {
            calendar.setTime(d);
        }
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return new Timestamp(calendar.getTimeInMillis());
    }

    //获取两个时间相差的天数
    public static long compareDays(Date startDate, Date endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return (endDate.getTime() - startDate.getTime()) / (DAY);
    }

    /**
     * 判断是否是周末
     *
     * @param date
     * @return
     * @throws Exception
     */
    public static boolean isWeekend(Date date) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return true;
        } else {
            return false;
        }

    }

    public static Date add(Date date, int amount, int type) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(type, amount);
        return calendar.getTime();
    }

    /**
     * 判断是否是节假日
     *
     * @param date
     * @param holidayList
     * @return
     */
    public static boolean isHoliday(String date, List<String> holidayList) {
        if (holidayList.size() > 0) {
            for (int i = 0; i < holidayList.size(); i++) {
                if (date.equals(holidayList.get(i))) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * 获取日期间工作日天数 [开始日期，结束日期]
     *
     * @param start
     * @param end
     * @param holidayList
     * @return
     */
    public static int getWorkDayNum(Date start, Date end, List<String> holidayList) {
        int day = 0;

        start = dateFormat(start, "yyyy-MM-dd");
        end = dateFormat(end, "yyyy-MM-dd");

        //循环遍历每个日期
        while (start.getTime() <= end.getTime()) {

            String temp = DateUtils.parseDate(start, "yyyy-MM-dd");
            //判断是否为节假日
            if (!isHoliday(temp, holidayList)) {
                day++;
            }
            //日期往后加一天
            start = DateUtils.add(start, 1, Calendar.DAY_OF_MONTH);

        }
        return day;
    }

    public static Date strToDate(String formate, String date) {
        try {
            SimpleDateFormat f = new SimpleDateFormat(formate);
            return f.parse(date);
        } catch (Exception var3) {
            return null;
        }
    }

    public static Date dateFormat(Date date, String format) {
        String dateStr = DateUtils.parseDate(date, format);
        return DateUtils.strToDate(format, dateStr);
    }

    /**
     * 获取当前时间之后n个工作日的日期
     *
     * @param holidayList 节假日（日期格式：2019-01-01,2019-01-04,2019-01-05,......）
     * @param num         需要设置的n个工作日
     * @param day         当前日期
     * @return
     */
    public static Date getNextWorkDay(List<String> holidayList, int num, Date day) {
        if (day == null) {
            return null;
        }
        int delay = 1;
        while (delay <= num) {
            // 获取+1天的日期
            Date endDay = DateUtils.add(day, 1, Calendar.DAY_OF_MONTH);
            String time = DateUtils.parseDate(endDay, "yyyy-MM-dd");

            //当前日期+1即tomorrow,判断是否是节假日,都不是则将日期+1,直到循环num次即可
            if (!isHoliday(time, holidayList)) {
                delay++;
            }
            day = endDay;
        }
        return day;
    }

    /**
     * 比较两个日期字符串的大小
     * @param date1           第一个日期
     * @param date2           第二个日期
     * @return 比较结果：返回2表示第二个时间在第一个时间之前；返回1表示第一个时间在第二个时间之前；返回0表示两个时间相同
     */
    public static int compare_dateStr(String date1, String date2,String formatStr) {
        try {
            //字符串格式化转换为日期类型
            Date dt1 = DateUtils.parseToDate(date1,formatStr);
            Date dt2 = DateUtils.parseToDate(date1,formatStr);
            //返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数
            long l1=dt1.getTime();
            long l2=dt2.getTime();
            //比较两个时间毫秒数的大小
            if (l1>l2) {
                return -1;//返回2表示第二个时间在第一个时间之前
            } else if (l1<l2) {
                return 1;//返回1表示第一个时间在第二个时间之前
            } else {
                return 0;//返回0表示两个时间相同
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 9;
    }
}
