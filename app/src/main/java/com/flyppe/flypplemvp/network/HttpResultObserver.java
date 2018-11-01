package com.flyppe.flypplemvp.network;

import android.content.Context;
import android.text.TextUtils;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 这里对RxJava+Retrofit做请求网络的响应做统一封装
 *
 * Created by flypple on 2016/9/21.
 */
public abstract class HttpResultObserver<T> implements Observer<HttpResult<T>> {
    Context context;

    public HttpResultObserver(Context context) {
        this.context = context;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onError(Throwable e) {
        /**
         * 统一错误处理
         */
        /*if (NetworkUtils.isNetworkAvailable(context)) {
           // ToastUtils.showToast("服务器连接错误！错误代码：" + e.getMessage().toString(), context);
            _onError("服务器连接错误！错误代码：" + e.getMessage().toString());
        } else {
            ToastUtils.showToast("网络异常，请检查网络设置", context);
            _onError("网络异常，请检查网络设置");
        }

        Logger.e(e.getMessage());*/
    }

    @Override
    public void onNext(HttpResult<T> t) {
        if (t != null) {
            if (!TextUtils.isEmpty(t.getStatus())) {
                if (t.getStatus().equals("此处填和后台约定的请求成功的响应码")) {
                    if (t.getData() == null) {
                        onSuccessNoData();
                    } else {
                        onSuccess(t.getData());
                    }
                }else {
                    if (!TextUtils.isEmpty(t.getMsg()))
                        _onError(t.getMsg());
                }
            }
        }
    }

    @Override
    public void onComplete() {

    }

    public abstract void onSuccess(T t);

    public abstract void _onError(String msg);

    public abstract void onSuccessNoData();
}
