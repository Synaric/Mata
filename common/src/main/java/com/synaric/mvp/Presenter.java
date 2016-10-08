package com.synaric.mvp;

/**
 * MVP中的Presenter。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
