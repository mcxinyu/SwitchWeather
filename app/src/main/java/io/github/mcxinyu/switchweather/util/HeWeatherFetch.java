package io.github.mcxinyu.switchweather.util;

import android.net.Uri;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import io.github.mcxinyu.switchweather.model.City;
import io.github.mcxinyu.switchweather.model.Condition;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.V5City;

/**
 * Created by 跃峰 on 2016/8/20.
 */
@Deprecated
public class HeWeatherFetch {
    private static final String TAG = "HeWeatherFetch";
    private static final String API_KEY = "33fdb5ca3d254333a617911e122f65ac";
    // private static final Uri ENDPOINT = Uri.parse("https://api.heweather.com/x3/");
    private static final Uri ENDPOINT = Uri.parse("https://free-api.heweather.com/v5/");

    private enum WEATHER_PORT {CONDITION, WEATHER, CITY_LIST, CITY;}

    /**
     * 和风天气API
     *
     * @param port
     * @param city 城市名称，city可通过城市中英文名称、ID、IP和经纬度进行查询，经纬度查询格式为：经度,纬度
     * @return
     */
    private String buildUrl(WEATHER_PORT port, String city) {
        // Uri.Builder builder = null;
        // condition 天气代码接口（allcond、代码）
        // weather 天气接口
        // citylist （国内城市：allchina（先支持国内）、 热门城市：hotworld、 全部城市：allworld（以后支持，需要升级数据库？））
        // attractions 景点接口 （要钱，弃用）

        if (port.equals(WEATHER_PORT.CONDITION)) {
            return "https://cdn.heweather.com/condition-code.txt";
        } else if (port.equals(WEATHER_PORT.WEATHER)) {
            return ENDPOINT.buildUpon()
                    .appendPath("weather")
                    .appendQueryParameter("city", city)
                    .appendQueryParameter("key", API_KEY)
                    .build()
                    .toString();
        } else if (port.equals(WEATHER_PORT.CITY)) {
            return ENDPOINT.buildUpon()
                    .appendPath("search")
                    .appendQueryParameter("city", city)
                    .appendQueryParameter("key", API_KEY)
                    .build()
                    .toString();
        }
        return null;
    }

    private byte[] getUrlBytes(String urlString) throws IOException {
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            InputStream inputStream = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
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

    private String getUrlString(String urlString) throws IOException {
        return new String(getUrlBytes(urlString));
    }

    public HeWeatherModel fetchWeatherBean(String cityId) {
        String url = buildUrl(WEATHER_PORT.WEATHER, cityId);
        return downloadWeather(url);
    }

    public List<Condition> fetchConditionList() {
        String url = buildUrl(WEATHER_PORT.CONDITION, "allcond");
        return downloadConditionList(url);
    }

    /**
     * 搜索城市
     *
     * @param city 可通过城市中英文名称、ID、IP和经纬度进行查询
     * @return
     */
    public V5City searchCity(String city) {
        String url = buildUrl(WEATHER_PORT.CITY, city);
        return downloadV5CityList(url);
    }

    /**
     * 废弃
     *
     * @return
     */
    public List<City> fetchCityList() {
        String url = buildUrl(WEATHER_PORT.CITY_LIST, "allchina");
        return downloadCityList(url);
    }

    private HeWeatherModel downloadWeather(String url) {
        HeWeatherModel weatherModel = null;
        try {
            String result = getUrlString(url);
            Log.i(TAG, "downloadWeather: Weather info : " + result);

            if (!result.contains("\"status\":\"ok\"")) {
                return null;
            }
            Gson gson = new Gson();
            weatherModel = gson.fromJson(result, HeWeatherModel.class);
            LogUtils.i(TAG, "downloadWeather: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weatherModel;
    }

    private List<Condition> downloadConditionList(String url) {
        List<Condition> conditionBeanList = null;
        try {
            String result = getUrlString(url);
            //Log.i(TAG, "downloadConditionList: " + result);

            result = dealV5Condition(result).toString();
            String jsonBody = getJsonBody(result);
            if ("not found".equals(jsonBody)) {
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            conditionBeanList = gson.fromJson(result, new TypeToken<List<Condition>>() {
            }.getType());
            LogUtils.i(TAG, "downloadConditionList: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conditionBeanList;
    }

    /**
     * v5 版本的处理方法
     *
     * @param text
     * @return
     */
    private JSONObject dealV5Condition(String text) {
        JSONObject root = new JSONObject();
        if (text == null || text.equals("")) {
            try {
                root.put("status", "failed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return root;
        }

        try {
            String[] lines = text.split("\n");
            JSONArray array = new JSONArray();

            for (String line : lines) {
                if (line.contains("http")) {
                    String[] entries = line.split("\t");
                    JSONObject lineObject = new JSONObject();
                    if (entries.length == 4) {
                        lineObject.put("code", entries[0])
                                .put("txt", entries[1])
                                .put("txt_en", entries[2])
                                .put("icon", entries[3]);
                    }
                    array.put(lineObject);
                }
            }
            root.put("cond_info", array);
            root.put("status", "ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }

    private V5City downloadV5CityList(String url) {
        V5City city = null;
        try {
            String result = getUrlString(url);
            if (result.contains("unknown city")) {
                return null;
            }
            Gson gson = new Gson();
            city = gson.fromJson(result, new TypeToken<V5City>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return city;
    }

    /**
     * 废弃
     *
     * @param url
     * @return
     */
    private List<City> downloadCityList(String url) {
        List<City> cityListList = null;
        try {
            String result = getUrlString(url);

            result = dealV5CityList(result);
            String jsonBody = getJsonBody(result);
            if ("not found".equals(jsonBody)) {
                return null;
            }
            result = "[" + jsonBody + "]";
            Gson gson = new Gson();
            cityListList = gson.fromJson(result, new TypeToken<List<City>>() {
            }.getType());
            LogUtils.i(TAG, "downloadCityList: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cityListList;
    }

    /**
     * 废弃
     *
     * @param result
     * @return
     */
    private String dealV5CityList(String result) {
        if (result == null || result.equals("")) {
            return null;
        }

        result = result + "\"status\":\"ok\"";

        return result;
    }

    private String getJsonBody(String result) {
        if (result != null && result.contains("\"status\":\"ok\"")) {
            result = result.substring(result.indexOf("[") + 1, result.lastIndexOf("]"));
        } else {
            LogUtils.e(TAG, "getJsonBody: not found he weather info : with " + result);
            return "not found";
        }
        return result;
    }
}
