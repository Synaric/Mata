package com.synaric.app.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.widget.Button;

import com.mata.common.utils.SystemUtil;

/**
 * 普通状态是一个可拖拽的按钮，点击后能产生若干选项。
 * Created by Synaric on 2016/9/21 0021.
 */
public class ActionButton extends View implements View.OnClickListener{

    /**
     * 默认按钮大小。
     */
    public static final int DEFAULT_SIZE = 56;

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

    public ActionButton(Context context) {
        super(context);
        init(context);
    }

    public ActionButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        Resources resources = getResources();
        bitmapClosed = BitmapFactory.decodeResource(resources, R.drawable.ic_play_arrow_white_48dp);
        bitmapOpened = BitmapFactory.decodeResource(resources, R.drawable.ic_pause_black_48dp);
        drawable = new ActionButtonDrawable(bitmapOpened, bitmapClosed);
        setBackground(drawable);
        setOnClickListener(this);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        params.width = SystemUtil.dp2px(context, DEFAULT_SIZE);
        params.height = SystemUtil.dp2px(context, DEFAULT_SIZE);
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        params.type = WindowManager.LayoutParams.TYPE_PRIORITY_PHONE;

        ViewConfiguration sysConfig = ViewConfiguration.get(context);
        scaledTouchSlop = sysConfig.getScaledTouchSlop();
    }

    public void show() {
        windowManager.addView(this, params);
    }

    public void hide() {
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
        if(drawable != null) drawable.toggle();
    }
}
