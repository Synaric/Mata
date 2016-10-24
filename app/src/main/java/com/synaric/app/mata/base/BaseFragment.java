package com.synaric.app.mata.base;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synaric.app.mata.R;
import com.synaric.app.mata.event.NetworkStateChanged;
import com.synaric.app.mata.event.RequestFinish;
import com.synaric.app.mata.event.RequestStartFragment;
import com.synaric.app.mata.widget.SwipeBackLayout;
import com.synaric.app.widget.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * <pre>
 *     如果要关闭swipe back模式：
 *     protected void onCreate() {
 *         setEnableSwipeBack(false);
 *     }
 *     例如{@link com.synaric.app.mata.module.main.root.HomeFragment}就不应该开启swipe back模式。
 * </pre>
 * <br/><br/>Created by Synaric on 2016/10/11 0011.
 */
public abstract class BaseFragment extends SupportFragment {

    protected final EventBus eventBus = EventBus.getDefault();

    protected BaseActivity activity;

    protected SwipeBackLayout mSwipeBackLayout;

    protected Toolbar toolbar;
    private OnLockDrawLayoutListener lockDrawLayoutListener;

    /**
     * 是否允许右滑退出。
     */
    private boolean enableSwipeBack = true;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (enableSwipeBack && context instanceof OnLockDrawLayoutListener) {
            lockDrawLayoutListener = (OnLockDrawLayoutListener) context;
        }
        activity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreate();
        if(enableSwipeBack) onFragmentCreate();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutId = getLayoutId();
        View root;
        if(layoutId <= 0) {
            root = ViewUtils.createDescriptionView(getContext(), getClass().getSimpleName());
        } else {
            root = inflater.inflate(layoutId, container, false);
        }
        ButterKnife.inject(this, root);
        eventBus.register(this);
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
        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        if(toolbar == null) return;
        toolbar.setTitle(R.string.app_name);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayUseLogoEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }

        //必须在setSupportActionBar之后设置
        toolbar.setNavigationOnClickListener(v -> postBackPressed());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        eventBus.unregister(this);
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
        if (enableSwipeBack && hidden && mSwipeBackLayout != null) {
            mSwipeBackLayout.hiddenFragment();
        }
    }

    /**
     * 在{@link #onCreateView(View)}中调用，绑定{@link SwipeBackLayout}。
     * @param view 根视图。
     */
    protected View attachToSwipeBack(View view) {
        mSwipeBackLayout.attachToFragment(this, view);
        return mSwipeBackLayout;
    }

    protected void screenTransitAnim(View view, int targetId, Intent intent) {
        ((BaseActivity) getContext()).screenTransitAnim(view, targetId, intent);
    }

    public void setEnableSwipeBack(boolean enableSwipeBack) {
        this.enableSwipeBack = enableSwipeBack;
    }

    public abstract int getLayoutId();

    @Override
    protected void initFragmentBackground(View view) {
        if (enableSwipeBack) {
            if (view instanceof SwipeBackLayout) {
                View childView = ((SwipeBackLayout) view).getChildAt(0);
                setBackground(childView);
            } else {
                setBackground(view);
            }
        }
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
     * @param post 是否使用异步请求。参考{@link #postStartFragment(Class, BaseFragment, int)}。
     */
    public void startFragmentSingleTask(
            Class<? extends BaseFragment> from,
            Class<? extends BaseFragment> to,
            boolean post) {

        BaseFragment fragment = findFragment(to);
        if (fragment == null) {
            popTo(from, false, () -> {
                try {
                    if (post) {
                        start(to.newInstance());
                    } else {
                        postStartFragment(from, to.newInstance(), SupportFragment.STANDARD);
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

    /**
     * 异步通过EventBus请求跳转新Fragment。接收端需要监听{@link RequestStartFragment}事件，并且必须
     * 为一个{@link me.yokeyword.fragmentation.SupportActivity}。这个方法区别于
     * {@link #startFragment(BaseFragment, int)}，适用{@link Fragment#getParentFragment()}返回null
     * 的情况（例如：ViewPager中的Fragment）。如果需要在这种Fragment内跳转新Fragment，就需要通过这个
     * 方法发送消息给顶层控制{@link me.yokeyword.fragmentation.SupportActivity}。
     * @param from 当前Fragment。
     * @param to 目标Fragment。
     * @param launchMode 启动模式。
     */
    public void postStartFragment(Class<? extends BaseFragment> from, BaseFragment to, int launchMode) {
        eventBus.post(new RequestStartFragment(from, to, launchMode));
    }

    public void postBackPressed() {
        eventBus.post(new RequestFinish());
    }

    private void onFragmentCreate() {
        mSwipeBackLayout = new SwipeBackLayout(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    /**
     * 根Fragment需要覆写本方法并监听事件（加上{@link Subscribe}注解）。
     */
    public void onEvent(RequestStartFragment event) {
        Class<?> from = event.from;
        if(from == null || !(getClass() == from)) return;
        start(event.to, event.launchMode);
    }

    /**
     * 根Fragment需要覆写本方法并监听事件（加上{@link Subscribe}注解）。
     */
    @SuppressWarnings("deprecation")
    public void onEvent(RequestFinish event) {
        activity.onBackPressed();
    }

    /**
     * 子Fragment如要监听网络情况，需覆写本方法并监听事件（加上{@link Subscribe}注解）。
     */
    public void onEvent(NetworkStateChanged event) {

    }

    public interface OnLockDrawLayoutListener {

        void onLockDrawLayout(boolean lock);
    }
}
