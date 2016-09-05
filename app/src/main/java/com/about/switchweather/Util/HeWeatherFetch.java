package com.about.switchweather.Util;

import android.net.Uri;
import android.util.Log;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.WeatherBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class HeWeatherFetch {
    private static final String TAG = "HeWeatherFetch";
    private static final String API_KEY = "33fdb5ca3d254333a617911e122f65ac";
    private static final Uri ENDPOINT = Uri.parse("https://api.heweather.com/x3/");

    private enum WEATHER_PORT {CONDITION, WEATHER, CITYLIST};

    private String buildUrl(WEATHER_PORT port, String query){
        Uri.Builder builder = null;
        // condition 天气代码接口（allcond、代码）
        // weather 天气接口
        // citylist （国内城市：allchina（先支持国内）、 热门城市：hotworld、 全部城市：allworld（以后支持，需要升级数据库？））
        // attractions 景点接口 （要钱，弃用）

        if (port.equals(WEATHER_PORT.CONDITION)){
            builder = ENDPOINT.buildUpon()
                    .appendPath("condition")
                    .appendQueryParameter("search", query);
        } else if (port.equals(WEATHER_PORT.WEATHER)){
            builder = ENDPOINT.buildUpon()
                    .appendPath("weather")
                    .appendQueryParameter("city", query);
        } else if (port.equals(WEATHER_PORT.CITYLIST)){
            builder = ENDPOINT.buildUpon()
                    .appendPath("citylist")
                    .appendQueryParameter("search", query);
        }
        return builder.appendQueryParameter("key", API_KEY).build().toString();
    }

    public byte[] getUrlBytes(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            InputStream inputStream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with" + urlString);
            }

            int byteRead = 0;
            byte[] buffer = new byte[1024];

            while ((byteRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, byteRead);
            }
            outputStream.close();

            return outputStream.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    public String getUrlString(String urlString) throws IOException {
        return new String(getUrlBytes(urlString));
    }

    public WeatherBean fetchWeatherBean(String cityName){
        String url = buildUrl(WEATHER_PORT.WEATHER, cityName);
        return downloadWeather(url);
    }

    public List<Condition> fetchConditionList(){
        String url = buildUrl(WEATHER_PORT.CONDITION, "allcond");
        return downloadConditionList(url);
    }

    public List<City> fetchCityList(){
        String url = buildUrl(WEATHER_PORT.CITYLIST, "allchina");
        return downloadCityList(url);
    }

    private WeatherBean downloadWeather(String url) {
        WeatherBean weatherBean = null;
        try {
            String result = getUrlString(url);
            //String result = weatherInfoExample;
            //Log.i(TAG, "downloadWeather: Weather info : " + result);

            String jsonBody = dealHeWeatherJson(result);

            if ("not found".equals(jsonBody)){
                return null;
            }
            Gson gson = new Gson();
            weatherBean = gson.fromJson(jsonBody, WeatherBean.class);
            Log.i(TAG, "downloadWeather: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherBean;
    }

    private List<Condition> downloadConditionList(String url) {
        List<Condition> conditionBeanList = null;
        try {
            String result = getUrlString(url);
            //Log.i(TAG, "downloadConditionList: " + result);

            String jsonBody = dealHeWeatherJson(result);
            if ("not found".equals(jsonBody)){
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            conditionBeanList = gson.fromJson(result, new TypeToken<List<Condition>>(){}.getType());
            Log.i(TAG, "downloadConditionList: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conditionBeanList;
    }

    private List<City> downloadCityList(String url) {
        List<City> cityListList = null;
        try {
            String result = getUrlString(url);
            //Log.i(TAG, "downloadCityList: " + result);

            String jsonBody = dealHeWeatherJson(result);
            if ("not found".equals(jsonBody)){
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            cityListList = gson.fromJson(result, new TypeToken<List<City>>(){}.getType());
            Log.i(TAG, "downloadCityList: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityListList;
    }

    private String dealHeWeatherJson(String result) {
        if (result.contains("\"status\":\"ok\"")) {
            result = result.substring(result.indexOf("[") + 1, result.lastIndexOf("]"));
        } else {
            Log.e(TAG, "dealHeWeatherJson: not found he weather info : with " + result);
            return "not found";
        }
        return result;
    }
}
