package com.about.switchweather.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.about.switchweather.DataBase.WeatherDbSchema.CityTable;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.DailyForecastTable;
import com.about.switchweather.DataBase.WeatherDbSchema.HourlyForecastTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.DailyForecast;
import com.about.switchweather.Model.HourlyForecast;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Model.WeatherModel;
import com.about.switchweather.Model.WeatherModel.HeWeather5Bean;
import com.about.switchweather.Model.WeatherModel.HeWeather5Bean.DailyForecastBean;
import com.about.switchweather.Model.WeatherModel.HeWeather5Bean.HourlyForecastBean;
import com.about.switchweather.Util.WeatherUtil;

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
        mDatabase = new WeatherBaseHelper(mContext).getWritableDatabase();
    }

    // -------- 增加 --------
    public void addCityList(List<City> cityList){
        if (getCityList().size() != 0) {
            removeCityList();
        }

        List<ContentValues> valuesList = getCityListValues(cityList);
        for (int i = 0; i < cityList.size(); i++) {
            mDatabase.insert(CityTable.NAME, null, valuesList.get(i));
        }
    }

    public void addConditionList(List<Condition> conditionBeanList){
        if (getConditionList().size() != 0) {
            removeConditionList();
        }

        List<ContentValues> valuesList = getConditionListValues(conditionBeanList);
        for (int i = 0; i < valuesList.size(); i++) {
            mDatabase.insert(ConditionTable.NAME, null, valuesList.get(i));
        }
    }

    public void addDailyForecastList(WeatherModel weatherModel){
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();

        deleteDailyForecastOldDate(cityId);

        MyCursorWrapper wrapper = queryAllRows(DailyForecastTable.NAME,
                DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + ">DATE('now','localtime')",
                new String[]{cityId});

        if (wrapper.getCount() < 1){
            List<ContentValues> valuesList = getDailyForecastListValues(weatherModel);
            for (int i = 0; i < valuesList.size(); i++) {
                mDatabase.insert(DailyForecastTable.NAME, null, valuesList.get(i));
            }
        } else {
            List<ContentValues> valuesList = getDailyForecastListValues(weatherModel);
            // weatherModel包含未来7天数据
            for (int i = 0; i < valuesList.size(); i++) {
                String date = valuesList.get(i).getAsString(DailyForecastTable.Columns.DATE);
                MyCursorWrapper cursor = queryAllRows(DailyForecastTable.NAME,
                        DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?",
                        new String[]{cityId, date});

                if (cursor.getCount() != 0){
                    mDatabase.update(DailyForecastTable.NAME,
                            valuesList.get(i),
                            DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?",
                            new String[]{cityId, date});
                } else {
                    mDatabase.insert(DailyForecastTable.NAME, null, valuesList.get(i));
                }

            }
        }
    }

    public void addHourlyForecastList(WeatherModel weatherModel){
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();

        deleteHourlyForecastOldDate(cityId);

        MyCursorWrapper wrapper = queryAllRows(HourlyForecastTable.NAME,
                HourlyForecastTable.Columns.CITY_ID + "=? and " + HourlyForecastTable.Columns.DATE + ">DATE('now','localtime')",
                new String[]{cityId});

        if (wrapper.getCount() < 1){
            List<ContentValues> valuesList = getHourlyForecastListValues(weatherModel);
            for (ContentValues values : valuesList) {
                mDatabase.insert(HourlyForecastTable.NAME, null, values);
            }
        } else {
            List<ContentValues> valuesList = getHourlyForecastListValues(weatherModel);
            for (ContentValues values : valuesList) {
                String date = values.getAsString(HourlyForecastTable.Columns.DATE);
                mDatabase.update(HourlyForecastTable.NAME, values,
                        HourlyForecastTable.Columns.CITY_ID + "=? and " + HourlyForecastTable.Columns.DATE + "=?",
                        new String[]{cityId, date});
            }
        }

    }

    public void addWeatherInfo(WeatherModel weatherModel){
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();

        MyCursorWrapper wrapper = queryAllRows(WeatherInfoTable.NAME,
                WeatherInfoTable.Columns.Basic.ID + "=?",
                new String[]{cityId});

        if (wrapper.getCount() < 1){
            mDatabase.insert(WeatherInfoTable.NAME, null, getWeatherInfoValues(weatherModel));
        } else {
            mDatabase.update(WeatherInfoTable.NAME,
                    getWeatherInfoValues(weatherModel),
                    WeatherInfoTable.Columns.Basic.ID + "=?",
                    new String[]{cityId});
        }

        addDailyForecastList(weatherModel);
        addHourlyForecastList(weatherModel);
    }

    // -------- 删除 --------
    public void removeCityList(){
        mDatabase.delete(CityTable.NAME, null, null);
    }

    public void removeConditionList(){
        mDatabase.delete(ConditionTable.NAME, null, null);
    }

    public void deleteDailyForecast(String cityId) {
        if (cityId == null){
            return;
        }
        mDatabase.delete(DailyForecastTable.NAME, DailyForecastTable.Columns.CITY_ID + "=?", new String[]{cityId});
    }

    public void deleteDailyForecastOldDate(String cityId){
        if (cityId == null){
            return;
        }
        mDatabase.delete(DailyForecastTable.NAME,
                DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "<DATE('now','-1 day','localtime')",
                new String[]{cityId});
    }

    public void deleteHourlyForecast(String cityId){
        if (cityId == null){
            return;
        }
        mDatabase.delete(HourlyForecastTable.NAME, HourlyForecastTable.Columns.CITY_ID + "=?", new String[]{cityId});
    }

    public void deleteHourlyForecastOldDate(String cityId){
        if (cityId == null){
            return;
        }
        mDatabase.delete(HourlyForecastTable.NAME,
                HourlyForecastTable.Columns.CITY_ID + "=? and " + HourlyForecastTable.Columns.DATE + "<DATE('now', 'localtime')",
                new String[]{cityId});
    }

    public void deleteWeatherInfo(String cityId){
        mDatabase.delete(WeatherInfoTable.NAME, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{cityId});

        deleteDailyForecast(cityId);
        deleteHourlyForecast(cityId);
    }

    // -------- 修改 --------
    // public void updateDailyForecastList(WeatherModel weatherModel){
    //     String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();
    //
    //     List<ContentValues> valuesList = getDailyForecastListValues(weatherModel);
    //     for (int i = 0; i < valuesList.size(); i++) {
    //         String date = valuesList.get(i).getAsString(DailyForecastTable.Columns.DATE);
    //         MyCursorWrapper cursor = queryAllRows(DailyForecastTable.NAME,
    //              DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?",
    //              new String[]{cityId, date});
    //
    //         if (cursor.getCount() != 0){
    //             mDatabase.update(DailyForecastTable.NAME,
    //              valuesList.get(i),
    //              DailyForecastTable.Columns.CITY_ID + "=? and " + DailyForecastTable.Columns.DATE + "=?",
    //              new String[]{cityId, date});
    //         } else {
    //             mDatabase.insert(DailyForecastTable.NAME, null, valuesList.get(i));
    //         }
    //
    //     }
    // }

    // public void updateHourlyForecastList(WeatherModel weatherModel){
    //     String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();
    //     deleteHourlyForecastOldDate(cityId);
    //
    //     List<ContentValues> valuesList = getHourlyForecastListValues(weatherModel);
    //     for (ContentValues values : valuesList) {
    //         String date = values.getAsString(HourlyForecastTable.Columns.DATE);
    //         mDatabase.update(HourlyForecastTable.NAME, values,
    //                 HourlyForecastTable.Columns.CITY_ID + "=? and " + HourlyForecastTable.Columns.DATE + "=?",
    //                 new String[]{cityId, date});
    //     }
    // }

    // public void updateWeatherInfo(WeatherModel weatherModel){
    //     String id = weatherModel.getHeWeather5().get(0).getBasic().getId();
    //     ContentValues values = getWeatherInfoValues(weatherModel);
    //     mDatabase.update(WeatherInfoTable.NAME, values, WeatherInfoTable.Columns.Basic.ID + "=?", new String[]{id});
    // }

    // -------- 查询 --------
    private MyCursorWrapper queryAllRows(String table, String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(table, null, whereClause, whereArgs, null, null, null);

        return new MyCursorWrapper(cursor);
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

    public City getCity(String cityId){
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

    public List<Condition> getConditionList(){
        List<Condition> conditionList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(ConditionTable.NAME, null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                conditionList.add(cursor.getCondition());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return conditionList;
    }

    public Condition getCondition(String code){
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

    public List<DailyForecast> getDailyForecastList(String cityId){
        if (cityId == null){
            return null;
        }

        List<DailyForecast> dailyForecastList = new ArrayList<>();
        MyCursorWrapper cursor = queryAllRows(DailyForecastTable.NAME,
                DailyForecastTable.Columns.CITY_ID + "=? and " +
                        DailyForecastTable.Columns.DATE + ">DATE('now','-2 day','localtime')",
                new String[]{cityId});
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

    public List<HourlyForecast> getHourlyForecastList(String cityId){
        if (cityId == null){
            return null;
        }

        List<HourlyForecast> list = new ArrayList<>();
        // MyCursorWrapper wrapper = queryAllRows(HourlyForecastTable.NAME,
        //         HourlyForecastTable.Columns.CITY_ID + "=? and " +
        //                 HourlyForecastTable.Columns.DATE + ">DATETIME('now', 'localtime')",
        //         new String[]{cityId});
        MyCursorWrapper wrapper = queryAllRows(HourlyForecastTable.NAME,
                HourlyForecastTable.Columns.CITY_ID + "=?", new String[]{cityId});
        try {
            wrapper.moveToFirst();
            while (!wrapper.isAfterLast()){
                list.add(wrapper.getHourlyForecast());
                wrapper.moveToNext();
            }
        } finally {
            wrapper.close();
        }

        return list;
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

    public WeatherInfo getWeatherInfo(String cityId) {
        if (cityId == null){
            return null;
        }

        MyCursorWrapper cursor = queryAllRows(WeatherInfoTable.NAME,
                WeatherInfoTable.Columns.Basic.ID + "=?",
                new String[]{cityId});
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

        MyCursorWrapper cursor = queryAllRows(WeatherInfoTable.NAME,
                WeatherInfoTable.Columns.Basic.CITY + "=?",
                new String[]{cityName});
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

    // -------- 包装 --------
    private static List<ContentValues> getCityListValues(List<City> cityList){
        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            City city = cityList.get(i);

            ContentValues values = new ContentValues();
            values.put(CityTable.Columns.ID, city.getId());
            values.put(CityTable.Columns.CITY_EN, city.getCityEn());
            values.put(CityTable.Columns.CITY_ZH, city.getCityZh());
            values.put(CityTable.Columns.COUNTRY_CODE, city.getCountryCode());
            values.put(CityTable.Columns.COUNTRY_EN, city.getCountryEn());
            values.put(CityTable.Columns.COUNTRY_ZH, city.getCountryZh());
            values.put(CityTable.Columns.PROVINCE_EN, city.getProvinceEn());
            values.put(CityTable.Columns.PROVINCE_ZH, city.getProvinceZh());
            values.put(CityTable.Columns.LEADER_EN, city.getLeaderEn());
            values.put(CityTable.Columns.LEADER_ZH, city.getLeaderZh());
            values.put(CityTable.Columns.LAT, city.getLat());
            values.put(CityTable.Columns.LON, city.getLon());
            list.add(values);
        }
        return list;
    }

    private static List<ContentValues> getConditionListValues(List<Condition> conditionList){
        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < conditionList.size(); i++) {
            Condition condition = conditionList.get(i);

            ContentValues values = new ContentValues();
            values.put(ConditionTable.Columns.CODE, condition.getCode());
            values.put(ConditionTable.Columns.TEXT, condition.getTxt());
            values.put(ConditionTable.Columns.TEXT_EN, condition.getTxtEn());
            values.put(ConditionTable.Columns.ICON, condition.getIcon());
            list.add(values);
        }
        return list;
    }

    private static List<ContentValues> getDailyForecastListValues(WeatherModel weatherModel){
        List<DailyForecastBean> dailyForecastBeanList = weatherModel.getHeWeather5().get(0).getDailyForecast();
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();
        String city = weatherModel.getHeWeather5().get(0).getBasic().getCity();

        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < dailyForecastBeanList.size(); i++) {
            DailyForecastBean dailyForecastBean = dailyForecastBeanList.get(i);

            ContentValues values = new ContentValues();
            values.put(DailyForecastTable.Columns.DATE, dailyForecastBean.getDate());
            values.put(DailyForecastTable.Columns.CITY_ID, cityId);
            values.put(DailyForecastTable.Columns.CITY, city);
            values.put(DailyForecastTable.Columns.Astro.SR, dailyForecastBean.getAstro().getSr());
            values.put(DailyForecastTable.Columns.Astro.SS, dailyForecastBean.getAstro().getSs());
            values.put(DailyForecastTable.Columns.Astro.MR, dailyForecastBean.getAstro().getMr());
            values.put(DailyForecastTable.Columns.Astro.MS, dailyForecastBean.getAstro().getMs());
            values.put(DailyForecastTable.Columns.Cond.CODE_D, dailyForecastBean.getCond().getCodeD());
            values.put(DailyForecastTable.Columns.Cond.CODE_N, dailyForecastBean.getCond().getCodeN());
            values.put(DailyForecastTable.Columns.Cond.TXT_D, dailyForecastBean.getCond().getTxtD());
            values.put(DailyForecastTable.Columns.Cond.TXT_N, dailyForecastBean.getCond().getTxtN());
            values.put(DailyForecastTable.Columns.HUM, dailyForecastBean.getHum());
            values.put(DailyForecastTable.Columns.PCPN, dailyForecastBean.getPcpn());
            values.put(DailyForecastTable.Columns.POP, dailyForecastBean.getPop());
            values.put(DailyForecastTable.Columns.PRES, dailyForecastBean.getPres());
            values.put(DailyForecastTable.Columns.Tmp.MAX, dailyForecastBean.getTmp().getMax());
            values.put(DailyForecastTable.Columns.Tmp.MIN, dailyForecastBean.getTmp().getMin());
            values.put(DailyForecastTable.Columns.UV, dailyForecastBean.getUv());
            values.put(DailyForecastTable.Columns.VIS, dailyForecastBean.getVis());
            values.put(DailyForecastTable.Columns.Wind.DEG, dailyForecastBean.getWind().getDeg());
            values.put(DailyForecastTable.Columns.Wind.DIR, dailyForecastBean.getWind().getDir());
            values.put(DailyForecastTable.Columns.Wind.SC, dailyForecastBean.getWind().getSc());
            values.put(DailyForecastTable.Columns.Wind.SPD, dailyForecastBean.getWind().getSpd());

            list.add(values);
        }
        return list;
    }

    private static List<ContentValues> getHourlyForecastListValues(WeatherModel weatherModel){
        List<HourlyForecastBean> hourlyForecastBeanList = weatherModel.getHeWeather5().get(0).getHourlyForecast();
        String cityId = weatherModel.getHeWeather5().get(0).getBasic().getId();
        String city = weatherModel.getHeWeather5().get(0).getBasic().getCity();

        List<ContentValues> list = new ArrayList<>();
        for (int i = 0; i < hourlyForecastBeanList.size(); i++) {
            HourlyForecastBean hourlyForecastBean = hourlyForecastBeanList.get(i);

            ContentValues values = new ContentValues();
            values.put(HourlyForecastTable.Columns.DATE, hourlyForecastBean.getDate());
            values.put(HourlyForecastTable.Columns.CITY_ID, cityId);
            values.put(HourlyForecastTable.Columns.CITY, city);
            values.put(HourlyForecastTable.Columns.Cond.CODE, hourlyForecastBean.getCond().getCode());
            values.put(HourlyForecastTable.Columns.Cond.TXT, hourlyForecastBean.getCond().getTxt());
            values.put(HourlyForecastTable.Columns.HUM, hourlyForecastBean.getHum());
            values.put(HourlyForecastTable.Columns.POP, hourlyForecastBean.getPop());
            values.put(HourlyForecastTable.Columns.PRES, hourlyForecastBean.getPres());
            values.put(HourlyForecastTable.Columns.TMP, hourlyForecastBean.getTmp());
            values.put(HourlyForecastTable.Columns.Wind.DEG, hourlyForecastBean.getWind().getDeg());
            values.put(HourlyForecastTable.Columns.Wind.DIR, hourlyForecastBean.getWind().getDir());
            values.put(HourlyForecastTable.Columns.Wind.SC, hourlyForecastBean.getWind().getSc());
            values.put(HourlyForecastTable.Columns.Wind.SPD, hourlyForecastBean.getWind().getSpd());

            list.add(values);
        }
        return list;
    }

    private static ContentValues getWeatherInfoValues(WeatherModel weatherModel){
        HeWeather5Bean weather5Bean = weatherModel.getHeWeather5().get(0);

        ContentValues values = new ContentValues();
        values.put(WeatherInfoTable.Columns.status, weather5Bean.getStatus());
        values.put(WeatherInfoTable.Columns.Basic.CITY, weather5Bean.getBasic().getCity());
        values.put(WeatherInfoTable.Columns.Basic.ID, weather5Bean.getBasic().getId());
        values.put(WeatherInfoTable.Columns.Basic.Update.LOC, weather5Bean.getBasic().getUpdate().getLoc());
        values.put(WeatherInfoTable.Columns.Basic.Update.UTC, weather5Bean.getBasic().getUpdate().getUtc());
        values.put(WeatherInfoTable.Columns.Now.Cond.CODE, weather5Bean.getNow().getCond().getCode());
        values.put(WeatherInfoTable.Columns.Now.Cond.TXT, weather5Bean.getNow().getCond().getTxt());
        values.put(WeatherInfoTable.Columns.Now.FL, weather5Bean.getNow().getFl());
        values.put(WeatherInfoTable.Columns.Now.HUM, weather5Bean.getNow().getHum());
        values.put(WeatherInfoTable.Columns.Now.PCPN, weather5Bean.getNow().getPcpn());
        values.put(WeatherInfoTable.Columns.Now.PRES, weather5Bean.getNow().getPres());
        values.put(WeatherInfoTable.Columns.Now.TMP, weather5Bean.getNow().getTmp());
        values.put(WeatherInfoTable.Columns.Now.VIS, weather5Bean.getNow().getVis());
        values.put(WeatherInfoTable.Columns.Now.Wind.DEG, weather5Bean.getNow().getWind().getDeg());
        values.put(WeatherInfoTable.Columns.Now.Wind.DIR, weather5Bean.getNow().getWind().getDir());
        values.put(WeatherInfoTable.Columns.Now.Wind.SC, weather5Bean.getNow().getWind().getSc());
        values.put(WeatherInfoTable.Columns.Now.Wind.SPD, weather5Bean.getNow().getWind().getSpd());
        values.put(WeatherInfoTable.Columns.DailyForecast.DATE, weather5Bean.getDailyForecast().get(0).getDate());
        values.put(WeatherInfoTable.Columns.DailyForecast.UV, weather5Bean.getDailyForecast().get(0).getUv());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D, weather5Bean.getDailyForecast().get(0).getCond().getCodeD());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N, weather5Bean.getDailyForecast().get(0).getCond().getCodeN());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D, weather5Bean.getDailyForecast().get(0).getCond().getTxtD());
        values.put(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N, weather5Bean.getDailyForecast().get(0).getCond().getTxtN());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MAX, weather5Bean.getDailyForecast().get(0).getTmp().getMax());
        values.put(WeatherInfoTable.Columns.DailyForecast.Tmp.MIN, weather5Bean.getDailyForecast().get(0).getTmp().getMin());
        values.put(WeatherInfoTable.Columns.DailyForecast.Astro.SR, weather5Bean.getDailyForecast().get(0).getAstro().getSr());
        values.put(WeatherInfoTable.Columns.DailyForecast.Astro.SS, weather5Bean.getDailyForecast().get(0).getAstro().getSs());
        values.put(WeatherInfoTable.Columns.Aqi.AQI, weather5Bean.getAqi().getCity().getAqi());
        values.put(WeatherInfoTable.Columns.Aqi.CO, weather5Bean.getAqi().getCity().getCo());
        values.put(WeatherInfoTable.Columns.Aqi.NO2, weather5Bean.getAqi().getCity().getNo2());
        values.put(WeatherInfoTable.Columns.Aqi.O3, weather5Bean.getAqi().getCity().getO3());
        values.put(WeatherInfoTable.Columns.Aqi.PM10, weather5Bean.getAqi().getCity().getPm10());
        values.put(WeatherInfoTable.Columns.Aqi.PM25, weather5Bean.getAqi().getCity().getPm25());
        values.put(WeatherInfoTable.Columns.Aqi.QLTY, weather5Bean.getAqi().getCity().getQlty());
        values.put(WeatherInfoTable.Columns.Aqi.SO2, weather5Bean.getAqi().getCity().getSo2());
        values.put(WeatherInfoTable.Columns.Suggestion.Air.BRF, weather5Bean.getSuggestion().getAir().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Air.TXT, weather5Bean.getSuggestion().getAir().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Comf.BRF, weather5Bean.getSuggestion().getComf().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Comf.TXT, weather5Bean.getSuggestion().getComf().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Cw.BRF, weather5Bean.getSuggestion().getCw().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Cw.TXT, weather5Bean.getSuggestion().getCw().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Drsg.BRF, weather5Bean.getSuggestion().getDrsg().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Drsg.TXT, weather5Bean.getSuggestion().getDrsg().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Flu.BRF, weather5Bean.getSuggestion().getFlu().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Flu.TXT, weather5Bean.getSuggestion().getFlu().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Sport.BRF, weather5Bean.getSuggestion().getSport().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Sport.TXT, weather5Bean.getSuggestion().getSport().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Trav.BRF, weather5Bean.getSuggestion().getTrav().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Trav.TXT, weather5Bean.getSuggestion().getTrav().getTxt());
        values.put(WeatherInfoTable.Columns.Suggestion.Uv.BRF, weather5Bean.getSuggestion().getUv().getBrf());
        values.put(WeatherInfoTable.Columns.Suggestion.Uv.TXT, weather5Bean.getSuggestion().getUv().getTxt());

        return values;
    }
}
