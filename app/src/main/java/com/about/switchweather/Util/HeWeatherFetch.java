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

    //private String weatherInfoExample = "{\"Example HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"91\",\"co\":\"1\",\"no2\":\"40\",\"o3\":\"99\",\"pm10\":\"49\",\"pm25\":\"35\",\"qlty\":\"良\",\"so2\":\"5\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-27 21:52\",\"utc\":\"2016-08-27 13:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:45\"},\"cond\":{\"code_d\":\"305\",\"code_n\":\"300\",\"txt_d\":\"小雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-27\",\"hum\":\"66\",\"pcpn\":\"4.6\",\"pop\":\"98\",\"pres\":\"1005\",\"tmp\":{\"max\":\"31\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"156\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"4\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:44\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-28\",\"hum\":\"76\",\"pcpn\":\"5.2\",\"pop\":\"86\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"9\",\"wind\":{\"deg\":\"10\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:43\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-29\",\"hum\":\"63\",\"pcpn\":\"1.2\",\"pop\":\"28\",\"pres\":\"1007\",\"tmp\":{\"max\":\"31\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"81\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:42\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-30\",\"hum\":\"53\",\"pcpn\":\"0.0\",\"pop\":\"22\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"96\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:41\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"302\",\"txt_d\":\"多云\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-08-31\",\"hum\":\"57\",\"pcpn\":\"0.0\",\"pop\":\"8\",\"pres\":\"1005\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"147\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"6\"}},{\"astro\":{\"sr\":\"06:06\",\"ss\":\"18:40\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"302\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-09-01\",\"hum\":\"61\",\"pcpn\":\"1.8\",\"pop\":\"57\",\"pres\":\"1001\",\"tmp\":{\"max\":\"31\",\"min\":\"25\"},\"vis\":\"10\",\"wind\":{\"deg\":\"208\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:06\",\"ss\":\"18:39\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-09-02\",\"hum\":\"70\",\"pcpn\":\"13.4\",\"pop\":\"50\",\"pres\":\"997\",\"tmp\":{\"max\":\"29\",\"min\":\"25\"},\"vis\":\"10\",\"wind\":{\"deg\":\"243\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-27 22:00\",\"hum\":\"89\",\"pop\":\"98\",\"pres\":\"1007\",\"tmp\":\"28\",\"wind\":{\"deg\":\"66\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"10\"}}],\"now\":{\"cond\":{\"code\":\"300\",\"txt\":\"阵雨\"},\"fl\":\"35\",\"hum\":\"89\",\"pcpn\":\"0\",\"pres\":\"1007\",\"tmp\":\"27\",\"vis\":\"2\",\"wind\":{\"deg\":\"30\",\"dir\":\"东风\",\"sc\":\"3-4\",\"spd\":\"16\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气多云，同时会感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，但丝毫不会影响您的心情。微风，虽天气稍热，却仍适宜旅游，不要错过机会呦！\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";
    //private String weatherInfoExample = "{\"HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"91\",\"co\":\"1\",\"no2\":\"40\",\"o3\":\"99\",\"pm10\":\"49\",\"pm25\":\"35\",\"qlty\":\"良\",\"so2\":\"5\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-27 21:52\",\"utc\":\"2016-08-27 13:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:45\"},\"cond\":{\"code_d\":\"305\",\"code_n\":\"300\",\"txt_d\":\"小雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-27\",\"hum\":\"66\",\"pcpn\":\"4.6\",\"pop\":\"98\",\"pres\":\"1005\",\"tmp\":{\"max\":\"31\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"156\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"4\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:44\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-28\",\"hum\":\"76\",\"pcpn\":\"5.2\",\"pop\":\"86\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"9\",\"wind\":{\"deg\":\"10\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:43\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-29\",\"hum\":\"63\",\"pcpn\":\"1.2\",\"pop\":\"28\",\"pres\":\"1007\",\"tmp\":{\"max\":\"31\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"81\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:42\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-30\",\"hum\":\"53\",\"pcpn\":\"0.0\",\"pop\":\"22\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"96\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:41\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"302\",\"txt_d\":\"多云\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-08-31\",\"hum\":\"57\",\"pcpn\":\"0.0\",\"pop\":\"8\",\"pres\":\"1005\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"147\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"6\"}},{\"astro\":{\"sr\":\"06:06\",\"ss\":\"18:40\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"302\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"雷阵雨\"},\"date\":\"2016-09-01\",\"hum\":\"61\",\"pcpn\":\"1.8\",\"pop\":\"57\",\"pres\":\"1001\",\"tmp\":{\"max\":\"31\",\"min\":\"25\"},\"vis\":\"10\",\"wind\":{\"deg\":\"208\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:06\",\"ss\":\"18:39\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-09-02\",\"hum\":\"70\",\"pcpn\":\"13.4\",\"pop\":\"50\",\"pres\":\"997\",\"tmp\":{\"max\":\"29\",\"min\":\"25\"},\"vis\":\"10\",\"wind\":{\"deg\":\"243\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-27 22:00\",\"hum\":\"89\",\"pop\":\"98\",\"pres\":\"1007\",\"tmp\":\"28\",\"wind\":{\"deg\":\"66\",\"dir\":\"东北风\",\"sc\":\"微风\",\"spd\":\"10\"}}],\"now\":{\"cond\":{\"code\":\"300\",\"txt\":\"阵雨\"},\"fl\":\"35\",\"hum\":\"89\",\"pcpn\":\"0\",\"pres\":\"1007\",\"tmp\":\"27\",\"vis\":\"2\",\"wind\":{\"deg\":\"30\",\"dir\":\"东风\",\"sc\":\"3-4\",\"spd\":\"16\"}},\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天天气多云，同时会感到有些热，不很舒适。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒，推荐您在室内进行低强度运动。\"},\"trav\":{\"brf\":\"适宜\",\"txt\":\"天气较好，但丝毫不会影响您的心情。微风，虽天气稍热，却仍适宜旅游，不要错过机会呦！\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";

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
