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
import com.yitouwushui.weibo.weibo.WeiboAdapter;
import com.yitouwushui.weibo.weibo.WeiboPageActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoveryFragment extends Fragment {

    public static final String WEIBO_DISCOVERY = "WEIBO_DISCOVERY";

    ListView listView_discovery;
    WeiboAdapter weiboAdapter;
    List<String> discoveryData = new ArrayList<>();

    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_first_page, container, false);
        listView_discovery = (ListView) v.findViewById(R.id.listView_discovery);
        if (savedInstanceState == null) {
            for (int i = 0; i <= 10; i++) {
                discoveryData.add(String.valueOf(i));
            }
        }

        weiboAdapter = new WeiboAdapter(getActivity(), discoveryData);
        listView_discovery.setAdapter(weiboAdapter);
        listView_discovery.setOnItemClickListener(new FirstListViewListener());

        return v;
    }

    public class FirstListViewListener implements android.widget.AdapterView.OnItemClickListener {


        @Override
        public void onItemClick(
                AdapterView<?> parent, View view, int position, long id) {

            Intent intent = new Intent(getActivity(), WeiboPageActivity.class);
            intent.putExtra(WEIBO_DISCOVERY, discoveryData.get(position));

            startActivity(intent);

        }
    }

}
