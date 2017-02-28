package com.about.switchweather.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.about.switchweather.DataBase.MyCursorWrapper;
import com.about.switchweather.DataBase.WeatherBaseHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.CityTable;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.DailyForecastTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.DailyForecast;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Model.WeatherModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/22.
 */
public class WeatherLab {
    private static WeatherLab sWeatherLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static WeatherLab get(Context context){
        if (sWeatherLab == null){
            sWeatherLab = new WeatherLab(context);
        }
        return sWeatherLab;
    }

    private WeatherLab(Context context) {
        mContext = context;
        mDatabase = new WeatherBaseHelper(context).getWritableDatabase();
    }

    public void addWeatherBean(WeatherModel weatherModel){
        mDatabase.insert(WeatherInfoTable.NAME, null, getWeatherBeanValues(weatherModel));
    }

    public void deleteWeatherInfo(String cityId){
        mDatabase.delete(WeatherInfoTable.NAME, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{cityId});
        deleteDailyForecast(cityId);
    }

    public List<WeatherInfo> getWeatherInfoList() {
        List<WeatherInfo> weatherInfoList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(WeatherInfoTable.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                weatherInfoList.add(cursor.getWeatherInfo());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return WeatherUtil.adjustLocationCity2ListFirst(weatherInfoList);
    }

    public WeatherInfo getWeatherInfoWithCityId(String cityId) {
        if (cityId == null){
            return null;
        }
        MyCursorWrapper cursor = queryAllRows(WeatherInfoTable.NAME, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{cityId});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWeatherInfo();
        } finally {
            cursor.close();
        }
    }

