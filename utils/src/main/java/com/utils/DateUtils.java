package com.utils;

import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYYMMDD = "yyyyMMdd";

    public static final long ONE_DAY_MI = 1000 * 60 * 60 * 24;

    public static final long ONE_HOUR_MI = 1000 * 60 * 60;

    public static final long ONE_MINUTS_MI = 1000 * 60;

    public static final long ONE_SECOND_MI = 1000;

    /**
     * 得到day后的日期
     *
     * @param strDate
     * @param day
     * @return 字符串格式
     */
    public static String addDay(String strDate, int day) {
        if (strDate == null) {
            return null;
        }
        Date date = str2Date(strDate, YYYY_MM_DD);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return date2SimpleStr(calendar.getTime());

    }

    /**
     *
     * @param strDate
     * @param format
     * @param day
     * @return 返回 时间字符串
     */
    public static String addDay(String strDate, String format, int day) {
        if (StringUtils.isBlank(strDate)) {
            return null;
        }
        if(StringUtils.isBlank(format)) {
            format = YYYY_MM_DD_HH_MM_SS;
        }
        Date date = str2Date(strDate, format);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, day);
        return date2Str(calendar.getTime(), format);
    }

    public static boolean afterNow(Date curDate) {
        Date date = new Date();
        long curTimes = curDate.getTime();
        long now = date.getTime();
        return curTimes - now > 0;
    }

    /**
     * 得到当前短日期
     *
     * @return
     */
    public static String currentDate() {
        return currentDate(YYYY_MM_DD);
    }

    public static String yestoday() {
        return someDay(-1);
    }

    public static String someDay(int n) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, n);
        return new SimpleDateFormat(YYYY_MM_DD).format(cal.getTime());
    }

    public static String currentDate(String format) {
        GregorianCalendar calenda = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(calenda.getTime());
    }

    /**
     * 得到上月当前天时间
     *
     * @return
     */
    public static String lastMonthCurrentDay(String format) {
        GregorianCalendar calenda = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        calenda.add(calenda.MONTH, -1);
        return sdf.format(calenda.getTime());
    }

    /**
     * 得到当前时间,带上时分秒的当前时间
     *
     * @return
     */
    public static Date currentTime() {
        GregorianCalendar calenda = new GregorianCalendar();
        return calenda.getTime();
    }

    /**
     * 获取今天的起始时刻
     *
     * @return
     */
    public static Date startTimeOfToday() {
        return startTimeOfDate(currentTime());
    }

    /**
     * 获取某天的起始时刻
     *
     * @return
     */
    public static Date startTimeOfDate(Date date) {
        return timeOfDay(date, 0, 0, 0, 0);
    }

    /**
     * 获取今天的中间时刻
     *
     * @return
     */
    public static Date middleTimeOfToday() {
        return middleTimeOfDate(currentTime());
    }

    /**
     * 获取今天的中间时刻
     *
     * @return
     */
    public static Date middleTimeOfDate(Date date) {
        return timeOfDay(date, 12, 0, 0, 0);
    }

    /**
     * 获取今天的结束时刻
     *
     * @return
     */
    public static Date endTimeOfToday() {
        return endTimeOfDate(currentTime());
    }

    /**
     * 获取某天的结束时刻
     *
     * @param date
     * @return
     */
    public static Date endTimeOfDate(Date date) {
        return timeOfDay(date, 23, 59, 59, 999);
    }

    /**
     * 获取某天的指定时间的时刻
     *
     * @param day
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date timeOfDay(Date day, int hour, int minute, int second, int millisecond) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }

//    public static void main(String[] args) {
////        DateFormat dateFormat = new SimpleDateFormat("")
//        String df = "yyyy-MM-dd\\'T\\'HH:mm:ss.SSS";
//        System.out.println(DateUtils.date2Str(startTimeOfToday(), df));
//        System.out.println(DateUtils.date2Str(middleTimeOfToday(), df));
//        System.out.println(DateUtils.date2Str(endTimeOfToday(), df));
//    }

    /**
     * 返回string类型，格式为yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String date2SimpleStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.ENGLISH);
        if (date == null) {
            return "";
        } else {
            return sdf.format(date);
        }
    }

    /**
     * java.util.Date转换为String
     *
     * @param date
     * @return
     */
    public static String date2Str(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS, Locale.ENGLISH);
        if (date == null) {
            return "";
        } else {
            return sdf.format(date);
        }
    }

    public static String date2Str(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        if (date == null) {
            return "";
        } else {
            return sdf.format(date);
        }
    }

    /**
     * 计算2个日期之间的天数
     *
     * @param start
     * @param end
     * @return
     */
    public static long getDaysBetween(Date start, Date end) {
        long d = (end.getTime() - start.getTime()) / (ONE_DAY_MI) + 1;
        return d;
    }


    public static Long[] getDateBetween(Date start, Date end) {
        long betweenDate = end.getTime() - start.getTime();
        long h = 0L;
        long m = 0L;
        long s = 0L;
        long d = betweenDate / (ONE_DAY_MI);
        betweenDate %= ONE_DAY_MI;
        h = betweenDate / ONE_HOUR_MI;
        betweenDate %= ONE_HOUR_MI;
        m = betweenDate / ONE_MINUTS_MI;
        betweenDate %= ONE_MINUTS_MI;
        s = betweenDate / ONE_SECOND_MI;
        Long[] array = {d, h, m, s};
        return array;
    }

    /**
     * 返回两个日期之间间隔的天数、小时、分钟、秒的字符串
     *
     * @param start
     * @param end
     * @return
     */
    public static String getDateBetweenString(Date start, Date end) {
        String dateString = "";
        Long[] dateBetween = getDateBetween(start, end);
        long day = dateBetween[0];
        long hour = dateBetween[1];
        long minute = dateBetween[2];
        long second = dateBetween[3];
        if (day > 0) {
            dateString = day + "天";
            if (hour > 0) dateString += hour + "小时";

        } else if (day == 0 && hour > 0) {
            dateString = hour + "小时";
            if (minute > 0) dateString += minute + "分钟";
        } else if (hour == 0 && minute > 0) {
            dateString = minute + "分钟";
            if (second > 0) dateString += second + "秒";
        }else{
            dateString = second + "秒";
        }
        return dateString;
    }


    /**
     * @param year  int 年份
     * @param month int 月份
     * @return int 某年某月的最后一天
     */
    public static int getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // 某年某月的最后一天
        return cal.getActualMaximum(Calendar.DATE);
    }


    /**
     * 获取指定月的前一月（年）或后一月（年）
     *
     * @param dateStr
     * @param addYear
     * @param addMonth
     * @param addDate
     * @return
     * @throws ParseException
     * @throws Exception
     */
    public static String getLastMonth(String dateStr, int addYear, int addMonth, int addDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM);
        Date sourceDate = sdf.parse(dateStr);
        Calendar cal = Calendar.getInstance();
        cal.setTime(sourceDate);
        cal.add(Calendar.YEAR, addYear);
        cal.add(Calendar.MONTH, addMonth);
        cal.add(Calendar.DATE, addDate);

        SimpleDateFormat returnSdf = new SimpleDateFormat(YYYY_MM);
        String date = returnSdf.format(cal.getTime());
        return date;
    }

    /**
     * 当前月份
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    public static Date getPreviousNSecond(Date date, int n) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int second = calendar.get(Calendar.SECOND);
        calendar.set(Calendar.SECOND, second - n);
        return calendar.getTime();
    }

    /**
     * 获取周日历
     *
     * @param year
     * @param month
     * @return
     */
    public static List<String> getWeekCalender(int year, int month) {
        List<String> dlist = new ArrayList<String>();

        Calendar cn = Calendar.getInstance();
        cn.set(year, (month - 1), 1); // 设置年、月、日

        // 按星期显示, 打印当月1号前面的空格数,页面显示用
        for (int j = 1; j < (cn.get(Calendar.DAY_OF_WEEK)); j++) {
            dlist.add("&nbsp;");
        }
        // 当月所有日期
        int day = 1;
        for (int i = day; i <= day; i++) {
            dlist.add(String.valueOf(i));

            cn.add(Calendar.DAY_OF_WEEK, 1); // 增加一天
            day = cn.get(Calendar.DAY_OF_MONTH); // 得到当天是几号
        }

        return dlist;
    }

    /**
     * 当前年份
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    /**
     * String转换为java.util.Date
     *
     * @param date
     * @param format "yyyy-MM-dd"
     * @return
     */
    public static Date str2Date(String date, String format) {
        DateFormat parser = new SimpleDateFormat(format);
        try {
            return parser.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Timestamp getCurrentTimeStamp() {
        return new Timestamp(new Date().getTime());
    }
}
