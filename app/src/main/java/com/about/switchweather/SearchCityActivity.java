package com.about.switchweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by 跃峰 on 2016/9/2.
 */
public class SearchCityActivity  extends SingleFragmentActivity{
    private Callbacks mCallbacks;
    private SearchView mSearchView;
    private SearchCityFragment searchCityFragment;
    private ActionBar mActionBar;

    public interface Callbacks{
        boolean onQueryTextChange(String query);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, SearchCityActivity.class);
        //intent.putExtra();
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.app_bar_weather;
    }

    @Override
    public Fragment createFragment() {

        searchCityFragment = SearchCityFragment.newInstance();
        try {
            mCallbacks = searchCityFragment;
        } catch (ClassCastException e) {
            throw new ClassCastException("Child fragment must implement BackHandledInterface");
        }

        return searchCityFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_search_city_activity, menu);
        final MenuItem searchItem = menu.findItem(R.id.add_menu_item);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setQueryHint(getString(R.string.search_view_hint_text));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 在这里实现数据的过滤
                mCallbacks.onQueryTextChange(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
