package com.mata.common.simple;

import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 简单化OnGestureListener。
 * Created by Synaric on 2016/9/21 0021.
 */
public abstract class SimpleOnGestureListener implements GestureDetector.OnGestureListener {

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }
}
