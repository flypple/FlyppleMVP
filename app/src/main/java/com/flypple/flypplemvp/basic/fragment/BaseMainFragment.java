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
package com.flypple.flypplemvp.basic.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flypple.flypplemvp.R;
import com.flypple.flypplemvp.basic.BasePresenter;
import com.flypple.flypplemvp.utils.GenericUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;


/**
 * 项目中担任一级页面Fragment的基类
 * 带有双击退出应用功能
 * Created by flypple on 2018/8/18.
 */
public abstract class BaseMainFragment<T extends BasePresenter> extends SupportFragment {

    protected T presenter;

    private View rootView;


    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    /**
     * 处理双击回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            _mActivity.finish();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
        }
        return true;
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
