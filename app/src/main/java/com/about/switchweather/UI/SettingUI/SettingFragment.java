package com.about.switchweather.UI.SettingUI;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import com.about.switchweather.R;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

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

        findPreference("setting_about").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new  LibsBuilder().withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR).start(getActivity());
                return false;
            }
        });
    }
}
