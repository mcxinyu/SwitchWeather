package io.github.mcxinyu.switchweather.api;

import android.content.Context;

import io.github.mcxinyu.switchweather.BuildConfig;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherRealTime;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.V5City;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by huangyuefeng on 2017/3/19.
 * Contact me : mcxinyu@foxmail.com
 */
public class WeatherApiHelper {

    private static final WeatherApi WEATHER_API = ApiFactory.getWeatherApi();

    @Deprecated
    public static Observable<HeWeatherModel> getHeWeatherInfo(String city) {
        return WEATHER_API.getHeWeatherInfo(city, BuildConfig.HE_WEATHER_API_KEY);
    }

    @Deprecated
    public static Observable<ResponseBody> getConditionCode(Context context) {
        return WEATHER_API.getConditionCode(ApiRetrofit.CONDITION_CODE);
    }

    public static Observable<V5City> searchCity(String city) {
        return WEATHER_API.searchCity(ApiRetrofit.CITY_SEARCH, city, BuildConfig.HE_WEATHER_API_KEY)
                .map(new Func1<V5City, V5City>() {
                    @Override
                    public V5City call(V5City v5City) {
                        return (v5City != null && !v5City.getHeWeather5().get(0).getStatus().contains("unknown")) ? v5City : null;
                    }
                });
        // return WEATHER_API.searchCity(ApiRetrofit.CITY_SEARCH, city, BuildConfig.HE_WEATHER_API_KEY);
    }

    public static Observable<CaiYunWeatherForecast> getCaiYunWeatherForecast(String coordinate) {
        return WEATHER_API.getCaiYunWeatherForecast(BuildConfig.CAI_YUN_API_KEY, coordinate);
    }

    public static Observable<CaiYunWeatherRealTime> getCaiYunWeatherRealTime(String coordinate) {
        return WEATHER_API.getCaiYunWeatherRealTime(BuildConfig.CAI_YUN_API_KEY, coordinate);
    }
}
