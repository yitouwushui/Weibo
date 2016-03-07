package com.yitouwushui.weibo.main;


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
import android.widget.Toast;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {

    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_discovery;
    WeiboAdapter weiboAdapter;
    ArrayList<Status> discoveryData = new ArrayList<>();
    int size = 0;

//    https://api.weibo.com/2/statuses/public_timeline.json

    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discovery, container, false);
        listView_discovery = (ListView) v.findViewById(R.id.listView_discovery);

        if (discoveryData.size() == 0) {
            getData();
            if (discoveryData.size() == 0) {
                NetQueryImpl.getInstance(getContext()).publicStatusQuery(handler);
            }
        }

        if (weiboAdapter == null) {
            weiboAdapter = new WeiboAdapter(getContext(), discoveryData);
//            weiboAdapter.notifyDataSetChanged();
        }
        listView_discovery.setAdapter(weiboAdapter);
        listView_discovery.setOnItemClickListener(new FirstListViewListener());

        return v;
    }

    public void freshen() {
        NetQueryImpl.getInstance(getContext()).publicStatusQuery(handler);
    }

    private void getData() {
        ArrayList<Status> newData = (ArrayList<Status>) Status.findWithQuery(
                Status.class,
                "select * from status order by id desc limit 10");
        for (int i = newData.size() - 1; i >= 0; i--) {
            Status status = newData.get(i);
            Status.User user = User.findById(Status.User.class, status.getUserIdStatus());
            status.setUser(user);
            discoveryData.add(status);
            if (weiboAdapter != null) {
                weiboAdapter.setData(discoveryData);
                weiboAdapter.notifyDataSetChanged();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_PUBLIC_STATUS) {
                getData();
            }
        }
    };

    public class FirstListViewListener implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            intent.putExtra(STATUS_SINGLE, discoveryData.get(position));
//            startActivity(intent);
            Toast.makeText(getActivity(), "跳转单微博" + position, Toast.LENGTH_SHORT).show();

        }
    }

}
