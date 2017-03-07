package com.about.switchweather.ui.editcity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.about.switchweather.R;
import com.about.switchweather.ui.MyApplication;
import com.about.switchweather.ui.SingleFragmentActivity;
import com.about.switchweather.util.QueryPreferences;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityActivity extends SingleFragmentActivity implements EditCityAdapter.OnSlidingViewClickListener, EditCityFragment.Callbacks{
    public static final String IS_DELETE_SOME = "edit_city_activity_is_delete_some";
    public static final String SELECT_CITY_ID = "edit_city_activity_select_city_id";
    public static final String LOCATION_BUTTON_STATE_CHANGE = "edit_city_activity_location_enable_state_change";
    public static final String LOCATION_CITY_CHANGE = "edit_city_activity_location_city_change";
    public static final String LOCATION_CITY_OLD = "edit_city_activity_location_city_old";
    public static final String LOCATION_CITY_NEW = "edit_city_activity_location_city_new";

    private boolean locationButtonInitialState = QueryPreferences.getStoreLocationButtonState(MyApplication.getContext());

    private Intent resultIntent;
    private ActionBar mActionBar;
    private EditCityFragment editCityFragment;
    private String TAG = "EditCityActivity";

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_content_container;
    }

    @Override
    public Fragment createFragment() {
        editCityFragment = EditCityFragment.newInstance();
        return editCityFragment;
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, EditCityActivity.class);
        //intent.putExtra();
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbar();
        resultIntent = new Intent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        mActionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        if (mActionBar != null) {
            mActionBar.setDisplayHomeAsUpEnabled(true);
            mActionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                // Toolbar 的返回按钮
                finish();
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onItemClick(View view, int position) {
        String cityId = editCityFragment.onItemClick(view, position);
        resultIntent.putExtra(SELECT_CITY_ID, cityId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onDeleteButtonClick(View view, int position) {
        boolean b = editCityFragment.onDeleteButtonClick(view, position);
        resultIntent.putExtra(IS_DELETE_SOME, b);
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onLocationButtonChange(boolean isChecked) {
        if (locationButtonInitialState != isChecked){
            resultIntent.putExtra(LOCATION_BUTTON_STATE_CHANGE, true);
        } else {
            resultIntent.putExtra(LOCATION_BUTTON_STATE_CHANGE, false);
        }
        setResult(RESULT_OK, resultIntent);
    }

    @Override
    public void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity) {
        resultIntent.putExtra(LOCATION_CITY_CHANGE, isLocationCityChange);
        resultIntent.putExtra(LOCATION_CITY_OLD, oldCity);
        resultIntent.putExtra(LOCATION_CITY_NEW, newCity);
        setResult(RESULT_OK, resultIntent);
    }
}
