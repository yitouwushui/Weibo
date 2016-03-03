package com.yitouwushui.weibo.main;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

    ImageView img_back;
    TextView text_title;
//    Callback callback;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_top, container, false);
        img_back = (ImageView) v.findViewById(R.id.img_back);
        text_title = (TextView) v.findViewById(R.id.text_titile);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
}


//    interface Callback{
//        String setTitle();
//    }
}
