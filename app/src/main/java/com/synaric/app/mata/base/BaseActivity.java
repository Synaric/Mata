package com.synaric.app.mata.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.synaric.common.utils.StatusBarUtil;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 所有Activity的基类。
 * Created by Synaric on 2016/9/5 0005.
 */
public abstract class BaseActivity extends SupportActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        onCreate();
        StatusBarUtil.compat(this);
    }

    protected void onCreate() {

    }

    public abstract int getLayoutId();
}
