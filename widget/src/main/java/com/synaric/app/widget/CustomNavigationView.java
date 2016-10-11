package com.synaric.app.widget;

import android.content.Context;
import android.support.design.widget.NavigationView;
import android.util.AttributeSet;

/**
 * 能够全屏的NavigationView。
 * <br/><br/>Created by Synaric on 2016/10/9 0009.
 */
public class CustomNavigationView extends NavigationView {

    private int mMaxWidth;

    public CustomNavigationView(Context context) {
        super(context);
        init(context);
    }

    public CustomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mMaxWidth = ViewUtil.getDisplayMetrics(context).widthPixels;
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        //使得控件宽度充满全屏
        switch (MeasureSpec.getMode(widthSpec)) {
            case MeasureSpec.EXACTLY:
                widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.AT_MOST:
                widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
                break;
            case MeasureSpec.UNSPECIFIED:
                widthSpec = MeasureSpec.makeMeasureSpec(mMaxWidth, MeasureSpec.EXACTLY);
                break;
        }
        super.onMeasure(widthSpec, heightSpec);
    }
}
