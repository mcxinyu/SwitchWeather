package io.github.mcxinyu.switchweather.api;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.github.mcxinyu.switchweather.BuildConfig;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.util.LogUtils;
import io.github.mcxinyu.switchweather.util.StateUtils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 跃峰 on 2016/9/18.
 * Contact Me : mcxinyu@foxmail.com
 */
public class ApiRetrofit {
    private static final String TAG = "ApiRetrofit";

    private WeatherApi mWeatherApi;

    public static final String CAI_YUN_WEATHER_V2_URL = "https://api.caiyunapp.com/";
    public static final String CITY_SEARCH = "https://api.heweather.com/v5/search";
    @Deprecated
    public static final String CONDITION_CODE = "https://cdn.heweather.com/condition-code.txt";
    @Deprecated
    public static final String HE_WEATHER_5_BASE_URL = "https://free-api.heweather.com/";

    public WeatherApi getWeatherApi() {
        return mWeatherApi;
    }

    public ApiRetrofit() {
        // 缓存 url
        File httpCacheDirectory = new File(SwitchWeatherApp.getInstance().getCacheDir(), "cacheData");
        int cacheSize = 1 * 1024 * 1024; // 1 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();

        Retrofit retrofitHeWeather5 = new Retrofit.Builder()
                .baseUrl(CAI_YUN_WEATHER_V2_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        mWeatherApi = retrofitHeWeather5.create(WeatherApi.class);
    }

    // 缓存
    private Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(7, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();

            Request request = chain.request();
            if (!StateUtils.isNetworkAvailable(SwitchWeatherApp.getInstance())) {
                // 网络不可用时，读取缓存
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
                LogUtils.d(TAG, "Cache Interceptor No Network");
            }

            Response originalResponse = chain.proceed(request);

            if (StateUtils.isNetworkAvailable(SwitchWeatherApp.getInstance())) {
                int maxAge = 0; // 这里设置为 0 就是说不进行缓存，我们也可以设置缓存时间
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24; // tolerate 1-day stale
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}
