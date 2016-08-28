package com.about.switchweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

/**
 * Created by 跃峰 on 2016/8/28.
 */
public class WeatherPagerActivity extends AppCompatActivity {
    private static final String EXTRA_WEATHER_CITY_ID = "WeatherPagerActivity.Weather_City_id";
    private ViewPager mViewPager;
    private List<WeatherInfo> mWeatherInfoList;

    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private RecyclerView mSlidingTabRecyclerView;
    private SlidingTabAdapter mAdapter;
    private String cityId;

    public static Intent newIntent(Context context, String cityId) {
        Intent intent = new Intent(context, WeatherPagerActivity.class);
        intent.putExtra(EXTRA_WEATHER_CITY_ID, cityId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager_fragment);
        cityId = (String) getIntent().getSerializableExtra(EXTRA_WEATHER_CITY_ID);

        initSlidingTab();

        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
        mViewPager = (ViewPager) findViewById(R.id.weather_view_pager);

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                WeatherInfo weatherInfo = mWeatherInfoList.get(position);
                return WeatherFragment.newInstance(weatherInfo.getBasicCityId(), true);
            }

            @Override
            public int getCount() {
                return mWeatherInfoList.size();
            }
        });

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId() == cityId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }

    private void initSlidingTab() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle("");
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        mActionBar.setDefaultDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mSlidingTabRecyclerView = (RecyclerView) findViewById(R.id.drawer_site_recycler_view);
        mSlidingTabRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));

        mAdapter = new SlidingTabAdapter();
        mSlidingTabRecyclerView.setAdapter(mAdapter);

        mToolbar.setNavigationIcon(R.drawable.ic_select_icon);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    private class SlidingTabAdapter extends RecyclerView.Adapter<SlidingTabHolder>{

        @Override
        public SlidingTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MyApplication.getContext());
            View view = inflater.inflate(R.layout.item_site_sliding_tab, parent, false);
            return new SlidingTabHolder(view);
        }

        @Override
        public void onBindViewHolder(SlidingTabHolder holder, int position) {
            WeatherInfo weatherInfo = mWeatherInfoList.get(position);
            holder.bindCity(weatherInfo);
        }

        @Override
        public int getItemCount() {
            return mWeatherInfoList.size();
        }
    }

    private class SlidingTabHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private WeatherInfo mWeatherInfo;
        private TextView mCityTextView;

        public SlidingTabHolder(View itemView) {
            super(itemView);
            mCityTextView = (TextView) itemView.findViewById(R.id.sliding_site_name_text_view);
            itemView.setOnClickListener(this);
        }

        public void bindCity(WeatherInfo weatherInfo){
            mWeatherInfo = weatherInfo;
            mCityTextView.setText(mWeatherInfo.getBasicCity());
        }

        @Override
        public void onClick(View v) {
            Intent intent = WeatherPagerActivity.newIntent(MyApplication.getContext(), mWeatherInfo.getBasicCityId());
            startActivity(intent);
            mDrawerLayout.closeDrawers();
        }
    }
}
