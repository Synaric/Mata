package com.synaric.common;

import android.app.Application;

/**
 * 基础应用配置、生命周期管理。
 * Created by Synaric on 2016/9/21 0021.
 */
public abstract class BaseApplication extends Application{

    protected abstract String getBaseUrl();

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化工具
        CommonUtilsConfiguration.init(this, getBaseUrl());
    }
}
