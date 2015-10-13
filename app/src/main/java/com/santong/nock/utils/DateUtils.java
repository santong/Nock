package com.santong.nock.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * Created by santong.
 * At 15/9/28 17:41
 */
public class DateUtils {

    /**
     * 获取两个日期相差的天数
     * TODO: 当两个日期的差的毫秒数大于long的最大值时，返回0，需改进。
     */
    public static int getDaysFrom2Date(Date firstDate, Date lastDate) {
        return Math.abs((int) ((firstDate.getTime() - lastDate.getTime()) / (1000 * 60 * 60 * 24)));
    }

    /**
     * 格式化日期为:yyyy-MM-dd 格式
     */
    public static String formatDate(Date date) {
        String format = "yyyy-MM-dd";
        return dateFormat(date, format);
    }

    /**
     * 格式化日期显示格式
     */
    public static String dateFormat(String strDate, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = formatter.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return "";
        }
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 格式化日期显示格式
     */
    public static String dateFormat(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }

    /**
     * 是否是今天
     */
    public static boolean isToday(Date date) {
        Calendar calendar = new GregorianCalendar();
        //年
        int year = calendar.get(Calendar.YEAR);
        //月
        int month = calendar.get(Calendar.MONTH);
        //日
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        //昨天23点59分59秒
        Calendar calendarYes = new GregorianCalendar(year, month, day - 1, 23, 59, 59);
        //今天23点59分59秒
        Calendar calendarToday = new GregorianCalendar(year, month, day, 23, 59, 59);
        return date.getTime() < calendarToday.getTimeInMillis() && date.getTime() > calendarYes.getTimeInMillis();
    }

    /**
     * 取得给定日期加上一定天数后的日期对象.
     *
     * @param date   给定的日期对象
     * @param amount 需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static Date getDateAdd(Date date, int amount) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, amount);
        return cal.getTime();
    }

    /**
     * 将"yyyy-MM-dd"格式的字符串转换成Date
     *
     * @param dateStr
     * @return
     */
    public static Date getDateFromStr(String dateStr) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = sdf.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
