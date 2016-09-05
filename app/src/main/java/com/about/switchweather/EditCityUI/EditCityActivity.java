package com.about.switchweather.EditCityUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.about.switchweather.UI.AppManager;
import com.about.switchweather.R;
import com.about.switchweather.UI.SingleFragmentActivity;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityActivity extends SingleFragmentActivity implements EditCityAdapter.OnSlidingViewClickListener{
    public static final String IS_DELETE_SOME = "edit_city_activity_is_delete_some";
    public static final String SELECT_CITY_ID = "edit_city_activity_select_city_id";

    private Callbacks mCallbacks;
    private Intent resultIntent;
    private ActionBar mActionBar;

    public interface Callbacks{
        String onItemClick(View view, int position);
        boolean onDeleteButtonClick(View view, int position);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_weather;
    }

    @Override
    public Fragment createFragment() {
        EditCityFragment editCityFragment = EditCityFragment.newInstance();
        try {
            mCallbacks = editCityFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Child fragment must implement BackHandledInterface");
        }
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
        AppManager.getAppManager().addActivity(this);

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
        String cityId = mCallbacks.onItemClick(view, position);
        resultIntent.putExtra(SELECT_CITY_ID, cityId);
        sendResult();
        finish();
    }

    @Override
    public void onDeleteButtonClick(View view, int position) {
        boolean b = mCallbacks.onDeleteButtonClick(view, position);
        resultIntent.putExtra(IS_DELETE_SOME, b);
        sendResult();
    }

    private void sendResult() {
        this.setResult(RESULT_OK, resultIntent);
    }
}
