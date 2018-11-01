package com.flyppe.flypplemvp.basic.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装普通ListView的Adapter基类，只需要继承后实现创建ViewHodler和绑定数据的操作即可
 * 泛型T：数据类型，泛型VH：ViewHodler类型
 *
 * Created by flypple on 2018/8/21.
 */
public abstract class BaseListAdapter<T, VH extends BaseListAdapter.ViewHolder> extends BaseAdapter {
    protected final Context mContext;
    protected final List<T> mData;

    public BaseListAdapter(Context context, List<T> data) {
        mContext = context;
        mData = data == null ? new ArrayList<T>() : data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final VH holder;
        if (convertView == null) {
            holder = createHolder(position, parent);
            convertView = holder.itemView;
        } else {
            holder = (VH) convertView.getTag();
        }
        bindData(position, holder, getItem(position));
        return convertView;
    }

    public abstract void bindData(int position, VH holder, T item);

    public abstract VH createHolder(int position, ViewGroup parent);

    /**
     * 更新数据
     */
    public void update(List<T> data) {
        mData.clear();
        addData(data);
    }

    /**
     * 添加数据
     */
    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 获取数据
     */
    public List<T> getData() {
        return mData;
    }

    public static abstract class ViewHolder {
        final View itemView;

        public ViewHolder(View itemView) {
            this.itemView = itemView;
            itemView.setTag(this);
        }
    }
}
