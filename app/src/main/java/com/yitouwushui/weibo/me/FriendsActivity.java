package com.yitouwushui.weibo.me;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    private PullToRefreshListView mRefreshView;
    private ImageView img_back;
    private TextView textView;
    private ListView listView_friends;
    private ArrayList<User> newData = new ArrayList<>();
    private ArrayList<User> data = new ArrayList<>();
    FriendsAdapter friendsAdapter;
    // 查询游标位置
    int cursur = 0;
    // 用户ID
    String idstr;
    // 判断上滑下滑
    boolean isLoadMore = false;
    // 返回的游标
    int previous_cursor, next_cursur;
    // 记录更新的数据
    int size = 0;
    // 判断是查看粉丝 或者 关注
    boolean isFollow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        // 初始化
        init();

        refresh();

        data = newData;
        changeAdapter(data);

        mRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                refresh();
                showMsg("咦？居然被发现了！这里是重置~加载更多请上滑");
                next_cursur = -1;
            }

            @Override
            public void onLoadMore(PullToRefreshBase<ListView> refreshView) {
                // 如果游标归0则不处理数据
                cursur = next_cursur;
                if (cursur == 0 && data.size() != 0) {
                    showMsg("没有数据啦o(>﹏<)o，其他用户没有授权");
                    mRefreshView.onRefreshComplete();
                } else {
                    loadMore();
                    showMsg("更新了 " + size + " 数据");
                }

            }
        });

        listView_friends.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    /**
     * 刷新数据
     *
     * @param data
     */
    private void changeAdapter(List<User> data) {
        if (friendsAdapter != null) {
            friendsAdapter.setData(data);
            friendsAdapter.notifyDataSetChanged();
            mRefreshView.onRefreshComplete();
        } else {
            friendsAdapter = new FriendsAdapter(this, data);
            listView_friends.setAdapter(friendsAdapter);
            mRefreshView.onRefreshComplete();
        }
    }

    /**
     *
     */
    private void loadMore() {
        isLoadMore = true;
        if (isFollow) {
            NetQueryImpl.getInstance(this).userFriendsQuery(handler, idstr, cursur);
        } else {
            NetQueryImpl.getInstance(this).userFollowersQuery(handler, idstr, cursur);
        }

    }

    private void refresh() {
        isLoadMore = false;
        if (isFollow) {
            NetQueryImpl.getInstance(this).userFriendsQuery(handler, idstr, cursur);
        } else {
            NetQueryImpl.getInstance(this).userFollowersQuery(handler, idstr, cursur);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_FRIENDS_USERS) {
                newData = (ArrayList<User>) msg.obj;
                previous_cursor = msg.arg2;
                next_cursur = msg.arg1;
                Log.e("cursur", String.valueOf(next_cursur));

                // 记录新数据量
                size = newData.size();
                if (isLoadMore) {
                    for (User user : newData) {
                        data.add(user);
                    }
                } else {
                    data = newData;
                }
                Log.d("----关注页面-----", String.valueOf(data.size()));
                // 更改适配器
                changeAdapter(data);
            }

        }
    };

    private void init() {
        mRefreshView = (PullToRefreshListView) findViewById(R.id.friends_lv);
        img_back = (ImageView) findViewById(R.id.friends_back);
        textView = (TextView) findViewById(R.id.friends_title);
        listView_friends = mRefreshView.getRefreshableView();

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // 从意图获得参数
        Intent intent = getIntent();
        idstr = intent.getStringExtra(App.ACTION_USERID);
        String title = intent.getStringExtra(App.ACTION_FOLLOW_TITLE);
        isFollow = intent.getBooleanExtra(App.ACTION_ISFOLLOW, false);
        textView.setText(title);

    }

    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
