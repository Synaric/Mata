package com.synaric.common;

import android.content.Context;

import com.synaric.common.utils.SystemUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Common模块初始化类。
 * 在APP启动是调用。
 * Created by Synaric on 2016/9/21 0021.
 */
public class CommonUtilsConfiguration {

    public static Retrofit retrofit;

    public static void init(Context context, String baseUrl) {
        initLogger(context);
        initRetrofit(baseUrl);
    }

    public static void initLogger(Context context) {
        Logger
            .init()
            .logLevel(SystemUtil.isDebugMode(context) ? LogLevel.FULL : LogLevel.NONE)
            .methodCount(3);
    }

    public static Retrofit initRetrofit(String baseUrl) {
        if (retrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            OkHttpClient okHttpClient = builder.build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;
    }
}
