package com.synaric.app.mata.module.local;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.mvp.View;

import rx.Observable;

/**
 * <br/><br/>Created by Synaric on 2016/10/27 0027.
 */
public class LocalAudiosPresenter extends BasePresenter {

    public void loadLocalAudios(View<?> view) {
        loadData(view, new SimpleApiCallback<String>() {

            @Override
            public Observable<String> onLoad() {
                return null;
            }
        });
    }
}
