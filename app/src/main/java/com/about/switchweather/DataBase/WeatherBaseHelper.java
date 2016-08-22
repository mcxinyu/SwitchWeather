package com.about.switchweather.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherBeanTable;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class WeatherBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "weather.db";

    public WeatherBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ConditionTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                ConditionTable.Columns.UUID + "," +
                ConditionTable.Columns.CODE + "," +
                ConditionTable.Columns.TEXT + "," +
                ConditionTable.Columns.TEXT_EN + "," +
                ConditionTable.Columns.ICON +
                ")");
        db.execSQL("create table " + WeatherBeanTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                WeatherBeanTable.Columns.UUID + "," +
                WeatherBeanTable.Columns.STATUS + "," +
                WeatherBeanTable.Columns.Basic.CITY + "," +
                WeatherBeanTable.Columns.Basic.Update.LOC + "," +
                WeatherBeanTable.Columns.Now.TMP + "," +
                WeatherBeanTable.Columns.Now.Cond.CODE + "," +
                WeatherBeanTable.Columns.Now.Cond.TXT + "," +
                WeatherBeanTable.Columns.DailyForecast.DATE + "," +
                WeatherBeanTable.Columns.DailyForecast.Tmp.MAX + "," +
                WeatherBeanTable.Columns.DailyForecast.Tmp.MIN + "," +
                WeatherBeanTable.Columns.DailyForecast.Cond.CODE_D + "," +
                WeatherBeanTable.Columns.DailyForecast.Cond.CODE_N + "," +
                WeatherBeanTable.Columns.DailyForecast.Cond.TXT_D + "," +
                WeatherBeanTable.Columns.DailyForecast.Cond.TXT_N +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
