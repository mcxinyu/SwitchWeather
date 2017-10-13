package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment;


public class SuggestionActivity extends SingleFragmentActivity {
    private String mCityName;
    private String mAction;

    public static Intent newIntent(Context context, String cityName, String action) {

        Intent intent = new Intent(context, SuggestionActivity.class);
        intent.putExtra(SuggestionFragment.CITY_NAME, cityName);
        intent.putExtra(SuggestionFragment.ACTION, action);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_container;
    }

    @Override
    public Fragment createFragment() {
        mCityName = getIntent().getStringExtra(SuggestionFragment.CITY_NAME);
        mAction = getIntent().getStringExtra(SuggestionFragment.ACTION);
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
                case SuggestionFragment.ACTION_COMF:
                    toolbar.setTitle(getString(R.string.suggestion_comf));
                    break;
                case SuggestionFragment.ACTION_FLU:
                    toolbar.setTitle(getString(R.string.suggestion_flu));
                    break;
                case SuggestionFragment.ACTION_DRSG:
                    toolbar.setTitle(getString(R.string.suggestion_drsg));
                    break;
                case SuggestionFragment.ACTION_CW:
                    toolbar.setTitle(getString(R.string.suggestion_cw));
                    break;
                case SuggestionFragment.ACTION_SPORT:
                    toolbar.setTitle(getString(R.string.suggestion_sport));
                    break;
                case SuggestionFragment.ACTION_UV:
                    toolbar.setTitle(getString(R.string.suggestion_uv));
                    break;
            }
        }

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
