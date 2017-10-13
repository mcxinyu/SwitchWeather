package io.github.mcxinyu.switchweather.service;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import io.github.mcxinyu.switchweather.database.WeatherDatabaseLab;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.WeatherInfo;
import io.github.mcxinyu.switchweather.util.HeWeatherFetch;
import io.github.mcxinyu.switchweather.util.LogUtils;
import io.github.mcxinyu.switchweather.util.WeatherUtil;

/**
 * Created by 跃峰 on 2016/9/8.
 * 定时更新天气，为的是获取一整天的小时天气，采用彩云天气后好像没必要了，以后如果要做定时提醒再使用吧
 */
public class UpdateWeatherService extends IntentService {
    private static final String TAG = "UpdateWeatherService";
    private static int REQUEST_CODE_DAILY = 127;
    private static int REQUEST_CODE_HOUR = 126;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, UpdateWeatherService.class);
        //intent.putExtra();
        return intent;
    }

    public static void setServiceDailyAlarm(Context context, boolean isOn) {
        Intent updateWeatherServiceIntent = UpdateWeatherService.newIntent(context);
        // 获取 UpdateWeatherService 的 PendingIntent
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE_DAILY, updateWeatherServiceIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());   //设置当前时间
            calendar.setTimeZone(TimeZone.getDefault());    // 这里时区需要设置一下，不然会有时差
            calendar.set(Calendar.HOUR_OF_DAY, 1);  //时，夜晚更新。
            calendar.set(Calendar.MINUTE, 20);  //分
            calendar.set(Calendar.SECOND, 0);   //秒
            calendar.set(Calendar.MILLISECOND, 0);  //毫秒
            // 选择的定时时间
            long selectTimeMillis = calendar.getTimeInMillis();

            long triggerTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
            long currentTimeMillis = System.currentTimeMillis();

            // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
            if (currentTimeMillis > selectTimeMillis) {
                calendar.add(Calendar.DAY_OF_MONTH, 1);
                selectTimeMillis = calendar.getTimeInMillis();
            }

            // 计算现在时间到设定时间的时间差
            long time = selectTimeMillis - currentTimeMillis;
            triggerTime += time;

            // 定闹钟
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime, AlarmManager.INTERVAL_DAY, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public static void setService6HourAlarm(Context context, boolean isOn) {
        Intent updateWeatherServiceIntent = UpdateWeatherService.newIntent(context);
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_CODE_HOUR, updateWeatherServiceIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());   //设置当前时间
            calendar.setTimeZone(TimeZone.getDefault());    // 这里时区需要设置一下，不然会有时差
            calendar.set(Calendar.HOUR_OF_DAY, 6);  //时
            calendar.set(Calendar.MINUTE, 0);  //分
            calendar.set(Calendar.SECOND, 0);   //秒
            calendar.set(Calendar.MILLISECOND, 0);  //毫秒
            // 选择的定时时间
            long selectTimeMillis = calendar.getTimeInMillis();

            long triggerTime = SystemClock.elapsedRealtime(); // 开机之后到现在的运行时间(包括睡眠时间)
            long currentTimeMillis = System.currentTimeMillis();

            // 如果当前时间大于设置的时间，那么就在加 6 小时，直到设置时间在未来 6 小时内
            while (currentTimeMillis > selectTimeMillis) {
                calendar.add(Calendar.HOUR_OF_DAY, 6);
                selectTimeMillis = calendar.getTimeInMillis();
            }

            // 计算现在时间到设定时间的时间差
            long time = selectTimeMillis - currentTimeMillis;
            triggerTime += time;

            // 定闹钟
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime, AlarmManager.INTERVAL_HOUR * 6, pendingIntent);
        } else {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }
    }

    public UpdateWeatherService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int date = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        // QueryPreferences.setStoreUpdateTime(this, year + "/" + month + "/" + date + " " + hour + ":" + minute + ":" + second);

        if (!WeatherUtil.isNetworkAvailableAndConnected()) {
            LogUtils.i(TAG, "onHandleIntent: Network Connection Exception!");
            return;
        }

        List<WeatherInfo> weatherInfoList = WeatherDatabaseLab.get(this).getWeatherInfoList();
        if (weatherInfoList.size() == 0) {
            return;
        }
        for (int i = 0; i < weatherInfoList.size(); i++) {
            // 更新所有选中的城市天气
            storeData(new HeWeatherFetch().fetchWeatherBean(weatherInfoList.get(i).getBasicCity()));
        }
    }

    private void storeData(HeWeatherModel weatherModel) {
        if (weatherModel != null) {
            WeatherDatabaseLab.get(this).addWeatherInfo(weatherModel);
        }
    }
}
