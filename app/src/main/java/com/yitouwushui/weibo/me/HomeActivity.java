package com.yitouwushui.weibo.me;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
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
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.utils.UIUtils;
import com.yitouwushui.weibo.utils.Util;
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

    User user;
    // id
    String idstr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home);
//        // 初始化主页面
//        initHome();


        getData();

        View v = getLayoutInflater().inflate(R.layout.activity_home_header, null);
        setContentView(v);
        initHeader(v);


    }

    /**
     * 获得意图id
     */
    private void showParam() {
        img_icon.setImageURI(Uri.parse(user.getAvatar_large()));
        tv_name.setText(user.getScreen_name());
        tv_follow.setText("关注: " + user.getFriends_count());
        tv_follower.setText("粉丝: " + user.getFollowers_count());
        tv_introduce.setText(user.getDescription());
        tv_location.setText(user.getLocation());
        tv_school.setText(user.getRemark() != null ? user.getRemark() : "无");
        tv_introduce2.setText(user.getDescription());
        tv_create_time.setText(Util.stringTranslateTime(user.getCreated_at()));

    }

    private void getData() {
        Intent intent = getIntent();
        idstr = intent.getStringExtra(App.ACTION_USERID);
        NetQueryImpl.getInstance(this).userOtherQuery(handler, idstr);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_USER) {
                user = (User) msg.obj;
                showParam();
            }

        }
    };


    /**
     * 初始化头文件
     */
    private void initHeader(View header) {
        img_back = (ImageView) header.findViewById(R.id.back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        img_icon = (SimpleDraweeView) header.findViewById(R.id.header_Icon);
        tv_name = (TextView) header.findViewById(R.id.header_name);
        tv_follow = (TextView) header.findViewById(R.id.header_follow);
        tv_follower = (TextView) header.findViewById(R.id.hreader_follower);
        tv_introduce = (TextView) header.findViewById(R.id.introduce);
        tab = (TabLayout) header.findViewById(R.id.tableLayout);
        tv_location = (TextView) header.findViewById(R.id.location);
        tv_school = (TextView) header.findViewById(R.id.school);
        tv_introduce2 = (TextView) header.findViewById(R.id.introduce2);
        tv_grade = (TextView) header.findViewById(R.id.dengji);
        tv_xingyong = (TextView) header.findViewById(R.id.xinyong);
        tv_create_time = (TextView) header.findViewById(R.id.create_time);
        tab.addTab(tab.newTab().setText("主页"));
        tab.addTab(tab.newTab().setText("微博"));
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
         * 上下滑动监听
         */
        mRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onLoadMore(PullToRefreshBase<ListView> refreshView) {

            }
        });


        header = getLayoutInflater().inflate(R.layout.activity_home_header, null);
        mListView.addHeaderView(header);
        // 初始化头文件控件
        initHeader(header);

        tab2.addTab(tab2.newTab().setText("主页"));
        tab2.addTab(tab2.newTab().setText("微博"));

        /**
         * 显示隐藏
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
                if (firstVisibleItem == 0) {
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
    }
}
