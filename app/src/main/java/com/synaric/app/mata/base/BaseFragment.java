package com.synaric.app.mata.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synaric.app.mata.event.NetworkStateChanged;
import com.synaric.app.mata.event.RequestFinish;
import com.synaric.app.mata.event.RequestStartFragment;
import com.synaric.app.mata.event.ShowFragmentStack;
import com.synaric.app.mata.widget.SwipeBackLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 封装了Fragment共通操作，并且具有SwipeBack功能。
 * <br/>如果要关闭swipe back模式：
 * <pre>
 *     protected void onCreate() {
 *         setEnableSwipeBack(false);
 *     }
 * 例如{@link com.synaric.app.mata.module.main.root.HomeFragment}就不应该开启swipe back模式。
 * </pre>
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
@SuppressWarnings("unused")
public abstract class BaseFragment extends SupportFragment {

    protected final EventBus eventBus = EventBus.getDefault();

    protected BaseActivity activity;

    protected Toolbar toolbar;

    protected SwipeBackLayout swipeBackLayout;

    private OnLockDrawLayoutListener lockDrawLayoutListener;

    private FragmentDelegate delegate;

    /**
     * 是否允许右滑退出。
     */
    private boolean enableSwipeBack = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (BaseActivity) context;
        delegate = new FragmentDelegate(activity, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
        if (enableSwipeBack && activity instanceof OnLockDrawLayoutListener) {
            lockDrawLayoutListener = (OnLockDrawLayoutListener) activity;
        }
        if(enableSwipeBack) onFragmentCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View root = delegate.onCreateView(layoutId, inflater, container);
        if(enableSwipeBack) {
            if(lockDrawLayoutListener != null) lockDrawLayoutListener.onLockDrawLayout(true);
            root = attachToSwipeBack(root);
        }
        onCreateView(root);
        initToolBar(root);
        return root;
    }

    protected void onCreate() {}

    protected void onCreateView(View root) {}

    protected void initToolBar(View root) {
        toolbar = delegate.initToolBar(root);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        delegate.onDestroyView();
        if(enableSwipeBack && lockDrawLayoutListener != null)
            lockDrawLayoutListener.onLockDrawLayout(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lockDrawLayoutListener = null;
        activity = null;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (enableSwipeBack && hidden && swipeBackLayout != null) {
            swipeBackLayout.hiddenFragment();
        }
    }

    /**
     * 绑定{@link SwipeBackLayout}。
     * @param view 根视图。
     */
    protected View attachToSwipeBack(View view) {
        if(swipeBackLayout != null) swipeBackLayout.attachToFragment(this, view);
        return swipeBackLayout;
    }

    protected void screenTransitAnim(View view, int targetId, Intent intent) {
        ((BaseActivity) getContext()).screenTransitAnim(view, targetId, intent);
    }

    public void setEnableSwipeBack(boolean enableSwipeBack) {
        this.enableSwipeBack = enableSwipeBack;
        if(swipeBackLayout != null) swipeBackLayout.setEnableGesture(enableSwipeBack);
    }

    public abstract int getLayoutId();

    @Override
    protected void initFragmentBackground(View view) {
        if (view instanceof SwipeBackLayout) {
            View childView = ((SwipeBackLayout) view).getChildAt(0);
            setBackground(childView);
        } else {
            setBackground(view);
        }
    }

    public void showFragmentStackHierarchyView() {
        eventBus.post(new ShowFragmentStack());
    }

    public void startFragment(BaseFragment fragment) {
        startFragment(fragment, SupportFragment.STANDARD);
    }

    public void startFragment(BaseFragment fragment, int launchMode) {
        BaseFragment parent = (BaseFragment) getParentFragment();
        if(parent == null) {
            start(fragment, launchMode);
        } else {
            parent.startFragment(fragment, launchMode);
        }
    }

    /**
     * 以SingleTask模式启动Fragment。
     * @param from 当前Fragment。
     * @param to 目标Fragment。
     * @param post 是否使用异步请求。参考{@link #postStartFragment(Class, SupportFragment, int)}。
     */
    public void startFragmentSingleTask(
            Class<? extends BaseFragment> from,
            Class<? extends BaseFragment> to,
            boolean post) {

        BaseFragment fragment = findFragment(to);
        if (fragment == null) {
            //如果不在栈内，则直接启动
            popTo(from, false, () -> {
                try {
                    if (post) {
                        postStartFragment(from, to.newInstance(), SupportFragment.STANDARD);
                    } else {
                        start(to.newInstance());
                    }
                } catch (java.lang.InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
        } else {
            // 如果已经在栈内,则以SingleTask模式start
            if (post) {
                start(fragment, SupportFragment.SINGLETASK);
            } else {
                postStartFragment(from, fragment, SupportFragment.STANDARD);
            }
        }
    }

    public void postStartFragment(Class<? extends BaseFragment> from, SupportFragment to, int launchMode) {
        delegate.postStartFragment(from, to, launchMode);
    }

    private void onFragmentCreate() {
        swipeBackLayout = new SwipeBackLayout(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        swipeBackLayout.setLayoutParams(params);
        swipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 异步请求开启Fragment事件。
     * 根Fragment需要覆写本方法并监听事件（加上{@link Subscribe}注解）。
     */
    public void onEvent(RequestStartFragment event) {
        Class<?> from = event.from;
        SupportFragment to = event.to;
        if(from == null || to == null ||  !(getClass() == from)) return;
        start(to, event.launchMode);
    }

    /**
     * 异步请求Fragment退栈事件。
     * 根Fragment需要覆写本方法并监听事件（加上{@link Subscribe}注解）。
     */
    @SuppressWarnings("deprecation")
    public void onEvent(RequestFinish event) {
        activity.onBackPressed();
    }

    /**
     * 监听网络情况。
     */
    @Subscribe
    public void onEvent(NetworkStateChanged event) {

    }

    /**
     * 单Activity模式中，SwipeBackFragment会在生命周期中请求锁定和释放DrawerLayout。
     * 持有DrawerLayout的全局界面需要实现这个监听，并根据Fragment栈情况锁定和释放DrawLayout。
     */
    public interface OnLockDrawLayoutListener {

        void onLockDrawLayout(boolean lock);
    }
}
