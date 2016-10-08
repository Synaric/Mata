package com.synaric.app.mata.base;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 请求服务端底层回调，最终以{@link ApiCallback}形式回调。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public class SubscriberCallback<T> extends Subscriber<T> {
    private ApiCallback<T> apiCallback;

    public SubscriberCallback(ApiCallback<T> apiCallback) {
        this.apiCallback = apiCallback;
    }

    @Override
    public void onCompleted() {
        apiCallback.onCompleted();
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            String msg = httpException.getMessage();
            if (code == 504) {
                msg = "网络不给力";
            }
            apiCallback.onFailure(code, msg);
        } else {
            apiCallback.onFailure(0, e.getMessage());
        }
        apiCallback.onCompleted();
    }

    @Override
    public void onNext(T t) {
        apiCallback.onSuccess(t);
    }
}
