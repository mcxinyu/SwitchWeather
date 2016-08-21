package com.about.switchweather.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class TimeUtil {
    private static Date date = new Date();

    public static String getCurrentWeekday() {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(date);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEE").format(mCalendar.getTime());
    }

    public static String getCurrentDate() {
        Calendar mCalendar = Calendar.getInstance();
        return null;
    }
}
