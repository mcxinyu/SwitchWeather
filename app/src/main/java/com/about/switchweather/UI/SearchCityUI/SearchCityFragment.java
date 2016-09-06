package com.about.switchweather.UI.SearchCityUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.about.switchweather.UI.MainUI.MainActivity;
import com.about.switchweather.Model.City;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.R;
import com.about.switchweather.Util.SortedListAdapter;
import com.about.switchweather.Util.WeatherLab;
import com.about.switchweather.databinding.SearchCityBinding;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/30.
 */
public class SearchCityFragment extends Fragment {
    public static final String TAG = SearchCityFragment.class.getSimpleName();
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
            SearchCityBinding binding = SearchCityBinding.inflate(inflater, parent, false);
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

        //数据绑定，嘿嘿。这个类是 item_search_city.xml 使用了数据绑定后自动生成的。
        //private final com.about.switchweather.databinding.ItemSearchCityBinding mBinding;
        private SearchCityBinding mBinding;

        public SearchHolder(SearchCityBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        protected void performBind(City city) {
            mBinding.setCity(city);
        }

        @Override
        public void onClick(View v) {
            // 选择城市后直接调用 MainActivity 可以执行加载天气信息并保存。是不是可以改成直接使用 MainEmptyFragment 呢？麻烦！
            Intent intent = MainActivity.newIntent(getActivity(), mBinding.getCity().getCity());
            startActivity(intent);
            getActivity().finish();
        }
    }

    private List<City> filter(List<City> cityList, String query){
        List<City> filteredList = new ArrayList<>();
        for (City city : cityList) {
            final String cityName = city.getCity();
            final String provName = city.getProv();
            if (cityName.contains(query) || provName.contains(query)){
                filteredList.add(city);
            }
        }
        return filteredList;
    }

    public boolean onQueryTextChange(String query) {
        List<City> filteredList = filter(mCityList, query);
        mSearchAdapter.edit()
                .replaceAll(filteredList)
                .commit();
        mSearchRecyclerView.scrollToPosition(0);
        return true;
    }
}
