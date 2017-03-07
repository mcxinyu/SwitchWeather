package com.about.switchweather.util;

import android.util.Log;

import static com.about.switchweather.BuildConfig.*;

/**
 * Created by 跃峰 on 2016/9/7.
 */
public class LogUtils {
    public static final boolean DEBUG = LOG_CALLS;

    public static void i(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.i(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(tag, msg, tr);
        }
    }
}
