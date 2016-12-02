package com.synaric.app.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.common.utils.ExtTextUtils;
import com.synaric.common.utils.OrderedItem;

import java.util.List;

/**
 * 展示字母索引。
 * <br/><br/>Created by Synaric on 2016/11/30 0030.
 */
public class AlphabetIndexView extends View {

    /**
     * 默认字体大小。
     */
    public static int TEXT_SIZE_DEFAULT = 30;

    public static long ANIMATION_INTERNAL = 3000;

    /**
     * 首字母索引集合。
     */
    public final static String[] INDEX = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };

    /**
     * 正常状态字体颜色。
     */
    private int textColor;

    /**
     * 选中状态字体颜色。
     */
    private int textColorSelected;

    /**
     * 当前选中的索引。
     */
    private int selectedPosition = 0;

    /**
     * 索引选择状态监听。
     */
    private OnIndexSelectedListener listener;

    private Paint paint;

    public AlphabetIndexView(Context context) {
        this(context, null);
    }

    public AlphabetIndexView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AlphabetIndexView);
        ColorStateList colors = typedArray.getColorStateList(R.styleable.AlphabetIndexView_aiv_text_color);
        if (colors == null) {
            textColor = Color.LTGRAY;
            textColorSelected = Color.BLUE;
        } else {
            textColor = colors.getDefaultColor();
            textColorSelected = colors.getColorForState(View.SELECTED_STATE_SET, Color.BLUE);
        }
        int textSize = typedArray.getDimensionPixelSize(
                R.styleable.AlphabetIndexView_aiv_text_size, TEXT_SIZE_DEFAULT);

        paint = new Paint();
        paint.setTypeface(Typeface.DEFAULT);
        paint.setAntiAlias(true);
        paint.setTextSize(textSize);

        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int lineHeight = height / INDEX.length;

        for (int i = 0; i < INDEX.length; ++i) {
            if (i == selectedPosition) {
                paint.setColor(textColorSelected);
            } else {
                paint.setColor(textColor);
            }
            float x = width / 2 - paint.measureText(INDEX[i]) / 2;
            float y = lineHeight * (i + 1);
            canvas.drawText(INDEX[i], x, y, paint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldPosition = selectedPosition;
        int newPosition = (int) (y / getHeight() * INDEX.length);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                show();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hide();
                    }
                }, ANIMATION_INTERNAL);
                if (getAlpha() < 0.8f) break;
                if (oldPosition != newPosition && newPosition >= 0 && newPosition < INDEX.length) {
                    select(newPosition);
                }
                break;
        }

        return true;
    }

    /**
     * 展示索引。
     */
    public void show() {
        ObjectAnimator.ofFloat(this, "alpha", 1).setDuration(200).start();
    }

    /**
     * 隐藏索引。
     */
    public void hide() {
        ObjectAnimator.ofFloat(this, "alpha", 0).setDuration(800).start();
    }

    /**
     * 与一个{@link CompoundRecyclerView}同步状态。点击字母索引后，列表会滚动到指定字母索引的位置。
     * 如果列表中没有以指定字母索引开头的数据，则什么也不会发生。
     * 当列表滚动结束，列表第一个可见项的首字母将会回显到字母索引上。
     * 一个同步的{@link CompoundRecyclerView}不会对数据按首字母排序，因此这个列表必须约定是已排序的。
     * @param compoundRecyclerView 需要同步的列表。
     * @see OrderedItem
     */
    public void syncState(final CompoundRecyclerView compoundRecyclerView) {
        setOnIndexSelectedListener(new OnIndexSelectedListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void onSelected(String index, int position) {
                CommonAdapter adapter = compoundRecyclerView.getAdapter();
                if (adapter == null) return;
                List<OrderedItem> data = (List<OrderedItem>) adapter.getData();
                int newPosition = ExtTextUtils.findFirstStartsWith(data, index);
                Logger.i("newPosition = " + newPosition);
                if (newPosition >= 0) {

                    compoundRecyclerView.smoothScrollToPosition(newPosition);
                }
            }
        });

//        compoundRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                OrderedItem item = (OrderedItem) compoundRecyclerView.findFirstVisibleItem();
//                String indexTag = item.getIndexTag();
//                if (TextUtils.isEmpty(indexTag) || !ExtTextUtils.isLetter(indexTag.charAt(0))) {
//                    select(INDEX.length - 1);
//                } else {
//                    char c = indexTag.toUpperCase().charAt(0);
//                    select(c - 65);
//                }
//            }
//        });
    }

    /**
     * 相当于手动点击了指定位置的索引。
     * @param position 取值范围[0, 26]，0代表'A'，26代表'#'（即一切非字母字符）。
     */
    public void select(int position) {
        if (position < 0) position = 0;
        if (position > 26) position = 26;
        selectInternal(position);
    }

    private void selectInternal(int newPosition) {
        if (newPosition != selectedPosition) {
            selectedPosition = newPosition;
            if (listener != null) listener.onSelected(INDEX[newPosition], newPosition);
            invalidate();
        }
    }

    public void setOnIndexSelectedListener(OnIndexSelectedListener listener) {
        this.listener = listener;
    }

    /**
     * 索引选择状态监听。
     */
    public interface OnIndexSelectedListener {

        /**
         * 当索引被用户点击时触发。
         * @param index 用户点击的索引。
         * @param position 用户点击的索引在首字母索引集合中的位置，取值[0, 26]。
         */
        void onSelected(String index, int position);
    }
}
