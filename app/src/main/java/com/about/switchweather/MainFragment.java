package com.about.switchweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Util.HeWeatherFetch;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class MainFragment extends Fragment {
    private static final String TAG = "MainFragment";

    private TextView mCityNameTextView;
    private TextView mTemperatureTextView;
    private TextView mUpdateTimeTextView;
    private TextView mMaxTemperatureTextView;
    private TextView mMinTemperatureTextView;
    private TextView mWeatherDescribeTextView;
    private ImageView mWeatherIconImageView;

    private WeatherBean mWeatherBean;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchWeather("深圳").execute();
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
        mUpdateTimeTextView.setText(mWeatherBean.getBasic().getUpdate().getLoc().replace(" ", "\n") + "更新");
        mMaxTemperatureTextView.setText(mWeatherBean.getDaily_forecast().get(0).getTmp().getMax() + "°");
        mMinTemperatureTextView.setText(mWeatherBean.getDaily_forecast().get(0).getTmp().getMin() + "°");
        mWeatherDescribeTextView.setText(mWeatherBean.getNow().getCond().getTxt());
    }

    private class FetchWeather extends AsyncTask<Void, Void, WeatherBean> {
        String mCityName;

        public FetchWeather(String cityName) {
            this.mCityName = cityName;
        }

        @Override
        protected WeatherBean doInBackground(Void... params) {
            return new HeWeatherFetch().fetchWeatherInfo(mCityName);
        }

        @Override
        protected void onPostExecute(WeatherBean weatherBean) {
            mWeatherBean = weatherBean;
            updateWeatherInfo();
        }
    }
}
