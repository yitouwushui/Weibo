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

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Pic_urls;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.net.NetQueryImpl;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;

public class FirstPageFragment extends Fragment {

    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_first;
    WeiboAdapter weiboAdapter;
    ArrayList<Status> fristData = new ArrayList<>();

//    https://api.weibo.com/2/statuses/user_timeline/ids.json

    public FirstPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (fristData.size() == 0) {
            getData();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);
        listView_first = (ListView) v.findViewById(R.id.listView_first_page);

//        if (fristData.size() == 0) {
//            NetQueryImpl.getInstance(getActivity()).friendStatusQuery(handler);
//        }
        if (weiboAdapter == null) {
            weiboAdapter = new WeiboAdapter(getActivity(), fristData);
        }
        listView_first.setAdapter(weiboAdapter);
        listView_first.setOnItemClickListener(new FirstListViewListener());

        return v;
    }

    /**
     * 从本地存储加载数据到当前discoveryData表
     */
    private void getData() {
        ArrayList<Status> newData = (ArrayList<Status>) Status.findWithQuery(
                Status.class,
                "select * from status where type = 2 limit 20 offset " + fristData.size());
        for (int i = newData.size() - 1; i >= 0; i--) {
            Status status = newData.get(i);
            User user = User.findById(User.class, status.getUserIdStatus());
            status.setUser(user);
            ArrayList<Pic_urls> pic_urlsList =
                    (ArrayList<Pic_urls>) Pic_urls.find(Pic_urls.class, "statusid=?", status.getIdstr());
            status.setPic_urls(pic_urlsList);
//            Log.e("首页", status.toString());
            fristData.add(status);
            if (weiboAdapter != null) {
                weiboAdapter.setData(fristData);
                weiboAdapter.notifyDataSetChanged();
            }
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_FRIENDS) {
                getData();
            }
        }
    };

    public class FirstListViewListener implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(STATUS_SINGLE, fristData.get(position));
            intent.putExtras(bundle);
            startActivity(intent);

        }
    }


}
