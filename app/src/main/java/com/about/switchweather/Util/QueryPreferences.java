package com.about.switchweather.Util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by 跃峰 on 2016/9/5.
 */
public class QueryPreferences {
    private static final String PREF_LOCATION_ENABLE = "location_enable";
    private static final String PREF_LOCATION_CITY_ID = "location_city_id";

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

    public static String getStoreLocationCityId(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOCATION_CITY_ID, null);
    }

    public static void setStoreLocationCityId(Context context, String cityName){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOCATION_CITY_ID, cityName)
                .apply();
    }
}
