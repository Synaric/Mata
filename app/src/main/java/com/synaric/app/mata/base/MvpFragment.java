package com.synaric.app.mata.base;

import com.synaric.app.mata.mvp.BasePresenter;

/**
 * 需要MVP架构的界面，要继承这个类，并配置相应的Presenter。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseManagedFragment {

    protected P presenter;

    @Override
    protected void onCreate() {
        super.onCreate();
        presenter = createPresenter();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    protected abstract P createPresenter();
}
