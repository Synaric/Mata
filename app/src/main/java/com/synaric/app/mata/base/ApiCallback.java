package com.synaric.app.mata.base;

import rx.Observable;

/**
 * 请求服务端回调。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public interface ApiCallback<T> {

    Observable<T> onLoad();

    void onSuccess(T data);

    void onFailure(int code, String msg);

    void onCompleted();

}
