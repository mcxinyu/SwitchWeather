package com.about.switchweather.Util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by 跃峰 on 2016/9/5.
 */
public class QueryPreferences {
    private static final String PREF_LOCATION_BUTTON_STATE = "setting_location_city";   // 与 settings_general.xml 中的 location_city 是同一功能
    private static final String PREF_LOCATION_CITY_NAME = "location_city_name";
    private static final String PREF_UPDATE_TIME = "alarm_update_time";
    private static final String PREF_NETWORK_STATE = "network_state";

    public static boolean getStoreLocationButtonState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOCATION_BUTTON_STATE, false);
    }

    public static void setStoreLocationButtonState(Context context, boolean enable){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LOCATION_BUTTON_STATE, enable)
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

    public static String getStoreUpdateTime(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_UPDATE_TIME, null);
    }

    public static void setStoreUpdateTime(Context context, String time){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_UPDATE_TIME, time)
                .apply();
    }

    public static String getStoreNetworkState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_NETWORK_STATE, null);
    }

    public static void setStoreNetworkState(Context context, String string){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_NETWORK_STATE, string)
                .apply();
    }
}
