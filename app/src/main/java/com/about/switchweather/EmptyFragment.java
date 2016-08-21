package com.about.switchweather;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 跃峰 on 2016/8/21.
 */
public class EmptyFragment extends Fragment {

    public static EmptyFragment newInstance(){
        return new EmptyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_info, container,false);

        return view;
    }
}
