package io.github.mcxinyu.switchweather.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.util.QueryPreferences;

/**
 * Created by huangyuefeng on 2017/3/7.
 */
public class PreferencesService extends IntentService
        implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = "PreferencesService";

    public static Intent newIntent(Context context) {

        Intent intent = new Intent(context, PreferencesService.class);
        return intent;
    }

    public PreferencesService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case QueryPreferences.SETTING_LOCATION_CITY:

                QueryPreferences.setLocationCityName(this, "");
                // if (settingLocationCity.isChecked()) {
                //     getPermissions();
                //     mLocationProvider.start();
                //     settingNotificationWidget.setEnabled(true);
                // } else {
                //     settingLocationCity.setSummary("");
                //     settingNotificationWidget.setChecked(false);
                //     settingNotificationWidget.setEnabled(false);
                // }
                break;
            case QueryPreferences.SETTING_TEMPERATURE_UNIT:
                // if (settingTemperatureUnit.getValue().equals("C")) {
                //     settingTemperatureUnit.setSummary(getResources().getString(R.string.celsius));
                // }else if (settingTemperatureUnit.getValue().equals("F")){
                //     settingTemperatureUnit.setSummary(getResources().getString(R.string.fahrenheit));
                // }
                Toast.makeText(SwitchWeatherApp.getInstance(), R.string.next_take_effect, Toast.LENGTH_SHORT).show();
                break;
            case QueryPreferences.SETTING_AUTO_UPDATE_TEXT:
                // UpdateWeatherService.setService6HourAlarm(SwitchWeatherApp.getInstance(), settingAutoUpdate.isChecked());
                break;
            case QueryPreferences.SETTING_NOTIFICATION_SERVICE:
                // setNotificationWidget(settingNotificationWidget.isChecked());
                break;
            case QueryPreferences.SETTING_POOR_AIR_TEXT:
                // setNotificationPoorAir(settingPoorAir.isChecked());
                break;
            case QueryPreferences.SETTING_SUNRISE_NOTIFICATION_TEXT:
                // setNotificationSunrise(settingSunriseNotification.isChecked());
                break;
            case QueryPreferences.SETTING_SUNSET_NOTIFICATION_TEXT:
                // setNotificationSunset(settingSunsetNotification.isChecked());
                break;
            default:
                break;
        }
    }
}
