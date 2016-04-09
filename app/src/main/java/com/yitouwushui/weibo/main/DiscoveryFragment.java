package com.yitouwushui.weibo.main;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.me.StatusAdapter;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {

    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_discovery;
    StatusAdapter discoveryAdapter;
    List<Status> discoveryData = new LinkedList<>();

    public DiscoveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (discoveryData.size() == 0) {
            getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discovery, container, false);
        listView_discovery = (ListView) v.findViewById(R.id.listView_discovery);


        if (discoveryAdapter == null) {
            discoveryAdapter = new StatusAdapter(getContext(), discoveryData);
        }
        listView_discovery.setAdapter(discoveryAdapter);
        listView_discovery.setOnItemClickListener(new FirstListViewListener());

        return v;
    }

    public void freshen() {
        NetQueryImpl.getInstance(getContext()).publicStatusQuery(handler);
    }

    /**
     * 从本地存储加载数据到当前discoveryData表
     */
    private void getData() {
        ArrayList<Status> newData = (ArrayList<Status>) Status.findWithQuery(
                Status.class,
                "select * from status where type = 1 limit 10");
        for (int i = 0; i < newData.size(); i++) {
            Status status = newData.get(i);
            User user = User.findById(User.class, status.getUserIdStatus());
            status.setUser(user);
            discoveryData.add(0,status);
        }

        if (discoveryAdapter != null) {
            discoveryAdapter.setData(discoveryData);
            discoveryAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 处理网络请求返回消息
     */
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_PUBLIC_STATUS) {
                getData();
            }
        }
    };

    /**
     * listView监听器
     */
    public class FirstListViewListener implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(STATUS_SINGLE, discoveryData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

}
