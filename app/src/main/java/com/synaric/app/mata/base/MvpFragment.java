package com.synaric.app.mata.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.synaric.app.mata.mvp.BasePresenter;

/**
 * 需要MVP架构的界面，要继承这个类，并配置相应的Presenter。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public abstract class MvpFragment<P extends BasePresenter> extends BaseFragment {

    protected P presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
