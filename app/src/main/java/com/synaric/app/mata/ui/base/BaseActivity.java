package com.synaric.app.mata.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.mata.common.utils.StatusBarUtil;

import butterknife.ButterKnife;

/**
 * 所有Activity的基类。
 * Created by Synaric on 2016/9/5 0005.
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
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
