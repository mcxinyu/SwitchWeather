package io.github.mcxinyu.switchweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.database.WeatherDatabaseLab;
import io.github.mcxinyu.switchweather.model.WeatherInfo;

/**
 * Created by huangyuefeng on 2017/3/6.
 */

public class AqiFragment extends Fragment {
    public static final String CITY_NAME = "city_name";
    @BindView(R.id.aqi_number_text_view)
    TextView mAqiNumber;
    @BindView(R.id.aqi_qlty_text_view)
    TextView mAqiQlty;
    @BindView(R.id.aqi_main_layout)
    RelativeLayout mAqiMainLayout;
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
    @BindView(R.id.aqi_affect)
    TextView mAqiAffect;
    @BindView(R.id.aqi_affect_text)
    TextView mAqiAffectText;
    @BindView(R.id.aqi_suggestion)
    TextView mAqiSuggestion;
    @BindView(R.id.aqi_suggestion_text)
    TextView mAqiSuggestionText;
    @BindView(R.id.activity_aqi)
    RelativeLayout mActivityAqi;
    Unbinder unbinder;

    private String aqi;
    private String aqiQlty;
    private String aqiSo2;
    private String aqiO3;
    private String aqiCo;
    private String aqiNo2;
    private String aqiPm10;
    private String aqiPm25;

    public static AqiFragment newInstance(String cityName) {

        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);

        AqiFragment fragment = new AqiFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String cityName = getArguments().getString(CITY_NAME);
        WeatherInfo weatherInfo = WeatherDatabaseLab.get(getActivity()).getWeatherInfoWithCityName(cityName);

        aqi = weatherInfo.getAqi();
        aqiQlty = weatherInfo.getAqiQlty();
        aqiSo2 = weatherInfo.getAqiSo2();
        aqiO3 = weatherInfo.getAqiO3();
        aqiCo = weatherInfo.getAqiCo();
        aqiNo2 = weatherInfo.getAqiNo2();
        aqiPm10 = weatherInfo.getAqiPm10();
        aqiPm25 = weatherInfo.getAqiPm25();
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
        mAqiNumber.setText(aqi);
        mAqiQlty.setText(aqiQlty);
        mAqiSo2.setText(aqiSo2);
        mAqiO3.setText(aqiO3);
        mAqiCo.setText(aqiCo);
        mAqiNo2.setText(aqiNo2);
        mAqiPm10.setText(aqiPm10);
        mAqiPm25.setText(aqiPm25);

        switch (aqiQlty) {
            case "优":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffect.setText(getString(R.string.aqi_affect_excellent));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_excellent));
                break;
            case "良":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiGood));
                mAqiAffect.setText(getString(R.string.aqi_affect_good));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_good));
                break;
            case "轻度污染":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiLightly));
                mAqiAffect.setText(getString(R.string.aqi_affect_lightly));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_lightly));
                break;
            case "中度污染":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiModerately));
                mAqiAffect.setText(getString(R.string.aqi_affect_moderately));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_moderately));
                break;
            case "重度污染":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiHeavily));
                mAqiAffect.setText(getString(R.string.aqi_affect_heavily));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_heavily));
                break;
            case "严重污染":
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiSeverely));
                mAqiAffect.setText(getString(R.string.aqi_affect_severely));
                mAqiSuggestion.setText(getString(R.string.aqi_suggestion_severely));
                break;
            default:
                mAqiQlty.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffect.setText("");
                mAqiSuggestion.setText("");
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
