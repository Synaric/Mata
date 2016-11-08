package com.synaric.app.mata.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.synaric.app.rxmodel.utils.ReflectUtils;
import com.synaric.common.utils.StatusBarUtils;

import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.debug.DebugFragmentRecord;
import xiaofei.library.hermeseventbus.HermesEventBus;

/**
 * 所有Activity的基类。
 * <br/><br/>Created by Synaric on 2016/9/5 0005.
 */
public abstract class BaseActivity extends SupportActivity{

    private boolean enableTransitAnim;

    private Method getFragmentRecords;

    private Fragmentation fragmentation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        HermesEventBus.getDefault().register(this);
        if(savedInstanceState == null) onCreate();
        StatusBarUtils.compat(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HermesEventBus.getDefault().unregister(this);
    }

    /**
     * 在{@link #onCreate(Bundle)}中调用，条件是savedInstanceState == null。
     */
    protected void onCreate() {
        //预加载可能用到的反射
        fragmentation = (Fragmentation) ReflectUtils.getDeclaredFieldValue(this, "mFragmentation");
        if (fragmentation != null) {
            getFragmentRecords = ReflectUtils.getDeclaredMethod(fragmentation, "getFragmentRecords");
        }
    }

    @SuppressWarnings("unchecked")
    protected List<DebugFragmentRecord> getFragmentRecords() {
        if(fragmentation == null) return null;
        return (List<DebugFragmentRecord>) ReflectUtils.invoke(fragmentation, getFragmentRecords);
    }

    /**
     * 开启MaterialDesign过渡动画。
     * 如果从A->B，则A调用{@link #screenTransitAnim(View, int, Intent)}，B的{@link #onCreate()}
     * 中调用本方法。
     */
    protected void enableTransitAnim() {
        enableTransitAnim = true;
    }

    /**
     * Material Design单元素曲线平移过渡动画，兼容4.X。
     */
    protected void screenTransitAnim(View view, int targetId, Intent intent) {

    }

    public abstract int getLayoutId();
}
