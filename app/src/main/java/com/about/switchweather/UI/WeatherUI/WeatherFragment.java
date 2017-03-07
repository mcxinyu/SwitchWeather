package com.about.switchweather.UI.WeatherUI;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.about.switchweather.DataBase.WeatherLab;
import com.about.switchweather.Model.DailyForecast;
import com.about.switchweather.Model.HourlyForecast;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Model.WeatherModel;
import com.about.switchweather.R;
import com.about.switchweather.UI.AqiUI.AqiActivity;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SuggestionUI.SuggestionActivity;
import com.about.switchweather.UI.adapter.DailyForecastAdapter;
import com.about.switchweather.UI.adapter.HourlyForecastAdapter;
import com.about.switchweather.Util.ColoredSnackbar;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.QueryPreferences;
import com.about.switchweather.Util.WeatherUtil;

import java.util.List;

import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_COMF;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_CW;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_DRSG;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_FLU;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_SPORT;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_UV;


/**
 * Created by 跃峰 on 2016/8/20.
 */
public class WeatherFragment extends Fragment implements DailyForecastAdapter.Callbacks, HourlyForecastAdapter.Callbacks{
    private static final String TAG = "WeatherFragment";
    private static final String ARG_WEATHER_CITY_ID = "WeatherFragment";
    private static final String ARG_WEATHER_UPDATED = "Weather_updated";

    private ScrollView mScrollView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout mMainWeatherLayout;  //用于设置背景
    private TextView mTemperatureTextView;  //温度
    private TextView mAqiTextView;  //空气质量
    private TextView mWeatherDescribeTextView;  //天气描述
    private TextView mTodayForecastWindTextView;    //今天风
    private TextView mTodayForecastHumidityTextView;  //湿度
    private TextView mTodayForecastFlTextView; //体感温度
    private TextView mTodayForecastAqiTextView; //空气质量
    private TextView mTodayForecastPressureTextView; //气压
    private TextView mTodayForecastUvTextView; //紫外线
    private TextView mTodayForecastWindDetailTextView;    //今天风等级
    private TextView mTodayForecastHumidityDetailTextView;  //湿度
    private TextView mTodayForecastFlDetailTextView; //体感温度
    private TextView mTodayForecastAqiDetailTextView; //空气质量
    private TextView mTodayForecastPressureDetailTextView; //气压
    private TextView mTodayForecastUvDetailTextView; //紫外线

    private TextView mSuggestionComfTextView; //舒适度指数
    private TextView mSuggestionFluTextView; //感冒指数
    private TextView mSuggestionDrsgTextView; //穿衣指数
    private TextView mSuggestionCwTextView; //洗车指数
    private TextView mSuggestionSportTextView; //运动指数
    private TextView mSuggestionUvTextView; //紫外线指数

    private LinearLayout mSuggestionComfLayout;
    private LinearLayout mSuggestionFluLayout;
    private LinearLayout mSuggestionDrsgLayout;
    private LinearLayout mSuggestionCwLayout;
    private LinearLayout mSuggestionSportLayout;
    private LinearLayout mSuggestionUvLayout;

    private RecyclerView mHourlyForecastRecyclerView;   //小时天气
    private RecyclerView mDailyForecastRecyclerView;    //未来几天天气

    private DailyForecastAdapter mDailyForecastAdapter;
    private HourlyForecastAdapter mHourlyForecastAdapter;

    private WeatherInfo mWeatherInfo;
    private List<DailyForecast> mDailyForecastList;
    private List<HourlyForecast> mHourlyForecastsList;

    private boolean isUpdate;
    private boolean weatherUpdated;
    private boolean isFahrenheit = false;   //是否是华氏度

