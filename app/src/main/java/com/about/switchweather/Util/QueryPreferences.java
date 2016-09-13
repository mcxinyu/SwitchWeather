package com.about.switchweather.Util;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by 跃峰 on 2016/9/5.
 */
public class QueryPreferences {
    private static final String PREF_LOCATION_CITY_NAME = "location_city_name";
    private static final String PREF_UPDATE_TIME = "alarm_update_time";
    private static final String PREF_NETWORK_STATE = "network_state";

    public static final String SETTING_LOCATION_CITY = "setting_location_city";
    public static final String SETTING_C_F_CITY = "setting_c_f_city";
    public static final String SETTING_AUTO_UPDATE_TEXT = "setting_auto_update_text";
    public static final String SETTING_NOTIFICATION_WIDGET = "setting_notification_widget";
    public static final String SETTING_POOR_AIR_TEXT = "setting_poor_air_text";
    public static final String SETTING_SUNRISE_NOTIFICATION_TEXT = "setting_sunrise_notification_text";
    public static final String SETTING_SUNSET_NOTIFICATION_TEXT = "setting_sunset_notification_text";
    public static final String SETTING_ABOUT = "setting_about";

    public static boolean getStoreLocationButtonState(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SETTING_LOCATION_CITY, false);
    }

    public static void setStoreLocationButtonState(Context context, boolean enable){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(SETTING_LOCATION_CITY, enable)
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

    public static String getSettingCFCity(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SETTING_C_F_CITY, null);
    }
}
