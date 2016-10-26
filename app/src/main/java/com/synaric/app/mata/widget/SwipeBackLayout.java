package com.synaric.app.mata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.synaric.app.mata.base.BaseFragment;

/**
 * <br/><br/>Created by Synaric on 2016/10/14 0014.
 */
public class SwipeBackLayout extends me.yokeyword.fragmentation.SwipeBackLayout {

    public SwipeBackLayout(Context context) {
        super(context);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwipeBackLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachToFragment(BaseFragment swipeBackFragment, View view) {
        addView(view);
        setFragment(swipeBackFragment, view);
    }
}
