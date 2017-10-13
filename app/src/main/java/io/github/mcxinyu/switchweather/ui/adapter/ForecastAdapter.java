package io.github.mcxinyu.switchweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast.ResultBean.DailyBean.SkyconBeanX;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast.ResultBean.DailyBean.TemperatureBeanX;
import io.github.mcxinyu.switchweather.model.CaiYunWeatherForecast.ResultBean.HourlyBean;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import io.github.mcxinyu.switchweather.util.TimeUtil;
import io.github.mcxinyu.switchweather.util.WeatherUtil;

/**
 * Created by huangyuefeng on 2017/3/2.
 */
public class ForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_HOURLY = 0x00;
    public static final int VIEW_TYPE_DAILY = 0x01;

    private Context mContext;
    private LayoutInflater inflater;
    private CaiYunWeatherForecast mForecast;
    private boolean isFahrenheit;
    private int mViewType;

    public ForecastAdapter(Context context, CaiYunWeatherForecast forecast, int viewType) {
        mContext = context;
        mForecast = forecast;
        mViewType = viewType;
        isFahrenheit = QueryPreferences.getSettingTemperatureUnit(mContext).equals("F");
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (mViewType == VIEW_TYPE_HOURLY) {
            view = inflater.inflate(R.layout.item_hourly_forecast, parent, false);
            return new HourlyForecastHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_daily_forecast, parent, false);
            return new DailyForecastHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mViewType == VIEW_TYPE_HOURLY) {
            ((HourlyForecastHolder) holder).bindHourlyForecast(mForecast.getResult().getHourly().getSkyCon().get(position),
                    mForecast.getResult().getHourly().getTemperature().get(position));
        } else {
            ((DailyForecastHolder) holder).bindDailyForecast(mForecast.getResult().getDaily().getSkyCon().get(position),
                    mForecast.getResult().getDaily().getTemperature().get(position));
        }
    }

    @Override
    public int getItemCount() {
        if (mViewType == VIEW_TYPE_HOURLY) {
            return mForecast.getResult().getHourly().getSkyCon().size();
        } else {
            return mForecast.getResult().getDaily().getSkyCon().size();
        }
    }

    class HourlyForecastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.hourly_forecast_date_text_view)
        TextView mHourlyForecastDateTextView;
        @BindView(R.id.hourly_forecast_icon_image_view)
        ImageView mHourlyForecastIconImageView;
        @BindView(R.id.hourly_forecast_condition_text_view)
        TextView mHourlyForecastConditionTextView;
        @BindView(R.id.hourly_forecast_temperature_text_view)
        TextView mHourlyForecastTemperatureTextView;

        HourlyForecastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindHourlyForecast(HourlyBean.SkyConBean skyConBean, HourlyBean.TemperatureBean temperatureBean) {
            String time = skyConBean.getDatetime();
            mHourlyForecastDateTextView.setText(time.substring(time.lastIndexOf(" ")));
            mHourlyForecastIconImageView.setImageDrawable(
                    mContext.getResources().getDrawable(WeatherUtil.convertSkyConToRes(skyConBean.getValue())));
            mHourlyForecastConditionTextView.setText(WeatherUtil.convertSkyConToDesc(skyConBean.getValue()));

            if (isFahrenheit) {
                mHourlyForecastTemperatureTextView.setText(
                        WeatherUtil.convertCelsius2Fahrenheit(temperatureBean.getValue()) + "°");
            } else {
                mHourlyForecastTemperatureTextView.setText(temperatureBean.getValue() + "°");
            }
        }
    }

    class DailyForecastHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.daily_forecast_weekly_text_view)
        TextView mDailyForecastWeeklyTextView;
        @BindView(R.id.daily_forecast_date_text_view)
        TextView mDailyForecastDateTextView;
        @BindView(R.id.daily_forecast_center_view)
        TextView mDailyForecastCenterView;
        @BindView(R.id.daily_forecast_icon_image_view)
        ImageView mDailyForecastIconImageView;
        @BindView(R.id.daily_forecast_wind_text_view)
        TextView mDailyForecastWindTextView;
        @BindView(R.id.daily_forecast_temperature_text_view)
        TextView mDailyForecastTemperatureTextView;

        DailyForecastHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bindDailyForecast(SkyconBeanX skyconBeanX, TemperatureBeanX temperatureBeanX) {
            mDailyForecastWeeklyTextView.setText(
                    TimeUtil.getNear3Weekday(mContext, skyconBeanX.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastDateTextView.setText(
                    TimeUtil.getDate(skyconBeanX.getDate(), new SimpleDateFormat("yyyy-MM-dd")));
            mDailyForecastIconImageView.setImageDrawable(
                    mContext.getResources().getDrawable(WeatherUtil.convertSkyConToRes(skyconBeanX.getValue())));
            mDailyForecastWindTextView.setText(WeatherUtil.convertSkyConToDesc(skyconBeanX.getValue()));
            if (isFahrenheit) {
                mDailyForecastTemperatureTextView.setText(
                        WeatherUtil.convertCelsius2Fahrenheit(temperatureBeanX.getMax()) + "° ~ " +
                                WeatherUtil.convertCelsius2Fahrenheit(temperatureBeanX.getMin()) + "°");
            } else {
                mDailyForecastTemperatureTextView.setText(
                        temperatureBeanX.getMax() + "° ~ " + temperatureBeanX.getMin() + "°");
            }
        }
    }
}
