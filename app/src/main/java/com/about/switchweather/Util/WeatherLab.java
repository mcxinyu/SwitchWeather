package com.about.switchweather.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.about.switchweather.DataBase.WeatherBaseHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherBeanTable;
import com.about.switchweather.Model.ConditionBean;
import com.about.switchweather.Model.WeatherBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        mDatabase.insert(WeatherBeanTable.NAME, null, getWeatherBeanValues(weatherBean));
    }

    public List<WeatherBean> getWeatherBeans() {
        return new ArrayList<>();
    }

    public WeatherBean getWeatherBean(UUID uuid) {
        return null;
    }

    public void updateWeatherBean(WeatherBean weatherBean){
        String uuid = weatherBean.getUUID().toString();
        ContentValues values = getWeatherBeanValues(weatherBean);

        mDatabase.update(WeatherBeanTable.NAME, values, WeatherBeanTable.Columns.UUID + "=?", new String[]{uuid});
    }

    private static ContentValues getWeatherBeanValues(WeatherBean weatherBean){
        ContentValues values = new ContentValues();
        values.put(WeatherBeanTable.Columns.UUID, weatherBean.getUUID().toString());
        values.put(WeatherBeanTable.Columns.STATUS, weatherBean.getStatus());
        values.put(WeatherBeanTable.Columns.Basic.CITY, weatherBean.getBasic().getCity());
        values.put(WeatherBeanTable.Columns.Basic.Update.LOC, weatherBean.getBasic().getUpdate().getLoc());
        values.put(WeatherBeanTable.Columns.Now.TMP, weatherBean.getNow().getTmp());
        values.put(WeatherBeanTable.Columns.Now.Cond.CODE, weatherBean.getNow().getCond().getCode());
        values.put(WeatherBeanTable.Columns.Now.Cond.TXT, weatherBean.getNow().getCond().getTxt());
        values.put(WeatherBeanTable.Columns.DailyForecast.DATE, weatherBean.getDaily_forecast().get(0).getDate());
        values.put(WeatherBeanTable.Columns.DailyForecast.Tmp.MAX, weatherBean.getDaily_forecast().get(0).getTmp().getMax());
        values.put(WeatherBeanTable.Columns.DailyForecast.Tmp.MIN, weatherBean.getDaily_forecast().get(0).getTmp().getMin());
        values.put(WeatherBeanTable.Columns.DailyForecast.Cond.CODE_D, weatherBean.getDaily_forecast().get(0).getCond().getCode_d());
        values.put(WeatherBeanTable.Columns.DailyForecast.Cond.CODE_N, weatherBean.getDaily_forecast().get(0).getCond().getCode_n());
        values.put(WeatherBeanTable.Columns.DailyForecast.Cond.TXT_D, weatherBean.getDaily_forecast().get(0).getCond().getTxt_d());
        values.put(WeatherBeanTable.Columns.DailyForecast.Cond.TXT_N, weatherBean.getDaily_forecast().get(0).getCond().getTxt_n());

        return values;
    }

    private static ContentValues getConditionValues(ConditionBean conditionBean, int position){
        ContentValues values = new ContentValues();
        values.put(ConditionTable.Columns.UUID, conditionBean.getCond_info().get(position).getMid().toString());
        values.put(ConditionTable.Columns.CODE, conditionBean.getCond_info().get(position).getCode());
        values.put(ConditionTable.Columns.TEXT, conditionBean.getCond_info().get(position).getTxt());
        values.put(ConditionTable.Columns.TEXT_EN, conditionBean.getCond_info().get(position).getTxt_en());
        values.put(ConditionTable.Columns.ICON, conditionBean.getCond_info().get(position).getIcon());

        return values;
    }
}
