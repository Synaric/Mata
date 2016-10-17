package com.synaric.app.mata.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.synaric.app.mata.widget.SwipeBackLayout;
import com.synaric.app.widget.ViewUtil;

import org.greenrobot.eventbus.EventBus;

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

    private SwipeBackLayout mSwipeBackLayout;

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
            root = ViewUtil.createDescriptionView(getContext(), getClass().getSimpleName());
        } else {
            root = inflater.inflate(layoutId, container, false);
        }
        ButterKnife.inject(this, root);
        onCreateView(root);
        if(enableSwipeBack) {
            if(lockDrawLayoutListener != null) lockDrawLayoutListener.onLockDrawLayout(true);
            attachToSwipeBack(root);
        }
        return root;
    }

    protected void onCreate() {}

    protected void onCreateView(View root) {}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

        if(enableSwipeBack && lockDrawLayoutListener != null)
            lockDrawLayoutListener.onLockDrawLayout(false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        lockDrawLayoutListener = null;
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

    private void onFragmentCreate() {
        mSwipeBackLayout = new SwipeBackLayout(_mActivity);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mSwipeBackLayout.setLayoutParams(params);
        mSwipeBackLayout.setBackgroundColor(Color.TRANSPARENT);
    }

    public interface OnLockDrawLayoutListener {

        void onLockDrawLayout(boolean lock);
    }
}
