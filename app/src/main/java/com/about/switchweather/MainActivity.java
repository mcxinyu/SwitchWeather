package com.about.switchweather;

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
    private enum UpdateType {CONDITION, CITYLIST}

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
    }

    @Override
    public void onFetchWeatherComplete(String id) {
        if (WeatherLab.get(this).getWeatherInfoWithCityId(id) == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(id);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private class FetchCondition extends AsyncTask<UpdateType, Void, Boolean> {

        @Override
        protected Boolean doInBackground(UpdateType... params) {
            if (params == null){

            }
            for (int i = 0; i < params.length; i++) {
                if (params[i].equals(UpdateType.CITYLIST)){
                    List<City> cityList = new HeWeatherFetch().fetchCityList();
                    storeCityList(cityList);
                } else if (params[i].equals(UpdateType.CONDITION)){
                    List<Condition> conditions = new HeWeatherFetch().fetchConditionList();
                    storeCondition(conditions);
                }
            }
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
