package io.github.mcxinyu.switchweather.util;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import io.github.mcxinyu.switchweather.model.V5City;

/**
 * Created by 跃峰 on 2016/9/5.
 */
public class QueryPreferences {
    private static final String PREF_LOCATION_CITY_NAME = "location_city_name";
    private static final String PREF_LOCATION_CITY_ID = "location_city_id";
    private static final String PREF_SEARCH_HISTORY = "search_history";

    private static final String PREF_UPDATE_TIME = "alarm_update_time";
    private static final String PREF_NETWORK_STATE = "network_state";

    public static final String SETTING_LOCATION_CITY = "setting_location_city";
    public static final String SETTING_TEMPERATURE_UNIT = "setting_temperature_unit";
    public static final String SETTING_AUTO_UPDATE_TEXT = "setting_auto_update_text";
    public static final String SETTING_NOTIFICATION_SERVICE = "setting_notification_service";
    public static final String SETTING_POOR_AIR_TEXT = "setting_poor_air_text";
    public static final String SETTING_SUNRISE_NOTIFICATION_TEXT = "setting_sunrise_notification_text";
    public static final String SETTING_SUNSET_NOTIFICATION_TEXT = "setting_sunset_notification_text";
    public static final String SETTING_ABOUT = "setting_about";
    public static final String SETTING_FEEDBACK = "setting_feedback";

    public static boolean getLocationButtonState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(SETTING_LOCATION_CITY, false);
    }

    public static void setLocationButtonState(Context context, boolean enable) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(SETTING_LOCATION_CITY, enable)
                .apply();
    }

    /**
     * 使用 {@link #getLocationCityId(Context)}
     *
     * @param context
     * @return
     */
    @Deprecated
    public static String getLocationCityName(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOCATION_CITY_NAME, "");
    }

    /**
     * 使用 {@link #setLocationCityId(Context, String)}
     *
     * @param context
     * @param cityName
     */
    @Deprecated
    public static void setLocationCityName(Context context, String cityName) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOCATION_CITY_NAME, cityName)
                .apply();
    }

    public static String getLocationCityId(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOCATION_CITY_ID, "");
    }

    public static void setLocationCityId(Context context, String cityId) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOCATION_CITY_ID, cityId)
                .apply();
    }

    public static String getUpdateTime(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_UPDATE_TIME, "");
    }

    public static void setUpdateTime(Context context, String time) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_UPDATE_TIME, time)
                .apply();
    }

    public static String getNetworkState(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_NETWORK_STATE, "");
    }

    public static void setNetworkState(Context context, String string) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_NETWORK_STATE, string)
                .apply();
    }

    public static String getSettingTemperatureUnit(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(SETTING_TEMPERATURE_UNIT, "C");
    }

    public static List<V5City.HeWeather5Bean.BasicBean> getSearchHistory(Context context) {
        String history = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_HISTORY, "");

        List<V5City.HeWeather5Bean.BasicBean> list = new ArrayList<>();
        if (!TextUtils.isEmpty(history)) {
            String[] split = history.split("@");
            for (int i = 0; i < split.length; i++) {
                String[] subSplit = split[i].split(",");
                if (subSplit.length >= 3) {
                    V5City.HeWeather5Bean.BasicBean bean = new V5City.HeWeather5Bean.BasicBean();
                    bean.setCity(subSplit[0]);
                    bean.setProv(subSplit[1]);
                    bean.setId(subSplit[2]);
                    list.add(bean);
                }
            }
            return list;
        }

        return list;
    }

    public static void setSearchHistory(Context context, String keyWord) {
        String oldHistory = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_SEARCH_HISTORY, "");

        // keyWord = keyWord.replaceAll("/[^'’[^\\p{P}]]/", "");

        if (!TextUtils.isEmpty(keyWord) && !oldHistory.contains(keyWord)) {
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putString(PREF_SEARCH_HISTORY, keyWord + "@" + oldHistory)
                    .apply();
        }
    }
}
