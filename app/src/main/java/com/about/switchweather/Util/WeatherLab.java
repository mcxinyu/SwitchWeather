package com.about.switchweather.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.about.switchweather.DataBase.MyCursorWrapper;
import com.about.switchweather.DataBase.WeatherBaseHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.CityTable;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Model.WeatherInfo;

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

    public void addWeatherBean(WeatherBean weatherBean){
        mDatabase.insert(WeatherInfoTable.NAME, null, getWeatherBeanValues(weatherBean));
    }

    public void deleteWeatherInfo(WeatherInfo weatherInfo){
        mDatabase.delete(WeatherInfoTable.NAME, WeatherInfoTable.Columns.Basic.ID, new String[]{weatherInfo.getBasicCityId()});
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
        return weatherInfoList;
    }

    public WeatherInfo getWeatherInfoWithCityId(String cityId) {
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

    public void updateWeatherInfo(WeatherBean weatherBean){
        String id = weatherBean.getBasic().getId();
        ContentValues values = getWeatherBeanValues(weatherBean);

        mDatabase.update(WeatherInfoTable.NAME, values, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{id});
    }

    private MyCursorWrapper queryAllRows(String table, String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(table, null, whereClause, whereArgs, null, null, null);

        return new MyCursorWrapper(cursor);
    }

    private static ContentValues getWeatherBeanValues(WeatherBean weatherBean){
        ContentValues values = new ContentValues();
        values.put(WeatherInfoTable.Columns.Basic.ID, weatherBean.getBasic().getId().toString());
        values.put(WeatherInfoTable.Columns.Basic.CITY, weatherBean.getBasic().getCity());
        values.put(WeatherInfoTable.Columns.Basic.Update.LOC, weatherBean.getBasic().getUpdate().getLoc());
        values.put(WeatherInfoTable.Columns.Now.TMP, weatherBean.getNow().getTmp());
        values.put(WeatherInfoTable.Columns.Now.Cond.CODE, weatherBean.getNow().getCond().getCode());
        values.put(WeatherInfoTable.Columns.Now.Cond.TXT, weatherBean.getNow().getCond().getTxt());
        values.put(WeatherInfoTable.Columns.DailyForecast.DATE, weatherBean.getDaily_forecast().get(0).getDate());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MAX, weatherBean.getDaily_forecast().get(0).getTmp().getMax());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MIN, weatherBean.getDaily_forecast().get(0).getTmp().getMin());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D, weatherBean.getDaily_forecast().get(0).getCond().getCode_d());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N, weatherBean.getDaily_forecast().get(0).getCond().getCode_n());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D, weatherBean.getDaily_forecast().get(0).getCond().getTxt_d());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N, weatherBean.getDaily_forecast().get(0).getCond().getTxt_n());
        values.put(WeatherInfoTable.Columns.STATUS, weatherBean.getStatus());

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
        MyCursorWrapper cursor = queryAllRows(ConditionTable.NAME, ConditionTable.Columns.CODE, new String[]{code});
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
            values.put(ConditionTable.Columns.TEXT_EN, conditionBeanList.get(i).getTxt_en());
            values.put(ConditionTable.Columns.ICON, conditionBeanList.get(i).getIcon());
            list.add(values);
        }
        return list;
    }

    public void addCityList(List<City> cityList){
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

    public City getCityWithCityId(String cityId){
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, CityTable.Columns.ID, new String[]{cityId});
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
        MyCursorWrapper cursor = queryAllRows(CityTable.NAME, CityTable.Columns.CITY, new String[]{cityName});
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
            values.put(CityTable.Columns.CITY, cityList.get(i).getCity());
            values.put(CityTable.Columns.CNTY, cityList.get(i).getCnty());
            values.put(CityTable.Columns.LAT, cityList.get(i).getLat());
            values.put(CityTable.Columns.LON, cityList.get(i).getLon());
            values.put(CityTable.Columns.PROV, cityList.get(i).getProv());
            list.add(values);
        }
        return list;
    }
}
