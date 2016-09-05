package com.about.switchweather.UI.MainUI;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.about.switchweather.UI.AppManager;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SingleFragmentActivity;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;
import com.about.switchweather.UI.WeatherUI.WeatherActivity;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private static final String EXTRA_WEATHER_CITY_NAME = "MainActivity.Weather_City_id";
    private static boolean fragmentHasBeenRunning = false;

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
        AppManager.getAppManager().addActivity(this);
        fragmentHasBeenRunning = false;

        // 如果数据库中没有数据就在网络获取，是不是应该改成对比结果后更新呢？（例如在第一次获取数据的时候被用户取消了）
        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (!fragmentHasBeenRunning) {
            if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityId(cityId) == null) {
                return;
            }
            Intent intent = WeatherActivity.newIntent(MyApplication.getContext(), cityId, updated);
            fragmentHasBeenRunning = true;
            startActivity(intent);
        }
    }

    private class FetchCondition extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // TODO: 2016/9/2 应该显示一个进度条，以避免 city list 还没有加载完就进入 city search
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<City> cityList = new HeWeatherFetch().fetchCityList();
            storeCityList(cityList);
            List<Condition> conditions = new HeWeatherFetch().fetchConditionList();
            storeCondition(conditions);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
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
