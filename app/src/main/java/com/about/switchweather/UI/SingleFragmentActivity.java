package com.about.switchweather.ui;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import com.about.switchweather.R;
import com.jaeger.library.StatusBarUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/8/20.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity {
    public abstract Fragment createFragment();

    protected static final List<SingleFragmentActivity> mActivities = new LinkedList<>();

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary), 30);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities){
            mActivities.add(this);
        }
        setContentView(getLayoutResId());
        setStatusBar();

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container);

        if (fragment == null){
            fragment = createFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities){
            mActivities.remove(this);
        }
    }

    public static void exitApp(){
        List<SingleFragmentActivity> copyActivities;
        synchronized (mActivities){
            // 集合遍历的过程中不能移除元素，所以需要复制到新集合中
            copyActivities = mActivities;
        }
        for (SingleFragmentActivity activity : copyActivities) {
            //调用 finish()，会指定 onDestroy()
            activity.finish();
        }
    }
}
