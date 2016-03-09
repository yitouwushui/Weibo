package com.yitouwushui.weibo.weibo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.Pic_urls;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.main.FirstPageFragment;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.util.TimeUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WeiboPageActivity extends AppCompatActivity implements MyScrollView.OnScrollListener {

    TabLayout tabLayout;
    // 顶栏标题
    TextView text_title;
    MyScrollView myScrollView;
    // tabLayout 顶部坐标
    int tabLayoutTop;

    // tabLayout 的两个位置
    LinearLayout tab1, tab2;
    RelativeLayout relayout;

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
    WBPageAdapter wbPageAdapter;
    ArrayList<Comments> commentsList = new ArrayList<>();
    ListView listView_comments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_page);
        // 初始化
        init();

//        tabAdapter = new TabAdapter(getSupportFragmentManager());
//
//        viewPager.setAdapter(tabAdapter);
//        tabLayout.setupWithViewPager(viewPager);

        showInformation();

        if (commentsList.size() == 0) {
            NetQueryImpl.getInstance(this).commentStatusQuery(handler, status.getIdstr());
        }
        wbPageAdapter = new WBPageAdapter(this, commentsList);
        listView_comments.setAdapter(wbPageAdapter);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_COMMENTS) {
                commentsList = (ArrayList<Comments>) msg.obj;
                for (int i = 0; i < commentsList.size(); i++) {
                    Log.e("-----------",commentsList.toString());
                }
                wbPageAdapter.setData(commentsList);
                wbPageAdapter.notifyDataSetChanged();
            }
        }
    };

    /**
     * 初始化控件
     */
    private void init() {
        tabLayout = (TabLayout) findViewById(R.id.tab_wbPage);
        text_title = (TextView) findViewById(R.id.text_titile);
        text_title.setText("微博正文");
        tabLayout.addTab(tabLayout.newTab().setText("转发"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("评论"), 1);
        tabLayout.addTab(tabLayout.newTab().setText("赞"), 2);

        img_icon = (SimpleDraweeView) findViewById(R.id.img_wbPage_icon);
        img_collect = (ImageView) findViewById(R.id.img_wbPage_collect);
        text_name = (TextView) findViewById(R.id.text_wbPage_name);
        text_time = (TextView) findViewById(R.id.text_wbPage_time);
        text_device = (TextView) findViewById(R.id.text_wbPage_device);
        text_word = (TextView) findViewById(R.id.text_wbPage_word);
        img_weibo1 = (SimpleDraweeView) findViewById(R.id.img_wbPage1);
        img_weibo2 = (SimpleDraweeView) findViewById(R.id.img_wbPage2);
        img_weibo3 = (SimpleDraweeView) findViewById(R.id.img_wbPage3);
        img_weibo4 = (SimpleDraweeView) findViewById(R.id.img_wbPage4);
        img_weibo5 = (SimpleDraweeView) findViewById(R.id.img_wbPage5);
        img_weibo6 = (SimpleDraweeView) findViewById(R.id.img_wbPage6);
        img_weibo7 = (SimpleDraweeView) findViewById(R.id.img_wbPage7);
        img_weibo8 = (SimpleDraweeView) findViewById(R.id.img_wbPage8);
        img_weibo9 = (SimpleDraweeView) findViewById(R.id.img_wbPage9);
        reLayout_weibo_tra = (RelativeLayout) findViewById(R.id.re_wbPage_tra);
        reLayout_weibo_com = (RelativeLayout) findViewById(R.id.re_wbPage_com);
        reLayout_weibo_zan = (RelativeLayout) findViewById(R.id.re_wbPage_zan);
        img_zan = (ImageView) findViewById(R.id.img_wbPage_zan);
        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        relayout = (RelativeLayout) findViewById(R.id.relative_wbPage);
        tab1 = (LinearLayout) findViewById(R.id.lineLayout_wb_tab1);
        tab2 = (LinearLayout) findViewById(R.id.lineLayout_wb_tab2);
        myScrollView.setOnScrollListener(this);
        picHashMap.put(0, img_weibo1);
        picHashMap.put(1, img_weibo2);
        picHashMap.put(2, img_weibo3);
        picHashMap.put(3, img_weibo4);
        picHashMap.put(4, img_weibo5);
        picHashMap.put(5, img_weibo6);
        picHashMap.put(6, img_weibo7);
        picHashMap.put(7, img_weibo8);
        picHashMap.put(8, img_weibo9);
        listView_comments = (ListView) findViewById(R.id.listView);
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
        text_time.setText(TimeUtil.stringTranslateTime(status.getCreated_at()));
        text_word.setText(status.getText());
        setTabTitle(0, status.getReposts_count());
        setTabTitle(1, status.getComments_count());
        setTabTitle(2, status.getAttitudes_count());
        img_zan.setImageResource(status.isFavorited() ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
        img_collect.setImageResource(status.isFavorited() ? R.drawable.shoucang2 : R.drawable.shoucang);

        // 获得图片ArrayList
        ArrayList<Pic_urls> pic_urlsList = status.getPic_urls();
        if (pic_urlsList != null) {
            int i = 0;
            for (; i < pic_urlsList.size(); i++) {
                // 获得图片控件的引用
                SimpleDraweeView picView = picHashMap.get(i);

                picView.setImageURI(Uri.parse(pic_urlsList.get(i).getThumbnail_pic()));
                picView.setVisibility(View.VISIBLE);
            }
            // 多余的控件隐藏
            for (; i < 9; i++) {
                picHashMap.get(i).setVisibility(View.GONE);

            }
        }
//        img_zan.setImageResource(isZan ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
    }


    /**
     * tabLayout 改变
     *
     * @param hasFocus
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // 把悬浮框上面一个控件的底部Y坐标记录
            tabLayoutTop = relayout.getBottom();
            Log.e("cheshi", String.valueOf(tabLayoutTop));
        }
    }

    @Override
    public void onScroll(int scrollY) {
        if (scrollY >= tabLayoutTop) {
            if (tabLayout.getParent() != tab1) {
                tab2.removeView(tabLayout);
                tab1.addView(tabLayout);
            }
        } else {
            if (tabLayout.getParent() != tab2) {
                tab1.removeView(tabLayout);
                tab2.addView(tabLayout);
            }
        }
    }

    /**
     * 修改tabLayout 显示
     *
     * @param index tab索引
     * @param count 数量
     */
    public void setTabTitle(int index, int count) {
        switch (index) {
            case 0:
                tabLayout.getTabAt(0).setText("转发  " + count);
                break;
            case 1:
                tabLayout.getTabAt(1).setText("评论  " + count);
                break;
            case 2:
                tabLayout.getTabAt(2).setText("赞  " + count);
                break;
        }
    }


    private class TabAdapter extends FragmentPagerAdapter {

        List<Fragment> fragments = new ArrayList<>();

        public TabAdapter(FragmentManager fm) {
            super(fm);
            fragments.add(new WeiboCommentsFragment());
            fragments.add(new WeiboCommentsFragment());
            fragments.add(new WeiboCommentsFragment());
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
