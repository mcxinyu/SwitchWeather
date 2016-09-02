package com.about.switchweather;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Util.ColoredSnackbar;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class MainEmptyFragment extends Fragment {
    private TextView mLoadingTextView;
    private String city = "深圳";
    private Callbacks mCallbacks;
    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onFetchWeatherComplete(String cityId, boolean updated);
    }

    /**
     * 由于该回调函数在 API>23 已被弃用，所以加上 SuppressWarnings("deprecation") 注解
     * @param activity
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (Callbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Hosting Activity must implement Interface");
        }
    }

    public static MainEmptyFragment newInstance(){
        return new MainEmptyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchWeather(city).execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_info, container,false);
        mLoadingTextView = (TextView) view.findViewById(R.id.loading_text_view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private class FetchWeather extends AsyncTask<Void, Void, WeatherBean> {

        String mCityName;
        public FetchWeather(String cityName) {
            this.mCityName = cityName;
        }

        @Override
        protected WeatherBean doInBackground(Void... params) {
            return new HeWeatherFetch().fetchWeatherBean(mCityName);
        }

        @Override
        protected void onPostExecute(WeatherBean weatherBean) {
            //无网、无存储
            if (!isNetworkAvailableAndConnected() && WeatherLab.get(getActivity()).getWeatherInfoWithCityName(city) == null){
                showSnackbarAlert(getResources().getString(R.string.network_is_not_available));
                return;
            }
            if (weatherBean == null){
                //有网、不成功、有存储
                if (WeatherLab.get(getActivity()).getWeatherInfoWithCityName(city) != null){
                    mCallbacks.onFetchWeatherComplete(WeatherLab.get(getActivity()).getWeatherInfoWithCityName(city).getBasicCityId(), false);
                    return;
                }
                //有网、不成功，无存储
                showSnackbarAlert(getResources().getString(R.string.update_failed));
                return;
            }
            if (WeatherLab.get(getActivity()).getWeatherInfoWithCityName(city) == null) {
                //有网、成功、无存储
                WeatherLab.get(getActivity()).addWeatherBean(weatherBean);
                WeatherLab.get(getActivity()).addDailyForecastList(weatherBean);
            } else {
                //有网、成功、有存储
                WeatherLab.get(getActivity()).updateWeatherInfo(weatherBean);
                WeatherLab.get(getActivity()).updateDailyForecastList(weatherBean);
            }
            mCallbacks.onFetchWeatherComplete(weatherBean.getBasic().getId(), true);
        }

    }

    private boolean isNetworkAvailableAndConnected(){
        ConnectivityManager manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isNetworkAvailable = manager.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable && manager.getActiveNetworkInfo().isConnected();
        return isNetworkConnected;
    }

    private void showSnackbarAlert(String text) {
        mLoadingTextView.setText("");
        Snackbar snackbar = Snackbar.make(getView(), text, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new FetchWeather(city).execute();
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite));
        ColoredSnackbar.alert(snackbar).show();
    }
}
