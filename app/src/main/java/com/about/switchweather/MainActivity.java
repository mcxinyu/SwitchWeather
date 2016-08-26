package com.about.switchweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mActionBar = getSupportActionBar();
        mToolbar.setNavigationIcon(R.drawable.ic_select_icon);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "setNavigationOnClickListener", Toast.LENGTH_SHORT).show();
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(MainActivity.this, "setOnMenuItemClickListener", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (WeatherLab.get(this).getWeatherInfoWithCityId(cityId) == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(cityId, updated);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
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
