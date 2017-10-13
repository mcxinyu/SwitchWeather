package io.github.mcxinyu.switchweather.ui.fragment;

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

import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.ui.widget.WeatherViewPaper;


/**
 * Created by 跃峰 on 2016/8/30.
 */
public class WeatherPagerFragment extends Fragment {
    private static final String ARG_WEATHER_CITY_ID = "weather_fragment_city_id";

    @BindView(R.id.weather_view_pager_container)
    WeatherViewPaper mWeatherViewPager;
    private Unbinder unbinder;

    private List<CityBean> mCityList;
    private String mCityId;
    private Callbacks mCallbacks;

    public static WeatherPagerFragment newInstance(String cityId) {

        Bundle args = new Bundle();
        args.putSerializable(ARG_WEATHER_CITY_ID, cityId);

        WeatherPagerFragment fragment = new WeatherPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface Callbacks {
        void onPageSelected(String cityName);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Hosting Activity must implement Interface");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);

        mCityId = (String) getArguments().getSerializable(ARG_WEATHER_CITY_ID);

        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_view_pager, container, false);
        unbinder = ButterKnife.bind(this, view);

        WeatherPagerAdapter adapter = new WeatherPagerAdapter(getChildFragmentManager(), mCityList);
        mWeatherViewPager.setAdapter(adapter);

        if (mCityId != null) {
            for (int i = 0; i < mCityList.size(); i++) {
                if (mCityList.get(i).getCityId().equals(mCityId)) {
                    mWeatherViewPager.setCurrentItem(i);
                    mCallbacks.onPageSelected(mCityList.get(i).getCityName());
                    break;
                }
            }
        } else {
            mCallbacks.onPageSelected(mCityList.get(0).getCityName());
        }

        mWeatherViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCityId = mCityList.get(position).getCityId();
                mCallbacks.onPageSelected(mCityList.get(position).getCityName());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkDataSetChanged()) {
            notifySetChange();
        }
    }

    private class WeatherPagerAdapter extends FragmentStatePagerAdapter {
        //使用一个 List 将数据源对应的 Fragment 都缓存起来
        private ArrayList<Fragment> mFragmentList;

        public WeatherPagerAdapter(FragmentManager fragmentManager, List<CityBean> cityList) {
            super(fragmentManager);
            updateDate(cityList);
        }

        /**
         * 当有数据源更新的时候，从 List 中取出相应的 Fragment，然后刷新 Adapter
         *
         * @param cityList
         */
        public void updateDate(List<CityBean> cityList) {
            ArrayList<Fragment> fragments = new ArrayList<>();
            for (int i = 0; i < cityList.size(); i++) {
                WeatherFragment weatherFragment = WeatherFragment.newInstance(cityList.get(i).getCityId());
                //resetFragmentView(weatherFragment);
                fragments.add(weatherFragment);
            }
            setFragmentList(fragments);
        }

        /**
         * 当数据源中删除某项时，将 List 中对应的 Fragment 也删除，然后刷新 Adapter
         *
         * @param fragmentList
         */
        private void setFragmentList(ArrayList<Fragment> fragmentList) {
            if (this.mFragmentList != null) {
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
            if (fragment.getView() != null) fragment.getView()
                    .setPadding(0, getStatusBarHeight(SwitchWeatherApp.getInstance()), 0, 0);
        }
    }

    private static int getStatusBarHeight(Context context) {
        // 获得状态栏高度
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    /**
     * 让 activity 调用的方法，当外部改变当前 pager index 的时候 fragment 做些改变
     *
     * @param cityId
     */
    public void onCurrentPagerItemChange(String cityId) {
        if (cityId == null) {
            return;
        }
        mCityId = cityId;
        for (int i = 0; i < mCityList.size(); i++) {
            if (mCityList.get(i).getCityId().equals(cityId)) {
                mWeatherViewPager.setCurrentItem(i);
            }
        }
    }

    public boolean checkDataSetChanged() {
        List<CityBean> list = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        return !(mCityList.size() == list.size() && mCityList.containsAll(list));
    }

    public void notifySetChange() {
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        ((WeatherPagerAdapter) mWeatherViewPager.getAdapter()).updateDate(mCityList);
    }
}
