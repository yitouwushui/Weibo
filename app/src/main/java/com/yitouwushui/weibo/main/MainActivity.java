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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;
import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.account.PicUrls;
import com.yitouwushui.weibo.account.Status;
import com.yitouwushui.weibo.weibo.WeiboAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FragmentManager fm;
    DiscoveryFragment discoveryFragment;
    FirstPageFragment firstPageFragment;
    MeFragment meFragment;
    MessageFragment messageFragment;
    BottomAdapter bottomAdapter;
    GridView gridView_bottom;

    String access_token = null;
    String uid;
    String expires_in;
    ArrayList<Status> listStatus = new ArrayList<>();
    ArrayList<PicUrls> picUrlslist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            discoveryFragment = new DiscoveryFragment();
            firstPageFragment = new FirstPageFragment();
            meFragment = new MeFragment();
            messageFragment = new MessageFragment();
        }
        init();


    }

    /**
     * 主页面切换
     */
    private void init() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        gridView_bottom = (GridView) findViewById(R.id.gridView);

        fm = getSupportFragmentManager();
        setVisibility(firstPageFragment);

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
                        showMsg("+");
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

        // 获取参数
        Intent intent = getIntent();
        access_token = intent.getStringExtra(App.ACCESS_TOKEN);
        uid = intent.getStringExtra("uid");
        expires_in = intent.getStringExtra("expires_in");
    }

    /**
     * 切换显示的fragment
     *
     * @param fragment
     */
    public void setVisibility(Fragment fragment) {
        fm.beginTransaction().replace(R.id.frameLayout, fragment).commit();
    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
