package com.about.switchweather.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;

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
                ConditionTable.Columns.CODE + "," +
                ConditionTable.Columns.TEXT + "," +
                ConditionTable.Columns.TEXT_EN + "," +
                ConditionTable.Columns.ICON +
                ")");

        db.execSQL("create table " + WeatherInfoTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                WeatherInfoTable.Columns.Basic.ID + "," +
                WeatherInfoTable.Columns.Basic.CITY + "," +
                WeatherInfoTable.Columns.Basic.Update.LOC + "," +
                WeatherInfoTable.Columns.Now.TMP + "," +
                WeatherInfoTable.Columns.Now.Cond.CODE + "," +
                WeatherInfoTable.Columns.Now.Cond.TXT + "," +
                WeatherInfoTable.Columns.DailyForecast.DATE + "," +
                WeatherInfoTable.Columns.DailyForecast.Tmp.MAX + "," +
                WeatherInfoTable.Columns.DailyForecast.Tmp.MIN + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N + "," +
                WeatherInfoTable.Columns.STATUS +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
