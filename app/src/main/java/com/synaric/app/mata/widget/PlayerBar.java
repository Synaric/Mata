package com.synaric.app.mata.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synaric.app.mata.R;
import com.synaric.app.widget.SlideUpLayout;

/**
 * 底部播放条。
 * 这个控件需要和{@link SlideUpLayout}同步状态，即调用{@link #syncState(SlideUpLayout)}。
 * <br/><br/>Created by Synaric on 2016/10/18 0018.
 */
public class PlayerBar extends LinearLayout implements Target {

    public PlayerBar(Context context) {
        this(context, null);
    }

    public PlayerBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlayerBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 监听上拉播放器界面的滑动位置，并相应进行动画。
     * @param slideUpLayout 需要监听的slideUpLayout。
     */
    public void syncState(SlideUpLayout slideUpLayout) {

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        View.inflate(context, R.layout.player_above, this);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        setBackground(new BitmapDrawable(getResources(), bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        //不作处理，保留上次背景
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setBackgroundResource(R.drawable.default_cover_radioplayerbar);
    }
}
