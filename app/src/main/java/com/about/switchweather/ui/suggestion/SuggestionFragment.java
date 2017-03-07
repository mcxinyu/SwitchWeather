package com.about.switchweather.ui.suggestion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.about.switchweather.database.WeatherLab;
import com.about.switchweather.model.WeatherInfo;
import com.about.switchweather.R;

/**
 * Created by huangyuefeng on 2017/3/6.
 */

public class SuggestionFragment extends Fragment {
    public static final String ACTION_COMF = "comf";
    public static final String ACTION_FLU = "flu";
    public static final String ACTION_DRSG = "drsg";
    public static final String ACTION_CW = "cw";
    public static final String ACTION_SPORT = "sport";
    public static final String ACTION_UV = "uv";

    public static final String CITY_NAME = "city_name";
    public static final String ACTION = "action";

    private String mCityName;
    private String mAction;
    private WeatherInfo mWeatherInfo;

    private TextView mSuggestionBrf;
    private TextView mSuggestionTxt;
    private TextView mSuggestionCityName;
    private TextView mSuggestionInfo1;
    private TextView mSuggestionInfo2;
    private TextView mSuggestionInfo3;

    public static SuggestionFragment newInstance(String cityName, String action) {

        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
        args.putString(ACTION, action);

        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityName = getArguments().getString(CITY_NAME);
        mAction = getArguments().getString(ACTION);

        mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfoWithCityName(mCityName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        mSuggestionBrf = (TextView) view.findViewById(R.id.suggestion_brf);
        mSuggestionTxt = (TextView) view.findViewById(R.id.suggestion_txt);
        mSuggestionCityName = (TextView) view.findViewById(R.id.suggestion_city_name);
        mSuggestionInfo1 = (TextView) view.findViewById(R.id.suggestion_info1);
        mSuggestionInfo2 = (TextView) view.findViewById(R.id.suggestion_info2);
        mSuggestionInfo3 = (TextView) view.findViewById(R.id.suggestion_info3);

        initView();
        return view;
    }

    private void initView() {
        mSuggestionCityName.setText(mCityName);
        switch (mAction){
            case ACTION_COMF:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionComfBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionComfTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_hum) + " " + mWeatherInfo.getNowHum() + getString(R.string.percent_sign));
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mWeatherInfo.getDailyForecastTmpMax() + "~" + mWeatherInfo.getDailyForecastTmpMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mWeatherInfo.getNowWindDir() + " " + mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_FLU:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionFluBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionFluTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mWeatherInfo.getNowCondTxt());
                int max = Integer.parseInt(mWeatherInfo.getDailyForecastTmpMax());
                int min = Integer.parseInt(mWeatherInfo.getDailyForecastTmpMin());
                mSuggestionInfo2.setText(getString(R.string.suggestion_tmp_short) + " " + (max - min));
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mWeatherInfo.getNowWindDir() + " " + mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_DRSG:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionDrsgBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionDrsgTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mWeatherInfo.getNowCondTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_wing) + " " + mWeatherInfo.getNowWindDir() + " " + mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
                mSuggestionInfo3.setText("");
                break;
            case ACTION_CW:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionCwBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionCwTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mWeatherInfo.getNowCondTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_wing) + " " + mWeatherInfo.getNowWindDir() + " " + mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
                mSuggestionInfo3.setText("");
                break;
            case ACTION_SPORT:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionSportBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionSportTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mWeatherInfo.getNowCondTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mWeatherInfo.getDailyForecastTmpMax() + "~" + mWeatherInfo.getDailyForecastTmpMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mWeatherInfo.getNowWindDir() + " " + mWeatherInfo.getNowWindSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_UV:
                mSuggestionBrf.setText(mWeatherInfo.getSuggestionUvBrf());
                mSuggestionTxt.setText(mWeatherInfo.getSuggestionUvTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mWeatherInfo.getNowCondTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mWeatherInfo.getDailyForecastTmpMax() + "~" + mWeatherInfo.getDailyForecastTmpMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_sr_ss) + " " + mWeatherInfo.getDailyForecastAstroSr() + getString(R.string.gap) + mWeatherInfo.getDailyForecastAstroSs());
                break;
        }
    }
}
