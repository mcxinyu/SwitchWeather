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

    //private String weatherInfoExample = "{\"HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"56\",\"co\":\"1\",\"no2\":\"36\",\"o3\":\"119\",\"pm10\":\"60\",\"pm25\":\"32\",\"qlty\":\"良\",\"so2\":\"8\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-24 14:52\",\"utc\":\"2016-08-24 06:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-24\",\"hum\":\"63\",\"pcpn\":\"0.7\",\"pop\":\"96\",\"pres\":\"1003\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"9\",\"wind\":{\"deg\":\"170\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-25\",\"hum\":\"63\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"169\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:46\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-26\",\"hum\":\"63\",\"pcpn\":\"15.5\",\"pop\":\"77\",\"pres\":\"1004\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:45\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-27\",\"hum\":\"73\",\"pcpn\":\"25.3\",\"pop\":\"75\",\"pres\":\"1005\",\"tmp\":{\"max\":\"31\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"43\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:44\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"100\",\"txt_d\":\"阵雨\",\"txt_n\":\"晴\"},\"date\":\"2016-08-28\",\"hum\":\"56\",\"pcpn\":\"1.0\",\"pop\":\"67\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"20\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"6\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:43\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2016-08-29\",\"hum\":\"52\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"106\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:42\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"305\",\"txt_d\":\"晴\",\"txt_n\":\"小雨\"},\"date\":\"2016-08-30\",\"hum\":\"62\",\"pcpn\":\"6.4\",\"pop\":\"45\",\"pres\":\"1007\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"151\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-24 16:00\",\"hum\":\"67\",\"pop\":\"95\",\"pres\":\"1003\",\"tmp\":\"34\",\"wind\":{\"deg\":\"159\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"date\":\"2016-08-24 19:00\",\"hum\":\"78\",\"pop\":\"32\",\"pres\":\"1003\",\"tmp\":\"31\",\"wind\":{\"deg\":\"138\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"date\":\"2016-08-24 22:00\",\"hum\":\"84\",\"pop\":\"0\",\"pres\":\"1004\",\"tmp\":\"29\",\"wind\":{\"deg\":\"118\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"6\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"37\",\"hum\":\"69\",\"pcpn\":\"0\",\"pres\":\"1003\",\"tmp\":\"31\",\"vis\":\"4\",\"wind\":{\"deg\":\"40\",\"dir\":\"西南风\",\"sc\":\"4-5\",\"spd\":\"22\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"很不舒适\",\"txt\":\"白天天气晴好，但烈日炎炎您会感到很热，很不舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒。推荐您进行室内运动。\"},\"trav\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，温度较高，天气较热，但有微风相伴，还是比较适宜旅游的，不过外出时要注意防暑防晒哦！\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";
    private String weatherInfoExample = "{\"HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"56\",\"co\":\"1\",\"no2\":\"36\",\"o3\":\"119\",\"pm10\":\"60\",\"pm25\":\"32\",\"qlty\":\"良\",\"so2\":\"8\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-24 14:52\",\"utc\":\"2016-08-24 06:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-24\",\"hum\":\"63\",\"pcpn\":\"0.7\",\"pop\":\"96\",\"pres\":\"1003\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"9\",\"wind\":{\"deg\":\"170\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-25\",\"hum\":\"63\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"169\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:46\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-26\",\"hum\":\"63\",\"pcpn\":\"15.5\",\"pop\":\"77\",\"pres\":\"1004\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:45\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"300\",\"txt_d\":\"阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-27\",\"hum\":\"73\",\"pcpn\":\"25.3\",\"pop\":\"75\",\"pres\":\"1005\",\"tmp\":{\"max\":\"31\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"43\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:44\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"100\",\"txt_d\":\"阵雨\",\"txt_n\":\"晴\"},\"date\":\"2016-08-28\",\"hum\":\"56\",\"pcpn\":\"1.0\",\"pop\":\"67\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"20\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"6\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:43\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2016-08-29\",\"hum\":\"52\",\"pcpn\":\"0.0\",\"pop\":\"1\",\"pres\":\"1006\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"106\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:05\",\"ss\":\"18:42\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"305\",\"txt_d\":\"晴\",\"txt_n\":\"小雨\"},\"date\":\"2016-08-30\",\"hum\":\"62\",\"pcpn\":\"6.4\",\"pop\":\"45\",\"pres\":\"1007\",\"tmp\":{\"max\":\"32\",\"min\":\"26\"},\"vis\":\"10\",\"wind\":{\"deg\":\"151\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"9\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-24 16:00\",\"hum\":\"67\",\"pop\":\"95\",\"pres\":\"1003\",\"tmp\":\"34\",\"wind\":{\"deg\":\"159\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"date\":\"2016-08-24 19:00\",\"hum\":\"78\",\"pop\":\"32\",\"pres\":\"1003\",\"tmp\":\"31\",\"wind\":{\"deg\":\"138\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"date\":\"2016-08-24 22:00\",\"hum\":\"84\",\"pop\":\"0\",\"pres\":\"1004\",\"tmp\":\"29\",\"wind\":{\"deg\":\"118\",\"dir\":\"东南风\",\"sc\":\"微风\",\"spd\":\"6\"}}],\"now\":{\"cond\":{\"code\":\"101\",\"txt\":\"多云\"},\"fl\":\"37\",\"hum\":\"69\",\"pcpn\":\"0\",\"pres\":\"1003\",\"tmp\":\"31\",\"vis\":\"4\",\"wind\":{\"deg\":\"40\",\"dir\":\"西南风\",\"sc\":\"4-5\",\"spd\":\"22\"}},\"suggestion\":{\"comf\":{\"brf\":\"很不舒适\",\"txt\":\"白天天气晴好，但烈日炎炎您会感到很热，很不舒适。\"},\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"},\"drsg\":{\"brf\":\"炎热\",\"txt\":\"天气炎热，建议着短衫、短裙、短裤、薄型T恤衫等清凉夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，户外运动请注意防晒。推荐您进行室内运动。\"},\"trav\":{\"brf\":\"较适宜\",\"txt\":\"天气较好，温度较高，天气较热，但有微风相伴，还是比较适宜旅游的，不过外出时要注意防暑防晒哦！\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";

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
            //Log.i(TAG, "downloadConditionList: " + result);

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
            //Log.i(TAG, "downloadCityList: " + result);

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
