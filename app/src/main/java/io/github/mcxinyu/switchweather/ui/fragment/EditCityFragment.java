package io.github.mcxinyu.switchweather.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.baidu.location.BDLocation;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.touch.OnItemMoveListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.database.dao.CityBean;
import io.github.mcxinyu.switchweather.database.dao.CityBeanDao;
import io.github.mcxinyu.switchweather.model.V5City;
import io.github.mcxinyu.switchweather.ui.activity.SearchCityActivity;
import io.github.mcxinyu.switchweather.ui.activity.WeatherActivity;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by 跃峰 on 2016/9/3.
 */
public class EditCityFragment extends BaseLocationFragment {
    private final int SDK_PERMISSION_REQUEST = 127;
    @BindView(R.id.edit_city_list_recycler_view)
    SwipeMenuRecyclerView mRecyclerView;
    @BindView(R.id.location_toggle_button)
    ToggleButton mLocationToggleButton;
    @BindView(R.id.current_city_text_view)
    TextView mCurrentCityTextView;
    @BindView(R.id.add_city_image_button)
    ImageButton mAddCityImageButton;
    Unbinder unbinder;

    private Callbacks mCallbacks;
    private EditCityAdapter mEditCityAdapter;
    private String locationCityId;
    private List<CityBean> mCityList;

    @Override
    protected void onReceiveLocationError() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface Callbacks {
        void onDataSetChanged();
    }

    public static EditCityFragment newInstance() {
        Bundle args = new Bundle();

        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Hosting Activity must implement Interface");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        intiView();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    private void intiView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setSwipeMenuCreator(new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.dp_100);
                int height = ViewGroup.LayoutParams.MATCH_PARENT;
                SwipeMenuItem deleteItem = new SwipeMenuItem(getContext())
                        .setBackground(R.drawable.selector_red)
                        .setImage(R.drawable.ic_action_delete)
                        .setText(getString(R.string.delete_text))
                        .setTextColor(Color.WHITE)
                        .setWeight(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);
            }
        });
        mRecyclerView.setSwipeItemClickListener(new SwipeItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                startActivity(WeatherActivity.newIntent(getContext(), mCityList.get(position).getCityId()));
            }
        });
        mRecyclerView.setSwipeMenuItemClickListener(new SwipeMenuItemClickListener() {
            @Override
            public void onItemClick(SwipeMenuBridge menuBridge) {
                menuBridge.closeMenu();
                if (menuBridge.getDirection() == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                    if (menuBridge.getPosition() == 0) {
                        SwitchWeatherApp.getDaoInstance().getCityBeanDao()
                                .delete(mCityList.get(menuBridge.getAdapterPosition()));
                        updateCityList();
                    }
                }
            }
        });
        mRecyclerView.setOnItemMoveListener(new OnItemMoveListener() {
            @Override
            public boolean onItemMove(RecyclerView.ViewHolder srcHolder, RecyclerView.ViewHolder targetHolder) {
                return false;
            }

            @Override
            public void onItemDismiss(RecyclerView.ViewHolder srcHolder) {

            }
        });
        mEditCityAdapter = new EditCityAdapter(getActivity());
        mRecyclerView.setAdapter(mEditCityAdapter);

        mAddCityImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchCityActivity.newIntent(getActivity());
                startActivity(intent);
            }
        });

        mLocationToggleButton.setChecked(QueryPreferences.getLocationButtonState(getActivity()));
        if (mLocationToggleButton.isChecked()) {
            CityBean cityBean = SwitchWeatherApp.getDaoInstance()
                    .getCityBeanDao()
                    .queryBuilder()
                    .where(CityBeanDao.Properties.IsLocation.eq(1))
                    .unique();
            if (cityBean != null) {
                // 先显示旧地址
                locationCityId = cityBean.getCityId();
                mCurrentCityTextView.setText(cityBean.getCityName());
                // 尝试定位看有新地址么？
                requestLocationPermission();
            }
        } else {
            mCurrentCityTextView.setText("");
        }

        mLocationToggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                QueryPreferences.setLocationButtonState(getActivity(), isChecked);
                if (isChecked) {
                    requestLocationPermission();
                } else {
                    mLocationService.stop();
                    mCurrentCityTextView.setText("");
                    CityBean cityBean = SwitchWeatherApp.getDaoInstance()
                            .getCityBeanDao()
                            .queryBuilder()
                            .where(CityBeanDao.Properties.IsLocation.eq(1))
                            .unique();
                    if (cityBean != null) {
                        SwitchWeatherApp.getDaoInstance()
                                .getCityBeanDao()
                                .delete(cityBean);
                    }
                }
                mCallbacks.onDataSetChanged();
            }
        });
    }

    private void updateCityList() {
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
        mCallbacks.onDataSetChanged();
        mEditCityAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onReceiveLocationInfo(final BDLocation location) {
        if (location != null) {
            WeatherApiHelper.searchCity(location.getLongitude() + "," + location.getLatitude())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .limit(1)
                    .subscribe(new Observer<V5City>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "获取城市信息失败，请稍后重试。", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNext(V5City v5City) {
                            if (v5City == null) {
                                Toast.makeText(getContext(), "未搜索到城市", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            mCurrentCityTextView.setText(v5City.getHeWeather5().get(0).getBasic().getCity());
                            CityBean city = SwitchWeatherApp.getDaoInstance()
                                    .getCityBeanDao()
                                    .queryBuilder()
                                    .where(CityBeanDao.Properties.IsLocation.eq(1))
                                    .unique();
                            if (city != null) {
                                // if v5City is not equal to the old city inside the database, do something
                                if (!city.getCityId().equals(v5City.getHeWeather5().get(0).getBasic().getId())) {
                                    storeV5CityToDatabase(v5City, true);
                                    updateCityList();
                                    // startActivity(WeatherActivity.newIntent(getContext(),
                                    //         v5City.getHeWeather5().get(0).getBasic().getId()));
                                }
                                // if equal, do nothing
                            } else {
                                storeV5CityToDatabase(v5City, true);
                                updateCityList();
                                // startActivity(WeatherActivity.newIntent(getContext(),
                                //         v5City.getHeWeather5().get(0).getBasic().getId()));
                            }
                        }
                    });
        }
    }

    class EditCityAdapter extends RecyclerView.Adapter<EditCityAdapter.EditCityHolder> {
        private LayoutInflater mInflater;

        EditCityAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public EditCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_sliding_button_view, parent, false);
            return new EditCityHolder(view);
        }

        @Override
        public void onBindViewHolder(EditCityHolder holder, final int position) {
            CityBean cityBean = mCityList.get(position);
            holder.bindView(cityBean);
        }

        @Override
        public int getItemCount() {
            return mCityList.size();
        }

        class EditCityHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.location_city_label)
            ImageView mImageViewCityLabel;
            @BindView(R.id.item_text_view_city_name)
            TextView mItemTextViewCityName;
            @BindView(R.id.item_text_view_prov_name)
            TextView mItemTextViewProvName;

            EditCityHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }

            void bindView(CityBean cityBean) {
                if (cityBean.getIsLocation()) {
                    mImageViewCityLabel.setVisibility(View.VISIBLE);
                }
                mItemTextViewCityName.setText(cityBean.getCityName());
                mItemTextViewProvName.setText(cityBean.getProvName());
            }
        }
    }
}
