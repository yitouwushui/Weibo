package com.yitouwushui.weibo.weibo;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.main.FirstPageFragment;

public class WeiboPageActivity extends AppCompatActivity implements MyScrollView.OnScrollListener {

    TabLayout tabLayout;
    TextView text_title;
    MyScrollView myScrollView;
    int tabLayoutTop;
    LinearLayout tab1, tab2;
    RelativeLayout relayout;

    // 微博信息控件
    ImageView img_icon;
    ImageView img_collect;
    TextView text_name;
    TextView text_time;
    TextView text_device;
    TextView text_word;
    ImageView img_weibo1;
    ImageView img_weibo2;
    ImageView img_weibo3;
    ImageView img_weibo4;
    ImageView img_weibo5;
    ImageView img_weibo6;
    ImageView img_weibo7;
    ImageView img_weibo8;
    ImageView img_weibo9;
    RelativeLayout reLayout_weibo_tra;
    RelativeLayout reLayout_weibo_com;
    RelativeLayout reLayout_weibo_zan;
    ImageView img_zan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weibo_page);
        // 初始化
        init();

        showInformation();

    }



    private void init() {
        tabLayout = (TabLayout) findViewById(R.id.tab_wbPage);
        text_title = (TextView) findViewById(R.id.text_titile);
        text_title.setText("微博正文");
        tabLayout.addTab(tabLayout.newTab().setText("转发"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("评论"), 1);
        tabLayout.addTab(tabLayout.newTab().setText("赞"), 2);

        img_icon = (ImageView) findViewById(R.id.img_wbPage_icon);
        img_collect = (ImageView) findViewById(R.id.img_wbPage_collect);
        text_name = (TextView) findViewById(R.id.text_wbPage_name);
        text_time = (TextView) findViewById(R.id.text_wbPage_time);
        text_device = (TextView) findViewById(R.id.text_wbPage_device);
        text_word = (TextView) findViewById(R.id.text_wbPage_word);
        img_weibo1 = (ImageView) findViewById(R.id.img_wbPage1);
        img_weibo2 = (ImageView) findViewById(R.id.img_wbPage2);
        img_weibo3 = (ImageView) findViewById(R.id.img_wbPage3);
        img_weibo4 = (ImageView) findViewById(R.id.img_wbPage4);
        img_weibo5 = (ImageView) findViewById(R.id.img_wbPage5);
        img_weibo6 = (ImageView) findViewById(R.id.img_wbPage6);
        img_weibo7 = (ImageView) findViewById(R.id.img_wbPage7);
        img_weibo8 = (ImageView) findViewById(R.id.img_wbPage8);
        img_weibo9 = (ImageView) findViewById(R.id.img_wbPage9);
        reLayout_weibo_tra = (RelativeLayout) findViewById(R.id.re_wbPage_tra);
        reLayout_weibo_com = (RelativeLayout) findViewById(R.id.re_wbPage_com);
        reLayout_weibo_zan = (RelativeLayout) findViewById(R.id.re_wbPage_zan);
        img_zan = (ImageView) findViewById(R.id.img_wbPage_zan);

        myScrollView = (MyScrollView) findViewById(R.id.myScrollView);
        relayout = (RelativeLayout) findViewById(R.id.relative_wbPage);
        tab1 = (LinearLayout) findViewById(R.id.lineLayout_wb_tab1);
        tab2 = (LinearLayout) findViewById(R.id.lineLayout_wb_tab2);
        myScrollView.setOnScrollListener(this);

    }

    private void showInformation() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = (String) bundle.get(FirstPageFragment.WEIBO_MESSAGE);

        img_icon.setImageResource(R.drawable.tx);
        text_name.setText(name);
        text_word.setText("微博正文12321321312321321");
//        img_zan.setImageResource(isZan ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // 把悬浮框上面一个控件的底部Y坐标记录
            tabLayoutTop = relayout.getBottom();
            Log.e("cheshi",String.valueOf(tabLayoutTop));
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
            if (tabLayout.getParent() != tab2){
                tab1.removeView(tabLayout);
                tab2.addView(tabLayout);
            }
        }
    }

    public void setTabTitle(int index, int count) {
        switch (index) {
            case 0:
                tabLayout.getTabAt(0).setText("转发  " + count);
                break;
            case 1:
                tabLayout.getTabAt(0).setText("评论  " + count);
                break;
            case 2:
                tabLayout.getTabAt(0).setText("赞  " + count);
                break;
        }
    }


}
