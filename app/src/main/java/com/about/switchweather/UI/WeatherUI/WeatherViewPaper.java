package com.about.switchweather.UI.WeatherUI;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by huangyuefeng on 2017/2/24.
 */

public class WeatherViewPaper extends ViewPager {

    public WeatherViewPaper(Context context) {
        super(context);
    }

    public WeatherViewPaper(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (Exception e){
            return true;
        }
    }
}
