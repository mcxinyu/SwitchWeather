package com.about.switchweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
    }

    @Override
    public void onFetchWeatherComplete(String id) {
        if (WeatherLab.get(this).getWeatherInfo(id) == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(id);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private class FetchCondition extends AsyncTask<Void, Void, List<Condition>> {
        @Override
        protected List<Condition> doInBackground(Void... params) {
            return new HeWeatherFetch().fetchConditionList();
        }

        @Override
        protected void onPostExecute(List<Condition> conditions) {
            storeCondition(conditions);
        }
    }

    private void storeCondition(List<Condition> conditions) {
        if (conditions == null){
            return;
        }
        WeatherLab.get(this).addConditionBean(conditions);
    }
}
