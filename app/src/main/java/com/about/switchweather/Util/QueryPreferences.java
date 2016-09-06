package com.about.switchweather.Util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by 跃峰 on 2016/9/5.
 */
public class QueryPreferences {
    private static final String PREF_LOCATION_ENABLE = "location_enable";
    private static final String PREF_LOCATION_CITY_NAME = "location_city_name";

    public static boolean getStoreLocationEnable(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOCATION_ENABLE, false);
    }

    public static void setStoreLocationEnable(Context context, boolean enable){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LOCATION_ENABLE, enable)
                .apply();
    }

    public static String getStoreLocationCityName(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOCATION_CITY_NAME, null);
    }

    public static void setStoreLocationCityName(Context context, String cityName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOCATION_CITY_NAME, cityName)
                .apply();
    }
}
