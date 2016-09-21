package com.synaric.app.widget;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.Property;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.orhanobut.logger.Logger;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 能够在收起和展开两种状态之间切换的圆形菜单。
 * Created by Synaric on 2016/9/21 0021.
 */
public class ActionButtonDrawable extends Drawable {

    private static final long ANIMATION_DURATION = 280;
    private static final Interpolator ANIMATION_INTERPOLATOR = new DecelerateInterpolator();

    private Paint linePaint;
    private Paint backgroundPaint;

    private float[] points = new float[8];
    private final RectF bounds = new RectF();

    private boolean mode;
    private ArgbEvaluator argbEvaluator = new ArgbEvaluator();

    private float rotation;

    private int strokeWidth = 10;
    private int colorClosed = Color.BLUE;
    private int colorOpened = Color.WHITE;

    public ActionButtonDrawable() {
        this(10, Color.BLUE, Color.WHITE);
    }

    public ActionButtonDrawable(int strokeWidth, int tickColor, int plusColor) {
        this.strokeWidth = strokeWidth;
        colorClosed = tickColor;
        colorOpened = plusColor;
        setupPaints();
    }

    private void setupPaints() {
        linePaint = new Paint(ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setColor(colorOpened);
        linePaint.setStrokeWidth(strokeWidth);
        linePaint.setStrokeCap(Paint.Cap.ROUND);

        backgroundPaint = new Paint(ANTI_ALIAS_FLAG);
        backgroundPaint.setStyle(Paint.Style.FILL);
        backgroundPaint.setColor(colorClosed);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);

        int padding = bounds.centerX()/2;

        this.bounds.left = bounds.left + padding;
        this.bounds.right = bounds.right - padding;
        this.bounds.top = bounds.top + padding;
        this.bounds.bottom = bounds.bottom - padding;

        setupPlusMode();
    }

    private void setupPlusMode() {
        points[0] = bounds.left;
        points[1] = bounds.centerY();
        points[2] = bounds.right;
        points[3] = bounds.centerY();
        points[4] = bounds.centerX();
        points[5] = bounds.top;
        points[6] = bounds.centerX();
        points[7] = bounds.bottom;
    }

    private float x(int pointIndex) {
        return points[xPosition(pointIndex)];
    }

    private float y(int pointIndex) {
        return points[yPosition(pointIndex)];
    }

    private int xPosition(int pointIndex) {
        return pointIndex*2;
    }

