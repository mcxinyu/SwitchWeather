package com.about.switchweather;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.UUID;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class MainEmptyFragment extends Fragment {
    private Callbacks mCallbacks;
    /**
     * Required interface for hosting activities.
     */
    public interface Callbacks {
        void onFetchWeatherComplete(UUID weatherBeanUUID);
    }

    /**
     * 由于该回调函数在 API>23 已被弃用，所以加上 SuppressWarnings("deprecation") 注解
     * @param activity
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)){
            throw new ClassCastException("Hosting Activity must implement BackHandledInterface");
        } else {
            mCallbacks = (Callbacks) activity;
        }
    }

    public static MainEmptyFragment newInstance(){
        return new MainEmptyFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchWeather("深圳").execute();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_info, container,false);

        return view;
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

            return new HeWeatherFetch().fetchWeatherInfo(mCityName);
        }

        @Override
        protected void onPostExecute(WeatherBean weatherBean) {
            WeatherLab.get(getActivity()).addWeatherBean(weatherBean);
            mCallbacks.onFetchWeatherComplete(weatherBean.getUUID());
        }
    }
}
