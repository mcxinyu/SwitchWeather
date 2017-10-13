package io.github.mcxinyu.switchweather.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.database.WeatherDatabaseLab;
import io.github.mcxinyu.switchweather.model.Condition;
import io.github.mcxinyu.switchweather.service.UpdateWeatherService;
import io.github.mcxinyu.switchweather.ui.fragment.MainEmptyFragment;
import io.github.mcxinyu.switchweather.ui.fragment.SearchCityFragment;
import io.github.mcxinyu.switchweather.util.LogUtils;
import okhttp3.ResponseBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Deprecated
public class MainActivity extends SingleFragmentActivity
        implements SearchCityFragment.Callbacks {
    private static final String TAG = "MainActivity";
    private static final String EXTRA_WEATHER_CITY_NAME = "MainActivity.Weather_City_id";

    private Fragment mFragment;

    public static Intent newIntent(Context context, @Nullable String cityName) {
        Intent intent = new Intent(context, MainActivity.class);
        if (cityName != null) {
            intent.putExtra(EXTRA_WEATHER_CITY_NAME, cityName);
        }
        return intent;
    }

    @Override
    public Fragment createFragment() {
        String cityName = getIntent().getStringExtra(EXTRA_WEATHER_CITY_NAME);
        if (cityName == null) {
            if (SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll().size() == 0) {
                mFragment = SearchCityFragment.newInstance(false);
            } else {
                mFragment = MainEmptyFragment.newInstance(null);
            }
        } else {
            mFragment = MainEmptyFragment.newInstance(cityName);
        }
        return mFragment;
    }

    @Override
    protected int getLayoutResId() {
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

        // 初始化工作
        // if (WeatherDatabaseLab.get(this).getConditionList().size() == 0) {
        //     new FetchBasedDataTask(this).execute();
        // }

        // initBackgroundService();
    }

    private void initBackgroundService() {
        // 开启每天凌晨 1 点更新一次天气，app 进程被杀后无法执行
        if (!SwitchWeatherApp.isServiceRunning(UpdateWeatherService.class)) {
            UpdateWeatherService.setServiceDailyAlarm(SwitchWeatherApp.getInstance(), true);
        }
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorWhite), 0);
    }

    @Override
    public void onHomeButtonClicked() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (mFragment instanceof SearchCityFragment) {
            if (!((SearchCityFragment) mFragment).onActivityBackPress()) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 获取天气代码
     */
    @Deprecated
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
            progressDialog.setMax(2);
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
            WeatherApiHelper.getConditionCode(MainActivity.this)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            progressDialog.cancel();
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        @Override
                        public void onNext(ResponseBody responseBody) {
                            publishProgress(1);

                            try {
                                String text = new String(responseBody.bytes());
                                List<Condition> conditions = listConditions(text);
                                storeCondition(conditions);
                                publishProgress(2);
                            } catch (IOException e) {
                                progressDialog.cancel();
                                e.printStackTrace();
                            }
                        }
                    });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.cancel();
            if (mFragment instanceof MainEmptyFragment) {
                ((MainEmptyFragment) mFragment).showSnackBarAlert(getResources().getString(R.string.loading_success), false);
            }
        }
    }

    @Deprecated
    private void storeCondition(List<Condition> conditions) {
        if (conditions == null) {
            return;
        }
        WeatherDatabaseLab.get(this).addConditionList(conditions);
        LogUtils.i(TAG, "storeCondition: OK");
    }

    @Deprecated
    private List<Condition> listConditions(String result) {
        List<Condition> conditionBeanList = null;
        try {
            result = convertV5ConditionToJson(result).toString();
            Gson gson = new Gson();
            conditionBeanList = gson.fromJson(result, new TypeToken<List<Condition>>() {
            }.getType());
            LogUtils.i(TAG, "downloadConditionList: STATUS OK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conditionBeanList;
    }

    @Deprecated
    private JSONObject convertV5ConditionToJson(String text) {
        JSONObject root = new JSONObject();
        if (text == null || text.equals("")) {
            try {
                root.put("status", "failed");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return root;
        }

        try {
            String[] lines = text.split("\n");
            JSONArray array = new JSONArray();

            for (String line : lines) {
                if (line.contains("http")) {
                    String[] entries = line.split("\t");
                    JSONObject lineObject = new JSONObject();
                    if (entries.length == 4) {
                        lineObject.put("code", entries[0])
                                .put("txt", entries[1])
                                .put("txt_en", entries[2])
                                .put("icon", entries[3]);
                    }
                    array.put(lineObject);
                }
            }
            root.put("cond_info", array);
            root.put("status", "ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
    }
}
