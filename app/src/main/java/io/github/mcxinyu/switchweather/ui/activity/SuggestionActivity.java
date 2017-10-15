package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.model.HeWeatherModel;
import io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment;

import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.ACTION;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.CITY_NAME;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.DAILY_FORECAST_BEAN;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.NOW_BEAN;
import static io.github.mcxinyu.switchweather.ui.fragment.SuggestionFragment.SUGGESTION_BEAN;


public class SuggestionActivity extends SingleFragmentActivity {
    private String mAction;

    public static Intent newIntent(Context context,
                                   String cityName,
                                   HeWeatherModel.HeWeather5Bean.SuggestionBean suggestionBean,
                                   HeWeatherModel.HeWeather5Bean.NowBean nowBean,
                                   HeWeatherModel.HeWeather5Bean.DailyForecastBean dailyForecastBean,
                                   String action) {

        Intent intent = new Intent(context, SuggestionActivity.class);
        intent.putExtra(CITY_NAME, cityName);
        intent.putExtra(SUGGESTION_BEAN, suggestionBean);
        intent.putExtra(NOW_BEAN, nowBean);
        intent.putExtra(DAILY_FORECAST_BEAN, dailyForecastBean);
        intent.putExtra(SuggestionFragment.ACTION, action);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_container;
    }

    @Override
    public Fragment createFragment() {
        String cityName = getIntent().getStringExtra(CITY_NAME);
        HeWeatherModel.HeWeather5Bean.SuggestionBean suggestionBean = getIntent().getParcelableExtra(SUGGESTION_BEAN);
        HeWeatherModel.HeWeather5Bean.NowBean nowBean = getIntent().getParcelableExtra(NOW_BEAN);
        HeWeatherModel.HeWeather5Bean.DailyForecastBean dailyForecastBean = getIntent().getParcelableExtra(DAILY_FORECAST_BEAN);
        mAction = getIntent().getStringExtra(ACTION);
        return SuggestionFragment.newInstance(cityName, suggestionBean, nowBean, dailyForecastBean, mAction);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            switch (mAction) {
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
