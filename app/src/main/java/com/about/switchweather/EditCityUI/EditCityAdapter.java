package com.about.switchweather.EditCityUI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.about.switchweather.Model.WeatherInfo;
import com.about.switchweather.UI.MyApplication;
import com.about.switchweather.UI.SlidingButtonView;
import com.about.switchweather.UI.SlidingButtonView.OnButtonSlidingListener;
import com.about.switchweather.Util.SlidingButtonUtils;
import com.about.switchweather.Util.WeatherLab;
import com.about.switchweather.databinding.SlidingButtonBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 跃峰 on 2016/9/4.
 */
public class EditCityAdapter extends RecyclerView.Adapter<EditCityAdapter.EditCityHolder> implements OnButtonSlidingListener {
    private final LayoutInflater mInflater;
    private final Context mContext;
    private OnSlidingViewClickListener mOnSlidingViewClickListener;
    private SlidingButtonView mSlidingButtonView;

    private List<WeatherInfo> mWeatherInfoList = new ArrayList<>();

    public interface OnSlidingViewClickListener{
        void onItemClick(View view,int position);
        void onDeleteButtonClick(View view,int position);
    }

    public EditCityAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mOnSlidingViewClickListener = (OnSlidingViewClickListener) context;
        mWeatherInfoList = WeatherLab.get(MyApplication.getContext()).getWeatherInfoList();
    }

    @Override
    public EditCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SlidingButtonBinding binding = SlidingButtonBinding.inflate(mInflater, parent, false);

        return new EditCityHolder(binding);
    }

    @Override
    public void onBindViewHolder(final EditCityHolder holder, int position) {
        WeatherInfo weatherInfo = mWeatherInfoList.get(position);
        holder.bindView(weatherInfo);

        //设置内容布局的宽为屏幕宽度
        holder.mBinding.layoutContent.getLayoutParams().width = SlidingButtonUtils.getScreenWidth(mContext);
        holder.mBinding.itemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断是否有删除菜单打开
                if(menuIsOpen()){
                    closeMenu();
                } else {
                    int layoutPosition = holder.getLayoutPosition();
                    mOnSlidingViewClickListener.onItemClick(v, layoutPosition);
                }
            }
        });
        holder.mBinding.deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int layoutPosition = holder.getLayoutPosition();
                mOnSlidingViewClickListener.onDeleteButtonClick(v, layoutPosition);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mWeatherInfoList.size();
    }

    @Override
    public void onMenuIsOpen(View view) {
        mSlidingButtonView = (SlidingButtonView) view;
    }

    @Override
    public void onDownOrMove(SlidingButtonView slidingButtonView) {
        if (menuIsOpen()){
            if (mSlidingButtonView != slidingButtonView){
                closeMenu();
            }
        }
    }

    private boolean menuIsOpen(){
        if (mSlidingButtonView != null){
            return true;
        }
        return false;
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        mSlidingButtonView.closeMenu();
        mSlidingButtonView = null;
    }

    public void removeItem(int position){
        WeatherLab.get(mContext).deleteWeatherInfo(mWeatherInfoList.get(position).getBasicCityId());
        mWeatherInfoList = WeatherLab.get(mContext).getWeatherInfoList();
        notifyDataSetChanged();
    }

    public WeatherInfo getCurrentItem(int position){
        return mWeatherInfoList.get(position);
    }

    class EditCityHolder extends RecyclerView.ViewHolder{
        private SlidingButtonBinding mBinding;

        public EditCityHolder(SlidingButtonBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            // 给 SlidingButtonView 设置监听！
            ((SlidingButtonView) mBinding.getRoot()).setSlidingButtonListener(EditCityAdapter.this);
        }

        public void bindView(WeatherInfo weatherInfo){
            mBinding.setWeatherInfo(weatherInfo);
        }
    }
}
