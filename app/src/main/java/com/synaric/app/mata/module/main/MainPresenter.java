package com.synaric.app.mata.module.main;

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
        loadData(new SimpleApiCallback<String>() {
            @Override
            public Observable<String> onLoad() {
                return standardModel.emptyRequest(queryBy(null));
            }
        });
    }
}
