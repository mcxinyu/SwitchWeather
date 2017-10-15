package io.github.mcxinyu.switchweather.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.DailyForecastBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.NowBean;
import io.github.mcxinyu.switchweather.model.HeWeatherModel.HeWeather5Bean.SuggestionBean;

/**
 * Created by huangyuefeng on 2017/3/6.
 */

public class SuggestionFragment extends Fragment {
    public static final String ACTION_COMF = "comf";
    public static final String ACTION_FLU = "flu";
    public static final String ACTION_DRSG = "drsg";
    public static final String ACTION_CW = "cw";
    public static final String ACTION_SPORT = "sport";
    public static final String ACTION_UV = "uv";

    public static final String CITY_NAME = "city_name";
    public static final String SUGGESTION_BEAN = "suggestion_bean";
    public static final String NOW_BEAN = "now_bean";
    public static final String DAILY_FORECAST_BEAN = "daily_forecast_nean";
    public static final String ACTION = "action";

    @BindView(R.id.suggestion_brf)
    TextView mSuggestionBrf;
    @BindView(R.id.suggestion_txt)
    TextView mSuggestionTxt;
    @BindView(R.id.suggestion_city_name)
    TextView mSuggestionCityName;
    @BindView(R.id.suggestion_info1)
    TextView mSuggestionInfo1;
    @BindView(R.id.suggestion_info2)
    TextView mSuggestionInfo2;
    @BindView(R.id.suggestion_info3)
    TextView mSuggestionInfo3;
    @BindView(R.id.activity_suggestion)
    FrameLayout mActivitySuggestion;
    private Unbinder unbinder;

    private String mCityName;
    private String mAction;
    private SuggestionBean mSuggestionBean;
    private NowBean mNowBean;
    private DailyForecastBean mDailyForecastBean;

    public static SuggestionFragment newInstance(String cityName,
                                                 SuggestionBean suggestionBean,
                                                 NowBean nowBean,
                                                 DailyForecastBean dailyForecastBean,
                                                 String action) {

        Bundle args = new Bundle();
        args.putString(CITY_NAME, cityName);
        args.putParcelable(SUGGESTION_BEAN, suggestionBean);
        args.putParcelable(NOW_BEAN, nowBean);
        args.putParcelable(DAILY_FORECAST_BEAN, dailyForecastBean);
        args.putString(ACTION, action);

        SuggestionFragment fragment = new SuggestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCityName = getArguments().getString(CITY_NAME);
        mSuggestionBean = getArguments().getParcelable(SUGGESTION_BEAN);
        mNowBean = getArguments().getParcelable(NOW_BEAN);
        mDailyForecastBean = getArguments().getParcelable(DAILY_FORECAST_BEAN);
        mAction = getArguments().getString(ACTION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suggestion, container, false);
        unbinder = ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {
        mSuggestionCityName.setText(mCityName);
        switch (mAction) {
            case ACTION_COMF:
                mSuggestionBrf.setText(mSuggestionBean.getComf().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getComf().getTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_hum) + " " + mNowBean.getHum() + getString(R.string.percent_sign));
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mDailyForecastBean.getTmp().getMax() + "~" +
                        mDailyForecastBean.getTmp().getMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mNowBean.getWind().getDir() + " " +
                        mNowBean.getWind().getSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_FLU:
                mSuggestionBrf.setText(mSuggestionBean.getFlu().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getFlu().getTxt());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mNowBean.getCond().getTxt());
                int max = Integer.parseInt(mDailyForecastBean.getTmp().getMax());
                int min = Integer.parseInt(mDailyForecastBean.getTmp().getMin());
                mSuggestionInfo2.setText(getString(R.string.suggestion_tmp_short) + " " + (max - min));
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mNowBean.getWind().getDir() + " " +
                        mNowBean.getWind().getSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_DRSG:
                mSuggestionBrf.setText(mSuggestionBean.getDrsg().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getDrsg().getBrf());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mNowBean.getCond().getTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_wing) + " " + mNowBean.getWind().getDir() + " " +
                        mNowBean.getWind().getSc() + getString(R.string.wind_class_text));
                mSuggestionInfo3.setVisibility(View.GONE);
                break;
            case ACTION_CW:
                mSuggestionBrf.setText(mSuggestionBean.getCw().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getCw().getBrf());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mNowBean.getCond().getTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_wing) + " " + mNowBean.getWind().getDir() + " " +
                        mNowBean.getWind().getSc() + getString(R.string.wind_class_text));
                mSuggestionInfo3.setVisibility(View.GONE);
                break;
            case ACTION_SPORT:
                mSuggestionBrf.setText(mSuggestionBean.getSport().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getSport().getBrf());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mNowBean.getCond().getTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mDailyForecastBean.getTmp().getMax() + "~" +
                        mDailyForecastBean.getTmp().getMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_wing) + " " + mNowBean.getWind().getDir() + " " +
                        mNowBean.getWind().getSc() + getString(R.string.wind_class_text));
                break;
            case ACTION_UV:
                mSuggestionBrf.setText(mSuggestionBean.getUv().getBrf());
                mSuggestionTxt.setText(mSuggestionBean.getUv().getBrf());
                mSuggestionInfo1.setText(getString(R.string.suggestion_cond) + " " + mNowBean.getCond().getTxt());
                mSuggestionInfo2.setText(getString(R.string.suggestion_rang) + " " + mDailyForecastBean.getTmp().getMax() + "~" +
                        mDailyForecastBean.getTmp().getMin());
                mSuggestionInfo3.setText(getString(R.string.suggestion_sr_ss) + " " + mDailyForecastBean.getAstro().getSr() + getString(R.string.gap) +
                        mDailyForecastBean.getAstro().getSs());
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
