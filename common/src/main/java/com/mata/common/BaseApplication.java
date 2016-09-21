package com.mata.common;

import android.app.Application;

/**
 * 基础应用配置、生命周期管理。
 * Created by Synaric on 2016/9/21 0021.
 */
public class BaseApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化工具
        CommonUtilsConfiguration.init(this);
    }
}
