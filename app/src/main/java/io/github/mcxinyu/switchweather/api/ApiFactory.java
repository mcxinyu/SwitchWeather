package io.github.mcxinyu.switchweather.api;

/**
 * Created by 跃峰 on 2016/9/18.
 * Contact Me : mcxinyu@foxmail.com
 * 使用单例模式
 */
public class ApiFactory {

    private static final Object monitor = new Object();
    private static WeatherApi weatherApi = null;

    public static WeatherApi getWeatherApi() {
        synchronized (monitor) {
            if (weatherApi == null) {
                weatherApi = new ApiRetrofit().getWeatherApi();
            }
            return weatherApi;
        }
    }
}
