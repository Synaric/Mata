package com.synaric.mvp;

/**
 * MVP中的View。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public interface View<T> {

    void onSuccess(T data);

    void onFailed(String error);

    void onLoading();

    void onComplete();
}
