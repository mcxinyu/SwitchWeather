package com.about.switchweather.database;

import android.database.Cursor;

import com.about.switchweather.database.WeatherDbSchema.CityTable;
import com.about.switchweather.database.WeatherDbSchema.ConditionTable;
import com.about.switchweather.database.WeatherDbSchema.DailyForecastTable;
import com.about.switchweather.model.City;
import com.about.switchweather.model.Condition;
import com.about.switchweather.model.DailyForecast;
import com.about.switchweather.model.HourlyForecast;
import com.about.switchweather.model.WeatherInfo;

import static com.about.switchweather.database.WeatherDbSchema.ConditionTable.Columns.ICON;

/**
 * Created by 跃峰 on 2016/8/22.
 */
public class MyCursorWrapper extends android.database.CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public MyCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public City getCity(){
        String id = getString(getColumnIndex(CityTable.Columns.ID));
        String cityEn = getString(getColumnIndex(CityTable.Columns.CITY_EN));
        String cityZh = getString(getColumnIndex(CityTable.Columns.CITY_ZH));
        String countryCode = getString(getColumnIndex(CityTable.Columns.COUNTRY_CODE));
        String countryEn = getString(getColumnIndex(CityTable.Columns.COUNTRY_EN));
        String countryZh = getString(getColumnIndex(CityTable.Columns.COUNTRY_ZH));
        String provinceEn = getString(getColumnIndex(CityTable.Columns.PROVINCE_EN));
        String provinceZh = getString(getColumnIndex(CityTable.Columns.PROVINCE_ZH));
        String leaderEn = getString(getColumnIndex(CityTable.Columns.LEADER_EN));
        String leaderZh = getString(getColumnIndex(CityTable.Columns.LEADER_ZH));
        String lat = getString(getColumnIndex(CityTable.Columns.LAT));
        String lon = getString(getColumnIndex(CityTable.Columns.LON));

