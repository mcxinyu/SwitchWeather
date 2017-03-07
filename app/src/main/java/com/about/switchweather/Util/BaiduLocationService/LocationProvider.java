package com.about.switchweather.util.baidulocationservice;

import android.content.Context;

import com.about.switchweather.util.QueryPreferences;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * Created by 跃峰 on 2016/9/7.
 */
public class LocationProvider {
    private static final String TAG = "LocationProvider";
    private Context mContext;
    private final LocationService mLocationService;
    private String locationCity;

    private Callbacks mCallbacks;

    public interface Callbacks{
        void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity);
        void onLocationComplete(boolean isSuccess, String currentCityName);
        void onLocationCriteriaException(String description);
        void onLocationNetWorkException(String description);
        void onLocationError(String description);
    }

    public LocationProvider(Context context) {
        mContext = context;
        mLocationService = new LocationService(mContext);
        mLocationService.registerListener(mListener);
        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
    }

    public void setCallbacks(Callbacks callbacks){
        mCallbacks = callbacks;
    }

    public void unSetCallbacks(){
        mCallbacks = null;
    }

    public void destroy() {
        mLocationService.unregisterListener(mListener);
        mLocationService.stop();
    }

    public void stop() {
        if (mLocationService != null){
            mLocationService.stop();
        }
    }

    public void start() {
        if (mLocationService != null) {
            mLocationService.start();
        }
    }

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (mCallbacks != null) {
                if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                    locationCity = location.getCity().substring(0, location.getCity().lastIndexOf("市"));
                    if (!locationCity.equals(QueryPreferences.getStoreLocationCityName(mContext))) {
                        mCallbacks.onLocationCityChange(true, QueryPreferences.getStoreLocationCityName(mContext), locationCity);
                        QueryPreferences.setStoreLocationCityName(mContext, locationCity);
                        return; // 调用 onLocationCityChange 就不调用 onLocationComplete
                    }
                    mCallbacks.onLocationComplete(true, locationCity);

                    /**
                     StringBuffer sb = new StringBuffer(256);
                     sb.append("time : ");
                     //时间也可以使用systemClock.elapsedRealtime()方法 获取的是自从开机以来，每次回调的时间；
                     //location.getTime() 是指服务端出本次结果的时间，如果位置不发生变化，则时间不变
                     sb.append(location.getTime());
                     sb.append("\nlocType : ");// 定位类型
                     sb.append(location.getLocType());
                     sb.append("\nlocType description : ");// *****对应的定位类型说明*****
                     sb.append(location.getLocTypeDescription());
                     sb.append("\nlatitude : ");// 纬度
                     sb.append(location.getLatitude());
                     sb.append("\nlontitude : ");// 经度
                     sb.append(location.getLongitude());
                     sb.append("\nradius : ");// 半径
                     sb.append(location.getRadius());
                     sb.append("\nCountryCode : ");// 国家码
                     sb.append(location.getCountryCode());
                     sb.append("\nCountry : ");// 国家名称
                     sb.append(location.getCountry());
                     sb.append("\ncitycode : ");// 城市编码
                     sb.append(location.getCityCode());
                     sb.append("\ncity : ");// 城市
                     sb.append(location.getCity());
                     sb.append("\nDistrict : ");// 区
                     sb.append(location.getDistrict());
                     sb.append("\nStreet : ");// 街道
                     sb.append(location.getStreet());
                     sb.append("\naddr : ");// 地址信息
                     sb.append(location.getAddrStr());
                     sb.append("\nUserIndoorState: ");// *****返回用户室内外判断结果*****
                     sb.append(location.getUserIndoorState());
                     sb.append("\nDirection(not all devices have value): ");
                     sb.append(location.getDirection());// 方向
                     sb.append("\nlocationdescribe: ");
                     sb.append(location.getLocationDescribe());// 位置语义化信息
                     sb.append("\nPoi: ");// POI信息
                     if (location.getPoiList() != null && !location.getPoiList().isEmpty()) {
                     for (int i = 0; i < location.getPoiList().size(); i++) {
                     Poi poi = (Poi) location.getPoiList().get(i);
                     sb.append(poi.getName() + ";");
                     }
                     }
                     if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                     sb.append("\nspeed : ");
                     sb.append(location.getSpeed());// 速度 单位：km/h
                     sb.append("\nsatellite : ");
                     sb.append(location.getSatelliteNumber());// 卫星数目
                     sb.append("\nheight : ");
                     sb.append(location.getAltitude());// 海拔高度 单位：米
                     sb.append("\ngps status : ");
                     sb.append(location.getGpsAccuracyStatus());// *****gps质量判断*****
                     sb.append("\ndescribe : ");
                     sb.append("gps定位成功");
                     } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                     // 运营商信息
                     if (location.hasAltitude()) {// *****如果有海拔高度*****
                     sb.append("\nheight : ");
                     sb.append(location.getAltitude());// 单位：米
                     }
                     sb.append("\noperationers : ");// 运营商信息
                     sb.append(location.getOperators());
                     sb.append("\ndescribe : ");
                     sb.append("网络定位成功");
                     } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                     sb.append("\ndescribe : ");
                     sb.append("离线定位成功，离线定位结果也是有效的");
                     } else if (location.getLocType() == BDLocation.TypeServerError) {
                     sb.append("\ndescribe : ");
                     sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
                     } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                     sb.append("\ndescribe : ");
                     sb.append("网络不同导致定位失败，请检查网络是否通畅");
                     } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                     sb.append("\ndescribe : ");
                     sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
                     }
                     System.out.println(sb);
                     */
                } else if (location.getLocType() == BDLocation.TypeServerError) {
                    mCallbacks.onLocationError("定位失败");
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    mCallbacks.onLocationNetWorkException("定位失败，请检查网络是否通畅");
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    mCallbacks.onLocationCriteriaException("定位失败，请尝试重启手机");
                } else if (location.getLocType() == BDLocation.TypeOffLineLocationFail) {
                    mCallbacks.onLocationError("定位失败");
                } else if (location.getSatelliteNumber() < 1){
                    mCallbacks.onLocationError("室内定位失败");
                }
            }
        }
    };

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
    }
}
