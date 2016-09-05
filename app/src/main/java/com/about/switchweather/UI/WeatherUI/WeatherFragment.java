package com.about.switchweather.UI.WeatherUI;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.about.switchweather.Model.DailyForecast;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.R;
import com.about.switchweather.Util.*;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public class WeatherFragment extends Fragment {
    private static final String TAG = "WeatherFragment";
    private static final String ARG_WEATHER_CITY_ID = "WeatherFragment";
    private static final String ARG_WEATHER_UPDATED = "Weather_updated";

    private TextView mCityNameTextView;
    private TextView mTemperatureTextView;
    private TextView mUpdateTimeTextView;
    private TextView mMaxTemperatureTextView;
    private TextView mMinTemperatureTextView;
    private TextView mWeatherDescribeTextView;
    private ImageView mWeatherIconImageView;
    private RecyclerView mRecyclerView;
    private DailyForecastAdapter mAdapter;
    private View mWeatherView;

    private WeatherInfo mWeatherInfo;
    private List<DailyForecast> mDailyForecastList;

    private boolean isUpdate;
    private boolean weatherUpdated;

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

        String cityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);
        weatherUpdated = getArguments().getBoolean(ARG_WEATHER_UPDATED);
        mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfoWithCityId(cityId);
        mDailyForecastList = WeatherLab.get(getActivity()).getDailyForecastList(mWeatherInfo.getBasicCityId());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        mWeatherView = view.findViewById(R.id.weather_view);

        mCityNameTextView = (TextView) view.findViewById(R.id.city_name_text_view);
        mTemperatureTextView = (TextView) view.findViewById(R.id.temperature_text_view);
        mUpdateTimeTextView = (TextView) view.findViewById(R.id.update_time_text_view);
        mMaxTemperatureTextView = (TextView) view.findViewById(R.id.max_temperature_text_view);
        mMinTemperatureTextView = (TextView) view.findViewById(R.id.min_temperature_text_view);
        mWeatherDescribeTextView = (TextView) view.findViewById(R.id.weather_describe_text_view);
        mWeatherIconImageView = (ImageView) view.findViewById(R.id.weather_image_view);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.daily_forecast_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        if (!isScreenLandscape()){
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        }
        mRecyclerView.setLayoutManager(layoutManager);

        if (mWeatherInfo != null){
            updateWeatherUI();
        }

        mUpdateTimeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUpdateTimeTextView.setText(getResources().getString(R.string.update_in_progress));
                new UpdateWeather(mWeatherInfo.getBasicCity()).execute();
            }
        });

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
        if (isUpdate) {
            mWeatherInfo = WeatherLab.get(getActivity()).getWeatherInfoWithCityId(mWeatherInfo.getBasicCityId());
            mDailyForecastList = WeatherLab.get(getActivity()).getDailyForecastList(mWeatherInfo.getBasicCityId());
        }

        mCityNameTextView.setText(mWeatherInfo.getBasicCity());
        mTemperatureTextView.setText(mWeatherInfo.getNowTmp() + "°");
        mUpdateTimeTextView.setText(TimeUtil.getDIYTime(MyApplication.getContext(), mWeatherInfo.getBasicUpdateLoc(), new SimpleDateFormat("yyyy-MM-dd HH:mm")) + getResources().getString(R.string.update_text));
        mMaxTemperatureTextView.setText(mWeatherInfo.getDfTmpMax() + "°");
        mMinTemperatureTextView.setText(mWeatherInfo.getDfTmpMin() + "°");
        mWeatherDescribeTextView.setText(mWeatherInfo.getNowCondTxt());
        mWeatherIconImageView.setImageDrawable(WeatherUtil.convertIconToRes(getActivity(), mWeatherInfo.getNowCondCode()));

        mAdapter = new DailyForecastAdapter();
        mRecyclerView.setAdapter(mAdapter);

        isUpdate = false;
    }

    private class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastHolder> {

        @Override
        public DailyForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_daily_forecast, parent, false);
            return new DailyForecastHolder(view);
        }

        @Override
        public void onBindViewHolder(DailyForecastHolder holder, int position) {
            DailyForecast dailyForecast = mDailyForecastList.get(position);
            holder.bindDailyForecast(dailyForecast);
        }

        @Override
        public int getItemCount() {
            return mDailyForecastList.size();
        }
    }

    private class DailyForecastHolder extends RecyclerView.ViewHolder {
        private DailyForecast mDailyForecast;
        private TextView mDailyForecastDateTextView;
        private TextView mDailyForecastWeeklyTextView;
        private ImageView mDailyForecastIconImageView;
        private TextView mDailyForecastTemperatureTextView;
        private TextView mDailyForecastWindTextView;

        public DailyForecastHolder(View itemView) {
            super(itemView);
            mDailyForecastWeeklyTextView = (TextView) itemView.findViewById(R.id.daily_forecast_weekly_text_view);
            mDailyForecastDateTextView = (TextView) itemView.findViewById(R.id.daily_forecast_date_text_view);
            mDailyForecastIconImageView = (ImageView) itemView.findViewById(R.id.daily_forecast_icon_image_view );
            mDailyForecastTemperatureTextView = (TextView) itemView.findViewById(R.id.daily_forecast_temperature_text_view);
            mDailyForecastWindTextView = (TextView) itemView.findViewById(R.id.daily_forecast_wind_text_view);
        }

        public void bindDailyForecast(DailyForecast dailyForecast){
            mDailyForecast = dailyForecast;
            mDailyForecastWeeklyTextView.setText(TimeUtil.getNear3Weekday(MyApplication.getContext(), mDailyForecast.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastDateTextView.setText(TimeUtil.getDate(mDailyForecast.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastIconImageView.setImageDrawable(WeatherUtil.convertIconToRes(getActivity(), mDailyForecast.getCondCodeD()));
            mDailyForecastTemperatureTextView.setText(mDailyForecast.getTmpMin() + "°-" + mDailyForecast.getTmpMax() + "°");
            mDailyForecastWindTextView.setText(mDailyForecast.getCondTxtD());
        }
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
            if (!isNetworkAvailableAndConnected()){
                showSnackbarAlert(getResources().getString(R.string.network_is_not_available));
                return;
            }
            if (weatherBean == null){
                showSnackbarAlert(getResources().getString(R.string.update_failed));
                return;
            }
            WeatherLab.get(getActivity()).updateWeatherInfo(weatherBean);
            WeatherLab.get(getActivity()).updateDailyForecastList(weatherBean);
            isUpdate = true;
            weatherUpdated = true;
            updateWeatherUI();
        }
    }

    private boolean isScreenLandscape() {
        //获取设置的配置信息
        Configuration mConfiguration = this.getResources().getConfiguration();
        //获取屏幕方向
        int ori = mConfiguration.orientation ;

        if(ori == mConfiguration.ORIENTATION_LANDSCAPE){
            //横屏
            return true;
        }else if(ori == mConfiguration.ORIENTATION_PORTRAIT){
            //竖屏
            return false;
        }
        return false;
    }

    private boolean isNetworkAvailableAndConnected(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void showSnackbarAlert(String text) {
        Snackbar snackbar = Snackbar.make(mWeatherView, text, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new UpdateWeather(mWeatherInfo.getBasicCity()).execute();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite));
        ColoredSnackbar.alert(snackbar).show();
    }
}
