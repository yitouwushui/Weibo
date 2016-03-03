package com.yitouwushui.weibo.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.Login.Login2Activity;
import com.yitouwushui.weibo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    ImageView img_icon;
    TextView text_name;
    TextView text_introduce;
    Button button_account;
    Button button_weibo;
    Button button_follow;
    Button button_follower;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_me, container, false);
        // 初始化
        init(v);

        UserButtonListener userButtonListener = new UserButtonListener();



        return v;
    }

    private void init(View v) {
        img_icon = (ImageView) v.findViewById(R.id.imageViewIcon);
        text_name = (TextView) v.findViewById(R.id.textViewName);
        text_introduce = (TextView) v.findViewById(R.id.textViewIntroduce);
        button_account = (Button) v.findViewById(R.id.button_account);
        button_weibo = (Button) v.findViewById(R.id.button_weibo);
        button_follow = (Button) v.findViewById(R.id.button_follow);
        button_follower = (Button) v.findViewById(R.id.button_follower);
    }

    private class UserButtonListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id){
                case R.id.imageViewIcon:
                    break;
                case R.id.button_account:
                    Intent intent = new Intent(getActivity(), Login2Activity.class);
                    startActivity(intent);
                    break;
                case R.id.button_weibo:
                    break;
                case R.id.button_follow:
                    break;
                case R.id.button_follower:
                    break;
            }
        }
    }

}
