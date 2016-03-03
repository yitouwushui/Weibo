package com.yitouwushui.weibo.weibo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.yitouwushui.weibo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yitouwushui on 2016/3/2.
 */
public class TranslateAdapter extends BaseAdapter {

    Context context;
    List<String> data = new ArrayList<>();
    LayoutInflater inflater;

    public TranslateAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
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
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
