package io.github.mcxinyu.switchweather;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import io.github.mcxinyu.switchweather.database.dao.DaoMaster;
import io.github.mcxinyu.switchweather.database.dao.DaoSession;
import io.github.mcxinyu.switchweather.util.baidulocationservice.LocationService;

/**
 * Created by huangyuefeng on 2017/8/25.
 * Contact me : mcxinyu@foxmail.com
 */
public class SwitchWeatherApp extends Application {
    private static final String DATABASE_NAME = "SwitchWeather";

    private static SwitchWeatherApp switchWeatherApp;
    private static DaoSession daoSession;

    public LocationService mLocationService;

    @Override
    public void onCreate() {
        super.onCreate();
        switchWeatherApp = this;
        setupDatabase();

        // 百度定位初始化
        mLocationService = new LocationService(switchWeatherApp);
    }

    public static SwitchWeatherApp getInstance() {
        return switchWeatherApp;
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(switchWeatherApp, DATABASE_NAME, null);
        SQLiteDatabase database = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstance() {
        return daoSession;
    }

    /**
     * 用来判断服务是否运行.
     *
     * @param cla 判断的服务类
     * @return true 在运行 false 不在运行
     */
    public static boolean isServiceRunning(Class cla) {
        ActivityManager activityManager = (ActivityManager) switchWeatherApp.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(cla.getName())) {
                return true;
            }
        }

        return false;
    }
}
