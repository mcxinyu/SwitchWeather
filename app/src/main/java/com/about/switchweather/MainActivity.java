package com.about.switchweather;

import android.support.v4.app.Fragment;
import com.about.switchweather.Model.WeatherBean;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    public void onFetchWeatherComplete(WeatherBean weatherBean) {
        if (weatherBean == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(weatherBean);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
}
