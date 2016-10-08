package com.synaric.app.mata;

import com.synaric.common.BaseApplication;
import com.synaric.app.mata.service.StandardModel;

/**
 * 初始化与生命周期监听。
 * <br/><br/>Created by Synaric on 2016/9/28 0028.
 */
public class MataApplication extends BaseApplication {

    @Override
    protected String getBaseUrl() {
        return StandardModel.BASE_URL;
    }
}
