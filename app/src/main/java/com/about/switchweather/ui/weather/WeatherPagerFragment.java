package com.about.switchweather.ui.weather;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.about.switchweather.model.WeatherInfo;
import com.about.switchweather.R;
import com.about.switchweather.ui.MyApplication;
import com.about.switchweather.database.WeatherLab;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/30.
 */
public class WeatherPagerFragment extends Fragment {
    private static final String ARG_WEATHER_CITY_ID = "weather_fragment_city_id";
    private static final String ARG_WEATHER_UPDATED = "weather_updated";

    private WeatherViewPaper mViewPager;
    private List<WeatherInfo> mWeatherInfoList;
    private FragmentManager fragmentManager;
    private String mCityId;
    private boolean mWeatherUpdated;
    private Callbacks mCallbacks;

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
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);

        mCityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);
        mWeatherUpdated = getArguments().getBoolean(ARG_WEATHER_UPDATED);
        fragmentManager = getActivity().getSupportFragmentManager();
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_view_pager, container, false);

        mViewPager = (WeatherViewPaper) view.findViewById(R.id.weather_view_pager_container);
        fragmentManager = getActivity().getSupportFragmentManager();
        WeatherPagerAdapter adapter = new WeatherPagerAdapter(fragmentManager, mWeatherInfoList);
        mViewPager.setAdapter(adapter);

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId().equals(mCityId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCallbacks.onPageSelected(mWeatherInfoList.get(position).getBasicCity());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    public interface Callbacks{
        void onPageSelected(String city);
    }

    public void setCallbacks(Callbacks callbacks){
        mCallbacks = callbacks;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mWeatherUpdated){
            notifySetChange();
        }
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
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < dataList.size(); i++) {
                WeatherFragment weatherFragment = WeatherFragment.newInstance(dataList.get(i).getBasicCityId(), mWeatherUpdated);
                //resetFragmentView(weatherFragment);
                fragments.add(weatherFragment);
            }
            setFragmentList(fragments);
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

    public void resetFragmentView(Fragment fragment) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            View contentView = getActivity().findViewById(android.R.id.content);
            if (contentView != null) {
                ViewGroup rootView;
                rootView = (ViewGroup) ((ViewGroup) contentView).getChildAt(0);
                if (rootView.getPaddingTop() != 0) {
                    rootView.setPadding(0, 0, 0, 0);
                }
            }
            if (fragment.getView() != null) fragment.getView().setPadding(0, getStatusBarHeight(MyApplication.getContext()), 0, 0);
        }
    }

    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
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
