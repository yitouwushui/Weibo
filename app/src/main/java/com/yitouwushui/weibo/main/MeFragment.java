package com.yitouwushui.weibo.main;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    ImageView img_icon;
    TextView text_name;
    TextView text_introduce;
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

        User user = User.findById(User.class,1);

        text_name.setText(user.getScreen_name());
        text_introduce.setText(user.getDescription());
        button_weibo.setText(user.getStatuses_count() + "\n微博");
        button_follow.setText(user.getFriends_count() + "\n关注");
        button_follower.setText(user.getFollowers_count() + "\n粉丝");
        return v;
    }

    private void init(View v) {
        img_icon = (ImageView) v.findViewById(R.id.imageViewIcon);
        text_name = (TextView) v.findViewById(R.id.textViewName);
        text_introduce = (TextView) v.findViewById(R.id.textViewIntroduce);
        button_weibo = (Button) v.findViewById(R.id.button_weibo);
        button_follow = (Button) v.findViewById(R.id.button_follow);
        button_follower = (Button) v.findViewById(R.id.button_follower);
    }

}
