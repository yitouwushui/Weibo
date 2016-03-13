package com.yitouwushui.weibo.me;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.utils.UIUtils;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshListView;

public class HomeActivity extends AppCompatActivity {

    // 头文件控件
    private ImageView img_back;
    private SimpleDraweeView img_icon;
    private TextView tv_name;
    private TextView tv_follow;
    private TextView tv_follower;
    private TextView tv_introduce;

    private TabLayout tab;
    private TextView tv_location;
    private TextView tv_school;
    private TextView tv_introduce2;
    private TextView tv_grade;
    private TextView tv_xingyong;
    private TextView tv_create_time;

    // 主界面控件
    RelativeLayout rela_header;
    ImageView img_back2;
    TextView tx_title;
    TabLayout tab2;
    PullToRefreshListView mRefreshListView;
    Button bt_follow;
    ListView mListView;
    View header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // 初始化主页面
        initHome();

        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onLoadMore(PullToRefreshBase<ListView> refreshView) {

            }
        });



    }

    /**
     * 初始化头文件
     */
    private void initHeader() {
        img_back = (ImageView) findViewById(R.id.back);
        img_icon = (SimpleDraweeView) findViewById(R.id.header_Icon);
        tv_name = (TextView) findViewById(R.id.header_name);
        tv_follow = (TextView) findViewById(R.id.header_follow);
        tv_follower = (TextView) findViewById(R.id.hreader_follower);
        tv_introduce = (TextView) findViewById(R.id.introduce);
        tab = (TabLayout) findViewById(R.id.tableLayout);
        tv_location = (TextView) findViewById(R.id.location);
        tv_school = (TextView) findViewById(R.id.school);
        tv_introduce2 = (TextView) findViewById(R.id.introduce2);
        tv_grade = (TextView) findViewById(R.id.dengji);
        tv_xingyong = (TextView) findViewById(R.id.xinyong);
        tv_create_time = (TextView) findViewById(R.id.create_time);
    }

    /**
     * 初始化主页面控件
     */
    private void initHome() {
        rela_header = (RelativeLayout) findViewById(R.id.home);
        img_back2 = (ImageView) findViewById(R.id.home_back);
        tx_title = (TextView) findViewById(R.id.home_titile);
        tab2 = (TabLayout) findViewById(R.id.tableLayout_home);
        mRefreshListView = (PullToRefreshListView) findViewById(R.id.home_lv);
        bt_follow = (Button) findViewById(R.id.home_follow);
        mListView = mRefreshListView.getRefreshableView();

        /**
         * 滑动监听
         */
        mRefreshListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(
                    AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                //是否显示悬浮逻辑
                int[] location = new int[2];
                header.getLocationInWindow(location);
                int topHeight = UIUtils.getStatusBarHeight(HomeActivity.this)
                        + UIUtils.dip2px(HomeActivity.this, 92); // 顶部高度，状态栏，标题栏，以及这个Tab的高度
                boolean needShowTab = true;
                if (firstVisibleItem == 0){
                    if (location[1] - topHeight > -header.getMeasuredHeight()) {
                        needShowTab = false;
                    }
                }
                if (needShowTab) {
                    rela_header.setVisibility(View.VISIBLE);
                } else {
                    rela_header.setVisibility(View.GONE);
                }
            }
        });

        header = getLayoutInflater().inflate(R.layout.activity_home_header, null);
        // 初始化头文件控件
        initHeader();
    }
}
