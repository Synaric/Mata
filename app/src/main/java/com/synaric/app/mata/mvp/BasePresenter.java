package com.synaric.app.mata.mvp;

import com.google.gson.Gson;
import com.synaric.app.mata.base.ApiCallback;
import com.synaric.app.mata.base.SubscriberCallback;
import com.synaric.app.mata.model.StandardModel;
import com.synaric.common.CommonUtilsConfiguration;
import com.synaric.mvp.Presenter;
import com.synaric.mvp.View;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * 提供了Presenter的基础实现，包括绑定和解绑View。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public class BasePresenter implements Presenter<View> {

    public static final StandardModel standardModel = CommonUtilsConfiguration.retrofit.create(StandardModel.class);

    public View view;

    private Gson gson;

    private CompositeSubscription compositeSubscription;

    public BasePresenter(View v) {
        gson = new Gson();
        attachView(v);
    }

    @Override
    public void attachView(View mvpView) {
        this.view = mvpView;
    }

    @Override
    public void detachView() {
        this.view = null;
        onUnsubscribe();
    }

    @SuppressWarnings("unchecked")
    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void onUnsubscribe() {
        if (compositeSubscription != null && compositeSubscription.hasSubscriptions()) {
            compositeSubscription.unsubscribe();
        }
    }

    protected <T> void loadData(ApiCallback<T> apiCallBack) {
        view.onLoading();
        addSubscription(
                apiCallBack.onLoad(),
                new SubscriberCallback<>(apiCallBack)
        );
    }

    protected Map<String, String> queryBy(Object obj) {
        Map<String, String> params = new HashMap<>();
        params.put("params", obj == null ? "{}" : gson.toJson(obj));
        params.put("ext", "{}");
        return params;
    }

    public abstract class SimpleApiCallback<T> implements ApiCallback<T> {

        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(T data) {
            view.onSuccess(data);
        }

        @Override
        public void onFailure(int code, String msg) {
            view.onFailed(msg);
        }

        @Override
        public void onCompleted() {
            view.onComplete();
        }
    }
}