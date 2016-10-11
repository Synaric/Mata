package com.synaric.app.widget;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * <br/><br/>Created by Synaric on 2016/10/9 0009.
 */
public class ViewUtil {

    /**
     * 获取屏幕宽高。
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        return displayMetrics;
    }

    /**
     * 创建一个描述TextView。
     */
    public static View createDescriptionView(Context context, String description) {
        TextView tv = new TextView(context);
        tv.setText(description);
        return tv;
    }
}
