/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.yitouwushui.weibo.view.pulltorefreshview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.utils.LogUtil;
import com.yitouwushui.weibo.view.GridViewWithHeaderAndFooter;
import com.yitouwushui.weibo.view.pulltorefreshview.internal.EmptyViewMethodAccessor;

public abstract class PullToRefreshAdapterViewBase<T extends AbsListView> extends PullToRefreshBase<T> implements
        OnScrollListener {

    private View mAutoLoadView;
    private ImageView mIvAutoLoadRefresh;
    private Animation mRotateAnimation;

    private static FrameLayout.LayoutParams convertEmptyViewLayoutParams(ViewGroup.LayoutParams lp) {
        FrameLayout.LayoutParams newLp = null;

        if (null != lp) {
            newLp = new FrameLayout.LayoutParams(lp);

            if (lp instanceof LinearLayout.LayoutParams) {
                newLp.gravity = ((LinearLayout.LayoutParams) lp).gravity;
            } else {
                newLp.gravity = Gravity.CENTER;
            }
        }

        return newLp;
    }

    private boolean mLastItemVisible;
    private OnScrollListener mOnScrollListener;
    private OnLastItemVisibleListener mOnLastItemVisibleListener;
    private View mEmptyView;

    private boolean mShowIndicator;
    private boolean mScrollEmptyView = true;

    public PullToRefreshAdapterViewBase(Context context) {
        super(context);
        mRefreshableView.setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRefreshableView.setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode) {
        super(context, mode);
        mRefreshableView.setOnScrollListener(this);
    }

    public PullToRefreshAdapterViewBase(Context context, Mode mode, AnimationStyle animStyle) {
        super(context, mode, animStyle);
        mRefreshableView.setOnScrollListener(this);
    }

    public boolean getShowIndicator() {
        return mShowIndicator;
    }

    public final void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount,
                               final int totalItemCount) {

        if (DEBUG) {
            LogUtil.d(LOG_TAG, "First Visible: " + firstVisibleItem + ". Visible Count: " + visibleItemCount
                    + ". Total Items:" + totalItemCount);
        }

        /**
         * Set whether the Last Item is Visible. lastVisibleItemIndex is a
         * zero-based index, so we minus one totalItemCount to check
         */
        // 如果显示出来的数目和总数一致，且mLastItemVisible = false；就让其等于一次true。（不能让其一直提交加载请求）

        if (null != mOnLastItemVisibleListener) {

            // headview是firstVisibleItem 0
            if (visibleItemCount + firstVisibleItem >= totalItemCount - 1 && firstVisibleItem > 0) {
//            if (refreshbleView.getLastVisiblePosition() == refreshbleView.getAdapter().getCount() - 1) {
                if (!mLastItemVisible) {
                    // 这个可以保证处于倒数第二个移动到倒数第一个过程只加载一次
                    mLastItemVisible = true;
                    // 必须有超过屏幕显示个数才自动加载，否则说明本来个数就很少
                    if (!noData) {
                        mOnLastItemVisibleListener.onLastItemVisible();
                    }
                }
            } else {
                mLastItemVisible = false;
//                (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount - 1);
            }

        }

        // Finally call OnScrollListener if we have one
        if (null != mOnScrollListener)

        {
            mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void setAutoLoad(boolean autoLoad) {
        this.mAutoLoad = autoLoad;
    }

    @Override
    public void setOnRefreshListener(final OnRefreshListener<T> listener) {
        super.setOnRefreshListener(listener);
        setNoMoreData(false);
        // 在这里判断xml的配置是否允许自动加载
        LogUtil.e("mAutoLoad:" + mAutoLoad);
        if (mAutoLoad) {
            setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
                int autoLoadCount = 0;// 记录自动加载的次数

                @Override
                public void onLastItemVisible() {
                    if (!isLoading) {
                        isLoading = true;

                        autoLoad();

                        AdapterView view = getRefreshableView();
                        if (view instanceof ListView) {
                            ListView lv = (ListView) view;
                            lv.addFooterView(mAutoLoadView);
                        } else if (view instanceof GridViewWithHeaderAndFooter) {
                            GridViewWithHeaderAndFooter gv = (GridViewWithHeaderAndFooter) view;
                            gv.addFooterView(mAutoLoadView);
                        }
                        autoLoadCount++;
                        mIvAutoLoadRefresh.startAnimation(mRotateAnimation);
                        listener.onLoadMore(PullToRefreshAdapterViewBase.this);
                        mLastRefreshTime = System.currentTimeMillis();
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mLastRefreshTime != 0 && System.currentTimeMillis() - mLastRefreshTime >= 4998l) {
                                    onRefreshComplete();
                                    Toast.makeText(getContext(), "网络有点慢，要不待会儿再试试吧。", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, 5000);
                    }
                }
            });
        }
    }

    @Override
    public void onRefreshComplete() {
        isLoading = false;
        if (mIvAutoLoadRefresh != null) {
            mIvAutoLoadRefresh.clearAnimation();
            mRotateAnimation.cancel();
            AdapterView view = getRefreshableView();
            if (view instanceof ListView) {
                ListView lv = (ListView) view;
                lv.removeFooterView(mAutoLoadView);
            } else if (view instanceof GridViewWithHeaderAndFooter) {
                GridViewWithHeaderAndFooter gv = (GridViewWithHeaderAndFooter) view;
                gv.removeFooterView(mAutoLoadView);
            }
        }
        super.onRefreshComplete();
    }

    public void setNoMoreData(boolean noData) {
        this.noData = noData;
        if (!noData) {
            AdapterView view = getRefreshableView();
            if (view instanceof ListView) {
                ListView lv = (ListView) view;
                lv.removeFooterView(mAutoLoadView);
            } else if (view instanceof GridViewWithHeaderAndFooter) {
                GridViewWithHeaderAndFooter gv = (GridViewWithHeaderAndFooter) view;
                gv.removeFooterView(mAutoLoadView);
            }
        }
    }

    private boolean isLoading;
    private boolean noData = false;// 保证已经全部加载后移到上面又移下来重复获取

    public final void onScrollStateChanged(final AbsListView view, final int state) {
        /**
         * Check that the scrolling has stopped, and that the last item is
         * visible.
         */
        if (state == OnScrollListener.SCROLL_STATE_IDLE && null != mOnLastItemVisibleListener && mLastItemVisible) {
        }

        if (null != mOnScrollListener) {
            mOnScrollListener.onScrollStateChanged(view, state);
        }
    }

    /**
     * Pass-through method for {@link PullToRefreshBase#getRefreshableView()
     * getRefreshableView()}.
     * {@link AdapterView#setAdapter(Adapter)}
     * setAdapter(adapter)}. This is just for convenience!
     *
     * @param adapter - Adapter to set
     */
    public void setAdapter(ListAdapter adapter) {
        ((AdapterView<ListAdapter>) mRefreshableView).setAdapter(adapter);
    }

    /**
     * Sets the Empty View to be used by the Adapter View.
     * <p/>
     * We need it handle it ourselves so that we can Pull-to-Refresh when the
     * Empty View is shown.
     * <p/>
     * Please note, you do <strong>not</strong> usually need to call this method
     * yourself. Calling setEmptyView on the AdapterView will automatically call
     * this method and set everything up. This includes when the Android
     * Framework automatically sets the Empty View based on it's ID.
     *
     * @param newEmptyView - Empty View to be used
     */
    public final void setEmptyView(View newEmptyView) {
        FrameLayout refreshableViewWrapper = getRefreshableViewWrapper();

        if (null != newEmptyView) {
            // New view needs to be clickable so that Android recognizes it as a
            // target for Touch Events


            ViewParent newEmptyViewParent = newEmptyView.getParent();
            if (null != newEmptyViewParent && newEmptyViewParent instanceof ViewGroup) {
                ((ViewGroup) newEmptyViewParent).removeView(newEmptyView);
            }

            // We need to convert any LayoutParams so that it works in our
            // FrameLayout
            FrameLayout.LayoutParams lp = convertEmptyViewLayoutParams(newEmptyView.getLayoutParams());
            if (null != lp) {
                refreshableViewWrapper.addView(newEmptyView, lp);
            } else {
                refreshableViewWrapper.addView(newEmptyView);
            }
        }

        if (mRefreshableView instanceof EmptyViewMethodAccessor) {
            ((EmptyViewMethodAccessor) mRefreshableView).setEmptyViewInternal(newEmptyView);
        } else {
            mRefreshableView.setEmptyView(newEmptyView);
        }
        mEmptyView = newEmptyView;
    }

    /**
     * Pass-through method for {@link PullToRefreshBase#getRefreshableView()
     * getRefreshableView()}.
     * {@link AdapterView#setOnItemClickListener(OnItemClickListener)
     * setOnItemClickListener(listener)}. This is just for convenience!
     *
     * @param listener - OnItemClickListener to use
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        mRefreshableView.setOnItemClickListener(listener);
    }

    public void setOnItemLongClickListtener(AdapterView.OnItemLongClickListener listtener) {
        mRefreshableView.setOnItemLongClickListener(listtener);
    }

    public final void setOnLastItemVisibleListener(OnLastItemVisibleListener listener) {
        mOnLastItemVisibleListener = listener;
    }

    public final void setOnScrollListener(OnScrollListener listener) {
        mOnScrollListener = listener;
    }

    public final void setScrollEmptyView(boolean doScroll) {
        mScrollEmptyView = doScroll;
    }

    @Override
    protected void handleStyledAttributes(TypedArray a) {
        // Set Show Indicator to the XML value, or default value
        mShowIndicator = a.getBoolean(R.styleable.PullToRefresh_ptrShowIndicator, !isPullToRefreshOverScrollEnabled());
    }

    protected boolean isReadyForPullStart() {
        return isFirstItemVisible();
    }

    protected boolean isReadyForPullEnd() {
        return isLastItemVisible();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != mEmptyView && !mScrollEmptyView) {
            mEmptyView.scrollTo(-l, -t);
        }
    }

    private boolean getShowIndicatorInternal() {
        return mShowIndicator && isPullToRefreshEnabled();
    }

    private boolean isFirstItemVisible() {
        final Adapter adapter = mRefreshableView.getAdapter();

        if (null == adapter || adapter.getCount() == 0) {
            if (DEBUG) {
                LogUtil.d(LOG_TAG, "isFirstItemVisible. Empty View.");
            }
            return true;

        } else {

            /**
             * This check should really just be:
             * mRefreshableView.getFirstVisiblePosition() == 0, but PtRListView
             * internally use a HeaderView which messes the positions up. For
             * now we'll just add one to account for it and rely on the inner
             * condition which checks getTop().
             */
            if (mRefreshableView.getFirstVisiblePosition() <= 1) {
                final View firstVisibleChild = mRefreshableView.getChildAt(0);
                if (firstVisibleChild != null) {
                    return firstVisibleChild.getTop() >= mRefreshableView.getTop();
                }
            }
        }

        return false;
    }

    private boolean isLastItemVisible() {
        final Adapter adapter = mRefreshableView.getAdapter();

        // 使用getCount是否为0来判断（之前是isEmpty，会导致ListView没有元素的时候HeaderView不能滑动）
        // 最终调用了HeaderViewListAdapter getCount() 130行
        // 注意ListView设置Adapter必须在addHeaderView之后
        if (null == adapter || adapter.getCount() == 0) {
            if (DEBUG) {
                LogUtil.d(LOG_TAG, "isLastItemVisible. Empty View.");
            }
            return true;
        } else {
            final int lastItemPosition = mRefreshableView.getCount() - 1;
            final int lastVisiblePosition = mRefreshableView.getLastVisiblePosition();

            if (DEBUG) {
                LogUtil.d(LOG_TAG, "isLastItemVisible. Last Item Position: " + lastItemPosition + " Last Visible Pos: "
                        + lastVisiblePosition);
            }

            /**
             * This check should really just be: lastVisiblePosition ==
             * lastItemPosition, but PtRListView internally uses a FooterView
             * which messes the positions up. For me we'll just subtract one to
             * account for it and rely on the inner condition which checks
             * getBottom().
             */
            if (lastVisiblePosition >= lastItemPosition - 1) {
                final int childIndex = lastVisiblePosition - mRefreshableView.getFirstVisiblePosition();
                final View lastVisibleChild = mRefreshableView.getChildAt(childIndex);
                if (lastVisibleChild != null) {
                    return lastVisibleChild.getBottom() <= mRefreshableView.getBottom();
                }
            }
        }

        return false;
    }


}
