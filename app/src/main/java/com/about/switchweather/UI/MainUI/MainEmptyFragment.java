package com.about.switchweather.UI.MainUI;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.about.switchweather.Model.WeatherBean;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.R;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SearchCityUI.SearchCityActivity;
import com.about.switchweather.Util.BaiduLocationService.LocationProvider;
import com.about.switchweather.Util.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class MainEmptyFragment extends Fragment implements LocationProvider.Callbacks{
    private static final String ARG_WEATHER_CITY_NAME = "WeatherFragment";
    private TextView mLoadingTextView;
    private Callbacks mCallbacks;
    private List<WeatherInfo> mWeatherInfoList;
    private ImageView mImageView;
    private Button mButton;
    private TextView mEmptyTextView;
    private int SDK_PERMISSION_REQUEST = 127;
    private LocationProvider mLocationProvider;
    private ToggleButton mLocationToggleButton;
    private TextView mCurrentCityTextView;

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

    public static MainEmptyFragment newInstance(@Nullable String cityName){ //可以空是因为可能是程序打开而不是增加城市后被调用
        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_NAME, cityName);
        MainEmptyFragment fragment = new MainEmptyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
        mLocationProvider = new LocationProvider(MainEmptyFragment.this, MyApplication.getContext());

        String cityName = (String) getArguments().getSerializable(ARG_WEATHER_CITY_NAME);

        if (cityName == null) { //如果 cityName 是空的，尝试查看有没有定位城市
            boolean locationButtonState = QueryPreferences.getStoreLocationButtonState(MyApplication.getContext());
            if (locationButtonState) {   // locationButtonState 是打开的时候执行
                getPermissions();
                if (!WeatherUtil.isNetworkAvailableAndConnected()){
                    // 因为百度地图 com.about.switchweather:remote D/baidu_location_service: NetworkCommunicationException! 之后并没有回调
                    // 所以不会调用 doFetchWeather()，只能自己判断是不是定位出错了。
                    doFetchWeather(null);
                } else {
                    mLocationProvider.start();
                }
            } else {
                doFetchWeather(null);
            }
        } else {
            doFetchWeather(cityName);
        }
    }

    @Override
    public void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity) {
        mLocationProvider.destroy();
        if (isLocationCityChange){
            if (oldCity != null) {
                WeatherLab.get(MyApplication.getContext())
                        .deleteWeatherInfo(WeatherLab.get(MyApplication.getContext()).getCityWithCityName(oldCity).getId());
            }
            // 因为城市改变的话说明 onLocationComplete 不用回调了
            // 先更新 newCity 是为了让 WeatherActivity 先打开定位的城市
            if (newCity != null) {
                mCurrentCityTextView.setText(newCity);
                doFetchWeather(newCity);
            }
            // 后台还要继续更新其他城市
            doFetchWeather(null);
        }
    }

    @Override
    public void onLocationComplete(boolean isSuccess, String currentCityName) {
        mLocationProvider.destroy();
        // 定位失败的话 currentCityName = null
        // 先更新 currentCityName 是为了让 WeatherActivity 先打开定位的城市
        if (currentCityName != null) {
            mCurrentCityTextView.setText(currentCityName);
            doFetchWeather(currentCityName);
        }
        // 后台还要继续更新其他城市
        doFetchWeather(null);
    }

    private void doFetchWeather(String cityName) {
        if (cityName != null){
            new FetchWeather(cityName).execute();
        } else {
            for (int i = 0; i < mWeatherInfoList.size(); i++) {
                //如果数量太多会不会出错？导致线程不同步？
                new FetchWeather(mWeatherInfoList.get(i).getBasicCity()).execute();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_info, container,false);
        mLoadingTextView = (TextView) view.findViewById(R.id.loading_text_view);
        mImageView = (ImageView) view.findViewById(R.id.no_info_image_view);
        mButton = (Button) view.findViewById(R.id.add_city_button);
        mEmptyTextView = (TextView) view.findViewById(R.id.empty_text_view);
        mLocationToggleButton = (ToggleButton) view.findViewById(R.id.no_info_location_toggle_button);
        mCurrentCityTextView = (TextView) view.findViewById(R.id.no_info_current_city_text_view);

        if (mWeatherInfoList.size() == 0){
            mLoadingTextView.setVisibility(View.GONE);
            mImageView.setVisibility(View.GONE);
            mButton.setVisibility(View.VISIBLE);
            mEmptyTextView.setVisibility(View.VISIBLE);
            if (!QueryPreferences.getStoreLocationButtonState(MyApplication.getContext())) {
                mLocationToggleButton.setVisibility(View.VISIBLE);
                mCurrentCityTextView.setVisibility(View.VISIBLE);
            }
        } else {
            mLoadingTextView.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.GONE);
            mEmptyTextView.setVisibility(View.GONE);
            mLocationToggleButton.setVisibility(View.GONE);
            mCurrentCityTextView.setVisibility(View.GONE);
        }

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchCityActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLocationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setStoreLocationButtonState(MyApplication.getContext(), isChecked);
                if (isChecked){
                    getPermissions();
                    mLocationProvider.start();
                } else {
                    mLocationProvider.stop();
                    mCurrentCityTextView.setText("");
                }
            }
        });

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
            WeatherBean weatherBean = new HeWeatherFetch().fetchWeatherBean(mCityName);
            storeData(weatherBean);
            return weatherBean;
        }

        private void storeData(WeatherBean weatherBean) {
            if (weatherBean != null){
                if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityName(mCityName) == null) {
                    //有网、成功、无存储即增加
                    WeatherLab.get(MyApplication.getContext()).addWeatherBean(weatherBean);
                    WeatherLab.get(MyApplication.getContext()).addDailyForecastList(weatherBean);
                } else {
                    //有网、成功、有存储即更新
                    WeatherLab.get(MyApplication.getContext()).updateWeatherInfo(weatherBean);
                    WeatherLab.get(MyApplication.getContext()).updateDailyForecastList(weatherBean);
                }
            }
        }

        @Override
        protected void onPostExecute(WeatherBean weatherBean) {
            //无网、无存储
            if (!WeatherUtil.isNetworkAvailableAndConnected() && WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityName(mCityName) == null){
                showSnackbarAlert(getResources().getString(R.string.network_is_not_available));
                return;
            }
            if (weatherBean == null){
                //有网、不成功、有存储
                if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityName(mCityName) != null){
                    mCallbacks.onFetchWeatherComplete(WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityName(mCityName).getBasicCityId(), false);
                    return;
                }
                //有网、不成功，无存储
                showSnackbarAlert(getResources().getString(R.string.update_failed));
                return;
            }
            //如果 activity 不在了怎么办？会出错！
            mCallbacks.onFetchWeatherComplete(weatherBean.getBasic().getId(), true);
        }
    }

    private void showSnackbarAlert(String text) {
        mLoadingTextView.setText("");
        Snackbar snackbar = Snackbar.make(getView(), text, Snackbar.LENGTH_INDEFINITE)
                .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        doFetchWeather(null);
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite));
        ColoredSnackbar.alert(snackbar).show();
    }

    private void getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        /***
         * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
         */
        // 定位精确位置
        if(!checkSelfPermissions(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!checkSelfPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
        }
    }

    private boolean checkSelfPermissions(String permission){
        return ContextCompat.checkSelfPermission(MyApplication.getContext(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (!checkSelfPermissions(permission)) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SDK_PERMISSION_REQUEST){
            if (checkSelfPermissions(permissions[0])){
                mLocationProvider.start();
            }
        }
    }
}