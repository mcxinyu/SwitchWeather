package io.github.mcxinyu.switchweather.ui.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.arlib.floatingsearchview.util.Util;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.api.WeatherApiHelper;
import io.github.mcxinyu.switchweather.data.SearchDataHelper;
import io.github.mcxinyu.switchweather.data.SearchV5CitySuggestion;
import io.github.mcxinyu.switchweather.database.dao.CityBeanDao;
import io.github.mcxinyu.switchweather.model.V5City;
import io.github.mcxinyu.switchweather.ui.activity.WeatherActivity;
import io.github.mcxinyu.switchweather.util.ColoredSnackbar;
import io.github.mcxinyu.switchweather.util.QueryPreferences;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by huangyuefeng on 2017/8/18.
 * Contact me : mcxinyu@foxmail.com
 */
public class SearchCityFragment extends BaseLocationFragment {
    private static final String TAG = "SearchCityFragment";
    private static final String HAS_HOME_BUTTON_ARG = "has_home_button";

    private static final long FIND_SUGGESTION_SIMULATED_DELAY = 250;

    private static final long ANIM_DURATION = 350;

    @BindView(R.id.logo_image_view)
    ImageView mLogoImageView;
    @BindView(R.id.floating_search_view)
    FloatingSearchView mSearchView;
    @BindView(R.id.dim_background)
    FrameLayout mDimSearchViewBackground;
    @BindView(R.id.hot_city_text_view)
    RecyclerView mHotCityTextView;
    @BindView(R.id.parent_view)
    RelativeLayout mParentView;
    @BindView(R.id.header_view)
    RelativeLayout mHeaderView;
    private Unbinder unbinder;

    private ColorDrawable mDimDrawable;

