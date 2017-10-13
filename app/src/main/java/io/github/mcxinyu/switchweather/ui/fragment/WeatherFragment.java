package io.github.mcxinyu.switchweather.ui.fragment;

import android.graphics.Typeface;
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
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.database.dao.CityBeanDao;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherRealTime;
import io.github.mcxinyu.switchweather.ui.activity.AqiActivity;
import io.github.mcxinyu.switchweather.ui.adapter.ForecastAdapter;
import io.github.mcxinyu.switchweather.util.ColoredSnackbar;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import io.github.mcxinyu.switchweather.util.WeatherUtil;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.schedulers.Schedulers;


/**
 * Created by 跃峰 on 2016/8/20.
 */
public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private static final String ARG_WEATHER_CITY_ID = "WeatherFragment";

    @BindView(R.id.center_view)
    TextView mCenterView;
    @BindView(R.id.temperature_text_view)
    TextView mTemperatureTextView;
    @BindView(R.id.aqi_text_view)
    TextView mAqiTextView;
    @BindView(R.id.weather_describe_text_view)
    TextView mWeatherDescribeTextView;
    @BindView(R.id.hourly_forecast_recycler_view)
    RecyclerView mHourlyForecastRecyclerView;
    @BindView(R.id.main_weather)
    RelativeLayout mMainWeather;
    @BindView(R.id.daily_forecast_recycler_view)
    RecyclerView mDailyForecastRecyclerView;
    @BindView(R.id.today_forecast_wind)
    TextView mTodayForecastWind;
    @BindView(R.id.today_forecast_wind_detail)
    TextView mTodayForecastWindDetail;
    @BindView(R.id.today_forecast_humidity)
    TextView mTodayForecastHumidity;
    @BindView(R.id.today_forecast_humidity_detail)
    TextView mTodayForecastHumidityDetail;
    @BindView(R.id.today_forecast_uv)
    TextView mTodayForecastUv;
    @BindView(R.id.today_forecast_uv_detail)
    TextView mTodayForecastUvDetail;
    @BindView(R.id.main_linear_layout)
    LinearLayout mMainLinearLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    Unbinder unbinder;

    private String mCityId;
    private String mCityName;
    private CaiYunWeatherRealTime mWeatherRealTime;
    private CaiYunWeatherForecast mWeatherForecast;

    private boolean isFahrenheit = false;   //是否是华氏度
    // 是否 setUserVisibleHint 的时候刷新了
    private boolean isWeatherInfoUpdateSuccess;

    public static WeatherFragment newInstance(String cityId) {
        //Log.i(TAG, "newInstance: is start now!");
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_ID, cityId);

        WeatherFragment fragment = new WeatherFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);
        mCityName = SwitchWeatherApp.getDaoInstance()
                .getCityBeanDao()
                .queryBuilder()
                .where(CityBeanDao.Properties.CityId.eq(mCityId))
                .unique()
                .getCityName();
        isFahrenheit = QueryPreferences.getSettingTemperatureUnit(getContext()).equals("F");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        initViewClick();

        LinearLayoutManager linearLayoutManagerH = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        linearLayoutManagerH.setSmoothScrollbarEnabled(true);
        linearLayoutManagerH.setAutoMeasureEnabled(true);
        mHourlyForecastRecyclerView.setLayoutManager(linearLayoutManagerH);

        LinearLayoutManager linearLayoutManagerV = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManagerV.setSmoothScrollbarEnabled(true);
        linearLayoutManagerV.setAutoMeasureEnabled(true);
        mDailyForecastRecyclerView.setLayoutManager(linearLayoutManagerV);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getWeatherInfo();
            }
        });
        mSwipeRefreshLayout.setDistanceToTriggerSync(300);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isWeatherInfoUpdateSuccess) {
            getWeatherInfo();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (mSwipeRefreshLayout == null) {
                isWeatherInfoUpdateSuccess = false;
                return;
            }
            getWeatherInfo();
        }
    }

    private void initViewClick() {
        mAqiTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AqiActivity.newIntent(getActivity(), mCityName));
            }
        });

        // mSuggestionComfLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_COMF));
        //     }
        // });
        // mSuggestionFluLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_FLU));
        //     }
        // });
        // mSuggestionDrsgLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_DRSG));
        //     }
        // });
        // mSuggestionCwLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_CW));
        //     }
        // });
        // mSuggestionSportLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_SPORT));
        //     }
        // });
        // mSuggestionUvLayout.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         startActivity(SuggestionActivity.newIntent(getActivity(), mCityName, ACTION_UV));
        //     }
        // });
    }

    private void updateWeatherUI() {
        mMainLinearLayout.setVisibility(View.VISIBLE);

        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "font/iconfont.ttf");

        if (isFahrenheit) {
            mTemperatureTextView.setText(WeatherUtil.convertCelsius2Fahrenheit(mWeatherRealTime.getResult().getTemperature()));
        } else {
            mTemperatureTextView.setText("" + (int) mWeatherRealTime.getResult().getTemperature());
        }

        mAqiTextView.setTypeface(iconfont);
        mTodayForecastWind.setTypeface(iconfont);
        mTodayForecastHumidity.setTypeface(iconfont);
        mTodayForecastUv.setTypeface(iconfont);

        mWeatherDescribeTextView.setText(WeatherUtil.convertSkyConToDesc(mWeatherRealTime.getResult().getSkyCon()));
        mAqiTextView.setText(getString(R.string.iconfont_aqi) + " " + mWeatherRealTime.getResult().getAqi()
                + " " + WeatherUtil.convertAqiToQuality(mWeatherRealTime.getResult().getAqi()));

        mTodayForecastWind.setText(getString(R.string.iconfont_wind) +
                WeatherUtil.convertDirectionToDesc(mWeatherRealTime.getResult().getWind().getDirection()));
        mTodayForecastWindDetail.setText(WeatherUtil.convertSpeedToDesc(mWeatherRealTime.getResult().getWind().getSpeed()));
        mTodayForecastHumidityDetail.setText(mWeatherRealTime.getResult().getHumidity() * 100 + getString(R.string.percent_sign));
        mTodayForecastUvDetail.setText(mWeatherForecast.getResult().getDaily().getUltraviolet().get(0).getIndex());

        mHourlyForecastRecyclerView.setAdapter(
                new ForecastAdapter(getActivity(), mWeatherForecast, ForecastAdapter.VIEW_TYPE_HOURLY));

        mDailyForecastRecyclerView.setAdapter(
                new ForecastAdapter(getActivity(), mWeatherForecast, ForecastAdapter.VIEW_TYPE_DAILY));

        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getWeatherInfo() {
        mSwipeRefreshLayout.setRefreshing(true);

        CityBean cityBean = SwitchWeatherApp.getDaoInstance()
                .getCityBeanDao()
                .queryBuilder()
                .where(CityBeanDao.Properties.CityId.eq(mCityId))
                .unique();

        Observable.zip(WeatherApiHelper.getCaiYunWeatherRealTime(cityBean.getLongitude() + "," + cityBean.getLatitude()),
                WeatherApiHelper.getCaiYunWeatherForecast(cityBean.getLongitude() + "," + cityBean.getLatitude()),
                new Func2<CaiYunWeatherRealTime, CaiYunWeatherForecast, Boolean>() {
                    @Override
                    public Boolean call(CaiYunWeatherRealTime caiYunWeatherRealTime, CaiYunWeatherForecast caiYunWeatherForecast) {
                        mWeatherRealTime = caiYunWeatherRealTime;
                        mWeatherForecast = caiYunWeatherForecast;
                        return caiYunWeatherRealTime != null && caiYunWeatherForecast != null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        if (isWeatherInfoUpdateSuccess)
                            updateWeatherUI();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mSwipeRefreshLayout.setRefreshing(false);
                        showSnackbarAlert(getString(R.string.retry_to_connect));
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        isWeatherInfoUpdateSuccess = aBoolean;
                    }
                });
    }

    public void showSnackbarAlert(String text) {
        Snackbar snackbar = Snackbar.make(mMainLinearLayout, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSwipeRefreshLayout.setRefreshing(true);
                        getWeatherInfo();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite));
        ColoredSnackbar.alert(snackbar).show();
    }
}
