package com.lids.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author yaoyou
 * @description 时间工具类
 */
public class TimeUtil {

    /**
     * 将yymmdd格式的字符串转换成Date对象
     * @param dateString "yymmdd"格式字符串
     * @return 相对于的Date对象
     * @throws ParseException
     */
    public static Date parseDate(String dateString) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(dateString);
    }

    /**
     * 将HH：MM格式的字符串转换成Date对象
     * @param timeString “HH:mm”格式的字符串
     * @return 相对于的Date对象
     * @throws ParseException
     */
    public static Date parseTime(String timeString) throws  ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        return sdf.parse(timeString);
    }

    /**
     * 比较传输的时间是否大于当前时间
     * @param tranDate 传输的时间
     * @return 大于当前时间返回true，否则返回false；
     * @throws ParseException
     */
    public static boolean compareDate(String tranDate) throws ParseException{
        Date time = parseDate(tranDate);

        //获取当天的零点时间
        Calendar currentDate = Calendar.getInstance();
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Date nowTime = currentDate.getTime();

        if (time.getTime() - nowTime.getTime() >= 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 比较两个时间的大小
     * @param time1
     * @param time2
     * @return 如果time1>time2返回true
     * @throws ParseException
     */
    public static boolean compareTime(String time1,String time2) throws ParseException{
        Date date1 = parseTime(time1);
        Date date2 = parseTime(time2);
        if (date1.getTime() - date2.getTime() > 0 ){
            return true;
        }else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.print(TimeUtil.parseDate("2018-03-28"));
//        System.out.print(TimeUtil.parseTime("14:21:40.0"));
    }

}
