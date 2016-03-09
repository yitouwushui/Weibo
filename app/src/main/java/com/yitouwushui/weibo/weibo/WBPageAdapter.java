package com.yitouwushui.weibo.weibo;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.entity.Comments;
import com.yitouwushui.weibo.entity.User;
import com.yitouwushui.weibo.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yitouwushui on 2016/3/1.
 */
public class WBPageAdapter extends BaseAdapter {

    Context context;
    List<Comments> data = new ArrayList<>();
    LayoutInflater inflater;
    boolean isZan = false;

    public WBPageAdapter(Context context, ArrayList<Comments> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Comments> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        Holder holder;
        CommentButtonListener commentButtonListener;
        // 新建
        if (v == null) {
            v = inflater.inflate(R.layout.wb_page_user_item, parent, false);
            holder = new Holder(v);
            commentButtonListener = new CommentButtonListener();
            holder.img_icon.setOnClickListener(commentButtonListener);
            holder.img_zan.setOnClickListener(commentButtonListener);
            v.setTag(holder);
            holder.img_icon.setTag(commentButtonListener);
            // 复用
        } else {
            holder = (Holder) v.getTag();
            commentButtonListener = (CommentButtonListener) holder.img_icon.getTag();
        }
        commentButtonListener.setPosition(position);

        Comments comments = data.get(position);
        User user = comments.getUser();

        Log.e("适配器", comments.toString());
        holder.img_icon.setImageURI(Uri.parse(user.getAvatar_large()));
        holder.text_name.setText(user.getScreen_name());
        holder.text_time.setText(TimeUtil.stringTranslateTime(comments.getCreated_at()));
        holder.text_word.setText(comments.getText());
        holder.img_zan.setImageResource(isZan ? R.drawable.weibo_zanh : R.drawable.weibo_zan);

        return v;
    }

    /**
     * 复用类
     */
    public class Holder {
        SimpleDraweeView img_icon;
        TextView text_name;
        TextView text_time;
        TextView text_word;
        ImageView img_zan;
        TextView text_zan_count;

        public Holder(View v) {
            img_icon = (SimpleDraweeView) v.findViewById(R.id.imageView_wei_icon);
            text_name = (TextView) v.findViewById(R.id.textView_weibo_name);
            text_time = (TextView) v.findViewById(R.id.textView_weibo_time);
            text_word = (TextView) v.findViewById(R.id.textView_weibo_word);
            img_zan = (ImageView) v.findViewById(R.id.imageView_weibo_zan);
            text_zan_count = (TextView) v.findViewById(R.id.text_zan_count);
        }
    }

    public class CommentButtonListener implements View.OnClickListener {
        private int list_position;

        public void setPosition(int list_position) {
            this.list_position = list_position;
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.text_zan_count) {
                Toast.makeText(context, "点赞" + list_position, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
