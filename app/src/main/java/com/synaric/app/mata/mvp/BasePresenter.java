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
 * 提供了Presenter的基础实现，包括绑定和解绑View。View需要实现{@link View}。
 * 读取数据并更新绑定的View，调用{@link #loadData(ApiCallback)}即可。
 * 如果这个View内部有许多子View（例如一个Activity中存在ViewPager + Fragment，并且每一个Fragment又实现
 * 了View接口），那么读取数据并更新子View需要调用{@link #loadData(View, ApiCallback)}。
 * <br/><br/>Created by Synaric on 2016/10/8 0008.
 */
public class BasePresenter implements Presenter<View> {

    public static final StandardModel standardModel = CommonUtilsConfiguration.retrofit.create(StandardModel.class);

    public View mainView;

    private Gson gson;

    private Map<Object, View> attachedViews;

    private CompositeSubscription compositeSubscription;

    public BasePresenter() {
        gson = new Gson();
        attachedViews = new HashMap<>();
    }

    public BasePresenter(View view) {
        this();
        attachView(view);
        mainView = view;
    }

    @Override
    public View attachView(View view) {
        Object tag = null;
        for(Map.Entry<Object, View> entry : attachedViews.entrySet()) {
            if (entry.getValue().equals(view)) {
                tag = entry.getKey();
                break;
            }
        }
        if(tag == null) tag = view;
        attachedViews.put(tag, view);
        return view;
    }

    public View attachView(String tag, View view) {
        //如果key为自身的对象已经存在于集合，首先删除
        attachedViews.remove(view);
        attachedViews.put(tag, view);
        return view;
    }

    @Override
    public View detachView(View view) {
        Object tag = null;
        for(Map.Entry<Object, View> entry : attachedViews.entrySet()) {
            if (entry.getValue().equals(view)) {
                tag = entry.getKey();
                break;
            }
        }
        if(tag == null) tag = view;
        return attachedViews.remove(tag);
    }

    public View detachView(String tag) {
        return attachedViews.remove(tag);
    }

    public void detachAllViews() {
        attachedViews.clear();
        mainView = null;
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
        loadData(mainView, apiCallBack);
    }

    protected <T> void loadData(View<?> view, ApiCallback<T> apiCallBack) {
        if(view != null) {
            view.onLoading();
            if(apiCallBack instanceof SimpleApiCallback)
                ((SimpleApiCallback) apiCallBack).attachView(view);
        }
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

        private View view;

        public SimpleApiCallback() {}

        public SimpleApiCallback(View view) {
            this.view = view;
        }

        public void attachView(View view) {
            this.view = view;
        }

        @SuppressWarnings("unchecked")
        @Override
        public void onSuccess(T data) {
            if(view == null) return;
            view.onSuccess(data);
        }

        @Override
        public void onFailure(int code, String msg) {
            if(view == null) return;
            view.onFailed(msg);
        }

        @Override
        public void onCompleted() {
            if(view == null) return;
            view.onComplete();
        }
    }
}