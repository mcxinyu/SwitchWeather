package io.github.mcxinyu.switchweather.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.DrawableRes;

import java.util.ArrayList;
import java.util.List;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.model.WeatherInfo;


/**
 * Created by 跃峰 on 2016/8/27.
 */
public class WeatherUtil {
    @DrawableRes
    public static int convertSkyConToRes(String skyCon) {
        switch (skyCon) {
            case "CLEAR_DAY":
                return R.drawable.weather_icon_clear_day;
            case "CLEAR_NIGHT":
                return R.drawable.weather_icon_clear_night;
            case "PARTLY_CLOUDY_DAY":
                return R.drawable.weather_icon_partly_cloudy_day;
            case "PARTLY_CLOUDY_NIGHT":
                return R.drawable.weather_icon_partly_cloudy_night;
            case "CLOUDY":
                return R.drawable.weather_icon_cloudy;
            case "RAIN":
                return R.drawable.weather_icon_rain;
            case "SNOW":
                return R.drawable.weather_icon_snow;
            case "WIND":
                return R.drawable.weather_icon_wind;
            case "FOG":
                return R.drawable.weather_icon_fog;
            case "HAZE":
                return R.drawable.weather_icon_haze;
            case "SLEET":
                return R.drawable.weather_icon_sleet;
            default:
                return R.drawable.weather_icon_unknow;
        }
    }

    public static String convertSkyConToDesc(String skyCon) {
        switch (skyCon) {
            case "CLEAR_DAY":
                return "晴天";
            case "CLEAR_NIGHT":
                return "晴夜";
            case "PARTLY_CLOUDY_DAY":
                return "多云";
            case "PARTLY_CLOUDY_NIGHT":
                return "多云";
            case "CLOUDY":
                return "阴";
            case "RAIN":
                return "雨";
            case "SNOW":
                return "雪";
            case "WIND":
                return "风";
            case "FOG":
                return "雾";
            case "HAZE":
                return "霾";
            case "SLEET":
                return "冻雨";
            default:
                return "";
        }
    }

    public static String convertAqiToQuality(int aqi) {
        if (aqi <= 50) {
            return "优";
        } else if (aqi > 50 && aqi <= 100) {
            return "良";
        } else if (aqi > 100 && aqi <= 150) {
            return "轻度污染";
        } else if (aqi > 150 && aqi <= 200) {
            return "中度污染";
        } else if (aqi > 200 && aqi <= 300) {
            return "重度污染";
        } else if (aqi > 300) {
            return "严重污染";
        } else {
            return "";
        }
    }

    public static String convertDirectionToDesc(double direction) {
        if (direction >= 348.76 || direction <= 11.25) {
            return "北风";
        } else if (direction >= 11.26 && direction <= 33.75) {
            return "北偏东风";
        } else if (direction >= 33.76 && direction <= 56.25) {
            return "东北风";
        } else if (direction >= 56.26 && direction <= 78.75) {
            return "东偏北风";
        } else if (direction >= 78.76 && direction <= 101.25) {
            return "东风";
        } else if (direction >= 101.26 && direction <= 123.75) {
            return "东偏南风";
        } else if (direction >= 123.76 && direction <= 146.25) {
            return "东南风";
        } else if (direction >= 146.26 && direction <= 168.75) {
            return "南偏东风";
        } else if (direction >= 168.76 && direction <= 191.25) {
            return "南风";
        } else if (direction >= 191.26 && direction <= 213.75) {
            return "南偏西风";
        } else if (direction >= 213.76 && direction <= 236.25) {
            return "西南风";
        } else if (direction >= 236.26 && direction <= 258.75) {
            return "西偏南风";
        } else if (direction >= 258.76 && direction <= 281.25) {
            return "西风";
        } else if (direction >= 281.26 && direction <= 303.75) {
            return "西偏北风";
        } else if (direction >= 303.76 && direction <= 326.25) {
            return "西北风";
        } else if (direction >= 326.26 && direction <= 348.75) {
            return "北偏西风";
        } else {
            return "";
        }
    }

    public static String convertSpeedToDesc(double speed) {
        double v = speed / 1000 / 60 / 60;
        if (v <= 0.2) {
            return "无风";
        } else if (v > 0.3 && v <= 1.5) {
            return "1级软风";
        } else if (v > 1.6 && v <= 3.3) {
            return "2级轻风";
        } else if (v > 3.4 && v <= 5.4) {
            return "3级微风";
        } else if (v > 5.5 && v <= 7.9) {
            return "4级和风";
        } else if (v > 8.0 && v <= 10.7) {
            return "5级轻劲风";
        } else if (v > 10.8 && v <= 13.8) {
            return "6级强风";
        } else if (v > 13.9 && v <= 17.1) {
            return "7级疾风";
        } else if (v > 17.2 && v <= 20.7) {
            return "8级大风";
        } else if (v > 20.8 && v <= 24.4) {
            return "9级烈风";
        } else if (v > 24.5 && v <= 28.4) {
            return "10级狂风";
        } else if (v > 28.5 && v <= 32.6) {
            return "11级暴风";
        } else if (v > 32.7 && v <= 36.9) {
            return "12级台风";
        } else if (v > 37.0 && v <= 41.4) {
            return "轻微龙卷风 13级";
        } else if (v > 41.5 && v <= 46.1) {
            return "中等龙卷风 14级";
        } else if (v > 46.2 && v <= 50.9) {
            return "超大龙卷风 15级";
        } else if (v > 51.0 && v <= 56.0) {
            return "极大龙卷风 16级";
        } else if (v > 56.1 && v <= 61.2) {
            return "强台龙卷风 17级";
        } else if (v >= 61.3) {
            return "世界末日，躲不了风";
        } else {
            return "";
        }
    }

    /**
     * 将定位到的城市在 list 中的位置移到第一位
     *
     * @param weatherInfoList
     * @return
     */
    public static List<WeatherInfo> adjustLocationCity2ListFirst(List<WeatherInfo> weatherInfoList) {
        if (!QueryPreferences.getLocationButtonState(SwitchWeatherApp.getInstance())) {
            return weatherInfoList;
        }
        String cityName = QueryPreferences.getLocationCityName(SwitchWeatherApp.getInstance());
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

    public static boolean isNetworkAvailableAndConnected() {
        ConnectivityManager manager = (ConnectivityManager) SwitchWeatherApp.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;
        return isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
    }

    public static String convertCelsius2Fahrenheit(double temperature) {
        return "" + (int) Math.round(temperature * 1.8 + 32.0);
    }
}
