package com.about.switchweather.Util;

import android.net.Uri;
import android.util.Log;
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

    private enum WEATHER_PORT {CONDITION, WEATHER, ATTRACTIONS};

    private String weatherInfoExample = "{\"EXAMPLE HeWeather data service 3.0\":[{\"aqi\":{\"city\":{\"aqi\":\"66\",\"co\":\"1\",\"no2\":\"61\",\"o3\":\"82\",\"pm10\":\"64\",\"pm25\":\"35\",\"qlty\":\"良\",\"so2\":\"9\"}},\"basic\":{\"city\":\"深圳\",\"cnty\":\"中国\",\"id\":\"CN101280601\",\"lat\":\"22.544000\",\"lon\":\"114.109000\",\"update\":{\"loc\":\"2016-08-20 20:52\",\"utc\":\"2016-08-20 12:52\"}},\"daily_forecast\":[{\"astro\":{\"sr\":\"06:02\",\"ss\":\"18:51\"},\"cond\":{\"code_d\":\"103\",\"code_n\":\"101\",\"txt_d\":\"晴间多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-20\",\"hum\":\"66\",\"pcpn\":\"0.0\",\"pop\":\"68\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"27\"},\"vis\":\"10\",\"wind\":{\"deg\":\"248\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"7\"}},{\"astro\":{\"sr\":\"06:02\",\"ss\":\"18:50\"},\"cond\":{\"code_d\":\"302\",\"code_n\":\"300\",\"txt_d\":\"雷阵雨\",\"txt_n\":\"阵雨\"},\"date\":\"2016-08-21\",\"hum\":\"60\",\"pcpn\":\"5.4\",\"pop\":\"91\",\"pres\":\"1001\",\"tmp\":{\"max\":\"32\",\"min\":\"27\"},\"vis\":\"7\",\"wind\":{\"deg\":\"280\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"10\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:49\"},\"cond\":{\"code_d\":\"300\",\"code_n\":\"101\",\"txt_d\":\"阵雨\",\"txt_n\":\"多云\"},\"date\":\"2016-08-22\",\"hum\":\"69\",\"pcpn\":\"5.3\",\"pop\":\"37\",\"pres\":\"1003\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"135\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"5\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:48\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"100\",\"txt_d\":\"多云\",\"txt_n\":\"晴\"},\"date\":\"2016-08-23\",\"hum\":\"69\",\"pcpn\":\"1.5\",\"pop\":\"14\",\"pres\":\"1003\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"177\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"2\"}},{\"astro\":{\"sr\":\"06:03\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"100\",\"txt_d\":\"晴\",\"txt_n\":\"晴\"},\"date\":\"2016-08-24\",\"hum\":\"66\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1002\",\"tmp\":{\"max\":\"34\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"206\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"8\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:47\"},\"cond\":{\"code_d\":\"100\",\"code_n\":\"101\",\"txt_d\":\"晴\",\"txt_n\":\"多云\"},\"date\":\"2016-08-25\",\"hum\":\"68\",\"pcpn\":\"0.2\",\"pop\":\"27\",\"pres\":\"1004\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"207\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"3\"}},{\"astro\":{\"sr\":\"06:04\",\"ss\":\"18:46\"},\"cond\":{\"code_d\":\"101\",\"code_n\":\"101\",\"txt_d\":\"多云\",\"txt_n\":\"多云\"},\"date\":\"2016-08-26\",\"hum\":\"68\",\"pcpn\":\"0.1\",\"pop\":\"32\",\"pres\":\"1005\",\"tmp\":{\"max\":\"33\",\"min\":\"28\"},\"vis\":\"10\",\"wind\":{\"deg\":\"211\",\"dir\":\"无持续风向\",\"sc\":\"微风\",\"spd\":\"1\"}}],\"hourly_forecast\":[{\"date\":\"2016-08-20 22:00\",\"hum\":\"87\",\"pop\":\"64\",\"pres\":\"1004\",\"tmp\":\"29\",\"wind\":{\"deg\":\"220\",\"dir\":\"西南风\",\"sc\":\"微风\",\"spd\":\"7\"}}],\"now\":{\"cond\":{\"code\":\"300\",\"txt\":\"阵雨\"},\"fl\":\"37\",\"hum\":\"84\",\"pcpn\":\"0\",\"pres\":\"1003\",\"tmp\":\"29\",\"vis\":\"10\",\"wind\":{\"deg\":\"190\",\"dir\":\"南风\",\"sc\":\"3-4\",\"spd\":\"15\"}},\"status\":\"ok\",\"suggestion\":{\"comf\":{\"brf\":\"较不舒适\",\"txt\":\"白天虽然有雨，但仍无法削弱较高气温带来的暑意，同时降雨造成湿度加大会您感到有些闷热，不很舒适。\"},\"cw\":{\"brf\":\"不宜\",\"txt\":\"不宜洗车，未来24小时内有雨，如果在此期间洗车，雨水和路上的泥水可能会再次弄脏您的爱车。\"},\"drsg\":{\"brf\":\"热\",\"txt\":\"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。\"},\"flu\":{\"brf\":\"少发\",\"txt\":\"各项气象条件适宜，发生感冒机率较低。但请避免长期处于空调房间中，以防感冒。\"},\"sport\":{\"brf\":\"较不宜\",\"txt\":\"有降水，推荐您在室内进行低强度运动；若坚持户外运动，须注意选择避雨防滑地点，并携带雨具。\"},\"trav\":{\"brf\":\"一般\",\"txt\":\"有降水，稍热，微风，旅游指数一般，外出请尽量避开降雨时间，若外出，请注意防雷并携带雨具。\"},\"uv\":{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\"}}}]}";
    private String conditionInfoExample = "{\"status\":\"ok\",\"EXAMPLE cond_info\":[{\"code\":\"100\",\"txt\":\"晴\",\"txt_en\":\"Sunny/Clear\",\"icon\":\"http://files.heweather.com/cond_icon/100.png\"},{\"code\":\"101\",\"txt\":\"多云\",\"txt_en\":\"Cloudy\",\"icon\":\"http://files.heweather.com/cond_icon/101.png\"},{\"code\":\"102\",\"txt\":\"少云\",\"txt_en\":\"Few Clouds\",\"icon\":\"http://files.heweather.com/cond_icon/102.png\"},{\"code\":\"103\",\"txt\":\"晴间多云\",\"txt_en\":\"Partly Cloudy\",\"icon\":\"http://files.heweather.com/cond_icon/103.png\"},{\"code\":\"104\",\"txt\":\"阴\",\"txt_en\":\"Overcast\",\"icon\":\"http://files.heweather.com/cond_icon/104.png\"},{\"code\":\"200\",\"txt\":\"有风\",\"txt_en\":\"Windy\",\"icon\":\"http://files.heweather.com/cond_icon/200.png\"},{\"code\":\"201\",\"txt\":\"平静\",\"txt_en\":\"Calm\",\"icon\":\"http://files.heweather.com/cond_icon/201.png\"},{\"code\":\"202\",\"txt\":\"微风\",\"txt_en\":\"Light Breeze\",\"icon\":\"http://files.heweather.com/cond_icon/202.png\"},{\"code\":\"203\",\"txt\":\"和风\",\"txt_en\":\"Moderate/Gentle Breeze\",\"icon\":\"http://files.heweather.com/cond_icon/203.png\"},{\"code\":\"204\",\"txt\":\"清风\",\"txt_en\":\"Fresh Breeze\",\"icon\":\"http://files.heweather.com/cond_icon/204.png\"},{\"code\":\"205\",\"txt\":\"强风/劲风\",\"txt_en\":\"Strong Breeze\",\"icon\":\"http://files.heweather.com/cond_icon/205.png\"},{\"code\":\"206\",\"txt\":\"疾风\",\"txt_en\":\"High Wind, Near Gale\",\"icon\":\"http://files.heweather.com/cond_icon/206.png\"},{\"code\":\"207\",\"txt\":\"大风\",\"txt_en\":\"Gale\",\"icon\":\"http://files.heweather.com/cond_icon/207.png\"},{\"code\":\"208\",\"txt\":\"烈风\",\"txt_en\":\"Strong Gale\",\"icon\":\"http://files.heweather.com/cond_icon/208.png\"},{\"code\":\"209\",\"txt\":\"风暴\",\"txt_en\":\"Storm\",\"icon\":\"http://files.heweather.com/cond_icon/209.png\"},{\"code\":\"210\",\"txt\":\"狂爆风\",\"txt_en\":\"Violent Storm\",\"icon\":\"http://files.heweather.com/cond_icon/210.png\"},{\"code\":\"211\",\"txt\":\"飓风\",\"txt_en\":\"Hurricane\",\"icon\":\"http://files.heweather.com/cond_icon/211.png\"},{\"code\":\"212\",\"txt\":\"龙卷风\",\"txt_en\":\"Tornado\",\"icon\":\"http://files.heweather.com/cond_icon/212.png\"},{\"code\":\"213\",\"txt\":\"热带风暴\",\"txt_en\":\"Tropical Storm\",\"icon\":\"http://files.heweather.com/cond_icon/213.png\"},{\"code\":\"300\",\"txt\":\"阵雨\",\"txt_en\":\"Shower Rain\",\"icon\":\"http://files.heweather.com/cond_icon/300.png\"},{\"code\":\"301\",\"txt\":\"强阵雨\",\"txt_en\":\"Heavy Shower Rain\",\"icon\":\"http://files.heweather.com/cond_icon/301.png\"},{\"code\":\"302\",\"txt\":\"雷阵雨\",\"txt_en\":\"Thundershower\",\"icon\":\"http://files.heweather.com/cond_icon/302.png\"},{\"code\":\"303\",\"txt\":\"强雷阵雨\",\"txt_en\":\"Heavy Thunderstorm\",\"icon\":\"http://files.heweather.com/cond_icon/303.png\"},{\"code\":\"304\",\"txt\":\"雷阵雨伴有冰雹\",\"txt_en\":\"Hail\",\"icon\":\"http://files.heweather.com/cond_icon/304.png\"},{\"code\":\"305\",\"txt\":\"小雨\",\"txt_en\":\"Light Rain\",\"icon\":\"http://files.heweather.com/cond_icon/305.png\"},{\"code\":\"306\",\"txt\":\"中雨\",\"txt_en\":\"Moderate Rain\",\"icon\":\"http://files.heweather.com/cond_icon/306.png\"},{\"code\":\"307\",\"txt\":\"大雨\",\"txt_en\":\"Heavy Rain\",\"icon\":\"http://files.heweather.com/cond_icon/307.png\"},{\"code\":\"308\",\"txt\":\"极端降雨\",\"txt_en\":\"Extreme Rain\",\"icon\":\"http://files.heweather.com/cond_icon/308.png\"},{\"code\":\"309\",\"txt\":\"毛毛雨/细雨\",\"txt_en\":\"Drizzle Rain\",\"icon\":\"http://files.heweather.com/cond_icon/309.png\"},{\"code\":\"310\",\"txt\":\"暴雨\",\"txt_en\":\"Storm\",\"icon\":\"http://files.heweather.com/cond_icon/310.png\"},{\"code\":\"311\",\"txt\":\"大暴雨\",\"txt_en\":\"Heavy Storm\",\"icon\":\"http://files.heweather.com/cond_icon/311.png\"},{\"code\":\"312\",\"txt\":\"特大暴雨\",\"txt_en\":\"Severe Storm\",\"icon\":\"http://files.heweather.com/cond_icon/312.png\"},{\"code\":\"313\",\"txt\":\"冻雨\",\"txt_en\":\"Freezing Rain\",\"icon\":\"http://files.heweather.com/cond_icon/313.png\"},{\"code\":\"400\",\"txt\":\"小雪\",\"txt_en\":\"Light Snow\",\"icon\":\"http://files.heweather.com/cond_icon/400.png\"},{\"code\":\"401\",\"txt\":\"中雪\",\"txt_en\":\"Moderate Snow\",\"icon\":\"http://files.heweather.com/cond_icon/401.png\"},{\"code\":\"402\",\"txt\":\"大雪\",\"txt_en\":\"Heavy Snow\",\"icon\":\"http://files.heweather.com/cond_icon/402.png\"},{\"code\":\"403\",\"txt\":\"暴雪\",\"txt_en\":\"Snowstorm\",\"icon\":\"http://files.heweather.com/cond_icon/403.png\"},{\"code\":\"404\",\"txt\":\"雨夹雪\",\"txt_en\":\"Sleet\",\"icon\":\"http://files.heweather.com/cond_icon/404.png\"},{\"code\":\"405\",\"txt\":\"雨雪天气\",\"txt_en\":\"Rain And Snow\",\"icon\":\"http://files.heweather.com/cond_icon/405.png\"},{\"code\":\"406\",\"txt\":\"阵雨夹雪\",\"txt_en\":\"Shower Snow\",\"icon\":\"http://files.heweather.com/cond_icon/406.png\"},{\"code\":\"407\",\"txt\":\"阵雪\",\"txt_en\":\"Snow Flurry\",\"icon\":\"http://files.heweather.com/cond_icon/407.png\"},{\"code\":\"500\",\"txt\":\"薄雾\",\"txt_en\":\"Mist\",\"icon\":\"http://files.heweather.com/cond_icon/500.png\"},{\"code\":\"501\",\"txt\":\"雾\",\"txt_en\":\"Foggy\",\"icon\":\"http://files.heweather.com/cond_icon/501.png\"},{\"code\":\"502\",\"txt\":\"霾\",\"txt_en\":\"Haze\",\"icon\":\"http://files.heweather.com/cond_icon/502.png\"},{\"code\":\"503\",\"txt\":\"扬沙\",\"txt_en\":\"Sand\",\"icon\":\"http://files.heweather.com/cond_icon/503.png\"},{\"code\":\"504\",\"txt\":\"浮尘\",\"txt_en\":\"Dust\",\"icon\":\"http://files.heweather.com/cond_icon/504.png\"},{\"code\":\"506\",\"txt\":\"火山灰\",\"txt_en\":\"Volcanic Ash\",\"icon\":\"http://files.heweather.com/cond_icon/506.png\"},{\"code\":\"507\",\"txt\":\"沙尘暴\",\"txt_en\":\"Duststorm\",\"icon\":\"http://files.heweather.com/cond_icon/507.png\"},{\"code\":\"508\",\"txt\":\"强沙尘暴\",\"txt_en\":\"Sandstorm\",\"icon\":\"http://files.heweather.com/cond_icon/508.png\"},{\"code\":\"900\",\"txt\":\"热\",\"txt_en\":\"Hot\",\"icon\":\"http://files.heweather.com/cond_icon/900.png\"},{\"code\":\"901\",\"txt\":\"冷\",\"txt_en\":\"Cold\",\"icon\":\"http://files.heweather.com/cond_icon/901.png\"},{\"code\":\"999\",\"txt\":\"未知\",\"txt_en\":\"Unknown\",\"icon\":\"http://files.heweather.com/cond_icon/999.png\"}]}";

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

    public WeatherBean fetchWeatherBean(String cityName){
        String url = buildUrl(WEATHER_PORT.WEATHER, cityName);
        return downloadWeather(url);
    }

    public List<Condition> fetchConditionList(){
        String url = buildUrl(WEATHER_PORT.CONDITION, "allcond");
        return downloadConditionList(url);
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
            //String result = getUrlString(url);
            String result = conditionInfoExample;
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
