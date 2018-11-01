package com.flyppe.flypplemvp.basic.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装普通ViewPager的Adapter基类，只需要继承后实现getView方法，创建View并绑定数据即可
 * 泛型T：数据类型
 *
 * Created by flypple on 2018/8/27.
 */
public abstract class BasePagerAdapter<T> extends PagerAdapter {

    protected Context mContext;
    protected List<T> mData = null;
    protected List<String> mTitleList = null;

    public BasePagerAdapter(Context context, List<T> data) {
        this(context, data, new ArrayList<String>());
    }

    /**
     * 这个构造方法可以直接添加用于标题的一组String数据
     */
    public BasePagerAdapter(Context context, List<T> data, List<String> titleList) {
        mContext = context;
        mData = data == null ? new ArrayList<T>() : data;
        mTitleList = titleList == null ? new ArrayList<String>() : titleList;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = getView(container, mData.get(position),position);
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View paramView, Object paramObject) {
        return paramView == paramObject;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(mTitleList.size() > position){
            return mTitleList.get(position);
        }
        return super.getPageTitle(position);
    }

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
    public List<T>getData(){
        return mData;
    }

    public abstract View getView(ViewGroup container,T item,int position);
}
