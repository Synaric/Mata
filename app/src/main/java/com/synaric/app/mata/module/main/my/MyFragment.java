package com.synaric.app.mata.module.main.my;

import com.synaric.app.mata.base.BaseFragment;

/**
 * "我的"界面。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class MyFragment extends BaseFragment implements MyView<String> {

    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void onSuccess(String data) {

    }

    @Override
    public void onFailed(String error) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onComplete() {

    }
}
