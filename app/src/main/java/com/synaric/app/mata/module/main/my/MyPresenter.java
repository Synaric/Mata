package com.synaric.app.mata.module.main.my;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.mvp.View;

import rx.Observable;

public class MyPresenter extends BasePresenter {

    public MyPresenter(View v) {
        super(v);
    }

    /**
     * 读取"我的"界面内容。
     */
    public void loadMyData() {
        //accessData(new SimpleApiCallback<String>(standardModel.emptyRequest(queryBy(null))));
    }
}
