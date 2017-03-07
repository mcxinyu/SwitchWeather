package com.about.switchweather.UI.AqiUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.about.switchweather.R;
import com.about.switchweather.UI.SingleFragmentActivity;

import static com.about.switchweather.UI.AqiUI.AqiFragment.CITY_NAME;

public class AqiActivity extends SingleFragmentActivity {
    private String cityName;
    private Toolbar mToolbar;
    private ActionBar mActionBar;

    public static Intent newIntent(Context context, String cityName) {

        Intent intent = new Intent(context, AqiActivity.class);
        intent.putExtra(CITY_NAME, cityName);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_content_container;
    }

    @Override
    public Fragment createFragment() {
        cityName = getIntent().getStringExtra(CITY_NAME);
        return AqiFragment.newInstance(cityName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (mToolbar != null) {
            mToolbar.setTitle(getString(R.string.aqi_title));
        }
        setSupportActionBar(mToolbar);
        mActionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        if (mActionBar != null) {
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeButtonEnabled(true);
        }
    }
}
