package com.synaric.app.mata.module.main.root;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.mvp.View;

import rx.Observable;

/**
 * 主界面Presenter。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public class MainPresenter extends BasePresenter{

    public MainPresenter(View v) {
        super(v);
    }

    public void loadMain() {
        accessData(new SimpleApiCallback<>(standardModel.emptyRequest(queryBy(null))));
    }
}
