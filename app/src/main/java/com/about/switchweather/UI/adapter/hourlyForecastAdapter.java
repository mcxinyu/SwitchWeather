package com.about.switchweather.UI.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.about.switchweather.Model.HourlyForecast;
import com.about.switchweather.R;
import com.about.switchweather.Util.WeatherUtil;

import java.util.List;

/**
 * Created by huangyuefeng on 2017/3/2.
 */
public class HourlyForecastAdapter extends RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastHolder>{
    private Context mContext;
    private List<HourlyForecast> mData;
    private LayoutInflater inflater;
    private Callbacks mCallbacks;

    public HourlyForecastAdapter(Context context, List<HourlyForecast> data) {
        mContext = context;
        mData = data;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public HourlyForecastHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_hourly_forecast, parent, false);
        return new HourlyForecastHolder(view);
    }

    @Override
    public void onBindViewHolder(HourlyForecastHolder holder, int position) {
        holder.bindHourlyForecast(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class HourlyForecastHolder extends RecyclerView.ViewHolder {
        private HourlyForecast mHourlyForecast;
        private TextView mHourlyForecastDateTextView;
        private ImageView mHourlyForecastIconImageView;
        private TextView mHourlyForecastConditionTextView;
        private TextView mHourlyForecastTemperatureTextView;

        public HourlyForecastHolder(View itemView) {
            super(itemView);
            mHourlyForecastDateTextView = (TextView) itemView.findViewById(R.id.hourly_forecast_date_text_view);
            mHourlyForecastIconImageView = (ImageView) itemView.findViewById(R.id.hourly_forecast_icon_image_view);
            mHourlyForecastConditionTextView = (TextView) itemView.findViewById(R.id.hourly_forecast_condition_text_view);
            mHourlyForecastTemperatureTextView = (TextView) itemView.findViewById(R.id.hourly_forecast_temperature_text_view);
        }

        public void bindHourlyForecast(HourlyForecast hourlyForecast){
            mHourlyForecast = hourlyForecast;
            String[] time = mHourlyForecast.getDate().split(" ");
            if (time.length > 1) {
                if (time[1].substring(0, 1).equals("0")){
                    mHourlyForecastDateTextView.setText(time[1].substring(1));
                } else {
                    mHourlyForecastDateTextView.setText(time[1]);
                }
            }
            mHourlyForecastIconImageView.setImageDrawable(
                    mContext.getResources().getDrawable(WeatherUtil.convertIconToRes(mHourlyForecast.getCondCode())));
            mHourlyForecastConditionTextView.setText(mHourlyForecast.getCondTxt());
            if (mCallbacks != null) {
                if (mCallbacks.isFahrenheit()) {
                    mHourlyForecastTemperatureTextView.setText(WeatherUtil.convertCelsius2Fahrenheit(mHourlyForecast.getTmp()) + "° ~ " + WeatherUtil.convertCelsius2Fahrenheit(mHourlyForecast.getTmp()) + "°");
                } else {
                    mHourlyForecastTemperatureTextView.setText(mHourlyForecast.getTmp() + "°");
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
