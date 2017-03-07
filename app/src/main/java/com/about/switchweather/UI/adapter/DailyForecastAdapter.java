package com.about.switchweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.about.switchweather.model.DailyForecast;
import com.about.switchweather.R;
import com.about.switchweather.ui.MyApplication;
import com.about.switchweather.util.TimeUtil;
import com.about.switchweather.util.WeatherUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by huangyuefeng on 2017/3/2.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.DailyForecastHolder> {
    private Context mContext;
    private List<DailyForecast> mData;
    private LayoutInflater inflater;
    private Callbacks mCallbacks;

    public DailyForecastAdapter(Context context, List<DailyForecast> data) {
        mContext = context;
        mData = data;
        inflater = LayoutInflater.from(mContext);

        // try {
        //     mCallbacks = (DailyForecastAdapter.Callbacks) mContext;
        // } catch (ClassCastException e) {
        //     throw new ClassCastException("Hosting Activity must implement Interface");
        // }
    }

    @Override
    public DailyForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_daily_forecast, parent, false);
        return new DailyForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(DailyForecastHolder holder, int position) {
        holder.bindDailyForecast(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class DailyForecastHolder extends RecyclerView.ViewHolder {
        private DailyForecast mDailyForecast;
        private TextView mDailyForecastDateTextView;
        private TextView mDailyForecastWeeklyTextView;
        private ImageView mDailyForecastIconImageView;
        private TextView mDailyForecastWindTextView;
        private TextView mDailyForecastTemperatureTextView;

        public DailyForecastHolder(View itemView) {
            super(itemView);
            mDailyForecastWeeklyTextView = (TextView) itemView.findViewById(R.id.daily_forecast_weekly_text_view);
            mDailyForecastDateTextView = (TextView) itemView.findViewById(R.id.daily_forecast_date_text_view);
            mDailyForecastIconImageView = (ImageView) itemView.findViewById(R.id.daily_forecast_icon_image_view );
            mDailyForecastWindTextView = (TextView) itemView.findViewById(R.id.daily_forecast_wind_text_view);
            mDailyForecastTemperatureTextView = (TextView) itemView.findViewById(R.id.daily_forecast_temperature_text_view);
        }

        public void bindDailyForecast(DailyForecast dailyForecast){
            mDailyForecast = dailyForecast;
            mDailyForecastWeeklyTextView.setText(TimeUtil.getNear3Weekday(MyApplication.getContext(), mDailyForecast.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastDateTextView.setText(TimeUtil.getDate(mDailyForecast.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastIconImageView.setImageDrawable(mContext.getResources().getDrawable(WeatherUtil.convertIconToRes(mDailyForecast.getCondCodeD())));
            mDailyForecastWindTextView.setText(mDailyForecast.getCondTxtD());
            if (mCallbacks != null) {
                if (mCallbacks.isFahrenheit()) {
                    mDailyForecastTemperatureTextView.setText(WeatherUtil.convertCelsius2Fahrenheit(mDailyForecast.getTmpMin()) + "° ~ " + WeatherUtil.convertCelsius2Fahrenheit(mDailyForecast.getTmpMax()) + "°");
                } else {
                    mDailyForecastTemperatureTextView.setText(mDailyForecast.getTmpMin() + "° ~ " + mDailyForecast.getTmpMax() + "°");
                }
            }
        }
    }

    public void setCallbacks(Callbacks callbacks) {
        mCallbacks = callbacks;
    }

    public interface Callbacks {
        boolean isFahrenheit();
        void setFahrenheit(boolean fahrenheit);
    }
}
