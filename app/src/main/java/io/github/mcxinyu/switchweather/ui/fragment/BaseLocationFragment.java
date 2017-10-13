package io.github.mcxinyu.switchweather.ui.fragment;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import java.util.List;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.database.dao.CityBeanDao;
import io.github.mcxinyu.switchweather.model.V5City;
import io.github.mcxinyu.switchweather.ui.activity.WeatherActivity;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import io.github.mcxinyu.switchweather.util.WeatherUtil;
import io.github.mcxinyu.switchweather.util.baidulocationservice.LocationService;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by huangyuefeng on 2017/8/25.
 * Contact me : mcxinyu@foxmail.com
 */
public abstract class BaseLocationFragment extends Fragment {

    protected LocationService mLocationService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationService = SwitchWeatherApp.getInstance().mLocationService;
        mLocationService.registerListener(mLocationListener);
        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
    }

    @Override
    public void onStop() {
        mLocationService.unregisterListener(mLocationListener);
        mLocationService.stop();
        super.onStop();
    }

    private BDAbstractLocationListener mLocationListener = new BDAbstractLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                onReceiveLocationInfo(location);
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                onReceiveLocationError();
                Toast.makeText(getContext(),
                        "服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因",
                        Toast.LENGTH_LONG).show();
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                onReceiveLocationError();
                Toast.makeText(getContext(),
                        "网络不同导致定位失败，请检查网络是否通畅",
                        Toast.LENGTH_SHORT).show();
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                onReceiveLocationError();
                Toast.makeText(getContext(),
                        "无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机",
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    protected void requestLocationPermission() {
        RxPermissions rxPermissions = new RxPermissions(getActivity());
        rxPermissions.requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(new Action1<Permission>() {
                    @Override
                    public void call(Permission permission) {
                        if (permission.granted) {
                            if (permission.name.equals(Manifest.permission.ACCESS_FINE_LOCATION)
                                    || permission.name.equals(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                                if (!WeatherUtil.isNetworkAvailableAndConnected()) {
                                    Toast.makeText(getContext(), getString(R.string.network_is_not_available), Toast.LENGTH_SHORT).show();
                                } else {
                                    // Toast.makeText(getContext(), getString(R.string.get_locatoin), Toast.LENGTH_SHORT).show();
                                    mLocationService.start();
                                }
                            }
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了权限申请
                            QueryPreferences.setLocationButtonState(getContext(), false);
                        } else {
                            // 用户拒绝，并且选择不再提示
                            // 可以引导用户进入权限设置界面开启权限
                            QueryPreferences.setLocationButtonState(getContext(), false);
                        }
                    }
                });
    }

    protected void onReceiveLocationInfo(final BDLocation location) {
        if (location != null) {
            WeatherApiHelper.searchCity(location.getLongitude() + "," + location.getLatitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .limit(1)
                    .subscribe(new Observer<V5City>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "获取城市信息失败，请稍后重试。", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(V5City v5City) {
                            if (v5City == null) {
                                Toast.makeText(getContext(), "未搜索到城市", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            CityBean city = SwitchWeatherApp.getDaoInstance()
                                    .getCityBeanDao()
                                    .queryBuilder()
                                    .where(CityBeanDao.Properties.IsLocation.eq(1))
                                    .unique();
                            if (city != null) {
                                // if v5City is not equal to the old city inside the database, do something
                                if (!city.getCityId().equals(v5City.getHeWeather5().get(0).getBasic().getId())) {
                                    storeV5CityToDatabase(v5City, true);
                                    startActivity(WeatherActivity.newIntent(getContext(),
                                            v5City.getHeWeather5().get(0).getBasic().getId()));
                                }
                                // if equal, do nothing
                            } else {
                                storeV5CityToDatabase(v5City, true);
                                startActivity(WeatherActivity.newIntent(getContext(),
                                        v5City.getHeWeather5().get(0).getBasic().getId()));
                            }
                        }
                    });
        }
    }

    protected void storeV5CityToDatabase(V5City v5City, boolean isLocation) {
        if (isLocation) {
            List<CityBean> list = SwitchWeatherApp.getDaoInstance()
                    .getCityBeanDao()
                    .queryBuilder()
                    .where(CityBeanDao.Properties.IsLocation.eq(1))
                    .list();
            SwitchWeatherApp.getDaoInstance()
                    .getCityBeanDao()
                    .deleteInTx(list);
        }
        SwitchWeatherApp.getDaoInstance()
                .getCityBeanDao()
                .insertOrReplace(v5City.getHeWeather5().get(0).getBasic().toCityBean(isLocation));
    }

    protected abstract void onReceiveLocationError();
}
