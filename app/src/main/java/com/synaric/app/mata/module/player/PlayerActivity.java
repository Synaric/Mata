package com.synaric.app.mata.module.player;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseActivity;

/**
 * "播放器"界面。
 */
public class PlayerActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_player;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        enableTransitAnim();
    }
}
