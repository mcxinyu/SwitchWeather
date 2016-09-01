package com.about.switchweather.DataBase;

import android.database.Cursor;
import com.about.switchweather.DataBase.WeatherDbSchema.CityTable;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.DailyForecastTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.DailyForecast;
import com.about.switchweather.Model.WeatherInfo;

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

    public WeatherInfo getWeatherInfo(){
        String ID = getString(getColumnIndex(WeatherInfoTable.Columns.Basic.ID));
        String CITY = getString(getColumnIndex(WeatherInfoTable.Columns.Basic.CITY));
        String LOC = getString(getColumnIndex(WeatherInfoTable.Columns.Basic.Update.LOC));
        String STATUS = getString(getColumnIndex(WeatherInfoTable.Columns.STATUS));
        String TMP = getString(getColumnIndex(WeatherInfoTable.Columns.Now.TMP));
        String CODE = getString(getColumnIndex(WeatherInfoTable.Columns.Now.Cond.CODE));
        String TXT = getString(getColumnIndex(WeatherInfoTable.Columns.Now.Cond.TXT));
        String DATE = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.DATE));
        String MAX = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Tmp.MAX));
        String MIN = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Tmp.MIN));
        String CODE_D = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_D));
        String CODE_N = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Cond.CODE_N));
        String TXT_D = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_D));
        String TXT_N = getString(getColumnIndex(WeatherInfoTable.Columns.DailyForecast.Cond.TXT_N));

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setBasicCityId(ID);
        weatherInfo.setBasicCity(CITY);
        weatherInfo.setBasicUpdateLoc(LOC);
        weatherInfo.setStatus(STATUS);
        weatherInfo.setNowTmp(TMP);
        weatherInfo.setNowCondCode(CODE);
        weatherInfo.setNowCondTxt(TXT);
        weatherInfo.setDfDate(DATE);
        weatherInfo.setDfTmpMax(MAX);
        weatherInfo.setDfTmpMin(MIN);
        weatherInfo.setDfCondCodeD(CODE_D);
        weatherInfo.setDfCondCodeN(CODE_N);
        weatherInfo.setDfCondTxtD(TXT_D);
        weatherInfo.setDfCondTxtN(TXT_N);

        return weatherInfo;
    }

    public Condition getCondition(){
        String CODE = getString(getColumnIndex(ConditionTable.Columns.CODE));
        String TEXT = getString(getColumnIndex(ConditionTable.Columns.TEXT));
        String TEXT_EN = getString(getColumnIndex(ConditionTable.Columns.TEXT_EN));
        String ICON = getString(getColumnIndex(ConditionTable.Columns.ICON));

        Condition conditionBean = new Condition();
        conditionBean.setCode(CODE);
        conditionBean.setTxt(TEXT);
        conditionBean.setTxtEn(TEXT_EN);
        conditionBean.setIcon(ICON);

        return conditionBean;
    }

    public City getCity(){
        String ID = getString(getColumnIndex(CityTable.Columns.ID));
        String CITY = getString(getColumnIndex(CityTable.Columns.CITY));
        String CNTY = getString(getColumnIndex(CityTable.Columns.CNTY));
        String LAT = getString(getColumnIndex(CityTable.Columns.LAT));
        String LON = getString(getColumnIndex(CityTable.Columns.LON));
        String PROV = getString(getColumnIndex(CityTable.Columns.PROV));

        City city = new City(CITY, CNTY, ID, LAT, LON, PROV);
        //city.setId(ID);
        //city.setCity(CITY);
        //city.setCnty(CNTY);
        //city.setLat(LAT);
        //city.setLon(LON);
        //city.setProv(PROV);

        return city;
    }

    public DailyForecast getDailyForecast(){
        String DATE = getString(getColumnIndex(DailyForecastTable.Columns.DATE));
        String CITY_ID = getString(getColumnIndex(DailyForecastTable.Columns.CITY_ID));
        String CITY = getString(getColumnIndex(DailyForecastTable.Columns.CITY));
        String ASTRO_SR = getString(getColumnIndex(DailyForecastTable.Columns.ASTRO_SR));
        String ASTRO_SS = getString(getColumnIndex(DailyForecastTable.Columns.ASTRO_SS));
        String COND_CODE_D = getString(getColumnIndex(DailyForecastTable.Columns.COND_CODE_D));
        String COND_CODE_N = getString(getColumnIndex(DailyForecastTable.Columns.COND_CODE_N));
        String COND_TXT_D = getString(getColumnIndex(DailyForecastTable.Columns.COND_TXT_D));
        String COND_TXT_N = getString(getColumnIndex(DailyForecastTable.Columns.COND_TXT_N));
        String HUM = getString(getColumnIndex(DailyForecastTable.Columns.HUM));
        String PCPN = getString(getColumnIndex(DailyForecastTable.Columns.PCPN));
        String POP = getString(getColumnIndex(DailyForecastTable.Columns.POP));
        String PRES = getString(getColumnIndex(DailyForecastTable.Columns.PRES));
        String TMP_MAX = getString(getColumnIndex(DailyForecastTable.Columns.TMP_MAX));
        String TMP_MIN = getString(getColumnIndex(DailyForecastTable.Columns.TMP_MIN));
        String VIS = getString(getColumnIndex(DailyForecastTable.Columns.VIS));
        String WIND_DEG = getString(getColumnIndex(DailyForecastTable.Columns.WIND_DEG));
        String WIND_DIR = getString(getColumnIndex(DailyForecastTable.Columns.WIND_DIR));
        String WIND_SC = getString(getColumnIndex(DailyForecastTable.Columns.WIND_SC));
        String WIND_SPD = getString(getColumnIndex(DailyForecastTable.Columns.WIND_SPD));

        DailyForecast dailyForecast = new DailyForecast();
        dailyForecast.setDate(DATE);
        dailyForecast.setCityId(CITY_ID);
        dailyForecast.setCity(CITY);
        dailyForecast.setAstroSr(ASTRO_SR);
        dailyForecast.setAstroSs(ASTRO_SS);
        dailyForecast.setCondCodeD(COND_CODE_D);
        dailyForecast.setCondCodeN(COND_CODE_N);
        dailyForecast.setCondTxtD(COND_TXT_D);
        dailyForecast.setCondTxtN(COND_TXT_N);
        dailyForecast.setHum(HUM);
        dailyForecast.setPcpn(PCPN);
        dailyForecast.setPop(POP);
        dailyForecast.setPres(PRES);
        dailyForecast.setTmpMax(TMP_MAX);
        dailyForecast.setTmpMin(TMP_MIN);
        dailyForecast.setVis(VIS);
        dailyForecast.setWindDeg(WIND_DEG);
        dailyForecast.setWindDir(WIND_DIR);
        dailyForecast.setWindSc(WIND_SC);
        dailyForecast.setWindSpd(WIND_SPD);

        return dailyForecast;
    }
}
