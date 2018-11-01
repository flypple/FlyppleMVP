/*
 * Copyright 2016 XuJiaji
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.flyppe.flypplemvp.basic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyppe.flypplemvp.R;
import com.flyppe.flypplemvp.basic.BasePresenter;
import com.flyppe.flypplemvp.utils.GenericUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;


/**
 * 项目中次级页面Fragment的基类之一
 * 带有滑动退出功能
 * Created by flypple on 2018/8/18.
 */
public abstract class BaseBackFragment<T extends BasePresenter> extends SwipeBackFragment {

    protected T presenter;

    private View rootView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setParallaxOffset(0.5f);
    }

    protected void initToolbarNav(Toolbar toolbar) {
        if(toolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.ic_arrow_back_white_24dp);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _mActivity.onBackPressed();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        try {
            /*
             * 通过反射获取presenter对象，同时实现一起实例化了model对象，
             * 并初始化了presenter，实现了对view和model的持有
             */
            presenter = GenericUtils.newPresenter(this);
            if (presenter != null) {
                presenter.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        rootView = inflater.inflate(getLayoutId(), container, false);
        ButterKnife.bind(this, rootView);
        onInit();
        onListener();
        return rootView;
    }

    /**
     * 添加监听
     */
    protected void onListener() {

    }

    protected abstract int getLayoutId();

    /**
     * 初始化控件
     */
    protected void onInit() {
    }

    public View getRootView() {
        return this.rootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.end();
        }
    }
}
