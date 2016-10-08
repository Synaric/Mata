package com.synaric.app.mata.base;

import android.os.Bundle;

import com.synaric.app.mata.mvp.BasePresenter;

/**
 * 需要MVP架构的界面，需要继承这个类，并配置相应的Presenter。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        presenter = createPresenter();
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