        return new City(id, cityEn, cityZh,
                countryCode, countryEn, countryZh,
                provinceEn, provinceZh,
                leaderEn, leaderZh,
                lat, lon);
    }

    public Condition getCondition(){
        String code = getString(getColumnIndex(ConditionTable.Columns.CODE));
        String text = getString(getColumnIndex(ConditionTable.Columns.TEXT));
        String text_en = getString(getColumnIndex(ConditionTable.Columns.TEXT_EN));
        String icon = getString(getColumnIndex(ICON));

        return new Condition(code, text, text_en, icon);
    }

    public DailyForecast getDailyForecast(){
        String date = getString(getColumnIndex(DailyForecastTable.Columns.DATE));
        String cityId = getString(getColumnIndex(DailyForecastTable.Columns.CITY_ID));
        String city = getString(getColumnIndex(DailyForecastTable.Columns.CITY));
        String astroSr = getString(getColumnIndex(DailyForecastTable.Columns.Astro.SR));
        String astroSs = getString(getColumnIndex(DailyForecastTable.Columns.Astro.SS));
        String astroMr = getString(getColumnIndex(DailyForecastTable.Columns.Astro.MR));
        String astroMs = getString(getColumnIndex(DailyForecastTable.Columns.Astro.MS));
        String condCodeD = getString(getColumnIndex(DailyForecastTable.Columns.Cond.CODE_D));
        String condCodeN = getString(getColumnIndex(DailyForecastTable.Columns.Cond.CODE_N));
        String condTxtD = getString(getColumnIndex(DailyForecastTable.Columns.Cond.TXT_D));
        String condTxtN = getString(getColumnIndex(DailyForecastTable.Columns.Cond.TXT_N));
        String hum = getString(getColumnIndex(DailyForecastTable.Columns.HUM));
        String pcpn = getString(getColumnIndex(DailyForecastTable.Columns.PCPN));
        String pop = getString(getColumnIndex(DailyForecastTable.Columns.POP));
        String pres = getString(getColumnIndex(DailyForecastTable.Columns.PRES));
        String tmpMax = getString(getColumnIndex(DailyForecastTable.Columns.Tmp.MAX));
        String tmpMin = getString(getColumnIndex(DailyForecastTable.Columns.Tmp.MIN));
        String uv = getString(getColumnIndex(DailyForecastTable.Columns.UV));
        String vis = getString(getColumnIndex(DailyForecastTable.Columns.VIS));
        String windDeg = getString(getColumnIndex(DailyForecastTable.Columns.Wind.DEG));
        String windDir = getString(getColumnIndex(DailyForecastTable.Columns.Wind.DIR));
        String windSc = getString(getColumnIndex(DailyForecastTable.Columns.Wind.SC));
        String windSpd = getString(getColumnIndex(DailyForecastTable.Columns.Wind.SPD));

        return new DailyForecast(date,
                cityId, city,
                astroSr, astroSs, astroMr, astroMs,
                condCodeD, condCodeN, condTxtD, condTxtN,
                hum, pcpn, pop, pres,
                tmpMax, tmpMin,
                uv, vis,
                windDeg, windDir, windSc, windSpd);
    }

    public HourlyForecast getHourlyForecast(){
        String date = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.DATE));
        String cityId = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.CITY_ID));
        String city = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.CITY));
        String condCode = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Cond.CODE));
        String condTxt = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Cond.TXT));
        String hum = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.HUM));
        String pop = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.POP));
        String pres = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.PRES));
        String tmp = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.TMP));
        String windDeg = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Wind.DEG));
        String windDir = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Wind.DIR));
        String windSc = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Wind.SC));
        String windSpd = getString(getColumnIndex(WeatherDbSchema.HourlyForecastTable.Columns.Wind.SPD));

        return new HourlyForecast(date,
                cityId, city,
                condCode, condTxt,
                hum, pop, pres, tmp,
                windDeg, windDir, windSc, windSpd);
    }

    public WeatherInfo getWeatherInfo(){
        String status = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.status));
        String basicCity = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Basic.CITY));
        String basicCityId = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Basic.ID));
        String basicUpdateLoc = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Basic.Update.LOC));
        String basicUpdateUtc = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Basic.Update.UTC));
        String nowCondCode = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Cond.CODE));
        String nowCondTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Cond.TXT));
        String nowFl = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.FL));
        String nowHum = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.HUM));
        String nowPcpn = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.PCPN));
        String nowPres = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.PRES));
        String nowTmp = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.TMP));
        String nowVis = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.VIS));
        String nowWindDeg = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Wind.DEG));
        String nowWindDir = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Wind.DIR));
        String nowWindSc = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Wind.SC));
        String nowWindSpd = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Now.Wind.SPD));
        String dailyForecastDate = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.DATE));
        String dailyForecastUv = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.UV));
        String dailyForecastCondCodeD = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D));
        String dailyForecastCondCodeN = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N));
        String dailyForecastCondTxtD = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D));
        String dailyForecastCondTxtN = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N));
        String dailyForecastTmpMax = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Tmp.MAX));
        String dailyForecastTmpMin = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Tmp.MIN));
        String dailyForecastAstroSr = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Astro.SR));
        String dailyForecastAstroSs = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.DailyForecast.Astro.SS));
        String aqi = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.AQI));
        String aqiCo = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.CO));
        String aqiNo2 = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.NO2));
        String aqiO3 = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.O3));
        String aqiPm10 = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.PM10));
        String aqiPm25 = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.PM25));
        String aqiQlty = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.QLTY));
        String aqiSo2 = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Aqi.SO2));
        String suggestionAirBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Air.BRF));
        String suggestionAirTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Air.TXT));
        String suggestionComfBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Comf.BRF));
        String suggestionComfTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Comf.TXT));
        String suggestionCwBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Cw.BRF));
        String suggestionCwTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Cw.TXT));
        String suggestionDrsgBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Drsg.BRF));
        String suggestionDrsgTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Drsg.TXT));
        String suggestionFluBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Flu.BRF));
        String suggestionFluTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Flu.TXT));
        String suggestionSportBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Sport.BRF));
        String suggestionSportTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Sport.TXT));
        String suggestionTravBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Trav.BRF));
        String suggestionTravTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Trav.TXT));
        String suggestionUvBrf = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Uv.BRF));
        String suggestionUvTxt = getString(getColumnIndex(WeatherDbSchema.WeatherInfoTable.Columns.Suggestion.Uv.TXT));

        return new WeatherInfo(status,
                basicCity, basicCityId, basicUpdateLoc, basicUpdateUtc,
                nowCondCode, nowCondTxt,
                nowFl, nowHum, nowPcpn, nowPres, nowTmp, nowVis,
                nowWindDeg, nowWindDir, nowWindSc, nowWindSpd,
                dailyForecastDate, dailyForecastUv,
                dailyForecastCondCodeD, dailyForecastCondCodeN,
                dailyForecastCondTxtD, dailyForecastCondTxtN,
                dailyForecastTmpMax, dailyForecastTmpMin,
                dailyForecastAstroSr, dailyForecastAstroSs,
                aqi, aqiCo, aqiNo2, aqiO3,
                aqiPm10, aqiPm25, aqiQlty, aqiSo2,
                suggestionAirBrf, suggestionAirTxt,
                suggestionComfBrf, suggestionComfTxt,
                suggestionCwBrf, suggestionCwTxt,
                suggestionDrsgBrf, suggestionDrsgTxt,
                suggestionFluBrf, suggestionFluTxt,
                suggestionSportBrf, suggestionSportTxt,
                suggestionTravBrf, suggestionTravTxt,
                suggestionUvBrf, suggestionUvTxt);
    }
}
