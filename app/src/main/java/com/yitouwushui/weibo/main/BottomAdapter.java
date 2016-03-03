package com.yitouwushui.weibo.main;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yitouwushui.weibo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yitouwushui on 2016/2/25.
 */
public class BottomAdapter extends BaseAdapter {

    Context context;
    List<String> data = new ArrayList<>();
    LayoutInflater inflater;
    int select = 0;
    int color = Color.rgb(0x56, 0xab, 0xe4);

    public BottomAdapter(Context context) {
        data.add("首页");
        data.add("消息");
        data.add("+");
        data.add("发现");
        data.add("我");
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;
        TextView textView;

        if (position == 2) {
            convertView = inflater.inflate(R.layout.bottom_item2, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.imageView_bottom2);
            imageView.setImageResource(R.drawable.jia);

        } else {
            convertView = inflater.inflate(R.layout.bottom_item, parent, false);
            imageView = (ImageView) convertView.findViewById(R.id.imageView_bottom);
            textView = (TextView) convertView.findViewById(R.id.textView_bottom);
            if (position == select){
                textView.setTextColor(color);
            }
            textView.setText(data.get(position));
            switch (position) {
                case 0:
                    imageView.setImageResource(position == select ? R.drawable.homeb : R.drawable.home);
                    break;
                case 1:
                    imageView.setImageResource(position == select ? R.drawable.mailb : R.drawable.mail);
                    break;
                case 3:
                    imageView.setImageResource(position == select ? R.drawable.searchb2 : R.drawable.search);
                    break;
                case 4:
                    imageView.setImageResource(position == select ? R.drawable.meb : R.drawable.me);
                    break;
            }

        }

        return convertView;
    }


}