    private int yPosition(int pointIndex) {
        return xPosition(pointIndex) + 1;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), bounds.centerX(), backgroundPaint);

        canvas.save();
        canvas.rotate(180 * rotation, (x(0) + x(1))/2, (y(0) + y(1))/2);
        canvas.drawLine(x(0), y(0), x(1), y(1), linePaint);
        canvas.restore();

        canvas.save();
        canvas.rotate(180 * rotation, (x(2) + x(3)) / 2, (y(2) + y(3)) / 2);
        canvas.drawLine(x(2), y(2), x(3), y(3), linePaint);
        canvas.restore();
    }

    public void toggle() {
        if(mode) {
            animatePlus();
        } else {
            animateTick();
        }
        mode = !mode;
        Logger.d("ActionButton toggle: " + (isOpen() ? "open" : "close"));
    }

    @SuppressWarnings("unchecked")
    public void animateTick() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(this, mPropertyPointAX, bounds.left),
                ObjectAnimator.ofFloat(this, mPropertyPointAY, bounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointBX, bounds.centerX()),
                ObjectAnimator.ofFloat(this, mPropertyPointBY, bounds.bottom),

                ObjectAnimator.ofFloat(this, mPropertyPointCX, bounds.right),
                ObjectAnimator.ofFloat(this, mPropertyPointCY, bounds.centerX()/2),

                ObjectAnimator.ofFloat(this, mPropertyPointDX, bounds.centerX()),
                ObjectAnimator.ofFloat(this, mPropertyPointDY, bounds.bottom),

                ObjectAnimator.ofFloat(this, mRotationProperty, 0f, 1f),
                ObjectAnimator.ofObject(this, mLineColorProperty, argbEvaluator, colorClosed),
                ObjectAnimator.ofObject(this, mBackgroundColorProperty, argbEvaluator, Color.WHITE)
        );
        set.setDuration(ANIMATION_DURATION);
        set.setInterpolator(ANIMATION_INTERPOLATOR);
        set.start();
    }

    @SuppressWarnings("unchecked")
    public void animatePlus() {
        AnimatorSet set = new AnimatorSet();
        set.playTogether(
                ObjectAnimator.ofFloat(this, mPropertyPointAX, bounds.left),
                ObjectAnimator.ofFloat(this, mPropertyPointAY, bounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointBX, bounds.right),
                ObjectAnimator.ofFloat(this, mPropertyPointBY, bounds.centerY()),

                ObjectAnimator.ofFloat(this, mPropertyPointCX, bounds.centerX()),
                ObjectAnimator.ofFloat(this, mPropertyPointCY, bounds.top),

                ObjectAnimator.ofFloat(this, mPropertyPointDX, bounds.centerX()),
                ObjectAnimator.ofFloat(this, mPropertyPointDY, bounds.bottom),

                ObjectAnimator.ofFloat(this, mRotationProperty, 0f, 1f),
                ObjectAnimator.ofObject(this, mLineColorProperty, argbEvaluator, Color.WHITE),
                ObjectAnimator.ofObject(this, mBackgroundColorProperty, argbEvaluator, colorClosed)
        );
        set.setDuration(ANIMATION_DURATION);
        set.setInterpolator(ANIMATION_INTERPOLATOR);
        set.start();
    }

    @Override
    public void setAlpha(int alpha) {}

    @Override
    public void setColorFilter(ColorFilter cf) {}

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    public boolean isOpen() {
        return mode;
    }

    private Property<ActionButtonDrawable, Integer> mBackgroundColorProperty = new Property<ActionButtonDrawable, Integer>(Integer.class, "bg_color") {
        @Override
        public Integer get(ActionButtonDrawable object) {
            return object.backgroundPaint.getColor();
        }

        @Override
        public void set(ActionButtonDrawable object, Integer value) {
            object.backgroundPaint.setColor(value);
        }
    };

    private Property<ActionButtonDrawable, Integer> mLineColorProperty = new Property<ActionButtonDrawable, Integer>(Integer.class, "line_color") {
        @Override
        public Integer get(ActionButtonDrawable object) {
            return object.linePaint.getColor();
        }

        @Override
        public void set(ActionButtonDrawable object, Integer value) {
            object.linePaint.setColor(value);
        }
    };

    private Property<ActionButtonDrawable, Float> mRotationProperty = new Property<ActionButtonDrawable, Float>(Float.class, "rotation") {
        @Override
        public Float get(ActionButtonDrawable object) {
            return object.rotation;
        }

        @Override
        public void set(ActionButtonDrawable object, Float value) {
            object.rotation = value;
        }
    };

    private PointProperty mPropertyPointAX = new XPointProperty(0);
    private PointProperty mPropertyPointAY = new YPointProperty(0);
    private PointProperty mPropertyPointBX = new XPointProperty(1);
    private PointProperty mPropertyPointBY = new YPointProperty(1);
    private PointProperty mPropertyPointCX = new XPointProperty(2);
    private PointProperty mPropertyPointCY = new YPointProperty(2);
    private PointProperty mPropertyPointDX = new XPointProperty(3);
    private PointProperty mPropertyPointDY = new YPointProperty(3);

    private abstract class PointProperty extends Property<ActionButtonDrawable, Float> {

        protected int mPointIndex;

        private PointProperty(int pointIndex) {
            super(Float.class, "point_" + pointIndex);
            mPointIndex = pointIndex;
        }
    }

    private class XPointProperty extends PointProperty {

        private XPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(ActionButtonDrawable object) {
            return object.x(mPointIndex);
        }

        @Override
        public void set(ActionButtonDrawable object, Float value) {
            object.points[object.xPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }

    private class YPointProperty extends PointProperty {

        private YPointProperty(int pointIndex) {
            super(pointIndex);
        }

        @Override
        public Float get(ActionButtonDrawable object) {
            return object.y(mPointIndex);
        }

        @Override
        public void set(ActionButtonDrawable object, Float value) {
            object.points[object.yPosition(mPointIndex)] = value;
            invalidateSelf();
        }
    }
}
