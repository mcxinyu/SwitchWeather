package com.about.switchweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ToggleButton;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityFragment extends Fragment implements EditCityActivity.Callbacks{
    private RecyclerView mRecyclerView;
    private ToggleButton mLocationToggleButton;
    private ImageButton mAddCityImageButton;
    private EditCityAdapter mEditCityAdapter;

    public static EditCityFragment newInstance() {
        Bundle args = new Bundle();

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_city, container, false);
        intiView(view);

        return view;
    }

    private void intiView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.edit_city_list_recycler_view);
        mLocationToggleButton = (ToggleButton) view.findViewById(R.id.location_toggle_button);
        mAddCityImageButton = (ImageButton) view.findViewById(R.id.add_city_image_button);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mEditCityAdapter = new EditCityAdapter(getActivity());
        mRecyclerView.setAdapter(mEditCityAdapter);
    }

    @Override
    public String onItemClick(View view, int position) {
        return mEditCityAdapter.getCurrentItem(position).getBasicCityId();
    }

    @Override
    public boolean onDeleteButtonClick(View view, int position) {
        mEditCityAdapter.removeItem(position);
        return true;
    }
}
