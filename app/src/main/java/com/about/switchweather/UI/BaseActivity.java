package com.about.switchweather.UI;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/9/6.
 */
public class BaseActivity extends AppCompatActivity {
    protected static final List<BaseActivity> mActivities = new LinkedList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        synchronized (mActivities){
            mActivities.add(this);
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
        List<BaseActivity> copyActivities;
        synchronized (mActivities){
            // 集合遍历的过程中不能移除元素，所以需要复制到新集合中
            copyActivities = mActivities;
        }
        for (BaseActivity activity : copyActivities) {
            //调用 finish()，会 指定 onDestroy()
            activity.finish();
        }
    }
}
