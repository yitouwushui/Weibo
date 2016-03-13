package com.yitouwushui.weibo.me;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yitouwushui on 2016/3/12.
 */
public class FriendsAdapter extends BaseAdapter {
    Context context;
    List<User> data = new ArrayList<>();
    LayoutInflater inflater;

    public FriendsAdapter(Context context, List<User> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<User> data) {
        this.data = data;
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

        Holder holder;
        // 新建
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_item, parent, false);
            holder = new Holder(convertView);
            convertView.setTag(holder);
            // 复用
        } else {
            holder = (Holder) convertView.getTag();
        }
        User user = data.get(position);
        holder.img_icon.setImageURI(Uri.parse(user.getAvatar_large()));
        holder.text_name.setText(user.getScreen_name());
        holder.text_introduce.setText(user.getDescription());

        return convertView;
    }

    /**
     * 复用类
     */
    public class Holder {
        SimpleDraweeView img_icon;
        TextView text_name;
        TextView text_introduce;

        public Holder(View v) {
            text_name = (TextView) v.findViewById(R.id.friendName);
            img_icon = (SimpleDraweeView) v.findViewById(R.id.header_Icon);
            text_introduce = (TextView) v.findViewById(R.id.introduce);

        }
    }


    public void showMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
