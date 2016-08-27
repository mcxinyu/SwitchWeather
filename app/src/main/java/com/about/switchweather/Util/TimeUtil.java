package com.about.switchweather.Util;

import android.content.Context;
import com.about.switchweather.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class TimeUtil {
    private static Date sDate = new Date();
    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getWeekday(){
        return getWeekday(null, null);
    }

    public static String getWeekday(String date, SimpleDateFormat format) {
        Calendar mCalendar = Calendar.getInstance();
        if (date == null){
            mCalendar.setTime(sDate);
        }else {
            try {
                mCalendar.setTime(format.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEE").format(mCalendar.getTime());
    }

    public static String getNear3Weekday(Context context, String date, SimpleDateFormat format){
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(sDate);
        int today = mCalendar.get(Calendar.DAY_OF_YEAR);

        try {
            mCalendar.setTime(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int targetDay = mCalendar.get(Calendar.DAY_OF_YEAR);

        String timeText= "";
        switch (targetDay - today) {
            case -1:
                timeText = context.getResources().getString(R.string.yesterday_text);
                break;
            case 0:
                timeText = context.getResources().getString(R.string.today_text);
                break;
            case 1:
                timeText = context.getResources().getString(R.string.tomorrow_text);
                break;
            default:
                timeText = getWeekday(date, format);
                break;
        }
        return timeText.replace(" ", "\n");
    }

    public static String getDate(String date, SimpleDateFormat format) {
        Calendar mCalendar = Calendar.getInstance();
        if (date == null){
            mCalendar.setTime(sDate);
        }else {
            try {
                mCalendar.setTime(format.parse(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return new SimpleDateFormat("M.d").format(mCalendar.getTime());
    }

    public static String getDate() {
        return getDate(null, null);
    }

    /**
     * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
     */
    public static String getDIYTime(Context context, String date, SimpleDateFormat format) {
        long curTime = System.currentTimeMillis() / (long) 1000;
        long timeStamp = 0;
        try {
            timeStamp = format.parse(date).getTime() / (long) 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long timeDifference = curTime - timeStamp;

        if (timeDifference < 600 && timeDifference >= 0) {
            return context.getResources().getString(R.string.just_text);
        } else if (timeDifference >= 600 && timeDifference < 3600) {
            return timeDifference / 60 + context.getResources().getString(R.string.minutes_text);
        } else {
            return date;
        }
    }
}
