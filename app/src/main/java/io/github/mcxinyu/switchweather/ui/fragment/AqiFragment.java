package io.github.mcxinyu.switchweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast.ResultBean.HourlyBean.AqiBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.AqiBean.CityBean;
import io.github.mcxinyu.switchweather.util.WeatherUtil;

/**
 * Created by huangyuefeng on 2017/3/6.
 */
public class AqiFragment extends Fragment {
    public static final String HE_WEATHER_MODEL_AQI_BEAN = "he_weather_model_aqi_bean";
    public static final String CAI_YUN_WEATHER_AQI_FORECAST_LIST = "cai_yun_weather_aqi_forecast_list";

    @BindView(R.id.aqi_number_text_view)
    TextView mAqiNumberTextView;
    @BindView(R.id.aqi_qlty_text_view)
    TextView mAqiQltyTextView;
    @BindView(R.id.aqi_forecast)
    TextView mAqiForecast;
    @BindView(R.id.aqi_qlty_forecast_recycler)
    RecyclerView mAqiQltyForecastRecycler;
    @BindView(R.id.aqi_main_polluted)
    TextView mAqiMainPolluted;
    @BindView(R.id.aqi_so2)
    TextView mAqiSo2;
    @BindView(R.id.aqi_o3)
    TextView mAqiO3;
    @BindView(R.id.aqi_co)
    TextView mAqiCo;
    @BindView(R.id.aqi_no2)
    TextView mAqiNo2;
    @BindView(R.id.aqi_pm10)
    TextView mAqiPm10;
    @BindView(R.id.aqi_pm25)
    TextView mAqiPm25;
    @BindView(R.id.aqi_qlty_grid_layout)
    GridLayout mAqiQltyGridLayout;
    @BindView(R.id.aqi_main_layout)
    RelativeLayout mAqiMainLayout;
    @BindView(R.id.aqi_affect)
    TextView mAqiAffect;
    @BindView(R.id.aqi_affect_text)
    TextView mAqiAffectText;
    @BindView(R.id.aqi_suggestion)
    TextView mAqiSuggestion;
    @BindView(R.id.aqi_suggestion_text)
    TextView mAqiSuggestionText;
    @BindView(R.id.activity_aqi)
    LinearLayout mActivityAqi;
    private Unbinder unbinder;

    private CityBean mAqiBean;
    private ArrayList<AqiBean> mAqiBeanList;

    public static AqiFragment newInstance(CityBean aqiBean, ArrayList<AqiBean> aqiBeanList) {

        Bundle args = new Bundle();
        args.putParcelable(HE_WEATHER_MODEL_AQI_BEAN, aqiBean);
        args.putParcelableArrayList(CAI_YUN_WEATHER_AQI_FORECAST_LIST, aqiBeanList);
        AqiFragment fragment = new AqiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAqiBean = getArguments().getParcelable(HE_WEATHER_MODEL_AQI_BEAN);
        mAqiBeanList = getArguments().getParcelableArrayList(CAI_YUN_WEATHER_AQI_FORECAST_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_aqi, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        String aqi = mAqiBeanList.get(0).getValue() + "";
        String aqiQlty = WeatherUtil.convertAqiToQuality(mAqiBeanList.get(0).getValue());
        mAqiNumberTextView.setText(aqi);
        mAqiQltyTextView.setText(aqiQlty);

        switch (aqiQlty) {
            case "优":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffectText.setText(getString(R.string.aqi_affect_excellent));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_excellent));
                break;
            case "良":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiGood));
                mAqiAffectText.setText(getString(R.string.aqi_affect_good));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_good));
                break;
            case "轻度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiLightly));
                mAqiAffectText.setText(getString(R.string.aqi_affect_lightly));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_lightly));
                break;
            case "中度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiModerately));
                mAqiAffectText.setText(getString(R.string.aqi_affect_moderately));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_moderately));
                break;
            case "重度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiHeavily));
                mAqiAffectText.setText(getString(R.string.aqi_affect_heavily));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_heavily));
                break;
            case "严重污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiSeverely));
                mAqiAffectText.setText(getString(R.string.aqi_affect_severely));
                mAqiSuggestionText.setText(getString(R.string.aqi_suggestion_severely));
                break;
            default:
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffectText.setText("N/A");
                mAqiSuggestionText.setText("N/A");
                break;
        }

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mAqiQltyForecastRecycler.setLayoutManager(linearLayoutManager);
        mAqiQltyForecastRecycler.setAdapter(new AqiHourlyAdapter());

        mAqiSo2.setText(mAqiBean.getSo2());
        mAqiO3.setText(mAqiBean.getO3());
        mAqiCo.setText(mAqiBean.getCo());
        mAqiNo2.setText(mAqiBean.getNo2());
        mAqiPm10.setText(mAqiBean.getPm10());
        mAqiPm25.setText(mAqiBean.getPm25());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    class AqiHourlyAdapter extends RecyclerView.Adapter<AqiHourlyAdapter.AqiHourlyHolder> {

        @Override
        public AqiHourlyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new AqiHourlyHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_aqi_hourly_forecast, parent, false));
        }

        @Override
        public void onBindViewHolder(AqiHourlyHolder holder, int position) {
            holder.bindView(mAqiBeanList.get(position));
        }

        @Override
        public int getItemCount() {
            return mAqiBeanList.size();
        }

        class AqiHourlyHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.hourly_text_view)
            TextView mHourlyTextView;
            @BindView(R.id.aqi_text_view)
            TextView mAqiTextView;
            @BindView(R.id.aqi_qlty_text_view)
            TextView mAqiQltyTextView;

            AqiHourlyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            void bindView(AqiBean aqiBean) {
                mHourlyTextView.setText(aqiBean.getDatetime().split(" ")[1]);
                mAqiTextView.setText(aqiBean.getValue() + "");
                mAqiQltyTextView.setText(WeatherUtil.convertAqiToQuality(aqiBean.getValue()));
            }
        }
    }
}
