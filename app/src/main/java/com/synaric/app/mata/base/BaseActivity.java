package com.synaric.app.mata.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kale.activityoptions.ActivityCompatICS;
import com.kale.activityoptions.ActivityOptionsCompatICS;
import com.kale.activityoptions.transition.TransitionCompat;
import com.synaric.app.mata.R;
import com.synaric.common.utils.StatusBarUtils;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 所有Activity的基类。
 * Created by Synaric on 2016/9/5 0005.
 */
public abstract class BaseActivity extends SupportActivity{

    private boolean enableTransitAnim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        onCreate();
        StatusBarUtils.compat(this);
    }

    protected void onCreate() {

    }

    /**
     * 开启MaterialDesign过渡动画。
     * 如果从A->B，则A调用{@link #screenTransitAnim(View, int, Intent)}，B的{@link #onCreate()}
     * 中调用本方法。
     */
    protected void enableTransitAnim() {
        TransitionCompat.startTransition(this, getLayoutId());
        enableTransitAnim = true;
    }

    /**
     * Material Design单元素曲线平移过渡动画，兼容4.X。
     */
    protected void screenTransitAnim(View view, int targetId, Intent intent) {
        ActivityOptionsCompatICS options = ActivityOptionsCompatICS.
                makeSceneTransitionAnimation(this, view, targetId);
        ActivityCompatICS.startActivity(this, intent, options.toBundle());
    }

    @Override
    public void onBackPressedSupport() {
        if(enableTransitAnim) {
            TransitionCompat.finishAfterTransition(this);
            return;
        }
        super.onBackPressedSupport();
    }

    public abstract int getLayoutId();
}
