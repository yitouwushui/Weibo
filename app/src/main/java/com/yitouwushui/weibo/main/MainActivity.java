package com.yitouwushui.weibo.main;

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
import com.yitouwushui.weibo.R;

public class MainActivity extends AppCompatActivity {

    FrameLayout frameLayout;
    FragmentManager fm;
    DiscoveryFragment discoveryFragment;
    FirstPageFragment firstPageFragment;
    MeFragment meFragment;
    MessageFragment messageFragment;
    BottomAdapter bottomAdapter;
    GridView gridView_bottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fresco.initialize(this);

        if (savedInstanceState == null) {
            discoveryFragment = new DiscoveryFragment();
            firstPageFragment = new FirstPageFragment();
            meFragment = new MeFragment();
            messageFragment = new MessageFragment();
        }

        init();
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
    }

    public void weiBo(View view) {

    }

    public void follow(View view) {

    }

    public void following(View view) {
    }

    public void homePage(View view) {
    }

}