    public static WeatherFragment newInstance(String cityId, boolean updated){
        //Log.i(TAG, "newInstance: is start now!");
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_ID, cityId);
        args.putBoolean(ARG_WEATHER_UPDATED, updated);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isFahrenheit = QueryPreferences.getSettingTemperatureUnit(MyApplication.getContext()).equals("F");
        String cityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);
        weatherUpdated = getArguments().getBoolean(ARG_WEATHER_UPDATED);
        mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfo(cityId);
        mDailyForecastList = WeatherLab.get(getActivity()).getDailyForecastList(mWeatherInfo.getBasicCityId());
        mHourlyForecastsList = WeatherLab.get(getActivity()).getHourlyForecastList(mWeatherInfo.getBasicCityId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        mScrollView = (ScrollView) view.findViewById(R.id.scroll_view);
        mMainWeatherLayout = (RelativeLayout) view.findViewById(R.id.main_weather);
        mTemperatureTextView = (TextView) view.findViewById(R.id.temperature_text_view);
        mAqiTextView = (TextView) view.findViewById(R.id.aqi_text_view);
        mWeatherDescribeTextView = (TextView) view.findViewById(R.id.weather_describe_text_view);
        mTodayForecastWindTextView = (TextView) view.findViewById(R.id.today_forecast_wind);
        mTodayForecastHumidityTextView = (TextView) view.findViewById(R.id.today_forecast_humidity);
        mTodayForecastFlTextView = (TextView) view.findViewById(R.id.today_forecast_fl);
        mTodayForecastAqiTextView = (TextView) view.findViewById(R.id.today_forecast_aqi);
        mTodayForecastPressureTextView = (TextView) view.findViewById(R.id.today_forecast_pressure);
        mTodayForecastUvTextView = (TextView) view.findViewById(R.id.today_forecast_uv);
        mTodayForecastWindDetailTextView = (TextView) view.findViewById(R.id.today_forecast_wind_detail);
        mTodayForecastHumidityDetailTextView = (TextView) view.findViewById(R.id.today_forecast_humidity_detail);
        mTodayForecastFlDetailTextView = (TextView) view.findViewById(R.id.today_forecast_fl_detail);
        mTodayForecastAqiDetailTextView = (TextView) view.findViewById(R.id.today_forecast_aqi_detail);
        mTodayForecastPressureDetailTextView = (TextView) view.findViewById(R.id.today_forecast_pressure_detail);
        mTodayForecastUvDetailTextView = (TextView) view.findViewById(R.id.today_forecast_uv_detail);
        mSuggestionComfTextView = (TextView) view.findViewById(R.id.suggestion_comf);
        mSuggestionFluTextView = (TextView) view.findViewById(R.id.suggestion_flu);
        mSuggestionDrsgTextView = (TextView) view.findViewById(R.id.suggestion_drsg);
        mSuggestionCwTextView = (TextView) view.findViewById(R.id.suggestion_cw);
        mSuggestionSportTextView = (TextView) view.findViewById(R.id.suggestion_sport);
        mSuggestionUvTextView = (TextView) view.findViewById(R.id.suggestion_uv);
        mSuggestionComfLayout = (LinearLayout) view.findViewById(R.id.suggestion_comf_layout);
        mSuggestionFluLayout = (LinearLayout) view.findViewById(R.id.suggestion_flu_layout);
        mSuggestionDrsgLayout = (LinearLayout) view.findViewById(R.id.suggestion_drsg_layout);
        mSuggestionCwLayout = (LinearLayout) view.findViewById(R.id.suggestion_cw_layout);
        mSuggestionSportLayout = (LinearLayout) view.findViewById(R.id.suggestion_sport_layout);
        mSuggestionUvLayout = (LinearLayout) view.findViewById(R.id.suggestion_uv_layout);

        mAqiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AqiActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity()));
            }
        });

        mSuggestionComfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_COMF));
            }
        });
        mSuggestionFluLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_FLU));
            }
        });
        mSuggestionDrsgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_DRSG));
            }
        });
        mSuggestionCwLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_CW));
            }
        });
        mSuggestionSportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_SPORT));
            }
        });
        mSuggestionUvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(), mWeatherInfo.getBasicCity(), ACTION_UV));
            }
        });

        mHourlyForecastRecyclerView = (RecyclerView) view.findViewById(R.id.hourly_forecast_recycler_view);
        mHourlyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mDailyForecastRecyclerView = (RecyclerView) view.findViewById(R.id.daily_forecast_recycler_view);
        mDailyForecastRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        if (mWeatherInfo != null){
            updateWeatherUI();
        }

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new UpdateWeatherTask(mWeatherInfo.getBasicCity()).execute();
            }
        });
        mSwipeRefreshLayout.setDistanceToTriggerSync(300);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!weatherUpdated){
            showSnackbarAlert(getResources().getString(R.string.update_failed));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateWeatherUI() {
        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "font/iconfont.ttf");

        if (isUpdate) {
            mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfo(mWeatherInfo.getBasicCityId());
            mDailyForecastList = WeatherLab.get(getActivity()).getDailyForecastList(mWeatherInfo.getBasicCityId());
        }

        if (isFahrenheit) {
            mTemperatureTextView.setText(WeatherUtil.convertCelsius2Fahrenheit(mWeatherInfo.getNowTmp()));
        } else {
            mTemperatureTextView.setText(mWeatherInfo.getNowTmp());
        }

        mAqiTextView.setTypeface(iconfont);
        mTodayForecastWindTextView.setTypeface(iconfont);
        mTodayForecastHumidityTextView.setTypeface(iconfont);
        mTodayForecastFlTextView.setTypeface(iconfont);
        mTodayForecastAqiTextView.setTypeface(iconfont);
        mTodayForecastPressureTextView.setTypeface(iconfont);
        mTodayForecastUvTextView.setTypeface(iconfont);

        mAqiTextView.setText(getString(R.string.iconfont_aqi) + " " + mWeatherInfo.getAqi() + " " + mWeatherInfo.getAqiQlty());
        mWeatherDescribeTextView.setText(mWeatherInfo.getNowCondTxt());

        mTodayForecastWindTextView.setText(getString(R.string.iconfont_wind) + mWeatherInfo.getNowWindDir());
        mTodayForecastWindDetailTextView.setText(mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
        mTodayForecastHumidityDetailTextView.setText(mWeatherInfo.getNowHum() + getString(R.string.percent_sign));
        mTodayForecastFlDetailTextView.setText(mWeatherInfo.getNowFl() + getString(R.string.celsius_sign));
        mTodayForecastAqiDetailTextView.setText(mWeatherInfo.getAqi() + " " + mWeatherInfo.getAqiQlty());
        mTodayForecastPressureDetailTextView.setText(mWeatherInfo.getNowPres() + getString(R.string.pressure_sign));
        mTodayForecastUvDetailTextView.setText(mWeatherInfo.getDailyForecastUv());

        mSuggestionComfTextView.setText(mWeatherInfo.getSuggestionComfBrf());
        mSuggestionFluTextView.setText(mWeatherInfo.getSuggestionFluBrf());
        mSuggestionDrsgTextView.setText(mWeatherInfo.getSuggestionDrsgBrf());
        mSuggestionCwTextView.setText(mWeatherInfo.getSuggestionCwBrf());
        mSuggestionSportTextView.setText(mWeatherInfo.getSuggestionSportBrf());
        mSuggestionUvTextView.setText(mWeatherInfo.getSuggestionUvBrf());

        mDailyForecastAdapter = new DailyForecastAdapter(getActivity(), mDailyForecastList);
        mDailyForecastAdapter.setCallbacks(this);
        mDailyForecastRecyclerView.setAdapter(mDailyForecastAdapter);

        mHourlyForecastAdapter = new HourlyForecastAdapter(getActivity(), mHourlyForecastsList);
        mHourlyForecastAdapter.setCallbacks(this);
        mHourlyForecastRecyclerView.setAdapter(mHourlyForecastAdapter);

        isUpdate = false;
    }

    @Override
    public boolean isFahrenheit() {
        return isFahrenheit;
    }

    @Override
    public void setFahrenheit(boolean fahrenheit) {
        isFahrenheit = fahrenheit;
    }

    private class UpdateWeatherTask extends AsyncTask<Void, Void, WeatherModel>{
        String mCityName;

        public UpdateWeatherTask(String cityName) {
            this.mCityName = cityName;
        }

        @Override
        protected WeatherModel doInBackground(Void... params) {
            return new HeWeatherFetch().fetchWeatherBean(mCityName);
        }

        @Override
        protected void onPostExecute(WeatherModel weatherModel) {
            if (!WeatherUtil.isNetworkAvailableAndConnected()){
                showSnackbarAlert(getResources().getString(R.string.network_is_not_available));
                return;
            }
            if (weatherModel == null){
                showSnackbarAlert(getResources().getString(R.string.update_failed));
                return;
            }
            WeatherLab.get(getActivity()).addWeatherInfo(weatherModel);
            isUpdate = true;
            weatherUpdated = true;
            if (mSwipeRefreshLayout.isRefreshing()){
                mSwipeRefreshLayout.setRefreshing(false);
            }
            updateWeatherUI();
        }
    }

    public void showSnackbarAlert(String text) {
        Snackbar snackbar = Snackbar.make(mScrollView, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new UpdateWeatherTask(mWeatherInfo.getBasicCity()).execute();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite));
        ColoredSnackbar.alert(snackbar).show();
    }

}
