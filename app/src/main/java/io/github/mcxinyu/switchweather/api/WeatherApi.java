package io.github.mcxinyu.switchweather.api;

import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherRealTime;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.V5City;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by 跃峰 on 2016/9/18.
 * Contact Me : mcxinyu@foxmail.com
 */
public interface WeatherApi {

    /**
     * 查询天气信息（以后支持多语言）
     *
     * @param city 城市名称，city可通过城市中英文名称、ID、IP和经纬度进行查询，经纬度查询格式为：经度longitude,纬度latitude
     * @param key
     * @return
     */
    @Deprecated
    @GET("v5/weather")
    Observable<HeWeatherModel> getHeWeatherInfo(@Query("city") String city, @Query("key") String key);

    /**
     * 获取天气状态码
     *
     * @param url https://cdn.heweather.com/condition-code.txt
     * @return
     */
    @Deprecated
    @GET
    Observable<ResponseBody> getConditionCode(@Url String url);

    /**
     * 搜索城市信息
     *
     * @param url  https://api.heweather.com/v5/search?city=city&key=key
     * @param city 城市名称，city可通过城市中英文名称、ID、IP和经纬度进行查询，经纬度查询格式为：经度longitude,纬度latitude
     * @param key
     * @return
     */
    @GET
    Observable<V5City> searchCity(@Url String url, @Query("city") String city, @Query("key") String key);

    /**
     * 彩云天气 天气预报接口
     *
     * @param apiKey
     * @param coordinate 经度longitude,纬度latitude
     * @return
     */
    @GET("v2/{api_key}/{coordinate}/forecast.json")
    Observable<CaiYunWeatherForecast> getCaiYunWeatherForecast(@Path("api_key") String apiKey,
                                                               @Path("coordinate") String coordinate);

    /**
     * 彩云天气 实时天气状况接口
     *
     * @param apiKey
     * @param coordinate 经度longitude,纬度latitude
     * @return
     */
    @GET("v2/{api_key}/{coordinate}/realtime.json")
    Observable<CaiYunWeatherRealTime> getCaiYunWeatherRealTime(@Path("api_key") String apiKey,
                                                               @Path("coordinate") String coordinate);
}
