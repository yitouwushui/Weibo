package com.yitouwushui.weibo.weibo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.main.FirstPageFragment;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.utils.Util;
import com.yitouwushui.weibo.utils.UIUtils;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.HashMap;

public class WeiboPageActivity extends AppCompatActivity {


    //存放微博九张图片控件的引用
    HashMap<Integer, SimpleDraweeView> picHashMap = new HashMap<>();

    // 微博信息控件
    SimpleDraweeView img_icon;
    ImageView img_collect;
    TextView text_name;
    TextView text_time;
    TextView text_device;
    TextView text_word;
    SimpleDraweeView img_weibo1;
    SimpleDraweeView img_weibo2;
    SimpleDraweeView img_weibo3;
    SimpleDraweeView img_weibo4;
    SimpleDraweeView img_weibo5;
    SimpleDraweeView img_weibo6;
    SimpleDraweeView img_weibo7;
    SimpleDraweeView img_weibo8;
    SimpleDraweeView img_weibo9;
    RelativeLayout reLayout_weibo_tra;
    RelativeLayout reLayout_weibo_com;
    RelativeLayout reLayout_weibo_zan;
    ImageView img_zan;
    Status status;
    StringBuilder sourceStart = new StringBuilder("来自 ");
    ArrayList<Comments> commentsList = new ArrayList<>();
    GridLayout gridLayout;
    int page = 1;
    int size = 0;

