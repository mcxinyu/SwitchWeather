package com.about.switchweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.about.switchweather.Model.WeatherBean;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private static final String ARG_WEATHER_BEAN = "MainFragment";

    private TextView mCityNameTextView;
    private TextView mTemperatureTextView;
    private TextView mUpdateTimeTextView;
    private TextView mMaxTemperatureTextView;
    private TextView mMinTemperatureTextView;
    private TextView mWeatherDescribeTextView;
    private ImageView mWeatherIconImageView;

    private WeatherBean mWeatherBean;

    public static MainFragment newInstance(WeatherBean weatherBean){
        Log.i(TAG, "newInstance: is start now!");
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_BEAN, weatherBean);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mWeatherBean = (WeatherBean) getArguments().getSerializable(ARG_WEATHER_BEAN);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mCityNameTextView = (TextView) view.findViewById(R.id.city_name_text_view);
        mTemperatureTextView = (TextView) view.findViewById(R.id.temperature_text_view);
        mUpdateTimeTextView = (TextView) view.findViewById(R.id.update_time_text_view);
        mMaxTemperatureTextView = (TextView) view.findViewById(R.id.max_temperature_text_view);
        mMinTemperatureTextView = (TextView) view.findViewById(R.id.min_temperature_text_view);
        mWeatherDescribeTextView = (TextView) view.findViewById(R.id.weather_describe_text_view);
        mWeatherIconImageView = (ImageView) view.findViewById(R.id.weather_image_view);

        if (mWeatherBean != null){
            updateWeatherInfo();
        }

        return view;
    }

    private void updateWeatherInfo() {
        mCityNameTextView.setText(mWeatherBean.getBasic().getCity());
        mTemperatureTextView.setText(mWeatherBean.getNow().getTmp() + "°");
        mUpdateTimeTextView.setText(mWeatherBean.getBasic().getUpdate().getLoc().replace(" ", "\n") + " 更新");
        mMaxTemperatureTextView.setText(mWeatherBean.getDaily_forecast().get(0).getTmp().getMax() + "°");
        mMinTemperatureTextView.setText(mWeatherBean.getDaily_forecast().get(0).getTmp().getMin() + "°");
        mWeatherDescribeTextView.setText(mWeatherBean.getNow().getCond().getTxt());
    }
}
