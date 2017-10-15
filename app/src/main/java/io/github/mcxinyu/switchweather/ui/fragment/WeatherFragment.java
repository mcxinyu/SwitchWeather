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

import java.util.ArrayList;

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
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.AqiBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.NowBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.SuggestionBean;
import io.github.mcxinyu.switchweather.ui.activity.AqiActivity;
import io.github.mcxinyu.switchweather.ui.activity.SuggestionActivity;
import io.github.mcxinyu.switchweather.ui.adapter.ForecastAdapter;
import io.github.mcxinyu.switchweather.util.ColoredSnackbar;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import io.github.mcxinyu.switchweather.util.WeatherUtil;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func3;
import rx.schedulers.Schedulers;

import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_COMF;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_CW;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_DRSG;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_FLU;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_SPORT;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION_UV;


/**
 * Created by 跃峰 on 2016/8/20.
 */
public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private static final String ARG_WEATHER_CITY_ID = "WeatherFragment";

    @BindView(R.id.center_view)
    TextView mCenterView;
    @BindView(R.id.main_linear_layout)
    LinearLayout mMainLinearLayout;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
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
    @BindView(R.id.today_forecast_fl)
    TextView mTodayForecastFl;
    @BindView(R.id.today_forecast_fl_detail)
    TextView mTodayForecastFlDetail;
    @BindView(R.id.today_forecast_visibility)
    TextView mTodayForecastVisibility;
    @BindView(R.id.today_forecast_visibility_detail)
    TextView mTodayForecastVisibilityDetail;
    @BindView(R.id.today_forecast_pressure)
    TextView mTodayForecastPressure;
    @BindView(R.id.today_forecast_pressure_detail)
    TextView mTodayForecastPressureDetail;
    @BindView(R.id.today_forecast_uv)
    TextView mTodayForecastUv;
    @BindView(R.id.today_forecast_uv_detail)
    TextView mTodayForecastUvDetail;
    @BindView(R.id.suggestion_comf)
    TextView mSuggestionComf;
    @BindView(R.id.suggestion_comf_layout)
    LinearLayout mSuggestionComfLayout;
    @BindView(R.id.suggestion_flu)
    TextView mSuggestionFlu;
    @BindView(R.id.suggestion_flu_layout)
    LinearLayout mSuggestionFluLayout;
    @BindView(R.id.suggestion_drsg)
    TextView mSuggestionDrsg;
    @BindView(R.id.suggestion_drsg_layout)
    LinearLayout mSuggestionDrsgLayout;
    @BindView(R.id.suggestion_cw)
    TextView mSuggestionCw;
    @BindView(R.id.suggestion_cw_layout)
    LinearLayout mSuggestionCwLayout;
    @BindView(R.id.suggestion_sport)
    TextView mSuggestionSport;
    @BindView(R.id.suggestion_sport_layout)
    LinearLayout mSuggestionSportLayout;
    @BindView(R.id.suggestion_uv)
    TextView mSuggestionUv;
    @BindView(R.id.suggestion_uv_layout)
    LinearLayout mSuggestionUvLayout;
    private Unbinder unbinder;

    private String mCityId;
    private String mCityName;
    private CaiYunWeatherRealTime mCaiYunWeatherRealTime;
    private CaiYunWeatherForecast mCaiYunWeatherForecast;
    private HeWeatherModel mHeWeatherModel;

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
                AqiBean.CityBean cityBean = null;
                if (null != mHeWeatherModel.getHeWeather5().get(0).getAqi()) {
                    cityBean = mHeWeatherModel.getHeWeather5().get(0).getAqi().getCity();
                }
                startActivity(AqiActivity.newIntent(getActivity(), cityBean,
                        new ArrayList<>(mCaiYunWeatherForecast.getResult().getHourly().getAqi())));
            }
        });

        mSuggestionComfLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_COMF));
            }
        });
        mSuggestionFluLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_FLU));
            }
        });
        mSuggestionDrsgLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_DRSG));
            }
        });
        mSuggestionCwLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_CW));
            }
        });
        mSuggestionSportLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_SPORT));
            }
        });
        mSuggestionUvLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SuggestionActivity.newIntent(getActivity(),
                        mCityName, mHeWeatherModel.getHeWeather5().get(0).getSuggestion(),
                        mHeWeatherModel.getHeWeather5().get(0).getNow(),
                        mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0),
                        ACTION_UV));
            }
        });
    }

    private void updateWeatherUI() {
        mMainLinearLayout.setVisibility(View.VISIBLE);

        Typeface iconfont = Typeface.createFromAsset(getActivity().getAssets(), "font/iconfont.ttf");

        if (isFahrenheit) {
            mTemperatureTextView.setText(WeatherUtil.convertCelsius2Fahrenheit(mCaiYunWeatherRealTime.getResult().getTemperature()));
        } else {
            mTemperatureTextView.setText("" + (int) mCaiYunWeatherRealTime.getResult().getTemperature());
        }

        mAqiTextView.setTypeface(iconfont);
        mTodayForecastWind.setTypeface(iconfont);
        mTodayForecastHumidity.setTypeface(iconfont);
        mTodayForecastFl.setTypeface(iconfont);
        mTodayForecastVisibility.setTypeface(iconfont);
        mTodayForecastPressure.setTypeface(iconfont);
        mTodayForecastUv.setTypeface(iconfont);

        mWeatherDescribeTextView.setText(WeatherUtil.convertSkyConToDesc(mCaiYunWeatherRealTime.getResult().getSkyCon()));
        mAqiTextView.setText(getString(R.string.iconfont_aqi) + " " + mCaiYunWeatherRealTime.getResult().getAqi()
                + " " + WeatherUtil.convertAqiToQuality(mCaiYunWeatherRealTime.getResult().getAqi()));

        mTodayForecastWind.setText(getString(R.string.iconfont_wind) +
                WeatherUtil.convertDirectionToDesc(mCaiYunWeatherRealTime.getResult().getWind().getDirection()));
        mTodayForecastWindDetail.setText(WeatherUtil.convertSpeedToDesc(mCaiYunWeatherRealTime.getResult().getWind().getSpeed()));
        mTodayForecastHumidityDetail.setText(mCaiYunWeatherRealTime.getResult().getHumidity() * 100 + getString(R.string.percent_sign));
        mTodayForecastUvDetail.setText(mCaiYunWeatherForecast.getResult().getDaily().getUltraviolet().get(0).getIndex());

        NowBean nowBean = mHeWeatherModel.getHeWeather5().get(0).getNow();
        mTodayForecastWind.setText(getString(R.string.iconfont_wind) + nowBean.getWind().getDir());
        mTodayForecastWindDetail.setText(nowBean.getWind().getSc() + getString(R.string.wind_class_text));
        mTodayForecastHumidityDetail.setText(nowBean.getHum() + getString(R.string.percent_sign));
        mTodayForecastFlDetail.setText(nowBean.getFl() + getString(R.string.celsius_sign));
        mTodayForecastVisibilityDetail.setText(nowBean.getVis() + " km");
        mTodayForecastPressureDetail.setText(nowBean.getPres() + getString(R.string.pressure_sign));
        mTodayForecastUvDetail.setText(mHeWeatherModel.getHeWeather5().get(0).getDailyForecast().get(0).getUv());

        mHourlyForecastRecyclerView.setAdapter(
                new ForecastAdapter(getActivity(), mCaiYunWeatherForecast, ForecastAdapter.VIEW_TYPE_HOURLY));

        mDailyForecastRecyclerView.setAdapter(
                new ForecastAdapter(getActivity(), mCaiYunWeatherForecast, ForecastAdapter.VIEW_TYPE_DAILY));

        SuggestionBean suggestionBean = mHeWeatherModel.getHeWeather5().get(0).getSuggestion();
        mSuggestionComf.setText(suggestionBean.getComf().getBrf());
        mSuggestionFlu.setText(suggestionBean.getFlu().getBrf());
        mSuggestionDrsg.setText(suggestionBean.getDrsg().getBrf());
        mSuggestionCw.setText(suggestionBean.getCw().getBrf());
        mSuggestionSport.setText(suggestionBean.getSport().getBrf());
        mSuggestionUv.setText(suggestionBean.getUv().getBrf());

        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setEnabled(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getWeatherInfo() {
        if (!mSwipeRefreshLayout.isEnabled()) {
            return;
        }
        mSwipeRefreshLayout.setRefreshing(true);

        CityBean cityBean = SwitchWeatherApp.getDaoInstance()
                .getCityBeanDao()
                .queryBuilder()
                .where(CityBeanDao.Properties.CityId.eq(mCityId))
                .unique();

        Observable.zip(WeatherApiHelper.getCaiYunWeatherRealTime(cityBean.getLongitude() + "," + cityBean.getLatitude()),
                WeatherApiHelper.getCaiYunWeatherForecast(cityBean.getLongitude() + "," + cityBean.getLatitude()),
                WeatherApiHelper.getHeWeatherInfo(cityBean.getLongitude() + "," + cityBean.getLatitude()),
                new Func3<CaiYunWeatherRealTime, CaiYunWeatherForecast, HeWeatherModel, Boolean>() {
                    @Override
                    public Boolean call(CaiYunWeatherRealTime caiYunWeatherRealTime,
                                        CaiYunWeatherForecast caiYunWeatherForecast,
                                        HeWeatherModel heWeatherModel) {
                        mCaiYunWeatherRealTime = caiYunWeatherRealTime;
                        mCaiYunWeatherForecast = caiYunWeatherForecast;
                        mHeWeatherModel = heWeatherModel;
                        return caiYunWeatherRealTime != null && caiYunWeatherForecast != null && heWeatherModel != null;
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
                           }
                );
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
