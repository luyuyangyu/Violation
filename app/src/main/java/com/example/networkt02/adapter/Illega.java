package com.example.networkt02.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


/**
 * public int getCount(): 适配器中数据集的数据个数；
 * public Object getItem(int position): 获取数据集中与索引对应的数据项；
 * public long getItemId(int position): 获取指定行对应的ID；
 * public View getView(int position,View convertView,ViewGroup parent): 获取没一行Item的显示内容。
 */

public class Illega extends BaseAdapter {

    //适配器中数据集的数据个数
    @Override
    public int getCount() {
        return 0;
    }

    //获取数据集中与索引对应的数据项
    @Override
    public Object getItem(int position) {
        return null;
    }

    //    获取指定行对应的ID
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //获取没一行Item的显示内容
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
