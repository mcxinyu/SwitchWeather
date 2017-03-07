package com.about.switchweather.ui.editcity;

import android.Manifest;
import android.app.Activity;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.about.switchweather.R;
import com.about.switchweather.ui.searchcity.SearchCityActivity;
import com.about.switchweather.util.baidulocationservice.LocationProvider;
import com.about.switchweather.util.QueryPreferences;

import java.util.ArrayList;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityFragment extends Fragment implements LocationProvider.Callbacks{
    private final int SDK_PERMISSION_REQUEST = 127;

    private Callbacks mCallbacks;
    private RecyclerView mRecyclerView;
    private ToggleButton mLocationToggleButton;
    private TextView mTextView;
    private ImageButton mAddCityImageButton;
    private EditCityAdapter mEditCityAdapter;
    private LocationProvider mLocationProvider;

    public interface Callbacks{

        void onLocationButtonChange(boolean isChecked);
        void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity);
    }
    public static EditCityFragment newInstance() {
        Bundle args = new Bundle();

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationProvider = new LocationProvider(getActivity());
        mLocationProvider.setCallbacks(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_city, container, false);
        intiView(view);

        return view;
    }

    @Override
    public void onDestroy() {
        mLocationProvider.unSetCallbacks();
        mLocationProvider.destroy();
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void intiView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.edit_city_list_recycler_view);
        mLocationToggleButton = (ToggleButton) view.findViewById(R.id.location_toggle_button);
        mTextView = (TextView) view.findViewById(R.id.current_city_text_view);
        mAddCityImageButton = (ImageButton) view.findViewById(R.id.add_city_image_button);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mEditCityAdapter = new EditCityAdapter(getActivity());
        mRecyclerView.setAdapter(mEditCityAdapter);

        mAddCityImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchCityActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLocationToggleButton.setChecked(QueryPreferences.getStoreLocationButtonState(getActivity()));
        if (mLocationToggleButton.isChecked()) {
            mTextView.setText(QueryPreferences.getStoreLocationCityName(getActivity()));
            getPermissions();
        } else {
            mTextView.setText("");
        }

        mLocationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setStoreLocationButtonState(getActivity(), isChecked);
                if (isChecked){
                    getPermissions();
                    mLocationProvider.start();
                } else {
                    mLocationProvider.stop();
                    mTextView.setText("");
                    QueryPreferences.setStoreLocationCityName(getActivity(), "");
                }
                mCallbacks.onLocationButtonChange(isChecked);
            }
        });
    }

    public String onItemClick(View view, int position) {
        return mEditCityAdapter.getCurrentItem(position).getBasicCityId();
    }

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
        if(!checkSelfPermissions(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!checkSelfPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        /*
         * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
         */
        // 读写权限
        //if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            //permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
        //}
        // 读取电话状态权限
        //if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
        //    permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
        //}

        if (permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
        }
    }

    private boolean checkSelfPermissions(String permission){
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
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
            boolean isGrant = false;
            for (int i = 0; i < permissions.length; i++) {
                if (checkSelfPermissions(permissions[i])){
                    isGrant = true;
                }
            }
            if (isGrant){
                mLocationProvider.start();
            }else {
                mLocationToggleButton.setChecked(false);
                mTextView.setText("");
            }
        }
    }

    @Override
    public void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity) {
        mCallbacks.onLocationCityChange(isLocationCityChange, oldCity, newCity);
        mTextView.setText(newCity);
    }

    @Override
    public void onLocationComplete(boolean isSuccess, String currentCityName) {
        mTextView.setText(currentCityName);
    }

    @Override
    public void onLocationCriteriaException(String description) {
        mLocationToggleButton.setChecked(false);
        mTextView.setText(description);
    }

    @Override
    public void onLocationNetWorkException(String description) {
        mLocationToggleButton.setChecked(false);
        mTextView.setText(description);
    }

    @Override
    public void onLocationError(String description) {
        mLocationToggleButton.setChecked(false);
        mTextView.setText(description);
    }
}
