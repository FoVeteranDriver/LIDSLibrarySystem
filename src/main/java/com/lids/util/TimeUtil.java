package com.lids.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author yaoyou
 * @description 时间工具类
 */
public class TimeUtil {

    /**
     *
     * @param time
     * @return
     */
    public static String format(long time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date(time));
    }


    /**
     * 将yymmdd的字符串格式转换成Date对象
     * @param timeString
     * @return
     * @throws ParseException
     */
    public static Long parse(String timeString) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        return sdf.parse(timeString).getTime();
    }

}