    private boolean hasHomeButton;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onHomeButtonClicked();
    }

    public static SearchCityFragment newInstance(boolean hasHomeButton) {

        Bundle args = new Bundle();
        args.putBoolean(HAS_HOME_BUTTON_ARG, hasHomeButton);
        SearchCityFragment fragment = new SearchCityFragment();
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

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hasHomeButton = getArguments().getBoolean(HAS_HOME_BUTTON_ARG, false);

        if (QueryPreferences.getLocationButtonState(getContext())) {
            if (SwitchWeatherApp.getDaoInstance().getCityBeanDao().queryBuilder()
                    .where(CityBeanDao.Properties.IsLocation.eq(1))
                    .list()
                    .isEmpty()) {
                requestLocationPermission();
            }
        }
    }

    @Override
    protected void onReceiveLocationError() {
        Toast.makeText(getContext(), getString(R.string.location_failed), Toast.LENGTH_SHORT).show();
    }

    public void showSnackBarAlert(String text, boolean showAction) {
        if (showAction) {
            Snackbar snackbar = Snackbar.make(mParentView, text, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getResources().getString(R.string.retry_to_connect), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // doFetchWeather(null);
                        }
                    }).setActionTextColor(getResources().getColor(R.color.colorWhite));
            ColoredSnackbar.alert(snackbar).show();
        } else {
            Snackbar snackbar = Snackbar.make(mParentView, text, Snackbar.LENGTH_INDEFINITE)
                    .setActionTextColor(getResources().getColor(R.color.colorWhite));
            ColoredSnackbar.alert(snackbar).show();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDimDrawable = new ColorDrawable(Color.BLACK);
        mDimDrawable.setAlpha(0);
        mDimSearchViewBackground.setBackground(mDimDrawable);

        setupFloatingSearch();
    }

    private void setupFloatingSearch() {
        if (hasHomeButton) {
            mSearchView.setLeftActionMode(FloatingSearchView.LEFT_ACTION_MODE_SHOW_HOME);
            mSearchView.setOnHomeActionClickListener(new FloatingSearchView.OnHomeActionClickListener() {
                @Override
                public void onHomeClicked() {
                    mCallbacks.onHomeButtonClicked();
                }
            });
        }

        // 边输入边搜索
        mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {

            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                if (!oldQuery.equals("") && newQuery.equals("")) {
                    mSearchView.clearSuggestions();
                    mSearchView.setSearchHint(getString(R.string.search_city_hint));
                    mSearchView.swapSuggestions(SearchDataHelper.getHistorySuggestion(getContext(), 5));
                } else {
                    mSearchView.clearSuggestions();
                    mSearchView.showProgress();
                    SearchDataHelper.findSuggestions(getContext(), newQuery, 5,
                            FIND_SUGGESTION_SIMULATED_DELAY,
                            new SearchDataHelper.OnFindSuggestionsListener() {
                                @Override
                                public void onResults(List<SearchV5CitySuggestion> results) {
                                    if (results != null) {
                                        mSearchView.swapSuggestions(results);
                                    }
                                    mSearchView.hideProgress();
                                }
                            });
                }

            }
        });

        mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                SearchV5CitySuggestion suggestion = (SearchV5CitySuggestion) searchSuggestion;
                final String[] split = suggestion.getBody().split(",");
                if (split.length >= 3) {
                    WeatherApiHelper.searchCity(split[2])
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .limit(1)
                            .subscribe(new Observer<V5City>() {
                                @Override
                                public void onCompleted() {
                                    startActivity(WeatherActivity.newIntent(getContext(), split[2]));
                                }

                                @Override
                                public void onError(Throwable e) {
                                    e.printStackTrace();
                                }

                                @Override
                                public void onNext(V5City v5City) {
                                    if (v5City == null) {
                                        Toast.makeText(getContext(), "未搜索到城市", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    storeV5CityToDatabase(v5City, false);
                                }
                            });
                }
            }

            @Override
            public void onSearchAction(String query) {

            }
        });

        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                // TODO: 2017/8/21 增加 SearchView 左右填满屏幕的动画
                int headerHeight = getResources().getDimensionPixelOffset(R.dimen.fragment_main_logo_view_header_height);
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY", headerHeight, 0);
                anim.setDuration(350);
                fadeDimBackground(0, 150, null);
                anim.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mSearchView.setSearchHint(getString(R.string.search_city_hint));
                        mSearchView.swapSuggestions(SearchDataHelper.getHistorySuggestion(getContext(), 5));
                    }
                });
                anim.start();
            }

            @Override
            public void onFocusCleared() {
                int headerHeight = getResources().getDimensionPixelOffset(R.dimen.fragment_main_logo_view_header_height);
                ObjectAnimator anim = ObjectAnimator.ofFloat(mSearchView, "translationY", 0, headerHeight);
                anim.setDuration(350);
                anim.start();
                fadeDimBackground(150, 0, null);

                if (mSearchView != null) {
                    mSearchView.clearQuery();
                    mSearchView.hideProgress();
                    mSearchView.setSearchHint(getString(R.string.search_city));
                }
            }
        });

        mSearchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                if (item.getItemId() == R.id.action_location) {
                    QueryPreferences.setLocationButtonState(SwitchWeatherApp.getInstance(), true);
                    requestLocationPermission();
                }
            }
        });

        mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
            @Override
            public void onBindSuggestion(View suggestionView, ImageView leftIcon,
                                         TextView textView, SearchSuggestion item, int itemPosition) {

                SearchV5CitySuggestion searchSuggestion = (SearchV5CitySuggestion) item;

                // String textColor = mIsDarkSearchTheme ? "#ffffff" : "#000000";
                // String textLight = mIsDarkSearchTheme ? "#bfbfbf" : "#787878";

                String textColor = "#000000";
                String textLight = "#787878";

                if (searchSuggestion.isHistory()) {
                    leftIcon.setImageDrawable(ResourcesCompat.getDrawable(getResources(),
                            R.drawable.ic_history_black_24dp, null));

                    Util.setIconColor(leftIcon, Color.parseColor(textColor));
                    leftIcon.setAlpha(.36f);
                } else {
                    leftIcon.setAlpha(0.0f);
                    leftIcon.setImageDrawable(null);
                }

                textView.setTextColor(Color.parseColor(textColor));
                // String text = searchSuggestion.getBody().replaceFirst(mSearchView.getQuery(),
                //                 "<font color=\"" + textLight + "\">" + mSearchView.getQuery() + "</font>");
                // textView.setText(Html.fromHtml(text));
                String body = searchSuggestion.getBody();
                String text = body.replace(body.substring(body.lastIndexOf(",")), "");
                textView.setText(text);
            }

        });

        mSearchView.setOnClearSearchActionListener(new FloatingSearchView.OnClearSearchActionListener() {
            @Override
            public void onClearSearchClicked() {

            }
        });
    }

    public boolean onActivityBackPress() {
        //if mSearchView.setSearchFocused(false) causes the focused search
        //to close, then we don't want to close the activity. if mSearchView.setSearchFocused(false)
        //returns false, we know that the search was already closed so the call didn't change the focus
        //state and it makes sense to call supper onBackPressed() and close the activity
        return mSearchView.setSearchFocused(false);
    }

    private void fadeDimBackground(int from, int to, Animator.AnimatorListener listener) {
        ValueAnimator anim = ValueAnimator.ofInt(from, to);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                int value = (Integer) animation.getAnimatedValue();
                mDimDrawable.setAlpha(value);
            }
        });
        if (listener != null) {
            anim.addListener(listener);
        }
        anim.setDuration(ANIM_DURATION);
        anim.start();
    }

}
