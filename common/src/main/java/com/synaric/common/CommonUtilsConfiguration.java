package com.synaric.common;

import android.content.Context;
import android.util.Log;

import com.synaric.app.rxmodel.RxModel;
import com.synaric.common.utils.SystemUtils;
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

    public static final String TAG = CommonUtilsConfiguration.class.getSimpleName();

    public static Retrofit retrofit;

    public static RxModel rxModel;

    public static void init(Context context, String baseUrl) {
        //确保多进程应用只初始化一次
        if (SystemUtils.isMainProgress(context)) {
            Log.d(TAG, "CommonUtilsConfiguration.init()");
            initLogger(context);
            initRetrofit(baseUrl);
            initRxModel(context);
        }
    }

    private static void initLogger(Context context) {
        Logger
            .init()
            .logLevel(SystemUtils.isDebugMode(context) ? LogLevel.FULL : LogLevel.NONE)
            .methodCount(3);
    }

    private static void initRetrofit(String baseUrl) {
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
    }

    private static void initRxModel(Context context) {
        if (rxModel == null) {
            rxModel = new RxModel.Builder(context).dbName("mata.db").build();
        }
    }
}
