package com.yitouwushui.weibo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Status;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;
import java.util.List;

public class FirstPageFragment extends Fragment{

    public static final String STATUS_SINGLE = "STATUS_SINGLE";

    ListView listView_first;
    WeiboAdapter weiboAdapter;
    ArrayList<Status> fristData = new ArrayList<>();

//    https://api.weibo.com/2/statuses/user_timeline/ids.json

    public FirstPageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);
        listView_first = (ListView) v.findViewById(R.id.listView_first_page);

        if (weiboAdapter == null) {
            weiboAdapter = new WeiboAdapter(getActivity(),fristData);
            listView_first.setAdapter(weiboAdapter);
            listView_first.setOnItemClickListener(new FirstListViewListener());
        }

        return v;
    }

    public class FirstListViewListener implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            intent.putExtra(STATUS_SINGLE, fristData.get(position));

            startActivity(intent);

        }
    }


}
