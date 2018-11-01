package com.flyppe.flypplemvp.network;

import android.content.Context;

import com.flyppe.flypplemvp.global.Constant;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 * Created by flypple on 2018/8/20.
 */

public class RetrofitHelper {

    private Context mCntext;

    OkHttpClient client;
    GsonConverterFactory factory = GsonConverterFactory.create(new GsonBuilder().create());
    private static RetrofitHelper instance = null;
    private Retrofit mRetrofit = null;
    public static RetrofitHelper getInstance(Context context){
        if (instance == null){
            synchronized (RetrofitHelper.class) {
                if (instance == null) {
                    instance = new RetrofitHelper(context);
                }
            }
        }
        return instance;
    }
    private RetrofitHelper(Context mContext){
        mCntext = mContext;
        init();
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        client = new OkHttpClient.Builder()
                .addInterceptor(new MoreBaseUrlInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addConverterFactory(factory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Api getApi(){
        return mRetrofit.create(Api.class);
    }
}
