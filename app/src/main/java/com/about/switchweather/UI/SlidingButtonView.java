package com.about.switchweather.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import com.about.switchweather.R;

/**
 * Created by 跃峰 on 2016/9/3.
 * 自定义的滑动删除 View
 */
public class SlidingButtonView extends HorizontalScrollView {
    private TextView mDeleteTextView;
    // 用于记录滚动条可以滚动的距离
    private int mScrollWidth;
    // 自定义的接口，用于传达滑动事件等
    private OnButtonSlidingListener mOnButtonSlidingListener;
    // 在onMeasure中只执行一次的判断
    private boolean once = false;
    // 记录按钮菜单是否打开，默认关闭false
    private boolean isOpen = false;

    public interface OnButtonSlidingListener {
        void onMenuIsOpen(View view);
        void onDownOrMove(SlidingButtonView slidingButtonView);
    }

    public void setSlidingButtonListener(OnButtonSlidingListener listener){
        mOnButtonSlidingListener = listener;
    }

    public SlidingButtonView(Context context) {
        this(context, null);
    }

    public SlidingButtonView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingButtonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOverScrollMode(OVER_SCROLL_NEVER);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!once){
            mDeleteTextView = (TextView) findViewById(R.id.delete_text_view);
            once = true;
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed){
            //让 Item 在每次变更布局大小时回到初始位置，并且获取滚动条的可移动距离
            this.scrollTo(0,0);
            //获取水平滚动条可以滑动的范围，即右侧 删除按钮 的宽度
            mScrollWidth = mDeleteTextView.getWidth();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // 滑动监听，传递按下、移动这些事件，并按滑动的距离大小控制菜单开关
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mOnButtonSlidingListener.onDownOrMove(this);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                changeScrollX();
                return true;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 为了让删除按钮显示在项的背后的效果
        mDeleteTextView.setTranslationX(l - mScrollWidth);
    }

    /**
     * 按滚动条被拖动距离判断关闭或打开菜单
     */
    private void changeScrollX() {
        if (getScrollX() >= (mScrollWidth/2)){
            this.smoothScrollTo(mScrollWidth, 0);
            isOpen = true;
            mOnButtonSlidingListener.onMenuIsOpen(this);
        } else {
            this.smoothScrollTo(0, 0);
            isOpen = false;
        }
    }

    /**
     * 打开菜单
     */
    public void openMenu(){
        if (isOpen){
            return;
        }
        this.smoothScrollTo(mScrollWidth, 0);
        isOpen = true;
        mOnButtonSlidingListener.onMenuIsOpen(this);
    }

    /**
     * 关闭菜单
     */
    public void closeMenu(){
        if (!isOpen){
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = false;
    }
}
