package com.about.switchweather.UI.SuggestionUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import com.about.switchweather.R;
import com.about.switchweather.UI.SingleFragmentActivity;

import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_COMF;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_CW;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_DRSG;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_FLU;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_SPORT;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.ACTION_UV;
import static com.about.switchweather.UI.SuggestionUI.SuggestionFragment.CITY_NAME;

public class SuggestionActivity extends SingleFragmentActivity {
    private String mCityName;
    private String mAction;

    public static Intent newIntent(Context context, String cityName, String action) {

        Intent intent = new Intent(context, SuggestionActivity.class);
        intent.putExtra(CITY_NAME, cityName);
        intent.putExtra(ACTION, action);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_content_container;
    }

    @Override
    public Fragment createFragment() {
        mCityName = getIntent().getStringExtra(CITY_NAME);
        mAction = getIntent().getStringExtra(ACTION);
        return SuggestionFragment.newInstance(mCityName, mAction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null){
            switch (mAction){
                case ACTION_COMF:
                    toolbar.setTitle(getString(R.string.suggestion_comf));
                    break;
                case ACTION_FLU:
                    toolbar.setTitle(getString(R.string.suggestion_flu));
                    break;
                case ACTION_DRSG:
                    toolbar.setTitle(getString(R.string.suggestion_drsg));
                    break;
                case ACTION_CW:
                    toolbar.setTitle(getString(R.string.suggestion_cw));
                    break;
                case ACTION_SPORT:
                    toolbar.setTitle(getString(R.string.suggestion_sport));
                    break;
                case ACTION_UV:
                    toolbar.setTitle(getString(R.string.suggestion_uv));
                    break;
            }
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }
}
