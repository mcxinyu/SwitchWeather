package com.about.switchweather;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.about.switchweather.Model.City;
import com.about.switchweather.Util.SortedListAdapter;
import com.about.switchweather.Util.WeatherLab;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/30.
 */
public class SearchCityFragment extends Fragment implements WeatherActivity.Callbacks{
    private static final Comparator<City> ALPHABETICAL_COMPARATOR = new Comparator<City>() {
        @Override
        public int compare(City city1, City city2) {
            return city1.getId().compareTo(city2.getId());
        }
    };
    private static SearchAdapter mSearchAdapter;
    private static List<City> mCityList;
    private static RecyclerView mSearchRecyclerView;

    public static SearchCityFragment newInstance() {
        Bundle args = new Bundle();

        SearchCityFragment fragment = new SearchCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityList = WeatherLab.get(MyApplication.getContext()).getCityList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);
        mSearchRecyclerView = (RecyclerView) view.findViewById(R.id.search_view_recycler_view);
        //mSearchRecyclerView.setItemAnimator(new DefaultItemAnimator());
        // improve performance if you know that changes in content do not change the size of the RecyclerView
        //mSearchRecyclerView.setHasFixedSize(true);
        mSearchRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mSearchAdapter = new SearchAdapter(MyApplication.getContext(), City.class, ALPHABETICAL_COMPARATOR);
        mSearchRecyclerView.setAdapter(mSearchAdapter);
        mSearchAdapter.edit()
                .add(mCityList)
                .commit();

        return view;
    }

    private class SearchAdapter extends SortedListAdapter<City> {

        public SearchAdapter(Context context, Class<City> itemClass, Comparator<City> comparator) {
            super(context, itemClass, comparator);
        }

        @Override
        protected ViewHolder<? extends City> onCreateViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
            com.about.switchweather.databinding.ItemSearchCityBinding binding =
                    com.about.switchweather.databinding.ItemSearchCityBinding.inflate(inflater, parent, false);
            return new SearchHolder(binding);
        }

        @Override
        protected boolean areItemsTheSame(City item1, City item2) {
            return item1.getId().equals(item2.getId());
        }

        @Override
        protected boolean areItemContentsTheSame(City oldItem, City newItem) {
            return oldItem.equals(newItem);
        }
    }

    private class SearchHolder extends SortedListAdapter.ViewHolder<City> implements View.OnClickListener{

        private final com.about.switchweather.databinding.ItemSearchCityBinding mBinding;

        public SearchHolder(com.about.switchweather.databinding.ItemSearchCityBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        @Override
        protected void performBind(City city) {
            mBinding.setCity(city);
        }

        @Override
        public void onClick(View v) {
            // 添加城市
        }
    }

    private static List<City> filter(List<City> cityList, String query){
        List<City> filteredList = new ArrayList<>();
        for (City city : cityList) {
            String cityName = city.getCity();
            if (cityName.contains(query)){
                filteredList.add(city);
            }
        }
        return filteredList;
    }

    @Override
    public void onCurrentPagerItemChange(String cityId, boolean updated) {
        // 由 WeatherPagerFragment 完成
    }

    @Override
    public boolean onQueryTextChange(String query) {
        List<City> filteredList = filter(mCityList, query);
        mSearchAdapter.edit()
                .replaceAll(filteredList)
                .commit();
        mSearchRecyclerView.scrollToPosition(0);
        return true;
    }
}
