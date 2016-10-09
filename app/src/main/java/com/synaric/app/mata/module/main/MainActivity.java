package com.synaric.app.mata.module.main;

import com.orhanobut.logger.Logger;

import com.synaric.app.mata.R;
import com.synaric.app.mata.base.BaseToolBarActivity;
import com.synaric.app.widget.ActionButton;

/**
 * 主界面。
 */
public class MainActivity extends BaseToolBarActivity<MainPresenter> implements MainView<String> {

    private ActionButton actionButton;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
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

    @Override
    protected void onCreate() {
        super.onCreate();
        actionButton = ActionButton.getOrCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActionButton.destroy();
    }
}
