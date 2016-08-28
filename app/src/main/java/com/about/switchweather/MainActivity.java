package com.about.switchweather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import com.about.switchweather.Model.City;
import com.about.switchweather.Model.Condition;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.Util.HeWeatherFetch;
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

public class MainActivity extends SingleFragmentActivity implements MainEmptyFragment.Callbacks {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private List<WeatherInfo> mWeatherInfoList;
    private RecyclerView mSlidingTabRecyclerView;
    private SlidingTabAdapter mAdapter;

    @Override
    public Fragment createFragment() {
        return MainEmptyFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
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

        if (WeatherLab.get(this).getCityList().size() == 0 || WeatherLab.get(this).getConditionBeanList().size() == 0){
            new FetchCondition().execute();
        }
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
            MainFragment fragment = MainFragment.newInstance(mWeatherInfo.getBasicCityId(), true);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            mDrawerLayout.closeDrawers();
        }
    }

    @Override
    public void onFetchWeatherComplete(String cityId, boolean updated) {
        if (WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityId(cityId) == null){
            return;
        }
        MainFragment fragment = MainFragment.newInstance(cityId, updated);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }

    private class FetchCondition extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            List<City> cityList = new HeWeatherFetch().fetchCityList();
            storeCityList(cityList);
            List<Condition> conditions = new HeWeatherFetch().fetchConditionList();
            storeCondition(conditions);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }

    private void storeCondition(List<Condition> conditions) {
        if (conditions == null){
            return;
        }
        WeatherLab.get(this).addConditionBean(conditions);
    }

    private void storeCityList(List<City> cityList) {
        if (cityList == null){
            return;
        }
        WeatherLab.get(this).addCityList(cityList);
    }
}
