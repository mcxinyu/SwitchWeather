package com.about.switchweather.ui.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.about.switchweather.model.City;
import com.about.switchweather.model.Condition;
import com.about.switchweather.R;
import com.about.switchweather.service.UpdateWeatherService;
import com.about.switchweather.ui.MyApplication;
import com.about.switchweather.ui.SingleFragmentActivity;
import com.about.switchweather.ui.weather.WeatherActivity;
import com.about.switchweather.util.HeWeatherFetch;
import com.about.switchweather.util.LogUtils;
import com.about.switchweather.database.WeatherLab;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private static final String EXTRA_WEATHER_CITY_NAME = "MainActivity.Weather_City_id";
    private static boolean fragmentHasBeenRunning = false;
    private String TAG = "MainActivity";
    private MainEmptyFragment mFragment;

    @Override
    public Fragment createFragment() {
        String cityName = getIntent().getStringExtra(EXTRA_WEATHER_CITY_NAME);
        mFragment = MainEmptyFragment.newInstance(cityName);
        return mFragment;
    }

    public static Intent newIntent(Context context, @Nullable String cityName) {
        Intent intent = new Intent(context, MainActivity.class);
        if (cityName != null) {
            intent.putExtra(EXTRA_WEATHER_CITY_NAME, cityName);
        }
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        // 使用无 DrawerLayout、无 Toolbar 的布局
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        intent.getStringExtra(EXTRA_WEATHER_CITY_NAME);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 Preference
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);
        fragmentHasBeenRunning = false;

        // 如果数据库中没有数据就在网络获取，是不是应该改成对比结果后更新呢？（例如在第一次获取数据的时候被用户取消了）
        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionList().size() == 0){
            new FetchBasedDataTask(this).execute();
        }

        initBackgroundService();
    }

    private void initBackgroundService() {
        // 开启每天凌晨 1 点更新一次天气
        if (!MyApplication.isServiceRunning(UpdateWeatherService.class)) {
            UpdateWeatherService.setServiceDailyAlarm(MyApplication.getContext(), true);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorWhite), 0);
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (!fragmentHasBeenRunning) {
            if (WeatherLab.get(MyApplication.getContext()).getWeatherInfo(cityId) == null) {
                // 如果数据库中没有数据， return
                return;
            }
            Intent intent = WeatherActivity.newIntent(MyApplication.getContext(), cityId, updated);
            fragmentHasBeenRunning = true;
            startActivity(intent);
        }
    }

    /**
     * 获取城市列表／天气信息列表
     */
    private class FetchBasedDataTask extends AsyncTask<Void, Integer, Void> {
        private Context mContext;
        private ProgressDialog progressDialog;

        public FetchBasedDataTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle(getResources().getString(R.string.loading_data));
            progressDialog.setCancelable(false);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(4);
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
            progressDialog.setMessage(values[0] * 25 + "%");
        }

        @Override
        protected Void doInBackground(Void... params) {
            List<City> cityList = new HeWeatherFetch().fetchCityList();
            publishProgress(1);
            List<Condition> conditions = new HeWeatherFetch().fetchConditionList();
            publishProgress(2);
            storeCondition(conditions);
            publishProgress(3);
            storeCityList(cityList);
            publishProgress(4);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();
            mFragment.showSnackBarAlert(getResources().getString(R.string.loading_success), false);
        }
    }

    private void storeCondition(List<Condition> conditions) {
        if (conditions == null){
            return;
        }
        WeatherLab.get(this).addConditionList(conditions);
        LogUtils.i(TAG, "storeCondition: OK");
    }

    private void storeCityList(List<City> cityList) {
        if (cityList == null){
            return;
        }
        WeatherLab.get(this).addCityList(cityList);
        LogUtils.i(TAG, "storeCityList: OK");
    }
}
