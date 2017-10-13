package io.github.mcxinyu.switchweather.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.github.mcxinyu.switchweather.R;
import io.github.mcxinyu.switchweather.SwitchWeatherApp;
import io.github.mcxinyu.switchweather.database.dao.CityBean;

/**
 * Created by 跃峰 on 2016/9/4.
 * 因为自定义了一个 SlidingButtonView 所以把 Adapter 抽取出来做一个类
 */
@Deprecated
public class EditCityAdapter extends RecyclerView.Adapter<EditCityAdapter.EditCityHolder> {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private List<CityBean> mCityList = new ArrayList<>();

    public interface OnSlidingViewClickListener {
        void onCitySelected(String cityId);

        void onDataSetChanged();
    }

    public EditCityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mCityList = SwitchWeatherApp.getDaoInstance().getCityBeanDao().loadAll();
    }

    @Override
    public EditCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_sliding_button_view, parent, false);
        return new EditCityHolder(view);
    }

    @Override
    public void onBindViewHolder(final EditCityHolder holder, final int position) {
        CityBean cityBean = mCityList.get(position);
        holder.bindView(cityBean);
    }

    @Override
    public int getItemCount() {
        return mCityList.size();
    }

    static class EditCityHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_text_view_city_name)
        TextView mItemTextViewCityName;
        @BindView(R.id.item_text_view_prov_name)
        TextView mItemTextViewProvName;

        EditCityHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bindView(CityBean cityBean) {
            mItemTextViewCityName.setText(cityBean.getCityName());
            // mItemTextViewProvName.setText();
        }
    }
}
