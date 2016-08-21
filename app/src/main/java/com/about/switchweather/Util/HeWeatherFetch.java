package com.about.switchweather.Util;

import android.net.Uri;
import android.util.Log;
import com.about.switchweather.Model.ConditionBean;
import com.about.switchweather.Model.WeatherBean;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class HeWeatherFetch {
    private static final String TAG = "HeWeatherFetch";
    private static final String API_KEY = "33fdb5ca3d254333a617911e122f65ac";
    private static final Uri ENDPOINT = Uri.parse("https://api.heweather.com/x3/");

    private enum WEATHER_PORT {CONDITION, WEATHER, ATTRACTIONS};

    private String buildUrl(WEATHER_PORT port, String query){
        // condition 天气代码接口（allcond、代码）
        // weather 天气接口
        // attractions 景点接口
        String params = "weather";
        if (port.equals(WEATHER_PORT.CONDITION)){
            params = "condition";
        } else if (port.equals(WEATHER_PORT.ATTRACTIONS)){
            params = "attractions";
        }

        Uri.Builder builder = ENDPOINT.buildUpon()
                .appendPath(params)
                .appendQueryParameter("city", query)
                .appendQueryParameter("key", API_KEY);

        return builder.build().toString();
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

    public WeatherBean fetchWeatherInfo(String cityName){
        String url = buildUrl(WEATHER_PORT.WEATHER, cityName);
        return downloadWeather(url);
    }

    private WeatherBean downloadWeather(String url) {
        WeatherBean mWeatherBean = null;
        try {
            String result = getUrlString(url);
            String jsonBody = dealHeWeatherJson(result);

            if ("not found".equals(jsonBody)){
                return null;
            }
            //parseWeather(mWeatherBean, jsonBody);
            Gson gson = new Gson();
            mWeatherBean = gson.fromJson(jsonBody, WeatherBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mWeatherBean;
    }

    private void parseWeather(WeatherBean weatherBean, String jsonBody) {
        Gson gson = new Gson();
        weatherBean = gson.fromJson(jsonBody, WeatherBean.class);
    }

    private String dealHeWeatherJson(String result) {
        if (result.contains("\"status\":\"ok\"")) {
            result = result.substring(result.indexOf("[") + 1, result.lastIndexOf("]"));
            Log.i(TAG, "dealHeWeatherJson: weather info :" + result);
        } else {
            Log.e(TAG, "dealHeWeatherJson: not found weather info : with " + result);
            return "not found";
        }
        return result;
    }

    public ConditionBean fetchConditionInfo(){
        String url = buildUrl(WEATHER_PORT.CONDITION, "allcond");
        return downloadCondition(url);
    }

    private ConditionBean downloadCondition(String url) {
        ConditionBean mConditionBean = null;
        try {
            String result = getUrlString(url);
            if (!result.contains("\"status\": \"ok\"")){
                return null;
            }
            parseCondition(mConditionBean, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void parseCondition(ConditionBean conditionBean, String result) {
        Gson gson = new Gson();
        conditionBean = gson.fromJson(result, conditionBean.getClass());
    }

}
