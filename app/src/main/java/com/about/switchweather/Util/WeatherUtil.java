package com.about.switchweather.Util;

import android.content.Context;
import android.net.ConnectivityManager;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.R;
import com.about.switchweather.UI.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/27.
 */
public class WeatherUtil {
    @SuppressWarnings("deprecation")
    public static int convertIconToRes(String code){
        switch (code){
            case "100":
                return R.drawable.weather_icon_100;
            case "101":
                return R.drawable.weather_icon_101;
            case "102":
                return R.drawable.weather_icon_102;
            case "103":
                return R.drawable.weather_icon_103;
            case "104":
                return R.drawable.weather_icon_104;
            case "200":
                return R.drawable.weather_icon_200;
            case "201":
                return R.drawable.weather_icon_201;
            case "202":
                return R.drawable.weather_icon_202;
            case "203":
                return R.drawable.weather_icon_203;
            case "204":
                return R.drawable.weather_icon_204;
            case "205":
                return R.drawable.weather_icon_205;
            case "206":
                return R.drawable.weather_icon_206;
            case "207":
                return R.drawable.weather_icon_207;
            case "208":
                return R.drawable.weather_icon_208;
            case "209":
                return R.drawable.weather_icon_209;
            case "210":
                return R.drawable.weather_icon_210;
            case "211":
                return R.drawable.weather_icon_211;
            case "212":
                return R.drawable.weather_icon_212;
            case "213":
                return R.drawable.weather_icon_213;
            case "300":
                return R.drawable.weather_icon_300;
            case "301":
                return R.drawable.weather_icon_301;
            case "302":
                return R.drawable.weather_icon_302;
            case "303":
                return R.drawable.weather_icon_303;
            case "304":
                return R.drawable.weather_icon_304;
            case "305":
                return R.drawable.weather_icon_305;
            case "306":
                return R.drawable.weather_icon_306;
            case "307":
                return R.drawable.weather_icon_307;
            case "308":
                return R.drawable.weather_icon_308;
            case "309":
                return R.drawable.weather_icon_309;
            case "310":
                return R.drawable.weather_icon_310;
            case "311":
                return R.drawable.weather_icon_311;
            case "312":
                return R.drawable.weather_icon_312;
            case "313":
                return R.drawable.weather_icon_313;
            case "400":
                return R.drawable.weather_icon_400;
            case "401":
                return R.drawable.weather_icon_401;
            case "402":
                return R.drawable.weather_icon_402;
            case "403":
                return R.drawable.weather_icon_403;
            case "404":
                return R.drawable.weather_icon_404;
            case "405":
                return R.drawable.weather_icon_405;
            case "406":
                return R.drawable.weather_icon_406;
            case "407":
                return R.drawable.weather_icon_407;
            case "500":
                return R.drawable.weather_icon_500;
            case "501":
                return R.drawable.weather_icon_501;
            case "502":
                return R.drawable.weather_icon_502;
            case "503":
                return R.drawable.weather_icon_503;
            case "504":
                return R.drawable.weather_icon_504;
            case "506":
                return R.drawable.weather_icon_506;
            case "507":
                return R.drawable.weather_icon_507;
            case "508":
                return R.drawable.weather_icon_508;
            case "900":
                return R.drawable.weather_icon_900;
            case "901":
                return R.drawable.weather_icon_901;
            case "999":
                return R.drawable.weather_icon_999;
            default:
                return R.drawable.weather_icon_999;
        }
    }

    /**
     * 将定位到的城市在 list 中的位置移到第一位
     * @param weatherInfoList
     * @return
     */
    public static List<WeatherInfo> adjustLocationCity2ListFirst(List<WeatherInfo> weatherInfoList) {
        if (!QueryPreferences.getStoreLocationButtonState(MyApplication.getContext())){
            return weatherInfoList;
        }
        String cityName = QueryPreferences.getStoreLocationCityName(MyApplication.getContext());
        if (cityName != null) {
            List<WeatherInfo> list = new ArrayList<>();
            for (int i = 0; i < weatherInfoList.size(); i++) {
                if (weatherInfoList.get(i).getBasicCity().equals(cityName)) {
                    list.add(0, weatherInfoList.get(i));
                    break;
                }
            }
            for (int i = 0; i < weatherInfoList.size(); i++) {
                if (!weatherInfoList.get(i).getBasicCity().equals(cityName)) {
                    list.add(weatherInfoList.get(i));
                }
            }
            weatherInfoList = list;
        }
        return weatherInfoList;
    }

    public static boolean isNetworkAvailableAndConnected(){
        ConnectivityManager manager = (ConnectivityManager) MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;
        return isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
    }

    public static String convertCelsius2Fahrenheit(String temperature){
        return "" + Math.round(Integer.parseInt(temperature) * 1.8 + 32);
    }
}
