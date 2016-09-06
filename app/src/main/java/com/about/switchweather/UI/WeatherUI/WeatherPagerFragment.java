package com.about.switchweather.UI.WeatherUI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.R;
import com.about.switchweather.Util.QueryPreferences;
import com.about.switchweather.Util.WeatherLab;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/30.
 */
public class WeatherPagerFragment extends Fragment {
    private static final String ARG_WEATHER_CITY_ID = "weather_fragment_city_id";
    private static final String ARG_WEATHER_UPDATED = "weather_updated";

    private ViewPager mViewPager;
    private List<WeatherInfo> mWeatherInfoList;
    private FragmentManager fragmentManager;
    private String mCityId;
    private boolean mWeatherUpdated;

    public static WeatherPagerFragment newInstance(String cityId, boolean updated) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_ID, cityId);
        args.putBoolean(ARG_WEATHER_UPDATED, updated);

        WeatherPagerFragment fragment = new WeatherPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);
        mWeatherUpdated = getArguments().getBoolean(ARG_WEATHER_UPDATED);
        fragmentManager = getActivity().getSupportFragmentManager();
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        mViewPager = (ViewPager) view.findViewById(R.id.weather_view_pager_container);
        fragmentManager = getActivity().getSupportFragmentManager();
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(fragmentManager, mWeatherInfoList);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId().equals(mCityId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        return view;
    }

    private class WeatherPagerAdapter extends FragmentStatePagerAdapter{
        //使用一个 List 将数据源对应的 Fragment 都缓存起来
        private ArrayList<Fragment> mFragmentList;

        public WeatherPagerAdapter(FragmentManager fragmentManager, List<WeatherInfo> weatherInfoList) {
            super(fragmentManager);
            updateDate(weatherInfoList);
        }

        /**
         * 当有数据源更新的时候，从 List 中取出相应的 Fragment，然后刷新 Adapter
         * @param dataList
         */
        public void updateDate(List<WeatherInfo> dataList) {
            dataList = getLocationCity2ListFirst(dataList);
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                fragments.add(WeatherFragment.newInstance(dataList.get(i).getBasicCityId(), mWeatherUpdated));
            }
            setFragmentList(fragments);
        }

        /**
         * 将定位到的城市在 list 中的位置移到第一位
         * @param weatherInfoList
         * @return
         */
        private List<WeatherInfo> getLocationCity2ListFirst(List<WeatherInfo> weatherInfoList) {
            if (!QueryPreferences.getStoreLocationEnable(MyApplication.getContext())){
                return weatherInfoList;
            }
            String cityId = QueryPreferences.getStoreLocationCityName(MyApplication.getContext());
            if (cityId != null) {
                List<WeatherInfo> list = new ArrayList<>();
                for (int i = 0; i < weatherInfoList.size(); i++) {
                    if (weatherInfoList.get(i).getBasicCityId().equals(cityId)) {
                        list.add(0, weatherInfoList.get(i));
                        break;
                    }
                }
                for (int i = 0; i < weatherInfoList.size(); i++) {
                    if (!weatherInfoList.get(i).getBasicCityId().equals(cityId)) {
                        list.add(weatherInfoList.get(i));
                    }
                }
                weatherInfoList = list;
            }
            return weatherInfoList;
        }

        /**
         * 当数据源中删除某项时，将 List 中对应的 Fragment 也删除，然后刷新 Adapter
         * @param fragmentList
         */
        private void setFragmentList(ArrayList<Fragment> fragmentList) {
            if (this.mFragmentList != null){
                //这个是不是有必要？？
                //for (int i = 0; i < mFragmentList.size(); i++) {
                //    mFragmentList.get(i).onDestroy();
                //}
                mFragmentList.clear();
            }
            this.mFragmentList = fragmentList;
            notifyDataSetChanged();
        }

        @Override
        public int getItemPosition(Object object) {
            // 最简单解决 notifyDataSetChanged() 页面不刷新问题的方法
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return this.mFragmentList.size();
        }
    }

    public void onCurrentPagerItemChange(String cityId, boolean updated) {
        if (cityId == null){
            return;
        }
        mCityId = cityId;
        mWeatherUpdated = updated;
        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId().equals(cityId)){
                mViewPager.setCurrentItem(i);
            }
        }
    }

    public void notifySetChange() {
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
        ((WeatherPagerAdapter) mViewPager.getAdapter()).updateDate(mWeatherInfoList);
    }
}