    // 头文件的控件
    private PullToRefreshListView mRefreshView;
    private ListView mListView;
    private WBPageAdapter mAdapter;
    private TextView tvRecomendCount;
    private TextView tvZhuanfaCount;
    private TextView tvZanCount;
    private TextView tvRecomendCount2;
    private TextView tvZhuanfaCount2;
    private TextView tvZanCount2;
    private View tab;
    private View header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_page);

        initView();

        showInformation();

        getData();

        mRefreshView.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                        page = 1;
                        getData();
                        mRefreshView.onRefreshComplete();
                        showMsg("重置O(∩_∩)~");
                    }

                    @Override
                    public void onLoadMore(PullToRefreshBase<ListView> refreshView) {
                        page++;
                        getData();
                        mRefreshView.onRefreshComplete();
                        showMsg("更新了   " + size + " 条数据");
                    }
                });
    }

    private void getData() {
        NetQueryImpl.getInstance(this).statusCommentQuery(handler, status.getIdstr(), page);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            ArrayList<Comments> newDate;
            if (msg.what == App.MESSAGE_STATUS_COMMENTS) {
                newDate = (ArrayList<Comments>) msg.obj;
                size = newDate.size();
                if (size == 0) {
                    showMsg("没数据啦，再刷手机炸了/(ㄒoㄒ)/~~");
                } else {
                    if (page == 1) {
                        commentsList = newDate;
                    } else {
                        for (int i = 0; i < newDate.size(); i++) {
                            commentsList.add(newDate.get(i));
                        }
                    }
                    mAdapter.setData(commentsList);
                    mAdapter.notifyDataSetChanged();
                    mRefreshView.onRefreshComplete();
                }
            }
        }
    };


    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRecomendCount = (TextView) findViewById(R.id.tv_pinglun);
        tvZhuanfaCount = (TextView) findViewById(R.id.tv_zhuanfa);
        tvZanCount = (TextView) findViewById(R.id.tv_zan);

        mRefreshView = (PullToRefreshListView) findViewById(R.id.ptr_lv);
        mListView = mRefreshView.getRefreshableView();
        // 添加头选项
        header = getLayoutInflater().inflate(R.layout.activity_pager_header, null);


        mListView.addHeaderView(header);
        initHeader(header);
        mAdapter = new WBPageAdapter(this, commentsList);
        mListView.setAdapter(mAdapter);

        img_zan = (ImageView) findViewById(R.id.iv_zan);

        tab = findViewById(R.id.tab);

        mRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                mAdapter.clear();
                mAdapter.notifyDataSetChanged();
                getData();
            }

            @Override
            public void onLoadMore(PullToRefreshBase<ListView> refreshView) {

            }
        });

        mRefreshView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(
                    AbsListView view, int firstVisibleItem,
                    int visibleItemCount, int totalItemCount) {
                // 是否显示悬浮的逻辑
                int[] location = new int[2];
                header.getLocationInWindow(location);
                int topHeight = UIUtils.getStatusBarHeight(WeiboPageActivity.this)
                        + UIUtils.dip2px(WeiboPageActivity.this, 90);// 顶部高度，状态栏，标题栏，以及这个Tab的高度
                boolean needShowTab = true;
                if (firstVisibleItem == 0) {
                    if (location[1] - topHeight > -header.getMeasuredHeight()) {
                        needShowTab = false;
                    }
                }
                if (needShowTab) {
                    tab.setVisibility(View.VISIBLE);
                } else {
                    tab.setVisibility(View.GONE);
                }
            }
        });

    }

    /**
     * 初始化控件
     */
    private void initHeader(View header) {
        img_icon = (SimpleDraweeView) header.findViewById(R.id.img_wbPage_icon);
        img_collect = (ImageView) header.findViewById(R.id.img_wbPage_collect);
        text_name = (TextView) header.findViewById(R.id.text_wbPage_name);
        text_time = (TextView) header.findViewById(R.id.text_wbPage_time);
        text_device = (TextView) header.findViewById(R.id.text_wbPage_device);
        text_word = (TextView) header.findViewById(R.id.text_wbPage_word);
        img_weibo1 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage1);
        img_weibo2 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage2);
        img_weibo3 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage3);
        img_weibo4 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage4);
        img_weibo5 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage5);
        img_weibo6 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage6);
        img_weibo7 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage7);
        img_weibo8 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage8);
        img_weibo9 = (SimpleDraweeView) header.findViewById(R.id.img_wbPage9);
        reLayout_weibo_tra = (RelativeLayout) header.findViewById(R.id.re_wbPage_tra);
        reLayout_weibo_com = (RelativeLayout) header.findViewById(R.id.re_wbPage_com);
        reLayout_weibo_zan = (RelativeLayout) header.findViewById(R.id.re_wbPage_zan);
        picHashMap.put(0, img_weibo1);
        picHashMap.put(1, img_weibo2);
        picHashMap.put(2, img_weibo3);
        picHashMap.put(3, img_weibo4);
        picHashMap.put(4, img_weibo5);
        picHashMap.put(5, img_weibo6);
        picHashMap.put(6, img_weibo7);
        picHashMap.put(7, img_weibo8);
        picHashMap.put(8, img_weibo9);
        gridLayout = (GridLayout) header.findViewById(R.id.gridLayout_weibo);
        tvRecomendCount2 = (TextView) header.findViewById(R.id.tv_pinglun);
        tvZhuanfaCount2 = (TextView) header.findViewById(R.id.tv_zhuanfa);
        tvZanCount2 = (TextView) header.findViewById(R.id.tv_zan);
    }

    /**
     * 显示信息
     */
    private void showInformation() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        status = (Status) bundle.get(FirstPageFragment.STATUS_SINGLE);
        User user = status.getUser();

        img_icon.setImageURI(Uri.parse(user.getAvatar_large()));
        text_name.setText(user.getScreen_name());
        text_device.setText(sourceStart.append(status.getSource()));
        sourceStart.replace(0, sourceStart.length(), "来自 ");
        text_time.setText(Util.stringTranslateTime(status.getCreated_at()));
        text_word.setText(status.getText());
        img_zan.setImageResource(status.isFavorited() ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
        img_collect.setImageResource(status.isFavorited() ? R.drawable.shoucang2 : R.drawable.shoucang);

        // 获得图片ArrayList
        String[] pic_urls = Util.getPicList(status.getBmiddle_pic());

        // 因为截断为空的时候会默认给一个为""的字符串,给索引为0的数组中
        if (pic_urls[0] != "") {
            int i = 0, leng = pic_urls.length;
            for (; i < pic_urls.length; i++) {
                if (leng < 2) {
                    gridLayout.setColumnCount(1);
                } else if (leng < 6) {
                    gridLayout.setColumnCount(2);
                } else {
                    gridLayout.setColumnCount(3);
                }
                // 获得图片控件的引用
                SimpleDraweeView picView = picHashMap.get(i);
                picView.setAspectRatio(0.6f);
                picView.setImageURI(Uri.parse(pic_urls[i]));
                picView.setVisibility(View.VISIBLE);
            }
            // 多余的控件隐藏
            for (; i < 9; i++) {
                picHashMap.get(i).setVisibility(View.GONE);
            }
        }


        tvZhuanfaCount2.setText("转发 " + status.getReposts_count());
        tvRecomendCount2.setText("评论 " + status.getComments_count());
        tvZanCount2.setText("赞 " + status.getAttitudes_count());
        tvZhuanfaCount.setText("转发 " + status.getReposts_count());
        tvRecomendCount.setText("评论 " + status.getComments_count());
        tvZanCount.setText("赞 " + status.getAttitudes_count());
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
