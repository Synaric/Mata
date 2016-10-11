package com.synaric.app.mata.module.main.root;

import com.orhanobut.logger.Logger;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.MvpActivity;

/**
 * 主界面。
 */
public class MainActivity extends MvpActivity<MainPresenter> implements MainView<String> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        //采用单Activity + 多Fragment的模式
        loadRootFragment(R.id.fl_container, HomeFragment.newInstance());
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void onSuccess(String data) {
        Logger.d(data);
    }

    @Override
    public void onFailed(String error) {
        Logger.d(error);
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
