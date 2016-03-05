package com.yitouwushui.weibo.main;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yitouwushui.weibo.R;
import com.yitouwushui.weibo.util.TimeUtil;
import com.yitouwushui.weibo.entity.Status;

import java.util.ArrayList;
import java.util.List;


public class WeiboAdapter extends BaseAdapter {

    Context context;
    List<Status> data = new ArrayList<>();
    LayoutInflater inflater;
    Boolean isZan = false;
    StringBuilder sourceStart = new StringBuilder("来自 ");


    public WeiboAdapter(Context context, ArrayList<Status> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Status> data) {
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

//        Log.e("xitong",String.valueOf(position));
        Holder holder;
        ButtonListener buttonListener;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.weibo_item, parent, false);
            holder = new Holder(convertView);
            buttonListener = new ButtonListener();
            holder.img_icon.setOnClickListener(buttonListener);
            holder.img_collect.setOnClickListener(buttonListener);
            holder.reLayout_weibo_tra.setOnClickListener(buttonListener);
            holder.reLayout_weibo_com.setOnClickListener(buttonListener);
            holder.reLayout_weibo_zan.setOnClickListener(buttonListener);
            convertView.setTag(holder);
            holder.img_icon.setTag(buttonListener);
            holder.img_collect.setOnClickListener(buttonListener);
            holder.reLayout_weibo_tra.setTag(buttonListener);
            holder.reLayout_weibo_com.setTag(buttonListener);
            holder.reLayout_weibo_zan.setTag(buttonListener);
        } else {
            holder = (Holder) convertView.getTag();
            buttonListener = (ButtonListener) holder.reLayout_weibo_zan.getTag();
        }
        buttonListener.setPosition(position);
        Status status = data.get(position);
        Status.User user = status.getUser();
        Log.e("--------", user.getAvatar_large());
        holder.img_icon.setImageURI(Uri.parse(user.getAvatar_large()));
        holder.text_name.setText(user.getScreen_name());
        holder.text_device.setText(sourceStart.append(status.getSource()));
        sourceStart.replace(0, sourceStart.length(), "来自 ");
        holder.text_time.setText(TimeUtil.getRelativeTime(status.getCreated_at()));
        holder.text_word.setText(status.getText());
        holder.text_tran.setText(String.valueOf(status.getReposts_count()));
        holder.text_comment.setText(String.valueOf(status.getComments_count()));
        holder.text_zan.setText(String.valueOf(status.getAttitudes_count()));
        holder.img_zan.setImageResource(status.isFavorited() ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
        holder.img_collect.setImageResource(status.isFavorited() ? R.drawable.shoucang2 : R.drawable.shoucang);
//        Status.Pic_ids pic_ids = new Status.Pic_ids();
        if (status.getThumbnail_pic() != "") {
//            Log.e("--------", status.getThumbnail_pic());
//            holder.img_weibo1.setImageURI(Uri.parse(status.getThumbnail_pic()));
//            holder.img_weibo1.setVisibility(View.VISIBLE);
//            holder.img_weibo2.setImageResource(R.drawable.home);
//            holder.img_weibo3.setImageResource(R.drawable.me);
//            holder.img_weibo4.setImageResource(R.drawable.weibo_zanh);
//            holder.img_weibo5.setImageResource(R.drawable.weibo_zan);
//            holder.img_weibo6.setImageResource(R.drawable.mailb);
//            holder.img_weibo7.setImageResource(R.drawable.psd);
//            holder.img_weibo8.setImageResource(R.drawable.name);
//            holder.img_weibo9.setImageResource(R.drawable.jia);
//            holder.img_weibo2.setVisibility(View.VISIBLE);
//            holder.img_weibo3.setVisibility(View.VISIBLE);
//            holder.img_weibo4.setVisibility(View.VISIBLE);
//            holder.img_weibo5.setVisibility(View.VISIBLE);
//            holder.img_weibo6.setVisibility(View.VISIBLE);
//            holder.img_weibo7.setVisibility(View.VISIBLE);
//            holder.img_weibo8.setVisibility(View.VISIBLE);
//            holder.img_weibo8.setVisibility(View.VISIBLE);
//            holder.img_weibo9.setVisibility(View.VISIBLE);
        }
        return convertView;
    }

    protected class Holder {
        SimpleDraweeView img_icon;
        ImageView img_collect;
        TextView text_name;
        TextView text_time;
        TextView text_device;
        TextView text_word;
        SimpleDraweeView img_weibo1;
        SimpleDraweeView img_weibo2;
        SimpleDraweeView img_weibo3;
        SimpleDraweeView img_weibo4;
        SimpleDraweeView img_weibo5;
        SimpleDraweeView img_weibo6;
        SimpleDraweeView img_weibo7;
        SimpleDraweeView img_weibo8;
        SimpleDraweeView img_weibo9;
        TextView text_tran;
        TextView text_comment;
        TextView text_zan;
        RelativeLayout reLayout_weibo_tra;
        RelativeLayout reLayout_weibo_com;
        RelativeLayout reLayout_weibo_zan;
        ImageView img_zan;

        public Holder(View v) {
            img_icon = (SimpleDraweeView) v.findViewById(R.id.imageView_wei_icon);
            img_collect = (ImageView) v.findViewById(R.id.imageView_weibo_collect);
            text_name = (TextView) v.findViewById(R.id.textView_weibo_name);
            text_time = (TextView) v.findViewById(R.id.textView_weibo_time);
            text_device = (TextView) v.findViewById(R.id.textView_weibo_device);
            text_word = (TextView) v.findViewById(R.id.textView_weibo_word);
            img_weibo1 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo1);
            img_weibo2 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo2);
            img_weibo3 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo3);
            img_weibo4 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo4);
            img_weibo5 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo5);
            img_weibo6 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo6);
            img_weibo7 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo7);
            img_weibo8 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo8);
            img_weibo9 = (SimpleDraweeView) v.findViewById(R.id.imageView_weibo9);
            text_tran = (TextView) v.findViewById(R.id.textView_weibo_tra);
            text_comment = (TextView) v.findViewById(R.id.textView_weibo_com);
            text_zan = (TextView) v.findViewById(R.id.textView_weibo_zan);
            reLayout_weibo_tra = (RelativeLayout) v.findViewById(R.id.reLayout_weibo_tra);
            reLayout_weibo_com = (RelativeLayout) v.findViewById(R.id.reLayout_weibo_com);
            reLayout_weibo_zan = (RelativeLayout) v.findViewById(R.id.reLayout_weibo_zan);
            img_zan = (ImageView) v.findViewById(R.id.imageView_weibo_zan);
        }
    }

    public class ButtonListener implements RelativeLayout.OnClickListener {
        private int list_position;

        public void setPosition(int list_position) {
            this.list_position = list_position;
        }


        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.reLayout_weibo_tra:
                    showMsg("转发" + list_position);
                    break;
                case R.id.reLayout_weibo_com:
                    showMsg("评论" + list_position);
                    break;
                case R.id.reLayout_weibo_zan:
                    zan();
                    break;
                case R.id.imageView_weibo_collect:
                    showMsg("收藏" + list_position);
                    break;
                case R.id.imageView_wei_icon:
                    showMsg("跳转个人信息" + list_position);
                    break;
            }
        }

        private void zan() {
            isZan = !isZan;
            notifyDataSetChanged();
        }
    }


    public void showMsg(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


}
