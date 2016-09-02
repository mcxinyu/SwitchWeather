package com.about.switchweather;

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
import com.about.switchweather.Util.WeatherLab;

import java.util.List;

/**
 * Created by 跃峰 on 2016/8/30.
 */
public class WeatherPagerFragment extends Fragment implements WeatherActivity.Callbacks {
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
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            
            @Override
            public Fragment getItem(int position) {
                WeatherInfo weatherInfo = mWeatherInfoList.get(position);
                return WeatherFragment.newInstance(weatherInfo.getBasicCityId(), mWeatherUpdated);
            }

            @Override
            public int getCount() {
                return mWeatherInfoList.size();
            }
        });

        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId().equals(mCityId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        return view;
    }

    @Override
    public void onCurrentPagerItemChange(String cityId, boolean updated) {
        mCityId = cityId;
        mWeatherUpdated = updated;
        for (int i = 0; i < mWeatherInfoList.size(); i++) {
            if (mWeatherInfoList.get(i).getBasicCityId().equals(cityId)){
                mViewPager.setCurrentItem(i);
            }
        }
    }
}
