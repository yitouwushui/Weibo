package com.yitouwushui.weibo.weibo;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yitouwushui.weibo.Login.App;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.main.FirstPageFragment;
import com.yitouwushui.weibo.main.MainActivity;
import com.yitouwushui.weibo.main.WeiboAdapter;
import com.yitouwushui.weibo.net.NetQueryImpl;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class WeiboCommentsFragment extends Fragment {

    ListView listView_comments;
    WBPageAdapter wbPageAdapter;
    ArrayList<Comments> commentsList = new ArrayList<>();

    public WeiboCommentsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weibo_comments, container, false);
        listView_comments = (ListView) v.findViewById(R.id.listView_wbPage);
        Intent intent = getActivity().getIntent();
        Bundle bundle = intent.getExtras();
        Status status = (Status) bundle.get(FirstPageFragment.STATUS_SINGLE);
        if (commentsList.size() == 0) {
            NetQueryImpl.getInstance(getContext()).commentStatusQuery(handler, status.getIdstr());
        }
        wbPageAdapter = new WBPageAdapter(getContext(), commentsList);
        listView_comments.setAdapter(wbPageAdapter);

        return v;
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == App.MESSAGE_COMMENTS) {
                commentsList = (ArrayList<Comments>) msg.obj;
                for (int i = 0; i < commentsList.size(); i++) {
                    Log.e("-----------",commentsList.toString());
                }
                wbPageAdapter.setData(commentsList);
                wbPageAdapter.notifyDataSetChanged();
            }
        }
    };

}
