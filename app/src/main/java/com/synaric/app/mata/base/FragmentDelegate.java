package com.synaric.app.mata.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synaric.app.mata.R;
import com.synaric.app.mata.event.RequestFinish;
import com.synaric.app.mata.event.RequestStartFragment;
import com.synaric.app.widget.ViewUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * <br/><br/>Created by Synaric on 2016/10/26 0026.
 */
public class FragmentDelegate {

    private BaseActivity activity;

    private Fragment wrapper;

    private HermesEventBus eventBus = HermesEventBus.getDefault();

    public FragmentDelegate(Context context, Fragment fragment) {
        activity = (BaseActivity) context;
        wrapper = fragment;
    }

    public View inflate(int layoutId, LayoutInflater inflater, @Nullable ViewGroup container) {
        View root;

        if(layoutId <= 0) {
            root = ViewUtils.createDescriptionView(wrapper.getContext(), wrapper.getClass().getSimpleName());
        } else {
            root = inflater.inflate(layoutId, container, false);
        }
        return root;
    }

    public View onCreateView(int layoutId, LayoutInflater inflater, @Nullable ViewGroup container) {
        View root = inflate(layoutId, inflater, container);
        ButterKnife.inject(wrapper, root);
        eventBus.register(wrapper);
        return root;
    }

    public void onDestroyView() {
        ButterKnife.reset(wrapper);
        eventBus.unregister(wrapper);
        activity = null;
        wrapper = null;
    }

    public Toolbar initToolBar(View root) {
        Toolbar toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        if(toolbar == null) return null;
        toolbar.setTitle(R.string.app_name);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        //必须在setSupportActionBar之后设置
        toolbar.setNavigationOnClickListener(v -> eventBus.post(new RequestFinish()));
        return toolbar;
    }

    /**
     * 异步通过EventBus请求跳转新Fragment。接收端需要监听{@link RequestStartFragment}事件，并且必须
     * 为一个{@link me.yokeyword.fragmentation.SupportActivity}。这个方法区别于
     * {@link BaseFragment#startFragment(BaseFragment, int)}，
     * 适用{@link android.app.Fragment#getParentFragment()}返回null的情况（例如：ViewPager中的
     * Fragment）。如果需要在这种Fragment内跳转新Fragment，就需要通过这个方法发送消息给顶层控制
     * {@link me.yokeyword.fragmentation.SupportActivity}。
     * @param from 当前Fragment。
     * @param to 目标Fragment。
     * @param launchMode 启动模式。
     */
    public void postStartFragment(
            Class<? extends BaseFragment> from,
            SupportFragment to,
            int launchMode) {
        eventBus.post(new RequestStartFragment(from, to, launchMode));
    }
}
