package com.yitouwushui.weibo.main;

import android.content.Intent;
import android.os.Bundle;
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
import com.yitouwushui.weibo.Login.LoginActivity;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.AccessToken;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.me.FriendsActivity;
import com.yitouwushui.weibo.me.MyStatusActivity;
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

        accessToken = AccessToken.findById(AccessToken.class, 8);
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
                        Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                        startActivity(intent);
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
     * 切换显示的fragment
     *
     * @param fragment 片段
     */
    public void setVisibility(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    /**
     * 通知消息
     *
     * @param msg 消息
     */
    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void exit(View view) {
        boolean isExit = AccessToken.delete(accessToken);
        if (isExit) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            showMsg("退出成功");
            finish();
        } else {
            showMsg("退出失败");
        }
    }

    public void myStatus(View view) {
        Intent intent = new Intent(this, MyStatusActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        startActivity(intent);

    }

    public void follow(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        intent.putExtra(App.ACTION_FOLLOW_TITLE, "全部关注");
        intent.putExtra(App.ACTION_ISFOLLOW, true);
        startActivity(intent);
    }

    public void following(View view) {
        Intent intent = new Intent(this, FriendsActivity.class);
        intent.putExtra(App.ACTION_USERID, user.getIdstr());
        intent.putExtra(App.ACTION_FOLLOW_TITLE, "粉丝");
        intent.putExtra(App.ACTION_ISFOLLOW, false);
        startActivity(intent);
    }

    public void homePage(View view) {
        IntentUtils.startHome(this, user.getIdstr());
    }

    public void freshen(View view) {
        discoveryFragment.freshen();
    }

}
