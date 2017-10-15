package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast.ResultBean.HourlyBean.AqiBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.AqiBean.CityBean;
import io.github.mcxinyu.switchweather.ui.fragment.AqiFragment;

import static io.github.mcxinyu.switchweather.ui.fragment.AqiFragment.CAI_YUN_WEATHER_AQI_FORECAST_LIST;
import static io.github.mcxinyu.switchweather.ui.fragment.AqiFragment.HE_WEATHER_MODEL_AQI_BEAN;

public class AqiActivity extends SingleFragmentActivity {
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    public static Intent newIntent(Context context, CityBean aqiBean, ArrayList<AqiBean> aqiBeanArrayList) {

        Intent intent = new Intent(context, AqiActivity.class);
        if (aqiBean == null) {
            aqiBean = new CityBean();
        }
        intent.putExtra(HE_WEATHER_MODEL_AQI_BEAN, aqiBean);
        intent.putParcelableArrayListExtra(CAI_YUN_WEATHER_AQI_FORECAST_LIST, aqiBeanArrayList);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_container;
    }

    @Override
    public Fragment createFragment() {
        CityBean aqiBean = getIntent().getParcelableExtra(HE_WEATHER_MODEL_AQI_BEAN);
        ArrayList<AqiBean> aqiBeanArrayList = getIntent().getParcelableArrayListExtra(CAI_YUN_WEATHER_AQI_FORECAST_LIST);
        return AqiFragment.newInstance(aqiBean, aqiBeanArrayList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.aqi_title));
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
