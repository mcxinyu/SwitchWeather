package com.about.switchweather.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.about.switchweather.DataBase.WeatherDbSchema.CityTable;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.DailyForecastTable;
import com.about.switchweather.DataBase.WeatherDbSchema.HourlyForecastTable;
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
        db.execSQL("create table " + CityTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                CityTable.Columns.ID + "," +
                CityTable.Columns.CITY_EN + "," +
                CityTable.Columns.CITY_ZH + "," +
                CityTable.Columns.COUNTRY_CODE + "," +
                CityTable.Columns.COUNTRY_EN + "," +
                CityTable.Columns.COUNTRY_ZH + "," +
                CityTable.Columns.PROVINCE_EN + "," +
                CityTable.Columns.PROVINCE_ZH + "," +
                CityTable.Columns.LEADER_EN + "," +
                CityTable.Columns.LEADER_ZH + "," +
                CityTable.Columns.LAT + "," +
                CityTable.Columns.LON +
                ")");

        db.execSQL("create table " + ConditionTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                ConditionTable.Columns.CODE + "," +
                ConditionTable.Columns.TEXT + "," +
                ConditionTable.Columns.TEXT_EN + "," +
                ConditionTable.Columns.ICON +
                ")");

        db.execSQL("create table " + DailyForecastTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                DailyForecastTable.Columns.DATE + "," +
                DailyForecastTable.Columns.CITY_ID + "," +
                DailyForecastTable.Columns.CITY + "," +
                DailyForecastTable.Columns.Astro.SR + "," +
                DailyForecastTable.Columns.Astro.SS + "," +
                DailyForecastTable.Columns.Astro.MR + "," +
                DailyForecastTable.Columns.Astro.MS + "," +
                DailyForecastTable.Columns.Cond.CODE_D + "," +
                DailyForecastTable.Columns.Cond.CODE_N + "," +
                DailyForecastTable.Columns.Cond.TXT_D + "," +
                DailyForecastTable.Columns.Cond.TXT_N + "," +
                DailyForecastTable.Columns.HUM + "," +
                DailyForecastTable.Columns.PCPN + "," +
                DailyForecastTable.Columns.POP + "," +
                DailyForecastTable.Columns.PRES + "," +
                DailyForecastTable.Columns.Tmp.MAX + "," +
                DailyForecastTable.Columns.Tmp.MIN + "," +
                DailyForecastTable.Columns.UV + "," +
                DailyForecastTable.Columns.VIS + "," +
                DailyForecastTable.Columns.Wind.DEG + "," +
                DailyForecastTable.Columns.Wind.DIR + "," +
                DailyForecastTable.Columns.Wind.SC + "," +
                DailyForecastTable.Columns.Wind.SPD +
                ")");

        db.execSQL("create table " + HourlyForecastTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                HourlyForecastTable.Columns.DATE + "," +
                HourlyForecastTable.Columns.CITY_ID + "," +
                HourlyForecastTable.Columns.CITY + "," +
                HourlyForecastTable.Columns.Cond.CODE + "," +
                HourlyForecastTable.Columns.Cond.TXT + "," +
                HourlyForecastTable.Columns.HUM + "," +
                HourlyForecastTable.Columns.POP + "," +
                HourlyForecastTable.Columns.PRES + "," +
                HourlyForecastTable.Columns.TMP + "," +
                HourlyForecastTable.Columns.Wind.DEG + "," +
                HourlyForecastTable.Columns.Wind.DIR + "," +
                HourlyForecastTable.Columns.Wind.SC + "," +
                HourlyForecastTable.Columns.Wind.SPD +
                ")");

        db.execSQL("create table " + WeatherInfoTable.NAME + "(" +
                "_id integer primary key autoincrement," +
                WeatherInfoTable.Columns.status + "," +
                WeatherInfoTable.Columns.Basic.CITY + "," +
                WeatherInfoTable.Columns.Basic.ID + "," +
                WeatherInfoTable.Columns.Basic.Update.LOC + "," +
                WeatherInfoTable.Columns.Basic.Update.UTC + "," +
                WeatherInfoTable.Columns.Now.Cond.CODE + "," +
                WeatherInfoTable.Columns.Now.Cond.TXT + "," +
                WeatherInfoTable.Columns.Now.FL + "," +
                WeatherInfoTable.Columns.Now.HUM + "," +
                WeatherInfoTable.Columns.Now.PCPN + "," +
                WeatherInfoTable.Columns.Now.PRES + "," +
                WeatherInfoTable.Columns.Now.TMP + "," +
                WeatherInfoTable.Columns.Now.VIS + "," +
                WeatherInfoTable.Columns.Now.Wind.DEG + "," +
                WeatherInfoTable.Columns.Now.Wind.DIR + "," +
                WeatherInfoTable.Columns.Now.Wind.SC + "," +
                WeatherInfoTable.Columns.Now.Wind.SPD + "," +
                WeatherInfoTable.Columns.DailyForecast.DATE + "," +
                WeatherInfoTable.Columns.DailyForecast.UV + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D + "," +
                WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N + "," +
                WeatherInfoTable.Columns.DailyForecast.Tmp.MAX + "," +
                WeatherInfoTable.Columns.DailyForecast.Tmp.MIN + "," +
                WeatherInfoTable.Columns.DailyForecast.Astro.SR + "," +
                WeatherInfoTable.Columns.DailyForecast.Astro.SS + "," +
                WeatherInfoTable.Columns.Aqi.AQI + "," +
                WeatherInfoTable.Columns.Aqi.CO + "," +
                WeatherInfoTable.Columns.Aqi.NO2 + "," +
                WeatherInfoTable.Columns.Aqi.O3 + "," +
                WeatherInfoTable.Columns.Aqi.PM10 + "," +
                WeatherInfoTable.Columns.Aqi.PM25 + "," +
                WeatherInfoTable.Columns.Aqi.QLTY + "," +
                WeatherInfoTable.Columns.Aqi.SO2 + "," +
                WeatherInfoTable.Columns.Suggestion.Air.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Air.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Comf.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Comf.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Cw.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Cw.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Drsg.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Drsg.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Flu.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Flu.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Sport.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Sport.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Trav.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Trav.TXT + "," +
                WeatherInfoTable.Columns.Suggestion.Uv.BRF + "," +
                WeatherInfoTable.Columns.Suggestion.Uv.TXT +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
