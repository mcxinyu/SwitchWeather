package io.github.mcxinyu.switchweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.ui.fragment.SearchCityFragment;
import io.github.mcxinyu.switchweather.ui.fragment.WeatherPagerFragment;

/**
 * Created by 跃峰 on 2016/8/28.
 */
public class WeatherActivity extends SingleFragmentActivity
        implements WeatherPagerFragment.Callbacks,
        SearchCityFragment.Callbacks {
    private static final String TAG = WeatherActivity.class.getSimpleName();
    private static final String EXTRA_WEATHER_CITY_ID = "weather_city_id";
    private static final int REQUEST_CODE_START_EDIT_CITY = 0;

    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private List<CityBean> mCityList;

    private WeatherPagerFragment mPagerFragment;

    /**
     * @param context
     * @param cityId
     * @return
     */
    public static Intent newIntent(Context context, String cityId) {
        Intent intent = new Intent(context, WeatherActivity.class);
        intent.putExtra(EXTRA_WEATHER_CITY_ID, cityId);
        return intent;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_drawer_fragment;
    }

    @Override
    public Fragment createFragment() {
        String cityId = getIntent().getStringExtra(EXTRA_WEATHER_CITY_ID);

        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();

        if (cityId == null) {
            if (mCityList.size() == 0) {
                startActivity(SearchCityActivity.newIntent(this));
                return null;
            } else {
                mPagerFragment = WeatherPagerFragment.newInstance(null);
            }
        } else {
            mPagerFragment = WeatherPagerFragment.newInstance(cityId);
        }
        return mPagerFragment;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCityList.size() == 0) {
            finish();
        }
        ButterKnife.bind(this);

        initToolbar();
        initDrawer();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // checkDataValidity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkDataValidity();
        reSetCurrentItem();
    }

    private void reSetCurrentItem() {
        String cityId = getIntent().getStringExtra(EXTRA_WEATHER_CITY_ID);
        mPagerFragment.onCurrentPagerItemChange(cityId);
    }

    /**
     * 检查城市列表是否为空
     */
    private void checkDataValidity() {
        List<CityBean> cityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        if (mCityList.size() == cityList.size() && mCityList.containsAll(cityList)) {
            return;
        }
        mCityList = cityList;

        if (mCityList.isEmpty()) {
            startActivity(SearchCityActivity.newIntent(this));
            finish();
        } else {
            updateNavCityList();
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            exitApp();
            super.onBackPressed();
        }
    }

    @Override
    protected void setStatusBar() {
        // StatusBarUtil.setTranslucentForDrawerLayout(WeatherActivity.this, (DrawerLayout) findViewById(R.id.drawer_layout));
        // StatusBarUtil.setTranslucentForCoordinatorLayout(WeatherActivity.this, 0);
    }

    private void initToolbar() {
        mToolbarTitle.setText("");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    private void initDrawer() {
        //声明mDrawerToggle对象,其中R.string.open和R.string.close简单可以用"open"和"close"替代
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.app_name, R.string.app_name);
        //实现箭头和三条杠图案切换和抽屉拉合的同步
        drawerToggle.syncState();
        //监听实现侧边栏的拉开和闭合,即抽屉drawer的闭合和打开
        mDrawerLayout.addDrawerListener(drawerToggle);

        mNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            // DrawerLayout 菜单选择的监听
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_edit_city:
                        // 编辑城市的入口
                        Intent editCityIntent = EditCityActivity.newIntent(WeatherActivity.this);
                        startActivityForResult(editCityIntent, REQUEST_CODE_START_EDIT_CITY);
                        break;
                    case R.id.menu_settings:
                        // 设置项的入口
                        Intent settingIntent = SettingActivity.newIntent(WeatherActivity.this);
                        startActivity(settingIntent);
                        break;
                    case R.id.menu_feedback:
                        // 反馈项的入口
                        sendEmailFeedfack();
                        break;
                    default:
                        break;
                }
                // 点击 item 后关闭 DrawerLayout
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        View headerView = mNavView.getHeaderView(0);
        final TextView emailTextView = (TextView) headerView.findViewById(R.id.textView);
        emailTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmailFeedfack();
            }
        });

        updateNavCityList();
    }

    private void sendEmailFeedfack() {
        Intent feedbackIntent = new Intent(Intent.ACTION_SENDTO);
        String uriText = "mailto:mcxinyu@gmail.com" +
                "?subject=" + getString(R.string.email_subject) +
                "&body=";
        uriText = uriText.replace(" ", "%20");
        Uri uri = Uri.parse(uriText);
        feedbackIntent.setData(uri);
        startActivity(feedbackIntent);
    }

    private void updateNavCityList() {
        if (mCityList == null) {
            return;
        }
        for (int i = 0; i < mCityList.size(); i++) {
            mNavView.getMenu().removeItem(R.id.menu_group_city_list + i + 123);
        }

        if (mCityList.size() > 0) {
            for (int i = 0; i < mCityList.size(); i++) {
                final int position = i;
                mNavView.getMenu().add(R.id.menu_group_city_list,
                        R.id.menu_group_city_list + i + 123,
                        i + 100,
                        mCityList.get(i).getCityName()).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        mPagerFragment.onCurrentPagerItemChange(
                                mCityList.get(position).getCityId());
                        // mToolbar.setTitle(mCityList.get(position).getBasicCity());
                        return false;
                    }
                });
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                // 添加城市的入口
                Intent addCityIntent = SearchCityActivity.newIntent(WeatherActivity.this);
                startActivity(addCityIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_START_EDIT_CITY:
                if (resultCode == RESULT_OK) {
                    // 点击了某个城市
                    String clickCityId = data.getExtras().getString(EditCityActivity.CITY_SELECTED, null);
                    mPagerFragment.onCurrentPagerItemChange(clickCityId);
                }
                break;
        }
    }

    @Override
    public void onPageSelected(String cityName) {
        mToolbarTitle.setText(cityName);
    }

    @Override
    public void onHomeButtonClicked() {

    }
}
