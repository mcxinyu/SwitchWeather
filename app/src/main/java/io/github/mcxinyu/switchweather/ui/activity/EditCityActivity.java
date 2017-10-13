package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.List;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.ui.adapter.EditCityAdapter;
import io.github.mcxinyu.switchweather.ui.fragment.EditCityFragment;


/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityActivity extends SingleFragmentActivity
        implements EditCityAdapter.OnSlidingViewClickListener,
        EditCityFragment.Callbacks {
    public static final String IS_DATA_SET_CHANGED = "edit_city_activity_data_set_changed";
    public static final String CITY_SELECTED = "edit_city_activity_city_selected";

    private Intent resultIntent;
    private List<CityBean> mCityList;

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_container;
    }

    @Override
    public Fragment createFragment() {
        EditCityFragment editCityFragment = EditCityFragment.newInstance();
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
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        resultIntent = new Intent();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        // 给左上角图标的左边加上一个返回的图标
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (mCityList.isEmpty()) {
                    showNoCityDialog();
                } else {
                    finish();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mCityList.isEmpty()) {
            showNoCityDialog();
        } else {
            super.onBackPressed();
        }
    }

    private void showNoCityDialog() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.at_least_add_one_city))
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exitApp();
                    }
                })
                .show();
    }

    @Override
    public void onCitySelected(String cityId) {
        resultIntent.putExtra(CITY_SELECTED, cityId);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onDataSetChanged() {
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        resultIntent.putExtra(IS_DATA_SET_CHANGED, true);
        setResult(RESULT_OK, resultIntent);
    }
}
