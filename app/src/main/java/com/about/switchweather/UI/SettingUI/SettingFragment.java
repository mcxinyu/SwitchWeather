package com.about.switchweather.UI.SettingUI;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.R;
import com.about.switchweather.Service.UpdateWeatherService;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.WeatherUI.WeatherActivity;
import com.about.switchweather.Util.BaiduLocationService.LocationProvider;
import com.about.switchweather.Util.QueryPreferences;
import com.about.switchweather.DataBase.WeatherLab;
import com.about.switchweather.Util.WeatherUtil;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

import java.util.ArrayList;

/**
 * Created by 跃峰 on 2016/9/10.
 */
public class SettingFragment extends PreferenceFragment implements LocationProvider.Callbacks , SharedPreferences.OnSharedPreferenceChangeListener {
    private String TAG;
    private SwitchPreference settingLocationCity;
    private ListPreference settingTemperatureUnit;
    private SwitchPreference settingAutoUpdate;
    private SwitchPreference settingNotificationWidget;
    private SwitchPreference settingPoorAir;
    private SwitchPreference settingSunriseNotification;
    private SwitchPreference settingSunsetNotification;

    private int SDK_PERMISSION_REQUEST = 127;
    private int REQUEST_CODE_NOTIFICATION_WIDGET = 128;
    private LocationProvider mLocationProvider;

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
        mLocationProvider = new LocationProvider(MyApplication.getContext());
        mLocationProvider.setCallbacks(this);

