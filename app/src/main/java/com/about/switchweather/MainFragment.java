package com.about.switchweather;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
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
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";
    private static final String ARG_WEATHER_BEAN_ID = "MainFragment";

    private TextView mCityNameTextView;
    private TextView mTemperatureTextView;
    private TextView mUpdateTimeTextView;
    private TextView mMaxTemperatureTextView;
    private TextView mMinTemperatureTextView;
    private TextView mWeatherDescribeTextView;
    private ImageView mWeatherIconImageView;

    private WeatherInfo mWeatherInfo;

    private boolean isUpdate;

    public static MainFragment newInstance(String id){
        Log.i(TAG, "newInstance: is start now!");
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_BEAN_ID, id);

        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String id = (String) getArguments().getSerializable(ARG_WEATHER_BEAN_ID);
        mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfo(id);
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

        if (mWeatherInfo != null){
            updateWeatherInfo();
        }

        mUpdateTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateWeather(mWeatherInfo.getBasicCity()).execute();
            }
        });

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void updateWeatherInfo() {
        if (isUpdate) {
            mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfo(mWeatherInfo.getBasicCityId());
        }

        mCityNameTextView.setText(mWeatherInfo.getBasicCity());
        mTemperatureTextView.setText(mWeatherInfo.getNowTmp() + "°");
        mUpdateTimeTextView.setText(mWeatherInfo.getBasicUpdateLoc().replace(" ", "\n") + " 更新");
        mMaxTemperatureTextView.setText(mWeatherInfo.getDfTmpMax() + "°");
        mMinTemperatureTextView.setText(mWeatherInfo.getDfTmpMin() + "°");
        mWeatherDescribeTextView.setText(mWeatherInfo.getNowCondTxt());
        mWeatherIconImageView.setImageDrawable(convertIconToRes(getActivity(), mWeatherInfo.getNowCondCode()));

        isUpdate = false;
    }

    private class UpdateWeather extends AsyncTask<Void, Void, WeatherBean>{
        String mCityName;

        public UpdateWeather(String cityName) {
            this.mCityName = cityName;
        }

        @Override
        protected WeatherBean doInBackground(Void... params) {
            return new HeWeatherFetch().fetchWeatherBean(mCityName);
        }

        @Override
        protected void onPostExecute(WeatherBean weatherBean) {
            WeatherLab.get(getActivity()).updateWeatherInfo(weatherBean);
            isUpdate = true;
            updateWeatherInfo();
        }
    }

    @SuppressWarnings("deprecation")
    private static Drawable convertIconToRes(Context context, String code){
        switch (code){
            case "100":
                return context.getResources().getDrawable(R.drawable.weather_icon_100);
            case "101":
                return context.getResources().getDrawable(R.drawable.weather_icon_101);
            case "102":
                return context.getResources().getDrawable(R.drawable.weather_icon_102);
            case "103":
                return context.getResources().getDrawable(R.drawable.weather_icon_103);
            case "104":
                return context.getResources().getDrawable(R.drawable.weather_icon_104);
            case "200":
                return context.getResources().getDrawable(R.drawable.weather_icon_200);
            case "201":
                return context.getResources().getDrawable(R.drawable.weather_icon_201);
            case "202":
                return context.getResources().getDrawable(R.drawable.weather_icon_202);
            case "203":
                return context.getResources().getDrawable(R.drawable.weather_icon_203);
            case "204":
                return context.getResources().getDrawable(R.drawable.weather_icon_204);
            case "205":
                return context.getResources().getDrawable(R.drawable.weather_icon_205);
            case "206":
                return context.getResources().getDrawable(R.drawable.weather_icon_206);
            case "207":
                return context.getResources().getDrawable(R.drawable.weather_icon_207);
            case "208":
                return context.getResources().getDrawable(R.drawable.weather_icon_208);
            case "209":
                return context.getResources().getDrawable(R.drawable.weather_icon_209);
            case "210":
                return context.getResources().getDrawable(R.drawable.weather_icon_210);
            case "211":
                return context.getResources().getDrawable(R.drawable.weather_icon_211);
            case "212":
                return context.getResources().getDrawable(R.drawable.weather_icon_212);
            case "213":
                return context.getResources().getDrawable(R.drawable.weather_icon_213);
            case "300":
                return context.getResources().getDrawable(R.drawable.weather_icon_300);
            case "301":
                return context.getResources().getDrawable(R.drawable.weather_icon_301);
            case "302":
                return context.getResources().getDrawable(R.drawable.weather_icon_302);
            case "303":
                return context.getResources().getDrawable(R.drawable.weather_icon_303);
            case "304":
                return context.getResources().getDrawable(R.drawable.weather_icon_304);
            case "305":
                return context.getResources().getDrawable(R.drawable.weather_icon_305);
            case "306":
                return context.getResources().getDrawable(R.drawable.weather_icon_306);
            case "307":
                return context.getResources().getDrawable(R.drawable.weather_icon_307);
            case "308":
                return context.getResources().getDrawable(R.drawable.weather_icon_308);
            case "309":
                return context.getResources().getDrawable(R.drawable.weather_icon_309);
            case "310":
                return context.getResources().getDrawable(R.drawable.weather_icon_310);
            case "311":
                return context.getResources().getDrawable(R.drawable.weather_icon_311);
            case "312":
                return context.getResources().getDrawable(R.drawable.weather_icon_312);
            case "313":
                return context.getResources().getDrawable(R.drawable.weather_icon_313);
            case "400":
                return context.getResources().getDrawable(R.drawable.weather_icon_400);
            case "401":
                return context.getResources().getDrawable(R.drawable.weather_icon_401);
            case "402":
                return context.getResources().getDrawable(R.drawable.weather_icon_402);
            case "403":
                return context.getResources().getDrawable(R.drawable.weather_icon_403);
            case "404":
                return context.getResources().getDrawable(R.drawable.weather_icon_404);
            case "405":
                return context.getResources().getDrawable(R.drawable.weather_icon_405);
            case "406":
                return context.getResources().getDrawable(R.drawable.weather_icon_406);
            case "407":
                return context.getResources().getDrawable(R.drawable.weather_icon_407);
            case "500":
                return context.getResources().getDrawable(R.drawable.weather_icon_500);
            case "501":
                return context.getResources().getDrawable(R.drawable.weather_icon_501);
            case "502":
                return context.getResources().getDrawable(R.drawable.weather_icon_502);
            case "503":
                return context.getResources().getDrawable(R.drawable.weather_icon_503);
            case "504":
                return context.getResources().getDrawable(R.drawable.weather_icon_504);
            case "506":
                return context.getResources().getDrawable(R.drawable.weather_icon_506);
            case "507":
                return context.getResources().getDrawable(R.drawable.weather_icon_507);
            case "508":
                return context.getResources().getDrawable(R.drawable.weather_icon_508);
            case "900":
                return context.getResources().getDrawable(R.drawable.weather_icon_900);
            case "901":
                return context.getResources().getDrawable(R.drawable.weather_icon_901);
            case "999":
                return context.getResources().getDrawable(R.drawable.weather_icon_999);
            default:
                return context.getResources().getDrawable(R.drawable.weather_icon_999);
        }
    }
}
