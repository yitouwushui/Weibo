package com.yitouwushui.weibo.me;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshListView;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;
import java.util.List;

public class MyStatusActivity extends AppCompatActivity {


    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_my_status;
    PullToRefreshListView mRefreshView;
    StatusAdapter myStatusAdapter;
    List<Status> myStatusData = new ArrayList<>();
    ImageView img_back;
    // 查询者的id
    String idstr;
    // 返回微博结果页码
    int page = 1;
    int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_status);

        init();
        getDate();
        changeAdapter(myStatusData);

        mRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                myStatusData.clear();
                getDate();
                showMsg("重置~<(￣3￣)>~~啦啦啦");
            }

            @Override
            public void onLoadMore(PullToRefreshBase<ListView> refreshView) {
                getDate();
                showMsg(String.valueOf(size));
            }
        });

        listView_my_status.setOnItemClickListener(new MyStatusViewListener());

    }

    public void getDate() {
        NetQueryImpl.getInstance(MyStatusActivity.this).myStatusQuery(handler, idstr, page);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ArrayList<Status> newData = new ArrayList<>();
            if (msg.what == App.MESSAGE_MY_STATUS) {
                newData = (ArrayList<Status>) msg.obj;
                int size = newData.size();
                if (size != 0) {
                    for (int i = 0; i < newData.size(); i++) {
                        myStatusData.add(newData.get(i));
                    }
                    page++;
                    changeAdapter(myStatusData);
                } else {
                    showMsg("木有数据了,别刷了，再刷手机都要炸");
                    mRefreshView.onRefreshComplete();
                }
            }

        }
    };

    /**
     * 刷新数据
     *
     * @param data
     */
    private void changeAdapter(List<Status> data) {
        if (myStatusAdapter != null) {
            myStatusAdapter.setData(data);
            myStatusAdapter.notifyDataSetChanged();
            mRefreshView.onRefreshComplete();
        } else {
            myStatusAdapter = new StatusAdapter(this, data);
            listView_my_status.setAdapter(myStatusAdapter);
            mRefreshView.onRefreshComplete();
        }
    }

    /**
     * 初始化
     */
    private void init() {
        mRefreshView = (PullToRefreshListView) findViewById(R.id.my_status_lv);
        listView_my_status = mRefreshView.getRefreshableView();
        img_back = (ImageView) findViewById(R.id.my_status_back);
        idstr = getIntent().getStringExtra(App.ACTION_USERID);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public class MyStatusViewListener
            implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(MyStatusActivity.this, WeiboPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(STATUS_SINGLE, myStatusData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


    public void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
