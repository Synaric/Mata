package com.synaric.app.mata.module.main.find;

import com.synaric.app.mata.base.BaseFragment;

/**
 * "发现"界面。
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public class FindFragment extends BaseFragment {

    public static FindFragment newInstance() {
        return new FindFragment();
    }

    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        setEnableSwipeBack(false);
    }
}
