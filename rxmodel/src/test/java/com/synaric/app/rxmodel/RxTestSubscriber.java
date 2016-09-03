package com.synaric.app.rxmodel;

import android.util.Log;

import rx.Subscriber;

/**
 *
 * Created by Synaric on 2016/8/29 0029.
 */
public class RxTestSubscriber<T> extends Subscriber<T>{

    private String TAG;

    RxTestSubscriber(String tag) {
        TAG = tag;
    }

    @Override
    public void onCompleted() {
        Log.d(TAG, "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: ");
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: " + t.toString());
    }
}
