package io.github.mcxinyu.switchweather.ui.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.database.WeatherDatabaseLab;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.model.WeatherInfo;
import io.github.mcxinyu.switchweather.ui.activity.WeatherActivity;
import io.github.mcxinyu.switchweather.util.ColoredSnackbar;
import io.github.mcxinyu.switchweather.util.HeWeatherFetch;
import io.github.mcxinyu.switchweather.util.WeatherUtil;


/**
 * Created by 跃峰 on 2016/8/21.
 */
@Deprecated
public class MainEmptyFragment extends Fragment {
    private static final String ARG_WEATHER_CITY_NAME = "WeatherFragment";

    @BindView(R.id.no_info_image_view)
    ImageView mNoInfoImageView;
    @BindView(R.id.loading_text_view)
    TextView mLoadingTextView;
    @BindView(R.id.fragment_no_info)
    RelativeLayout mFragmentNoInfo;
    private Unbinder unbinder;
    private boolean isMainEmptyFragmentClose = false;

    public static MainEmptyFragment newInstance(@Nullable String cityName) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_NAME, cityName);
        MainEmptyFragment fragment = new MainEmptyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String cityName = (String) getArguments().getSerializable(ARG_WEATHER_CITY_NAME);

        doFetchWeather(cityName);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_info, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void doFetchWeather(@Nullable String cityName) {
        if (cityName != null) {
            new FetchWeather(cityName).execute();
        } else {
            List<WeatherInfo> weatherInfoList = WeatherDatabaseLab.get(SwitchWeatherApp.getInstance()).getWeatherInfoList();

            for (int i = 0; i < weatherInfoList.size(); i++) {
                //如果数量太多会不会出错？导致线程不同步？
                new FetchWeather(weatherInfoList.get(i).getBasicCity()).execute();
            }
        }
    }

    private class FetchWeather extends AsyncTask<Void, Void, HeWeatherModel> {
        String mCityName;

        public FetchWeather(String cityName) {
            this.mCityName = cityName;
        }

        @Override
        protected HeWeatherModel doInBackground(Void... params) {
            HeWeatherModel weatherModel = new HeWeatherFetch().fetchWeatherBean(mCityName);
            storeWeatherData(weatherModel);
            return weatherModel;
        }

        private void storeWeatherData(HeWeatherModel weatherModel) {
            if (weatherModel != null) {
                WeatherDatabaseLab.get(SwitchWeatherApp.getInstance()).addWeatherInfo(weatherModel);
            }
        }

        @Override
        protected void onPostExecute(HeWeatherModel weatherModel) {
            //无网、无存储
            if (!WeatherUtil.isNetworkAvailableAndConnected() &&
                    WeatherDatabaseLab.get(SwitchWeatherApp.getInstance()).getWeatherInfoWithCityName(mCityName) == null) {
                showSnackBarAlert(getResources().getString(R.string.network_is_not_available), true);
                return;
            }
            if (weatherModel == null) {
                //有网、不成功、有存储
                if (WeatherDatabaseLab.get(SwitchWeatherApp.getInstance()).getWeatherInfoWithCityName(mCityName) != null) {
                    fetchWeatherComplete(
                            WeatherDatabaseLab.get(SwitchWeatherApp.getInstance())
                                    .getWeatherInfoWithCityName(mCityName).getBasicCityId(), false);
                    return;
                }
                //有网、不成功，无存储
                showSnackBarAlert(getResources().getString(R.string.update_failed), true);
                return;
            }

            // 更新成功，刷新
            fetchWeatherComplete(WeatherDatabaseLab.get(getContext()).getWeatherInfoList().get(0).getBasicCityId(), true);
        }
    }

    private void fetchWeatherComplete(String cityId, boolean updated) {
        if (!isMainEmptyFragmentClose) {
            if (WeatherDatabaseLab.get(SwitchWeatherApp.getInstance()).getWeatherInfo(cityId) == null) {
                // 如果数据库中没有数据，用搜索 SearchCityFragment 替换自己 return
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

                if (fragment == null) {
                    fragment = SearchCityFragment.newInstance(false);
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                            .commit();
                }
                return;
            }
            Intent intent = WeatherActivity.newIntent(SwitchWeatherApp.getInstance(), cityId);
            isMainEmptyFragmentClose = true;
            startActivity(intent);
        }
    }

    public void showSnackBarAlert(String text, boolean showAction) {
        mLoadingTextView.setText("");
        if (showAction) {
            Snackbar snackbar = Snackbar.make(mFragmentNoInfo, text, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            doFetchWeather(null);
                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorWhite));
            ColoredSnackbar.alert(snackbar).show();
        } else {
            Snackbar snackbar = Snackbar.make(mFragmentNoInfo, text, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.colorWhite));
            ColoredSnackbar.alert(snackbar).show();
        }
    }
}
