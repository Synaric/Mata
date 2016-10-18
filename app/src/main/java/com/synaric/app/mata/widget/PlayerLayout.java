package com.synaric.app.mata.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.synaric.app.mata.R;

/**
 * <br/><br/>Created by Synaric on 2016/10/18 0018.
 */
public class PlayerLayout extends LinearLayout {

    public PlayerLayout(Context context) {
        this(context, null);
    }

    public PlayerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.player_above, this);
    }
}
