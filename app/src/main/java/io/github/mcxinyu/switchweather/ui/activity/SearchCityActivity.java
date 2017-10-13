package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.ui.fragment.SearchCityFragment;


/**
 * Created by 跃峰 on 2016/9/2.
 */
public class SearchCityActivity extends SingleFragmentActivity
        implements SearchCityFragment.Callbacks {
    private SearchCityFragment mSearchFragment;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchCityActivity.class);
        //intent.putExtra();
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.content_container;
    }

    @Override
    public Fragment createFragment() {
        mSearchFragment = SearchCityFragment.newInstance(true);
        return mSearchFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        if (!mSearchFragment.onActivityBackPress()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onHomeButtonClicked() {
        finish();
    }
}
