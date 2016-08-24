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

    private String weatherInfoExample = "{\"EXAMPLE HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"66\",\"co\":\"1\",\"no2\":\"61\",\"o3\":\"82\",\"pm10\":\"64\",\"pm25\":\"35\",\"qlty\":\"良\",\"so2\":\"9\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-20 20:52\",\"utc\":\"2016-08-20 12:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:02\",\"ss\":\"18:51\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"101\",\"txt_d\":\"晴间多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-20\",\"hum\":\"66\",\"pcpn\":\"0.0\",\"pop\":\"68\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"248\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:02\",\"ss\":\"18:50\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-21\",\"hum\":\"60\",\"pcpn\":\"5.4\",\"pop\":\"91\",\"pres\":\"1001\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"7\",\"wind\":{\"deg\":\"280\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:49\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"101\",\"txt_d\":\"阵雨\",\"txt_n\":\"多云\"},\"date\":\"2016-08-22\",\"hum\":\"69\",\"pcpn\":\"5.3\",\"pop\":\"37\",\"pres\":\"1003\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"135\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:48\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"100\",\"txt_d\":\"多云\",\"txt_n\":\"晴\"},\"date\":\"2016-08-23\",\"hum\":\"69\",\"pcpn\":\"1.5\",\"pop\":\"14\",\"pres\":\"1003\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"177\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2016-08-24\",\"hum\":\"66\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1002\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"206\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"101\",\"txt_d\":\"晴\",\"txt_n\":\"多云\"},\"date\":\"2016-08-25\",\"hum\":\"68\",\"pcpn\":\"0.2\",\"pop\":\"27\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"207\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:46\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-26\",\"hum\":\"68\",\"pcpn\":\"0.1\",\"pop\":\"32\",\"pres\":\"1005\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-20 22:00\",\"hum\":\"87\",\"pop\":\"64\",\"pres\":\"1004\",\"tmp\":\"29\",\"wind\":{\"deg\":\"220\",\"dir\":\"西南风\",\"sc\":\"微风\",\"spd\":\"7\"}}],\"now\":{\"cond\":{\"code\":\"300\",\"txt\":\"阵雨\"},\"fl\":\"37\",\"hum\":\"84\",\"pcpn\":\"0\",\"pres\":\"1003\",\"tmp\":\"29\",\"vis\":\"10\",\"wind\":{\"deg\":\"190\",\"dir\":\"南风\",\"sc\":\"3-4\",\"spd\":\"15\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"热\",\"txt\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较不宜\",\"txt\":\"有降水，推荐您在室内进行低强度运动；若坚持户外运动，须注意选择避雨防滑地点，并携带雨具。\"},\"trav\":{\"brf\":\"一般\",\"txt\":\"有降水，稍热，微风，旅游指数一般，外出请尽量避开降雨时间，若外出，请注意防雷并携带雨具。\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";

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
            //String result = getUrlString(url);
            String result = weatherInfoExample;
            Log.i(TAG, "downloadWeather: Weather info : " + result);

            String jsonBody = dealHeWeatherJson(result);

            if ("not found".equals(jsonBody)){
                return null;
            }
            Gson gson = new Gson();
            weatherBean = gson.fromJson(jsonBody, WeatherBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherBean;
    }

    private List<Condition> downloadConditionList(String url) {
        List<Condition> conditionBeanList = null;
        try {
            String result = getUrlString(url);
            Log.i(TAG, "downloadConditionList: " + result);

            String jsonBody = dealHeWeatherJson(result);
            if ("not found".equals(jsonBody)){
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            conditionBeanList = gson.fromJson(result, new TypeToken<List<Condition>>(){}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conditionBeanList;
    }

    private List<City> downloadCityList(String url) {
        List<City> cityListList = null;
        try {
            String result = getUrlString(url);
            Log.i(TAG, "downloadCityList: " + result);

            String jsonBody = dealHeWeatherJson(result);
            if ("not found".equals(jsonBody)){
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            cityListList = gson.fromJson(result, new TypeToken<List<City>>(){}.getType());
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
