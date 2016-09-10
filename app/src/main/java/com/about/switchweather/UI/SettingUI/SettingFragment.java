package com.about.switchweather.UI.SettingUI;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import com.about.switchweather.R;

/**
 * Created by 跃峰 on 2016/9/10.
 */
public class SettingFragment extends PreferenceFragment {
    private String TAG;

    public static SettingFragment newInstance() {

        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_general);
    }
}
