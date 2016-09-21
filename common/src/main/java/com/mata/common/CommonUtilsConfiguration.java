package com.mata.common;

import android.content.Context;

import com.mata.common.utils.SystemUtil;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

/**
 * Common模块初始化类。
 * 在APP启动是调用。
 * Created by Synaric on 2016/9/21 0021.
 */
public class CommonUtilsConfiguration {

    public static void init(Context context) {
        Logger
            .init()
            .logLevel(SystemUtil.isDebugMode(context) ? LogLevel.FULL : LogLevel.NONE)
            .methodCount(3);
    }
}
