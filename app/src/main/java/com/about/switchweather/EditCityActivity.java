package com.about.switchweather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityActivity extends SingleFragmentActivity implements EditCityAdapter.OnSlidingViewClickListener{
    public static final String IS_DELETE_SOME = "edit_city_activity_is_delete_some";
    public static final String SELECT_CITY_ID = "edit_city_activity_select_city_id";

    private Callbacks mCallbacks;
    private Intent resultIntent;

    public interface Callbacks{
        String onItemClick(View view, int position);
        boolean onDeleteButtonClick(View view, int position);
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
        resultIntent = new Intent();
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
