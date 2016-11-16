package com.synaric.app.mata;

import com.synaric.app.player.PlayerService;
import com.synaric.common.BaseApplication;
import com.synaric.app.mata.model.StandardModel;
import com.synaric.common.utils.SystemUtils;

import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 初始化与生命周期监听。
 * <br/><br/>Created by Synaric on 2016/9/28 0028.
 */
public class MataApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        HermesEventBus.getDefault().init(this);
        if (SystemUtils.isMainProgress(this)) {
            PlayerService.init(getApplicationContext());
        }
    }

    @Override
    protected String getBaseUrl() {
        return StandardModel.BASE_URL;
    }
}
