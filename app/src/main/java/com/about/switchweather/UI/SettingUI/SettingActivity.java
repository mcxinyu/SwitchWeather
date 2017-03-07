package com.about.switchweather.UI.SettingUI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.about.switchweather.R;
import com.jaeger.library.StatusBarUtil;

/**
 * Created by 跃峰 on 2016/9/10.
 */
public class SettingActivity extends AppCompatActivity{

    private ActionBar mActionBar;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        //intent.putExtra();
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_content_container);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 30);

        getFragmentManager().beginTransaction()
                .add(R.id.fragment_container, SettingFragment.newInstance())
                .commit();

        initToolbar();
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
}
