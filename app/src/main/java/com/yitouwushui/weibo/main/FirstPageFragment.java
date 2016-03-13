package com.yitouwushui.weibo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.me.MyStatusAdapter;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshBase;
import com.yitouwushui.weibo.view.pulltorefreshview.PullToRefreshListView;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 首页
 */
public class FirstPageFragment extends Fragment {

    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_first;
    PullToRefreshListView mRefreshView;
    MyStatusAdapter statusAdapter;
    List<Status> firstData = new LinkedList<>();
    // 记录微博加载的位置
    Long statusIndex;

    public FirstPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (firstData.size() == 0) {
            getOldData();
        } else if (firstData.size() == 0) {
            getNewData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);
        mRefreshView = (PullToRefreshListView) v.findViewById(R.id.listView_first_page);


        listView_first = mRefreshView.getRefreshableView();
        new Runnable() {
            @Override
            public void run() {
                mRefreshView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
                    @Override
                    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                        NetQueryImpl.getInstance(getActivity()).friendStatusQuery(handler);
                    }

                    @Override
                    public void onLoadMore(PullToRefreshBase<ListView> refreshView) {
                        getOldData();// 上滑加载旧数据。
                    }
                });
            }
        }.run();

        if (statusAdapter == null) {
            statusAdapter = new MyStatusAdapter(getActivity(), firstData);
        }
        listView_first.setAdapter(statusAdapter);
        listView_first.setOnItemClickListener(new FirstListViewListener());
        return v;
    }

    /**
     * 从本地存储加载新的数据到当前firstData表
     */
    private void getNewData() {
        // 从本地文件取出首页微博的位置值
        statusIndex = getActivity().getPreferences(Context.MODE_PRIVATE).getLong("statusIndex", 0);
        Log.e(String.valueOf(firstData.size()), String.valueOf(statusIndex));

        ArrayList<Status> newData = (ArrayList<Status>) Status.findWithQuery(
                Status.class,
                "select * from status where type = 2 and id >" + statusIndex + " limit 20");

        for (int i = 0; i < newData.size(); i++) {
            Status status = newData.get(i);
            // 从数据库中取出用户资料
            User user = User.findById(User.class, status.getUserIdStatus());
            status.setUser(user);
            firstData.add(0, status);
        }

        // 将上次浏览的微博位置保存起来
        if (firstData.size() >= 20) {
            statusIndex = firstData.get(0).getId();
        }
        getActivity().getPreferences(Context.MODE_PRIVATE).edit()
                .putLong("statusIndex", (statusIndex)).commit();

        Log.e(String.valueOf(firstData.size()), String.valueOf(statusIndex));

        if (statusAdapter != null) {
            statusAdapter.setData(firstData);
            statusAdapter.notifyDataSetChanged();
            mRefreshView.onRefreshComplete();
        }
    }

    /**
     * 从本地加载老的数据
     */
    private void getOldData() {

        statusIndex = getActivity().getPreferences(Context.MODE_PRIVATE).getLong("statusIndex", 0);
        if (firstData.size() != 0) {
            statusIndex = firstData.get(firstData.size() - 1).getId();
        }
        ArrayList<Status> newData = (ArrayList<Status>) Status.findWithQuery(
                Status.class,
                "select * from (select * from status order by id desc) where type = 2 and id < " + statusIndex + " limit 20");
        if (newData == null || newData.size() == 0) {
            Toast.makeText(getContext(), "本地没有更早的数据啦!~(≧▽≦)/~", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < newData.size(); i++) {
                Status status = newData.get(i);
                // 从数据库中取出用户资料
                User user = User.findById(User.class, status.getUserIdStatus());
                status.setUser(user);
                firstData.add(status);
            }
            if (statusAdapter != null) {
                statusAdapter.setData(firstData);
                statusAdapter.notifyDataSetChanged();
                mRefreshView.onRefreshComplete();
            }
        }
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_FRIENDS_STATUS) {
                getNewData();
            }
        }
    };

    public class FirstListViewListener
            implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(STATUS_SINGLE, firstData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }


}
