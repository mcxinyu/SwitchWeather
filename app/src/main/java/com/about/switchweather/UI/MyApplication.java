package com.about.switchweather.UI;

import android.app.Application;
import android.content.Context;

/**
 * Created by 跃峰 on 2016/8/27.
 */
public class MyApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }
}
