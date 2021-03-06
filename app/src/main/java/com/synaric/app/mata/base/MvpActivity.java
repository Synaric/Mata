package com.synaric.app.mata.base;

import com.synaric.app.mata.mvp.BasePresenter;

/**
 * 需要MVP架构的界面，要继承这个类，并配置相应的Presenter。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {

    protected P presenter;

    @Override
    protected void onCreate() {
        super.onCreate();
        presenter = createPresenter();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachAllViews();
        }
    }

    protected abstract P createPresenter();
}
