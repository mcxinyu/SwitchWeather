package com.about.switchweather.UI.MainUI;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.R;
import com.about.switchweather.Service.UpdateWeatherService;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SingleFragmentActivity;
import com.about.switchweather.UI.WeatherUI.WeatherActivity;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.LogUtils;
import com.about.switchweather.Util.WeatherLab;
import com.jaeger.library.StatusBarUtil;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private static final String EXTRA_WEATHER_CITY_NAME = "MainActivity.Weather_City_id";
    private static boolean fragmentHasBeenRunning = false;
    private String TAG = "MainActivity";

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
        // 初始化 Preference
        PreferenceManager.setDefaultValues(this, R.xml.settings_general, false);
        fragmentHasBeenRunning = false;

        // 如果数据库中没有数据就在网络获取，是不是应该改成对比结果后更新呢？（例如在第一次获取数据的时候被用户取消了）
        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition(this).execute();
        }

        initBackground();
    }

    private void initBackground() {
        // 开启每天凌晨 1 点更新一次天气
        if (!MyApplication.isServiceRunning(UpdateWeatherService.class)) {
            UpdateWeatherService.setServiceDailyAlarm(MyApplication.getContext(), true);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorWhite), 50);
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (!fragmentHasBeenRunning) {
            if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityId(cityId) == null) {
                // 如果数据库中没有数据， return
                return;
            }
            Intent intent = WeatherActivity.newIntent(MyApplication.getContext(), cityId, updated);
            fragmentHasBeenRunning = true;
            startActivity(intent);
        }
    }

    private class FetchCondition extends AsyncTask<Void, Integer, Void> {
        private Context mContext;
        private ProgressDialog progressDialog;

        public FetchCondition(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Loading Data");
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
        }
    }

    private void storeCondition(List<Condition> conditions) {
        if (conditions == null){
            return;
        }
        WeatherLab.get(this).addConditionBean(conditions);
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
