package com.about.switchweather;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private static final String EXTRA_WEATHER_CITY_NAME = "MainActivity.Weather_City_id";

    @Override
    public Fragment createFragment() {
        String cityName = getIntent().getStringExtra(EXTRA_WEATHER_CITY_NAME);
        return MainEmptyFragment.newInstance(cityName);
    }

    public static Intent newIntent(Context context, @Nullable String cityName) {
        Intent intent = new Intent(context, MainActivity.class);
        if (cityName != null) {
            intent.putExtra(EXTRA_WEATHER_CITY_NAME, cityName);
        }
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityId(cityId) == null){
            return;
        }
        Intent intent = WeatherActivity.newIntent(MyApplication.getContext(), cityId, updated);
        startActivity(intent);
        finish();
    }

    private class FetchCondition extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            List<City> cityList = new HeWeatherFetch().fetchCityList();
            storeCityList(cityList);
            List<Condition> conditions = new HeWeatherFetch().fetchConditionList();
            storeCondition(conditions);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    private void storeCondition(List<Condition> conditions) {
        if (conditions == null){
            return;
        }
        WeatherLab.get(this).addConditionBean(conditions);
    }

    private void storeCityList(List<City> cityList) {
        if (cityList == null){
            return;
        }
        WeatherLab.get(this).addCityList(cityList);
    }
}
