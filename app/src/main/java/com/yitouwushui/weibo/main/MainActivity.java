package com.yitouwushui.weibo.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.AccessToken;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.me.FriendsActivity;
import com.yitouwushui.weibo.me.MyStatusActivity;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.utils.IntentUtils;


public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FragmentManager fm;
    DiscoveryFragment discoveryFragment;
    FirstPageFragment firstPageFragment;
    MeFragment meFragment;
    MessageFragment messageFragment;
    BottomAdapter bottomAdapter;
    GridView gridView_bottom;
    User user;
    AccessToken accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this);

        discoveryFragment = new DiscoveryFragment();
        firstPageFragment = new FirstPageFragment();
        meFragment = new MeFragment();
        messageFragment = new MessageFragment();

        init();

        accessToken = AccessToken.findById(AccessToken.class, NetQueryImpl.accessTokenID);
        user = User.findById(User.class, Long.valueOf(accessToken.getUid()));
    }

    /**
     * 初始化
     */
    private void init() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        gridView_bottom = (GridView) findViewById(R.id.gridView);

        fm = getSupportFragmentManager();
        setVisibility(meFragment);

        bottomAdapter = new BottomAdapter(MainActivity.this);
        gridView_bottom.setAdapter(bottomAdapter);

        gridView_bottom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bottomAdapter.select = position;
                switch (position) {
                    case 0:
                        setVisibility(firstPageFragment);
                        break;
                    case 1:
                        setVisibility(messageFragment);
                        break;
                    case 2:
                        IntentUtils.startUpdate(MainActivity.this);
                        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                        intent.putExtra(App.ACTION_UPDATE_TITLE, "发微博");
                        MainActivity.this.startActivityForResult(intent, App.ACTION_REQUEST_UPDATE);
                        break;
                    case 3:
                        setVisibility(discoveryFragment);
                        break;
                    case 4:
                        setVisibility(meFragment);
                        break;
                    default:
                        break;
                }
                bottomAdapter.notifyDataSetChanged();
                gridView_bottom.setAdapter(bottomAdapter);
            }
        });

    }

    /**
     * 从UpdateActivity中获取结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == App.ACTION_REQUEST_UPDATE && resultCode == RESULT_OK) {
            String title = data.getStringExtra(App.ACTION_UPDATE_TITLE);
            String input = data.getStringExtra(App.ACTION_UPDATE_INPUT);
            NetQueryImpl.getInstance(this).updateStatus(handler, input);

            showMsg("正在" + title);
        }


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_UPDATE_STATUS_FAIL) {
                showMsg("微博发送失败");
            }
            if (msg.what == App.MESSAGE_UPDATE_STATUS_SUCCESS) {

                showMsg("微博发送成功");
            }
        }
    };

    /**
     * 切换显示的fragment
     *
     * @param fragment 片段
     */
    public void setVisibility(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    /**
     * 退出登录
     *
     * @param view
     */
    public void exit(View view) {
//        boolean isExit = AccessToken.delete(accessToken);
//        if (isExit) {
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            showMsg("退出成功");
//            finish();
//        } else {
//            showMsg("退出失败");
//        }
    }

    /**
     * 我的微博
     *
     * @param view
     */
    public void myStatus(View view) {
        Intent intent = new Intent(this, MyStatusActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        startActivity(intent);

    }

    /**
     * 我的关注
     *
     * @param view
     */
    public void follow(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        intent.putExtra(App.ACTION_FOLLOW_TITLE, "全部关注");
        intent.putExtra(App.ACTION_ISFOLLOW, true);
        startActivity(intent);
    }

    /**
     * 我的粉丝
     *
     * @param view
     */
    public void following(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        intent.putExtra(App.ACTION_FOLLOW_TITLE, "粉丝");
        intent.putExtra(App.ACTION_ISFOLLOW, false);
        startActivity(intent);
    }

    /**
     * 个人主页
     *
     * @param view
     */
    public void homePage(View view) {
        IntentUtils.startHome(this, user.getIdstr());
    }

    /**
     * 热门页面刷新
     *
     * @param view
     */
    public void freshen(View view) {
        discoveryFragment.freshen();
    }





    /**
     * 通知消息
     *
     * @param msg 消息
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
