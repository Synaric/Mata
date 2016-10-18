package com.synaric.app.mata.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.synaric.common.utils.StatusBarUtils;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.Fragmentation;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.debug.DebugFragmentRecord;

/**
 * 所有Activity的基类。
 * Created by Synaric on 2016/9/5 0005.
 */
public abstract class BaseActivity extends SupportActivity{

    private boolean enableTransitAnim;

    private Class<?> myClass = getClass();

    private Method getFragmentRecords;

    private Fragmentation fragmentation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        EventBus.getDefault().register(this);
        onCreate();
        StatusBarUtils.compat(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void onCreate() {
        //预加载可能用到的反射
        fragmentation = getSuperFragmentation();
        if (fragmentation != null) {
            initFragmentationReflect();
        }
    }

    protected Fragmentation getSuperFragmentation() {
        try {
            Field mFragmentation = myClass.getDeclaredField("mFragmentation");
            mFragmentation.setAccessible(true);
            return (Fragmentation) mFragmentation.get(this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    protected List<DebugFragmentRecord> getFragmentRecords() {
        if(getFragmentRecords == null || fragmentation == null) return null;
        try {
            return (List<DebugFragmentRecord>) getFragmentRecords.invoke(fragmentation);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
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

    private void initFragmentationReflect() {
        Class<?> fragClass = Fragmentation.class;
        try {
            getFragmentRecords = fragClass.getDeclaredMethod("getFragmentRecords");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
