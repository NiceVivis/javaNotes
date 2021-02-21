package com.vivi.basic.data;

import com.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

@Slf4j
public class DateTest {

    private final Date date = new Date();

    /**
     * Date格式化成String类型的日期
     */
    @Test
    public void parseToString(){
        String sdfHm = DateUtils.parseToString(date,DateUtils.sdf);
        String YMD = DateUtils.parseToString(date,DateUtils.SDFHMS_SPLIT_SPRIT);
        String HMS = DateUtils.parseToString(date,DateUtils.HMS);
        log.info("sdfHm = {}",sdfHm);
        log.info("YMD = {}",YMD);
        log.info("HMS = {}",HMS);
    }

    /**
     * String格式化成Date类型的日期
     */
    @Test
    public void parseToDate(){
        String date1 = "2020-09-03 14:15:01";
        String date2 = "2020/09/03 14:15:24";
        Date sdf = DateUtils.parseToDate(date1,DateUtils.sdf);
        Date sdfH = DateUtils.parseToDate(date2,DateUtils.YMD);
        log.info("sdf = {}",sdf);
        log.info("sdfH = {}",sdfH);
    }

    /**
     * 日期加或减days天
     */
    @Test
    public void changeDayForDate(){
        Date datestr = DateUtils.changeDayForDate(date,0);
        Date start = DateUtils.changeDayForDate(date,-1);
        log.info("日期加2 = {}",datestr);
        log.info("日期减1 = {}",start);
    }

    /**
     * 计算两个日期的时间差
     */
    @Test
    public void caulateDelayTime(){
        Date start = DateUtils.changeDayForDate(date,5);
        String strDate = DateUtils.caulateDelayTime(date,start,0);
        log.info("时间差 = {}",strDate);
    }

    /**
     * 年月日转换为时分秒，时分秒设置为00:00:00
     */
    @Test
    public void ymsTOhms(){
        String sdfHm = DateUtils.parseToString(date,DateUtils.sdfHms);
        String dateStr = DateUtils.ymsTOhms(sdfHm,0);
        log.info("初始化数据 = {}",sdfHm);
        log.info("年月日转换为时分秒 = {}",dateStr);
    }

    /**
     * 判断一个日期是否今天之前
     */
    @Test
    public void beforeNowDay(){
        Date start = DateUtils.changeDayForDate(date,-1);
        boolean dateStr = DateUtils.beforeNowDay(start);
        log.info("判断start是否今天之前 = {}",dateStr);
    }

    /**
     * 时间格式转换
     */
    public void formatConvert(){
        //DateUtils.formatConvert(date,DateUtils.sdfHm,DateUtils.sdf);
    }

    /**
     * 舍弃时分秒
     */
    @Test
    public void abandonHms(){
        Date dateStr = DateUtils.abandonHms(date);
        log.info("舍弃时分秒 = {}",dateStr);
    }

    /**
     * 计算时间差
     */
    @Test
    public void caulateTimeGap(){
        Date start = new Date();
        Long time = DateUtils.caulateTimeGap(date,start);
        log.info("时间差 = {}",time);
    }

    /**
     * 根据开始和截止日期获取日期集合
     */
    @Test
    public void handleCirculationDate(){
        Date start = DateUtils.changeDayForDate(date,-20);
        List<Date> dateList = DateUtils.handleCirculationDate(start,date);
        log.info("开始时间 = {} - 结束时间 = {} -- 中间合集：{}",date,start,dateList);
    }

    /**
     * 指定日期距今第几周
     */
    @Test
    public void getCurrentWeek(){
        Date start = DateUtils.changeDayForDate(date,-20);
        int num = DateUtils.getCurrentWeek(start);
        log.info("start {} 距今多少周{}",start,num);
    }

    /**
     * 获取某月开始时间
     */
    @Test
    public void getBeginDayOfMonth(){
         String dat = "2020-02-10 00:00:00";
         Date simDate = DateUtils.parseToDate(dat,DateUtils.sdfHms);
        Date datestr = DateUtils.changeDayForDate(simDate,-7);
        Date dates = DateUtils.getBeginDayOfMonth(datestr);
        log.info("获取某月开始时间:{},datestr:{}",dates,datestr);
    }

    /**
     * 获取某月的结束时间
     */
    @Test
    public void getEndDayOfMonth(){
        Date start = DateUtils.changeDayForDate(date,-20);
        Date dates = DateUtils.getEndDayOfMonth(start);
        log.info("获取某月的结束时间:{}",dates);
    }

    /**
     * 获取某天上周开始时间
     */
    @Test
    public void getLastBeginDayOfWeek(){
        Date week = DateUtils.getLastBeginDayOfWeek(date);
        log.info("获取上周开始时间:{}",week);
    }

    /**
     * 获取某天本周开始时间
     */
    @Test
    public void getBeginDayOfWeek(){
        Date week = DateUtils.getBeginDayOfWeek(date);
        log.info("获取本周开始时间:{}",week);
    }

    /**
     * 判断是否是周末
     */
    @Test
    public void isWeekend() throws Exception {
        Boolean flag = DateUtils.isWeekend(date);
        log.info("{}是否为周末:{}",date,flag);
    }

    /**
     * 判断是否是节假日
     */
    @Test
    public void isHoliday() throws Exception {
        Date start = DateUtils.changeDayForDate(date,30);
        Boolean flag = DateUtils.isWeekend(start);
        log.info("{}是否为节假日:{}",date,flag);
    }

    @Test
    public void dateTest(){
        Date time = null;
        Date date = time==null?new Date():time;
        System.out.printf(""+date);
    }

    @Test
    public void compare(){
        int result = DateUtils.compare_dateStr("2020-12-11","2020-11-12",DateUtils.YMDHMS_LINE);
        log.info("result:{}",result);
    }
}
