package com.about.switchweather.UI.AqiUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.about.switchweather.DataBase.WeatherLab;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.R;

/**
 * Created by huangyuefeng on 2017/3/6.
 */

public class AqiFragment extends Fragment {
    public static final String CITY_NAME = "city_name";

    private String aqi;
    private String aqiQlty;
    private String aqiSo2;
    private String aqiO3;
    private String aqiCo;
    private String aqiNo2;
    private String aqiPm10;
    private String aqiPm25;

    private TextView mAqiNumberTextView;
    private TextView mAqiQltyTextView;
    private TextView mAqiSo2TextView;
    private TextView mAqiO3TextView;
    private TextView mAqiCoTextView;
    private TextView mAqiNo2TextView;
    private TextView mAqiPm10TextView;
    private TextView mAqiPm25TextView;
    private TextView mAqiAffectTextView;
    private TextView mAqiSuggestionTextView;


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
        WeatherInfo weatherInfo = WeatherLab.get(getActivity()).getWeatherInfoWithCityName(cityName);

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

        mAqiNumberTextView = (TextView) view.findViewById(R.id.aqi_number_text_view);
        mAqiQltyTextView = (TextView) view.findViewById(R.id.aqi_qlty_text_view);
        mAqiSo2TextView = (TextView) view.findViewById(R.id.aqi_so2);
        mAqiO3TextView = (TextView) view.findViewById(R.id.aqi_o3);
        mAqiCoTextView = (TextView) view.findViewById(R.id.aqi_co);
        mAqiNo2TextView = (TextView) view.findViewById(R.id.aqi_no2);
        mAqiPm10TextView = (TextView) view.findViewById(R.id.aqi_pm10);
        mAqiPm25TextView = (TextView) view.findViewById(R.id.aqi_pm25);
        mAqiAffectTextView = (TextView) view.findViewById(R.id.aqi_affect_text);
        mAqiSuggestionTextView = (TextView) view.findViewById(R.id.aqi_suggestion_text);

        initView();

        return view;
    }

    private void initView() {
        mAqiNumberTextView.setText(aqi);
        mAqiQltyTextView.setText(aqiQlty);
        mAqiSo2TextView.setText(aqiSo2);
        mAqiO3TextView.setText(aqiO3);
        mAqiCoTextView.setText(aqiCo);
        mAqiNo2TextView.setText(aqiNo2);
        mAqiPm10TextView.setText(aqiPm10);
        mAqiPm25TextView.setText(aqiPm25);

        switch (aqiQlty){
            case "优":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_excellent));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_excellent));
                break;
            case "良":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiGood));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_good));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_good));
                break;
            case "轻度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiLightly));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_lightly));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_lightly));
                break;
            case "中度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiModerately));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_moderately));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_moderately));
                break;
            case "重度污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiHeavily));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_heavily));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_heavily));
                break;
            case "严重污染":
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiSeverely));
                mAqiAffectTextView.setText(getString(R.string.aqi_affect_severely));
                mAqiSuggestionTextView.setText(getString(R.string.aqi_suggestion_severely));
                break;
            default:
                mAqiQltyTextView.setBackgroundColor(getResources().getColor(R.color.colorAqiExcellent));
                mAqiAffectTextView.setText("");
                mAqiSuggestionTextView.setText("");
                break;
        }
    }
}
