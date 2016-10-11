package com.synaric.app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;

import com.synaric.common.utils.SystemUtil;

/**
 * 普通状态是一个可拖拽的按钮，点击后能产生若干选项。
 * 要创建或者获取一个实例，使用{@link #getOrCreate(Context)}。
 * 要销毁实例，通常在{@link Activity#onDestroy()}，中调用{@link #destroy()}。
 * Created by Synaric on 2016/9/21 0021.
 */
public class ActionButton extends View implements View.OnClickListener{

    /**
     * 默认按钮大小。
     */
    public static final int DEFAULT_SIZE = 56;

    /**
     * 初始状态距离页边大小。
     */
    public static final int DEFAULT_MARGIN = 24;

    /**
     * 当控件位于初始状态或者{@link MotionEvent#ACTION_UP}触发时，{@link ActionButton#flagMotion}位
     * 于这种状态。
     */
    public static final int MOTION_NOTHING = 0;

    /**
     * 单击动作。
     */
    public static final int MOTION_CLICK = 1;

    /**
     * 拖拽动作。
     */
    public static final int MOTION_DRAG = 2;

    /**
     * 全局单例。
     */
    private static ActionButton singleton;

    /**
     * 绘制的内容。
     */
    private ActionButtonDrawable drawable;

    private WindowManager windowManager;

    private WindowManager.LayoutParams params;

    /**
     * 动作被识别为滑动的最小手指移动距离。
     */
    private int scaledTouchSlop;

    /**
     * 动作过程中，手指第一次按下时控件的位置。
     */
    private float viewDownX;
    private float viewDownY;

    /**
     * 动作过程中，手指第一次按下的位置。
     */
    private float fingerDownX;
    private float fingerDownY;

    /**
     * 上一次触摸事件中手指的位置。
     */
    private float fingerLastMoveX = -1;
    private float fingerLastMoveY = -1;

    /**
     * 表示当前的动作。
     * 这个控件支持的动作是单击和拖拽。
     */
    private int flagMotion;

    private Bitmap bitmapClosed;
    private Bitmap bitmapOpened;

    public static ActionButton getOrCreate(Context context) {
        if(singleton == null) {
            singleton = new ActionButton(context);
            singleton.createInternal();
        }
        return singleton;
    }

    public static void hide() {
        if(singleton != null) singleton.hideInternal();
    }

    public static void destroy() {
        if(singleton != null) singleton.destroyInternal();
    }

    public ActionButton(Context context) {
        super(context);
        init(context);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        int size = SystemUtil.dp2px(context, DEFAULT_SIZE);
        int margin = SystemUtil.dp2px(context, DEFAULT_MARGIN);

        Resources resources = getResources();
        bitmapClosed = BitmapFactory.decodeResource(resources, R.drawable.ic_play_arrow_white_48dp);
        bitmapOpened = BitmapFactory.decodeResource(resources, R.drawable.ic_pause_black_48dp);
        drawable = new ActionButtonDrawable(bitmapOpened, bitmapClosed);
        setBackground(drawable);
        setOnClickListener(this);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.width = size;
        params.height = size;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;

        DisplayMetrics metrics = Utils.getDisplayMetrics(context);
        params.horizontalMargin = 1.0f * margin / metrics.widthPixels;
        params.verticalMargin = 1.0f * margin / metrics.heightPixels;
        params.gravity = Gravity.BOTTOM | Gravity.END;

        ViewConfiguration sysConfig = ViewConfiguration.get(context);
        scaledTouchSlop = sysConfig.getScaledTouchSlop();
    }

    /**
     * 使侧边栏打开时能隐藏本控件，反之则显示。
     */
    public void syncState(DrawerLayout drawerLayout) {
        drawerLayout.addDrawerListener(new LocalDrawerListener());
    }

    private void createInternal() {
        windowManager.addView(this, params);
    }

    private void hideInternal() {
        windowManager.removeView(this);
    }

    private void destroyInternal() {
        windowManager.removeView(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fingerDownX = event.getRawX();
                fingerDownY = event.getRawY();
                if(!isEnabled()) return false;
                viewDownX = params.x;
                viewDownY = params.y;
                flagMotion = MOTION_CLICK;
                break;
            case MotionEvent.ACTION_MOVE:
                float fingerMoveX = event.getRawX() + 0.5f;
                float fingerMoveY = event.getRawY() + 0.5f;
                float dx = fingerMoveX - fingerDownX;
                float dy = fingerMoveY - fingerDownY;
                params.x = (int) (viewDownX + dx + 0.5f);
                params.y = (int) (viewDownY + dy + 0.5f);
                windowManager.updateViewLayout(this, params);

                try {
                    if(flagMotion == MOTION_CLICK || flagMotion == MOTION_NOTHING) {
                        if(fingerLastMoveX < 0 || fingerLastMoveY < 0)
                            return super.onTouchEvent(event);
                        float mdx = fingerMoveX - fingerLastMoveX;
                        float mdy = fingerMoveY - fingerLastMoveY;
                        //判断是否这个动作应该被识别为滑动而不是点击
                        if(mdx > scaledTouchSlop || mdy > scaledTouchSlop) {
                            flagMotion = MOTION_DRAG;
                        }
                    }
                } finally {
                    fingerLastMoveX = fingerMoveX;
                    fingerLastMoveY = fingerMoveY;
                }
                break;
            case MotionEvent.ACTION_UP:
                try {
                    //防止拖拽和单击事件同时触发
                    if(flagMotion == MOTION_DRAG) return true;
                } finally {
                    //重置动作flag
                    flagMotion = MOTION_NOTHING;
                    fingerLastMoveX = fingerLastMoveY = -1;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    public boolean isOpen() {
        return drawable.isOpen();
    }

    @Override
    public void onClick(View v) {
        if(drawable != null && isEnabled()) drawable.toggle();
    }

    /**
     * 侧边栏打开将隐藏{@link ActionButton}，反之则显示。
     */
    private class LocalDrawerListener extends DrawerLayout.SimpleDrawerListener{

        private int oldState = DrawerLayout.STATE_IDLE;

        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            setAlpha(1 - slideOffset);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            setVisibility(View.INVISIBLE);
            setEnabled(false);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
            if(oldState == DrawerLayout.STATE_IDLE && newState == DrawerLayout.STATE_DRAGGING) {
                setVisibility(View.VISIBLE);
                setEnabled(true);
            }
            oldState = newState;
        }
    }
}