    public WeatherInfo getWeatherInfoWithCityName(String cityName) {
        if (cityName == null){
            return null;
        }
        MyCursorWrapper cursor = queryAllRows(WeatherInfoTable.NAME, WeatherInfoTable.Columns.Basic.CITY + "=?", new String[]{cityName});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWeatherInfo();
        } finally {
            cursor.close();
        }
    }

    public void updateWeatherInfo(WeatherModel weatherModel){
        String id = weatherModel.getHeWeather5().get(0).getBasic().getId();
        ContentValues values = getWeatherBeanValues(weatherModel);

        mDatabase.update(WeatherInfoTable.NAME, values, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{id});
    }

    private MyCursorWrapper queryAllRows(String table, String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(table, null, whereClause, whereArgs, null, null, null);

        return new MyCursorWrapper(cursor);
    }

    private static ContentValues getWeatherBeanValues(WeatherModel weatherModel){
        ContentValues values = new ContentValues();
        values.put(WeatherInfoTable.Columns.Basic.ID, weatherModel.getHeWeather5().get(0).getBasic().getId());
        values.put(WeatherInfoTable.Columns.Basic.CITY, weatherModel.getHeWeather5().get(0).getBasic().getCity());
        values.put(WeatherInfoTable.Columns.Basic.Update.LOC, weatherModel.getHeWeather5().get(0).getBasic().getUpdate().getLoc());
        values.put(WeatherInfoTable.Columns.Now.TMP, weatherModel.getHeWeather5().get(0).getNow().getTmp());
        values.put(WeatherInfoTable.Columns.Now.Cond.CODE, weatherModel.getHeWeather5().get(0).getNow().getCond().getCode());
        values.put(WeatherInfoTable.Columns.Now.Cond.TXT, weatherModel.getHeWeather5().get(0).getNow().getCond().getTxt());
        values.put(WeatherInfoTable.Columns.DailyForecast.DATE, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getDate());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MAX, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getTmp().getMax());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MIN, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getTmp().getMin());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getCond().getCodeD());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getCond().getCodeN());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getCond().getTxtD());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N, weatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getCond().getTxtN());
        values.put(WeatherInfoTable.Columns.STATUS, weatherModel.getHeWeather5().get(0).getStatus());

        return values;
    }

    public void addConditionBean(List<Condition> conditionBeanList){
        if (getConditionBeanList().size() != 0) {
            removeConditionBean();
        }

        List<ContentValues> valuesList = getConditionValues(conditionBeanList);
        for (int i = 0; i < valuesList.size(); i++) {
            mDatabase.insert(ConditionTable.NAME, null, valuesList.get(i));
        }
    }

    public List<Condition> getConditionBeanList(){
        List<Condition> conditionBeanList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(ConditionTable.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                conditionBeanList.add(cursor.getCondition());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return conditionBeanList;
    }

    public Condition getConditionBean(String code){
        MyCursorWrapper cursor = queryAllRows(ConditionTable.NAME, ConditionTable.Columns.CODE + "=?", new String[]{code});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCondition();
        } finally {
            cursor.close();
        }
    }

    public void removeConditionBean(){
        mDatabase.delete(ConditionTable.NAME, null, null);
    }

    private static List<ContentValues> getConditionValues(List<Condition> conditionBeanList){
        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < conditionBeanList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(ConditionTable.Columns.CODE, conditionBeanList.get(i).getCode());
            values.put(ConditionTable.Columns.TEXT, conditionBeanList.get(i).getTxt());
            values.put(ConditionTable.Columns.TEXT_EN, conditionBeanList.get(i).getTxtEn());
            values.put(ConditionTable.Columns.ICON, conditionBeanList.get(i).getIcon());
            list.add(values);
        }
        return list;
    }

    public void addCityList(List<City> cityList){
        if (getCityList().size() != 0) {
            removeCityList();
        }

        List<ContentValues> valuesList = getCityListValues(cityList);
        for (int i = 0; i < cityList.size(); i++) {
            mDatabase.insert(CityTable.NAME, null, valuesList.get(i));
        }
    }

    public List<City> getCityList(){
        List<City> cityList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cityList.add(cursor.getCity());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cityList;
    }

    public List<City> getCityListLikeName(String cityName){
        if (cityName == null){
            return null;
        }
        List<City> cityList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, CityTable.Columns.CITY_ZH + " like '%?%'", new String[]{cityName});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                cityList.add(cursor.getCity());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return cityList;
    }

    //老出问题，可能是数据库里面的格式不对？？？查一查
    public City getCityWithCityId(String cityId){
        if (cityId == null){
            return null;
        }
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, CityTable.Columns.ID + "=?", new String[]{cityId});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCity();
        } finally {
            cursor.close();
        }
    }

    public City getCityWithCityName(String cityName){
        if (cityName == null){
            return null;
        }
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, CityTable.Columns.CITY_ZH + "=?", new String[]{cityName});
        try {
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCity();
        } finally {
            cursor.close();
        }
    }

    public void removeCityList(){
        mDatabase.delete(CityTable.NAME, null, null);
    }

    private static List<ContentValues> getCityListValues(List<City> cityList){
        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(CityTable.Columns.ID, cityList.get(i).getId());
            values.put(CityTable.Columns.CITY_EN, cityList.get(i).getCityEn());
            values.put(CityTable.Columns.CITY_ZH, cityList.get(i).getCityZh());
            values.put(CityTable.Columns.COUNTRY_CODE, cityList.get(i).getCountryCode());
            values.put(CityTable.Columns.COUNTRY_EN, cityList.get(i).getCountryEn());
            values.put(CityTable.Columns.COUNTRY_ZH, cityList.get(i).getCountryZh());
            values.put(CityTable.Columns.PROVINCE_EN, cityList.get(i).getProvinceEn());
            values.put(CityTable.Columns.PROVINCE_ZH, cityList.get(i).getProvinceZh());
            values.put(CityTable.Columns.LEADER_EN, cityList.get(i).getLeaderEn());
            values.put(CityTable.Columns.LEADER_ZH, cityList.get(i).getLeaderZh());
            values.put(CityTable.Columns.LAT, cityList.get(i).getLat());
            values.put(CityTable.Columns.LON, cityList.get(i).getLon());
            list.add(values);
        }
        return list;
    }

    private static List<ContentValues> getDailyForecastListValues(WeatherModel weatherModel){
        List<WeatherModel.HeWeather5Bean.DailyForecastBean> dailyForecastBeanList = weatherModel.getHeWeather5().get(0).getDailyForecast();

        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < dailyForecastBeanList.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(DailyForecastTable.Columns.DATE, dailyForecastBeanList.get(i).getDate());
            values.put(DailyForecastTable.Columns.CITY_ID, weatherModel.getHeWeather5().get(0).getBasic().getId());
            values.put(DailyForecastTable.Columns.CITY, weatherModel.getHeWeather5().get(0).getBasic().getCity());
            values.put(DailyForecastTable.Columns.ASTRO_SR, dailyForecastBeanList.get(i).getAstro().getSr());
            values.put(DailyForecastTable.Columns.ASTRO_SS, dailyForecastBeanList.get(i).getAstro().getSs());
            values.put(DailyForecastTable.Columns.COND_CODE_D, dailyForecastBeanList.get(i).getCond().getCodeN());
            values.put(DailyForecastTable.Columns.COND_CODE_N, dailyForecastBeanList.get(i).getCond().getCodeN());
            values.put(DailyForecastTable.Columns.COND_TXT_D, dailyForecastBeanList.get(i).getCond().getTxtD());
            values.put(DailyForecastTable.Columns.COND_TXT_N, dailyForecastBeanList.get(i).getCond().getTxtN());
            values.put(DailyForecastTable.Columns.HUM, dailyForecastBeanList.get(i).getHum());
            values.put(DailyForecastTable.Columns.PCPN, dailyForecastBeanList.get(i).getPcpn());
            values.put(DailyForecastTable.Columns.POP, dailyForecastBeanList.get(i).getPop());
            values.put(DailyForecastTable.Columns.PRES, dailyForecastBeanList.get(i).getPres());
            values.put(DailyForecastTable.Columns.TMP_MAX, dailyForecastBeanList.get(i).getTmp().getMax());
            values.put(DailyForecastTable.Columns.TMP_MIN, dailyForecastBeanList.get(i).getTmp().getMin());
            values.put(DailyForecastTable.Columns.VIS, dailyForecastBeanList.get(i).getVis());
            values.put(DailyForecastTable.Columns.WIND_DEG, dailyForecastBeanList.get(i).getWind().getDeg());
            values.put(DailyForecastTable.Columns.WIND_DIR, dailyForecastBeanList.get(i).getWind().getDir());
            values.put(DailyForecastTable.Columns.WIND_SC, dailyForecastBeanList.get(i).getWind().getSc());
            values.put(DailyForecastTable.Columns.WIND_SPD, dailyForecastBeanList.get(i).getWind().getSpd());

            list.add(values);
        }
        return list;
    }

    public void addDailyForecastList(WeatherModel weatherModel){
        deleteDailyForecastOldDate(weatherModel.getHeWeather5().get(0).getBasic().getId());

        List<ContentValues> valuesList = getDailyForecastListValues(weatherModel);
        for (int i = 0; i < valuesList.size(); i++) {
            mDatabase.insert(DailyForecastTable.NAME, null, valuesList.get(i));
        }
    }

    public void updateDailyForecastList(WeatherModel weatherModel){
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();

        List<ContentValues> valuesList = getDailyForecastListValues(weatherModel);
        for (int i = 0; i < valuesList.size(); i++) {
            String date = valuesList.get(i).getAsString(DailyForecastTable.Columns.DATE);
            MyCursorWrapper cursor = queryAllRows(DailyForecastTable.NAME, DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?", new String[]{cityId, date});

            if (cursor.getCount() != 0){
                mDatabase.update(DailyForecastTable.NAME, valuesList.get(i), DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?", new String[]{cityId, date});
            } else {
                mDatabase.insert(DailyForecastTable.NAME, null, valuesList.get(i));
            }

        }
    }

    public List<DailyForecast> getDailyForecastList(String cityId){
        if (cityId == null){
            return null;
        }
        List<DailyForecast> dailyForecastList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(DailyForecastTable.NAME, DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + ">DATE('now','-2 day','localtime')", new String[]{cityId});
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                dailyForecastList.add(cursor.getDailyForecast());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return dailyForecastList;
    }

    public void deleteDailyForecastOldDate(String cityId){
        if (cityId == null){
            return;
        }
        mDatabase.delete(DailyForecastTable.NAME, DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "<DATE('now','-20 day','localtime')", new String[]{cityId});
    }

    private void deleteDailyForecast(String cityId) {
        if (cityId == null){
            return;
        }
        mDatabase.delete(DailyForecastTable.NAME, DailyForecastTable.Columns.CITY_ID + "=?", new String[]{cityId});
    }
}