        initPreferences();
    }

    private void initPreferences() {
        settingLocationCity = (SwitchPreference) findPreference(QueryPreferences.SETTING_LOCATION_CITY);
        settingTemperatureUnit = (ListPreference) findPreference(QueryPreferences.SETTING_TEMPERATURE_UNIT);
        settingAutoUpdate = (SwitchPreference) findPreference(QueryPreferences.SETTING_AUTO_UPDATE_TEXT);
        settingNotificationWidget = (SwitchPreference) findPreference(QueryPreferences.SETTING_NOTIFICATION_WIDGET);
        settingPoorAir = (SwitchPreference) findPreference(QueryPreferences.SETTING_POOR_AIR_TEXT);
        settingSunriseNotification = (SwitchPreference) findPreference(QueryPreferences.SETTING_SUNRISE_NOTIFICATION_TEXT);
        settingSunsetNotification = (SwitchPreference) findPreference(QueryPreferences.SETTING_SUNSET_NOTIFICATION_TEXT);

        findPreference(QueryPreferences.SETTING_ABOUT).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                new LibsBuilder().withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR).start(getActivity());
                return false;
            }
        });

        findPreference(QueryPreferences.SETTING_FEEDBACK).setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
                String uriText = "mailto:mcxinyu@gmail.com" +
                        "?subject=" + getString(R.string.email_subject) +
                        "&body=";
                uriText = uriText.replace(" ", "%20");
                Uri uri = Uri.parse(uriText);
                feedbackIntent.setData(uri);
                startActivity(feedbackIntent);
                return false;
            }
        });

        initPreferenceSummary();
        initNotification();
    }

    private void initNotification() {
        setNotificationWidget(settingNotificationWidget.isChecked());
    }

    private void initPreferenceSummary() {
        if (settingTemperatureUnit.getValue().equals("F")) {
            settingTemperatureUnit.setSummary(getResources().getString(R.string.fahrenheit));
        } else {
            settingTemperatureUnit.setSummary(getResources().getString(R.string.celsius));
        }

        if (settingLocationCity.isChecked()){
            if (QueryPreferences.getStoreLocationCityName(MyApplication.getContext()) == null) {
                getPermissions();
                mLocationProvider.start();
            } else {
                settingLocationCity.setSummary(QueryPreferences.getStoreLocationCityName(MyApplication.getContext()));
            }
        } else {
            settingLocationCity.setSummary("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // Setup the initial values
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        // Set up a listener whenever a key changes
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        mLocationProvider.unSetCallbacks();
        mLocationProvider.destroy();
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case QueryPreferences.SETTING_LOCATION_CITY:
                if (settingLocationCity.isChecked()) {
                    getPermissions();
                    mLocationProvider.start();
                    settingNotificationWidget.setEnabled(true);
                } else {
                    QueryPreferences.setStoreLocationCityName(getActivity(), "");
                    settingLocationCity.setSummary("");
                    settingNotificationWidget.setChecked(false);
                    settingNotificationWidget.setEnabled(false);
                }
                break;
            case QueryPreferences.SETTING_TEMPERATURE_UNIT:
                if (settingTemperatureUnit.getValue().equals("C")) {
                    settingTemperatureUnit.setSummary(getResources().getString(R.string.celsius));
                }else if (settingTemperatureUnit.getValue().equals("F")){
                    settingTemperatureUnit.setSummary(getResources().getString(R.string.fahrenheit));
                }
                Toast.makeText(MyApplication.getContext(), R.string.next_take_effect, Toast.LENGTH_SHORT).show();
                break;
            case QueryPreferences.SETTING_AUTO_UPDATE_TEXT:
                UpdateWeatherService.setService6HourAlarm(MyApplication.getContext(), settingAutoUpdate.isChecked());
                break;
            case QueryPreferences.SETTING_NOTIFICATION_WIDGET:
                setNotificationWidget(settingNotificationWidget.isChecked());
                break;
            case QueryPreferences.SETTING_POOR_AIR_TEXT:
                setNotificationPoorAir(settingPoorAir.isChecked());
                break;
            case QueryPreferences.SETTING_SUNRISE_NOTIFICATION_TEXT:
                setNotificationSunrise(settingSunriseNotification.isChecked());
                break;
            case QueryPreferences.SETTING_SUNSET_NOTIFICATION_TEXT:
                setNotificationSunset(settingSunsetNotification.isChecked());
                break;
            default:
                break;
        }
    }

    private void setNotificationSunset(boolean checked) {
        // TODO: 2017/2/27 设置日出提醒
    }

    private void setNotificationSunrise(boolean checked) {
        // TODO: 2017/2/27 设置日落提醒
    }

    private void setNotificationPoorAir(boolean show) {
        // TODO: 2017/2/27 设置空气质量提醒
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        if (show){
        } else {
        }
    }

    private void setNotificationWidget(boolean isChecked) {
        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        String locationCityName = QueryPreferences.getStoreLocationCityName(MyApplication.getContext());
        if (isChecked && ((null == locationCityName) || ("".equals(locationCityName)))){
            settingNotificationWidget.setChecked(false);
            if (notificationManager != null) {
                notificationManager.cancel(REQUEST_CODE_NOTIFICATION_WIDGET);
            }
            Toast.makeText(getActivity(), "获取不到定位的城市", Toast.LENGTH_SHORT).show();
            return;
        }
        // TODO: 2017/2/27 只提供定位的城市通知栏？
        if (isChecked && notificationManager != null) {
            WeatherInfo weatherInfo = WeatherLab.get(MyApplication.getContext()).getWeatherInfoWithCityName(locationCityName);
            Resources resources = getResources();

            Intent intent = WeatherActivity.newIntent(MyApplication.getContext(), weatherInfo.getBasicCityId(), true);
            PendingIntent pendingIntent = PendingIntent.getActivity(MyApplication.getContext(), REQUEST_CODE_NOTIFICATION_WIDGET, intent, 0);

            String tmpNow;
            String tmpMin;
            String tmpMax;
            if (settingTemperatureUnit.getValue().equals("F")){
                tmpNow = WeatherUtil.convertCelsius2Fahrenheit(weatherInfo.getNowTmp()) + "°";
                tmpMin = WeatherUtil.convertCelsius2Fahrenheit(weatherInfo.getDailyForecastTmpMin()) + "°";
                tmpMax = WeatherUtil.convertCelsius2Fahrenheit(weatherInfo.getDailyForecastTmpMax()) + "°";
            } else {
                tmpNow = weatherInfo.getNowTmp() + "°";
                tmpMin = weatherInfo.getDailyForecastTmpMin() + "°";
                tmpMax = weatherInfo.getDailyForecastTmpMax() + "°";
            }

            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle()
                    .setBigContentTitle(weatherInfo.getBasicCity() + " " + weatherInfo.getNowCondTxt() + " " + tmpNow)
                    .addLine(tmpMin + "~" + tmpMax);

            Notification notification = new NotificationCompat.Builder(MyApplication.getContext())
                    .setTicker(weatherInfo.getBasicCity() + " " + weatherInfo.getNowCondTxt())
                    .setLargeIcon(BitmapFactory.decodeResource(resources, WeatherUtil.convertIconToRes(weatherInfo.getNowCondCode())))
                    .setSmallIcon(WeatherUtil.convertIconToRes(weatherInfo.getNowCondCode()))
                    .setContentTitle(weatherInfo.getBasicCity() + " " + weatherInfo.getNowCondTxt() + " " + tmpNow)
                    .setContentText(tmpMin + "~" + tmpMax)
                    .setContentIntent(pendingIntent)
                    .setStyle(inboxStyle)
                    .build();

            notification.flags = Notification.FLAG_ONGOING_EVENT; // 设置常驻 Flag

            notificationManager.notify(REQUEST_CODE_NOTIFICATION_WIDGET, notification);
        } else {
            notificationManager.cancel(REQUEST_CODE_NOTIFICATION_WIDGET);
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissions() {
        ArrayList<String> permissions = new ArrayList<>();
        /***
         * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
         */
        // 定位精确位置
        if(!checkSelfPermissions(Manifest.permission.ACCESS_FINE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(!checkSelfPermissions(Manifest.permission.ACCESS_COARSE_LOCATION)){
            permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (permissions.size() > 0) {
            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
        }
    }

    private boolean checkSelfPermissions(String permission){
        return ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (!checkSelfPermissions(permission)) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)){
                return true;
            }else{
                permissionsList.add(permission);
                return false;
            }
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SDK_PERMISSION_REQUEST){
            boolean isGrant = false;
            for (int i = 0; i < permissions.length; i++) {
                if (checkSelfPermissions(permissions[i])){
                    isGrant = true;
                }
            }
            if (isGrant){
                mLocationProvider.start();
            } else {
                settingLocationCity.setChecked(false);
                settingLocationCity.setSummary("");
            }
        }
    }

    @Override
    public void onLocationCityChange(boolean isLocationCityChange, String oldCity, String newCity) {
        settingLocationCity.setSummary(newCity);
    }

    @Override
    public void onLocationComplete(boolean isSuccess, String currentCityName) {
        settingLocationCity.setSummary(currentCityName);
    }

    @Override
    public void onLocationCriteriaException(String description) {
        settingLocationCity.setChecked(false);
        settingLocationCity.setSummary(description);
    }

    @Override
    public void onLocationNetWorkException(String description) {
        settingLocationCity.setChecked(false);
        settingLocationCity.setSummary(description);
    }

    @Override
    public void onLocationError(String description) {
        settingLocationCity.setChecked(false);
        settingLocationCity.setSummary(description);
    }
}
