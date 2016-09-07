package com.about.switchweather.UI.WeatherUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.R;
import com.about.switchweather.UI.EditCityUI.EditCityActivity;
import com.about.switchweather.UI.MainUI.MainActivity;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SearchCityUI.SearchCityActivity;
import com.about.switchweather.UI.SingleFragmentActivity;
import com.about.switchweather.Util.QueryPreferences;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

/**
 * Created by 跃峰 on 2016/8/28.
 */
public class WeatherActivity extends SingleFragmentActivity {
    private static final String EXTRA_WEATHER_CITY_ID = "WeatherActivity.Weather_City_id";
    private static final String EXTRA_WEATHER_INFO_UPDATED = "WeatherActivity.Weather_Info_Updated";
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private static final int REQUEST_CODE_START_EDIT_CITY = 0;

    private List<WeatherInfo> mWeatherInfoList;
    private boolean mWeatherInfoIsUpdated;
    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;

    private String mCityId;
    private ActionBar mActionBar;

    private WeatherPagerFragment weatherPagerFragment;

    public static Intent newIntent(Context context, String cityId, boolean updated) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra(EXTRA_WEATHER_CITY_ID, cityId);
        intent.putExtra(EXTRA_WEATHER_INFO_UPDATED, updated);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_weather;
    }

    @Override
    public Fragment createFragment() {
        mCityId = getIntent().getStringExtra(EXTRA_WEATHER_CITY_ID);
        mWeatherInfoIsUpdated = getIntent().getBooleanExtra(EXTRA_WEATHER_INFO_UPDATED, false);
        weatherPagerFragment = WeatherPagerFragment.newInstance(mCityId, mWeatherInfoIsUpdated);
        return weatherPagerFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkDataValidity();
        initToolbar();
        initDrawer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkDataValidity();
    }

    private void checkDataValidity() {
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();

        if (mWeatherInfoList.size() == 0){
            startActivity(MainActivity.newIntent(this, null));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
            super.onBackPressed();
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
    }

    private void initDrawer() {
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //声明mDrawerToggle对象,其中R.string.open和R.string.close简单可以用"open"和"close"替代
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.app_name, R.string.app_name);
        //实现箭头和三条杠图案切换和抽屉拉合的同步
        mDrawerToggle.syncState();
        //监听实现侧边栏的拉开和闭合,即抽屉drawer的闭合和打开
        mDrawer.addDrawerListener(mDrawerToggle);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // DrawerLayout 菜单选择的监听
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.edit_city_list:
                        // 编辑城市的入口
                        Intent intent = EditCityActivity.newIntent(WeatherActivity.this);
                        startActivityForResult(intent, REQUEST_CODE_START_EDIT_CITY);
                        break;
                    case R.id.nav_settings:
                        // 设置项的入口
                        Toast.makeText(MyApplication.getContext(), "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_feedback:
                        // 反馈项的入口
                        Toast.makeText(MyApplication.getContext(), "反馈", Toast.LENGTH_SHORT).show();
                        break;
                }
                // 点击 item 后关闭 DrawerLayout
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        updateNavCityList();
    }

    private void updateNavCityList() {
        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            mNavigationView.getMenu().removeItem(R.id.menu_group_city_list + i);
        }

        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            final int position = i;
            mNavigationView.getMenu().add(R.id.menu_group_city_list, R.id.menu_group_city_list + i, i+100, mWeatherInfoList.get(i).getBasicCity()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    weatherPagerFragment.onCurrentPagerItemChange(mWeatherInfoList.get(position).getBasicCityId(), mWeatherInfoIsUpdated);
                    return false;
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_weather_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_menu_item:
                // 添加城市的入口
                Intent intent = SearchCityActivity.newIntent(this);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE_START_EDIT_CITY:
                if (resultCode == RESULT_OK){
                    // 定位的需求改变了，优先级1，跳转到其他页面，本页面接下来的改变不用处理
                    boolean isLocationButtonStateChange = data.getExtras().getBoolean(EditCityActivity.LOCATION_BUTTON_STATE_CHANGE, false);
                    if (isLocationButtonStateChange){
                        boolean locationButtonState = QueryPreferences.getStoreLocationButtonState(MyApplication.getContext());
                        String locationCityName = QueryPreferences.getStoreLocationCityName(MyApplication.getContext());
                        if (locationButtonState){
                            //如果现在是“打开”，说明原来“关闭”，那么没有 oldCity，直接加载 locationCity，有没有城市改变了呢？
                            Intent intent = MainActivity.newIntent(MyApplication.getContext(), locationCityName);
                            startActivity(intent);
                            finish();
                            return;
                        } else {
                            //如果现在是“关闭”，说明原来“打开”，那么有 oldCity 需要删除
                            WeatherLab.get(MyApplication.getContext()).deleteWeatherInfo(WeatherLab.get(MyApplication.getContext()).getCityWithCityName(locationCityName).getId());
                            Intent intent = MainActivity.newIntent(MyApplication.getContext(), null);
                            startActivity(intent);
                            finish();
                            return;
                        }
                    }

                    // 定位的城市改变了，优先级2，跳转到其他页面，本页面接下来的改变不用处理
                    boolean isLocationCityChange = data.getExtras().getBoolean(EditCityActivity.LOCATION_CITY_CHANGE, false);
                    String newCity = data.getStringExtra(EditCityActivity.LOCATION_CITY_NEW);
                    String oldCity = data.getStringExtra(EditCityActivity.LOCATION_CITY_OLD);
                    if (isLocationCityChange){
                        //删除 oldCity
                        WeatherLab.get(MyApplication.getContext()).
                                deleteWeatherInfo(WeatherLab.get(MyApplication.getContext()).getCityWithCityName(oldCity).getId());
                        //加载 newCity
                        Intent intent = MainActivity.newIntent(MyApplication.getContext(), newCity);
                        startActivity(intent);
                        finish();
                        return;
                    }

                    // 有城市被删除，优先级3
                    boolean isDeleteSome = data.getExtras().getBoolean(EditCityActivity.IS_DELETE_SOME, false);
                    if (isDeleteSome){
                        updateNavCityList();
                        weatherPagerFragment.notifySetChange();
                    }

                    // 点击了某个城市，优先级4
                    String clickCityId = data.getExtras().getString(EditCityActivity.SELECT_CITY_ID, null);
                    weatherPagerFragment.onCurrentPagerItemChange(clickCityId, mWeatherInfoIsUpdated);
                }
                break;
        }
    }
}
