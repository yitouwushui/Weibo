package com.yitouwushui.weibo.weibo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by yitouwushui on 2016/3/2.
 */
public class MyScrollView extends ScrollView {

    private OnScrollListener onScrollListener;

    //主要是用在用户手指离开MyScrollview时，MyScrollView还在继续滑动，保存Y距离，再比较
    private int lastScrollY;

    public MyScrollView(Context context) {
        super(context, null);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置滚动接口
     *
     * @param onScrollListener
     */
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    /**
     * 用户手指离开MyScrollview时，获得MyScrollview滚动的Y距离，然后回调onScroll方法中。
     */
    private Handler handler = new Handler() {

        public void handleMessage(Message message) {
            // 获得scroll 的y坐标
            int scrollY = MyScrollView.this.getScrollY();

            // 此时距离不相等隔5毫秒发送
            if (lastScrollY != scrollY) {
                lastScrollY = scrollY;
                handler.sendMessageDelayed(handler.obtainMessage(), 5);
            }
            if (onScrollListener != null) {
                onScrollListener.onScroll(scrollY);
            }
        }
    };

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (onScrollListener != null) {
            onScrollListener.onScroll(lastScrollY = this.getScrollY());
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                handler.sendMessageDelayed(handler.obtainMessage(), 20);
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 滚动的回调接口
     */
    public interface OnScrollListener {
        /**
         * 回调方法
         *
         * @param scrollY MyScroll滑动Y方向距离
         */
        void onScroll(int scrollY);
    }
}
