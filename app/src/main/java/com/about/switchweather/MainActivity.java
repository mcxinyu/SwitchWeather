package com.about.switchweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.about.switchweather.Model.ConditionBean;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.UUID;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchCondition().execute();
    }

    @Override
    public void onFetchWeatherComplete(UUID weatherBeanUUID) {
        if (WeatherLab.get(this).getWeatherBean(weatherBeanUUID) == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(weatherBeanUUID);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private class FetchCondition extends AsyncTask<Void, Void, ConditionBean> {

        @Override
        protected ConditionBean doInBackground(Void... params) {
            return new HeWeatherFetch().fetchConditionInfo();
        }

        @Override
        protected void onPostExecute(ConditionBean conditionBean) {
            storeCondition(conditionBean);
        }
    }

    private void storeCondition(ConditionBean conditionBean) {
        if (conditionBean == null){
            return;
        }
    }
}
