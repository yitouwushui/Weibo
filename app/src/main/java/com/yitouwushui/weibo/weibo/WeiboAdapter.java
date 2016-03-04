package com.yitouwushui.weibo.weibo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yitouwushui.weibo.R;

import java.util.ArrayList;
import java.util.List;


public class WeiboAdapter extends BaseAdapter {

    Context context;
    List<String> data = new ArrayList<>();
    LayoutInflater inflater;
    Boolean isZan = false;

    public WeiboAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
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
        holder.img_icon.setImageResource(R.drawable.tx);
        holder.text_name.setText(data.get(position));
        holder.text_word.setText("微博正文");
        holder.text_tran.setText("5");
        holder.text_comment.setText("10");
        holder.text_zan.setText("1000");
        holder.img_zan.setImageResource(isZan ? R.drawable.weibo_zanh : R.drawable.weibo_zan);
        if(position % 2 == 0){
            holder.img_weibo1.setImageResource(R.drawable.mail);
            holder.img_weibo2.setImageResource(R.drawable.home);
            holder.img_weibo3.setImageResource(R.drawable.me);
            holder.img_weibo4.setImageResource(R.drawable.weibo_zanh);
            holder.img_weibo5.setImageResource(R.drawable.weibo_zan);
            holder.img_weibo6.setImageResource(R.drawable.mailb);
            holder.img_weibo7.setImageResource(R.drawable.psd);
            holder.img_weibo8.setImageResource(R.drawable.name);
            holder.img_weibo9.setImageResource(R.drawable.jia);
        } else{
            holder.img_weibo1.setVisibility(View.GONE);
            holder.img_weibo2.setVisibility(View.GONE);
            holder.img_weibo3.setVisibility(View.GONE);
            holder.img_weibo4.setVisibility(View.GONE);
            holder.img_weibo5.setVisibility(View.GONE);
            holder.img_weibo6.setVisibility(View.GONE);
            holder.img_weibo7.setVisibility(View.GONE);
            holder.img_weibo8.setVisibility(View.GONE);
            holder.img_weibo8.setVisibility(View.GONE);
            holder.img_weibo9.setVisibility(View.GONE);
        }
        return convertView;
    }

    protected class Holder {
        ImageView img_icon;
        ImageView img_collect;
        TextView text_name;
        TextView text_time;
        TextView text_device;
        TextView text_word;
        ImageView img_weibo1;
        ImageView img_weibo2;
        ImageView img_weibo3;
        ImageView img_weibo4;
        ImageView img_weibo5;
        ImageView img_weibo6;
        ImageView img_weibo7;
        ImageView img_weibo8;
        ImageView img_weibo9;
        TextView text_tran;
        TextView text_comment;
        TextView text_zan;
        RelativeLayout reLayout_weibo_tra;
        RelativeLayout reLayout_weibo_com;
        RelativeLayout reLayout_weibo_zan;
        ImageView img_zan;

        public Holder(View v) {
            img_icon = (ImageView) v.findViewById(R.id.imageView_wei_icon);
            img_collect = (ImageView) v.findViewById(R.id.imageView_weibo_collect);
            text_name = (TextView) v.findViewById(R.id.textView_weibo_name);
            text_time = (TextView) v.findViewById(R.id.textView_weibo_time);
            text_device = (TextView) v.findViewById(R.id.textView_weibo_device);
            text_word = (TextView) v.findViewById(R.id.textView_weibo_word);
            img_weibo1 = (ImageView) v.findViewById(R.id.imageView_weibo1);
            img_weibo2 = (ImageView) v.findViewById(R.id.imageView_weibo2);
            img_weibo3 = (ImageView) v.findViewById(R.id.imageView_weibo3);
            img_weibo4 = (ImageView) v.findViewById(R.id.imageView_weibo4);
            img_weibo5 = (ImageView) v.findViewById(R.id.imageView_weibo5);
            img_weibo6 = (ImageView) v.findViewById(R.id.imageView_weibo6);
            img_weibo7 = (ImageView) v.findViewById(R.id.imageView_weibo7);
            img_weibo8 = (ImageView) v.findViewById(R.id.imageView_weibo8);
            img_weibo9 = (ImageView) v.findViewById(R.id.imageView_weibo9);
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
