package com.yitouwushui.weibo.view.pulltorefreshview.internal;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;

import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.utils.UIUtils;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;

/**
 * Created by alvintiny on 14-6-7.
 */
public class HBLoadingLayout extends LoadingLayout {
    public HBLoadingLayout(Context context, PullToRefreshBase.Mode mode, PullToRefreshBase.Orientation scrollDirection, TypedArray attrs) {
        super(context, mode, scrollDirection, attrs);
        mHeaderImage.setImageDrawable(null);
        mHeaderImage.setBackgroundResource(R.drawable.refresh_arow_down);
    }

    @Override
    protected int getDefaultDrawableResId() {
        return R.drawable.refresh_arow_down;
    }

    @Override
    protected void onLoadingDrawableSet(Drawable imageDrawable) {

    }

    private int refreshTopResId = R.drawable.refresh_arow_down;

    @Override
    protected void onPullImpl(float scaleOfLayout) {
        LinearLayout.LayoutParams flLp = (LinearLayout.LayoutParams) mHeaderImage.getLayoutParams();
        flLp.width = UIUtils.dip2px(getContext(), 24);
        if (scaleOfLayout < 0.5357f) {
            flLp.height = UIUtils.dip2px(getContext(), 25);
            mHeaderImage.setBackgroundResource(R.drawable.refresh_arow_down);
        } else {
            mHeaderImage.setBackgroundResource(refreshTopResId);
            if (scaleOfLayout < 1) {
                flLp.height = UIUtils.dip2px(getContext(), scaleOfLayout * 40);
            } else {
                flLp.height = UIUtils.dip2px(getContext(), 40);
            }
        }
        mHeaderImage.requestLayout();
    }

    @Override
    protected void pullToRefreshImpl() {
        refreshTopResId = R.drawable.refresh_arow_down;
    }

    @Override
    protected void refreshingImpl() {
        LinearLayout.LayoutParams flLp = (LinearLayout.LayoutParams) mHeaderImage.getLayoutParams();
        flLp.topMargin = UIUtils.dip2px(getContext(), 0);
        flLp.height = UIUtils.dip2px(getContext(), 0);
        mHeaderImage.requestLayout();
//        mHeaderImage.setBackgroundResource(R.drawable.refreshing);
        mHeaderProgress.setVisibility(View.VISIBLE);
    }

    @Override
    protected void releaseToRefreshImpl() {

    }

    @Override
    protected void resetImpl() {
        refreshTopResId = R.drawable.refresh_arow_down;
        mHeaderImage.setBackgroundResource(R.drawable.refresh_arow_down);
        mHeaderProgress.setVisibility(View.GONE);
        LinearLayout.LayoutParams flLp = (LinearLayout.LayoutParams) mHeaderImage.getLayoutParams();
        flLp.topMargin = UIUtils.dip2px(getContext(), 25);
        mHeaderImage.requestLayout();
    }
}
