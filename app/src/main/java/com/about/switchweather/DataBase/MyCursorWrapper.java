package com.about.switchweather.DataBase;

import android.database.Cursor;
import com.about.switchweather.DataBase.WeatherDbSchema.ConditionTable;
import com.about.switchweather.DataBase.WeatherDbSchema.WeatherInfoTable;
import com.about.switchweather.Model.Condition;
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
        conditionBean.setTxt_en(TEXT_EN);
        conditionBean.setIcon(ICON);

        return conditionBean;
    }
}
