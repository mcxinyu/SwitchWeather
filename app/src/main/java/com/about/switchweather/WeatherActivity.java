package com.about.switchweather;

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
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

/**
 * Created by 跃峰 on 2016/8/28.
 */
public class WeatherActivity extends SingleFragmentActivity {
    private static final String EXTRA_WEATHER_CITY_ID = "WeatherActivity.Weather_City_id";
    private static final String EXTRA_WEATHER_INFO_UPDATED = "WeatherActivity.Weather_Info_Updated";
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private List<WeatherInfo> mWeatherInfoList;
    private boolean mWeatherInfoIsUpdated;

    private Toolbar mToolbar;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavigationView mNavigationView;
    private String mCityId;

    private Callbacks mCallbacks;
    private ActionBar mActionBar;

    public interface Callbacks{
        void onCurrentPagerItemChange(String cityId, boolean updated);
    }

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

        WeatherPagerFragment weatherPagerFragment = WeatherPagerFragment.newInstance(mCityId, mWeatherInfoIsUpdated);

        try {
            mCallbacks = weatherPagerFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Child fragment must implement BackHandledInterface");
        }

        return weatherPagerFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getAppManager().addActivity(this);

        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();

        initToolbar();
        initDrawer();
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            AppManager.getAppManager().AppExit(MyApplication.getContext());
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
                        Toast.makeText(MyApplication.getContext(), "click", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_settings:
                        break;
                    case R.id.nav_feedback:
                        break;
                }
                // 点击 item 后关闭 DrawerLayout
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            final int position = i;
            mNavigationView.getMenu().add(R.id.menu_group_city_list, i, i+100, mWeatherInfoList.get(i).getBasicCity()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (mCallbacks != null) {
                        mCallbacks.onCurrentPagerItemChange(mWeatherInfoList.get(position).getBasicCityId(), mWeatherInfoIsUpdated);
                    }
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
                Intent intent = SearchCityActivity.newIntent(this);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
