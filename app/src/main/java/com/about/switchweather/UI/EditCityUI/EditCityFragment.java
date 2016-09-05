package com.about.switchweather.UI.EditCityUI;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ToggleButton;
import com.about.switchweather.R;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SearchCityUI.SearchCityActivity;
import com.about.switchweather.Util.BaiduLocationService.LocationService;
import com.about.switchweather.Util.QueryPreferences;
import com.about.switchweather.Util.WeatherLab;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

import java.util.ArrayList;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityFragment extends Fragment implements EditCityActivity.Callbacks{
    private final int SDK_PERMISSION_REQUEST = 127;

    private RecyclerView mRecyclerView;
    private ToggleButton mLocationToggleButton;
    private ImageButton mAddCityImageButton;
    private EditCityAdapter mEditCityAdapter;
    private LocationService mLocationService;
    private String locationCity;

    public static EditCityFragment newInstance() {
        Bundle args = new Bundle();

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_city, container, false);
        intiView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mLocationService = new LocationService(MyApplication.getContext());
        mLocationService.registerListener(mListener);
        mLocationService.setLocationOption(mLocationService.getDefaultLocationClientOption());
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationService.unregisterListener(mListener);
        mLocationService.stop();
    }

    private void intiView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.edit_city_list_recycler_view);
        mLocationToggleButton = (ToggleButton) view.findViewById(R.id.location_toggle_button);
        mAddCityImageButton = (ImageButton) view.findViewById(R.id.add_city_image_button);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mEditCityAdapter = new EditCityAdapter(getActivity());
        mRecyclerView.setAdapter(mEditCityAdapter);

        mAddCityImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchCityActivity.newIntent(MyApplication.getContext());
                startActivity(intent);
            }
        });

        mLocationToggleButton.setChecked(QueryPreferences.getStoreLocationEnable(MyApplication.getContext()));
        if (mLocationToggleButton.isChecked()) {
            getPermissions();
            mLocationService.start();
        } else {
            mLocationService.stop();
            QueryPreferences.setStoreLocationCityId(MyApplication.getContext(), null);
        }

        mLocationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setStoreLocationEnable(MyApplication.getContext(), isChecked);
                if (isChecked){
                    getPermissions();
                    mLocationService.start();
                } else {
                    mLocationService.stop();
                    QueryPreferences.setStoreLocationCityId(MyApplication.getContext(), null);
                }
            }
        });
    }

    /**
     * 定位结果回调，重写onReceiveLocation方法，可以直接拷贝如下代码到自己工程中修改
     *
     */
    private BDLocationListener mListener = new BDLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                locationCity = location.getCity().substring(0, location.getCity().lastIndexOf("市"));
                QueryPreferences.setStoreLocationCityId(MyApplication.getContext(), WeatherLab.get(MyApplication.getContext()).getCityWithCityName(locationCity).getId());

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
            }
        }
    };

    @Override
    public String onItemClick(View view, int position) {
        return mEditCityAdapter.getCurrentItem(position).getBasicCityId();
    }

    @Override
    public boolean onDeleteButtonClick(View view, int position) {
        mEditCityAdapter.removeItem(position);
        return true;
    }

    private void getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        /***
         * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
         */
        // 定位精确位置
        if(!checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        /*
         * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
         */
        // 读写权限
        if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
        }
        // 读取电话状态权限
        if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
            //permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
        }

        if (permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
        }
    }

    private boolean checkSelfPermission(String permission){
        int selfPermission =  ContextCompat.checkSelfPermission(getActivity(), permission);
        if (selfPermission != PackageManager.PERMISSION_GRANTED){
            return false;
        }else {
            return true;
        }
    }

    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (!checkSelfPermission(permission)) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
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
    }
}
