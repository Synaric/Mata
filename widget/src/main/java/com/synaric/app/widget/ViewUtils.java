package com.synaric.app.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.synaric.common.utils.SystemUtils;

/**
 * <br/><br/>Created by Synaric on 2016/10/9 0009.
 */
public class ViewUtils {

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

    /**
     * 适配drawableLeft大小。
     */
    public static void resizeDrawableLeft(Context context, TextView tv, int id, int size) {
        resizeDrawableLeft(context, tv, id, size, size);
    }

    /**
     * 适配drawableLeft大小。
     */
    @SuppressWarnings("deprecation")
    public static void resizeDrawableLeft(Context context, TextView tv, int id, int width, int height) {
        Drawable drawable = context.getResources().getDrawable(id);
        if (drawable != null) {
            drawable.setBounds(
                    0, 0, SystemUtils.dp2px(context, width), SystemUtils.dp2px(context, height)
            );
            tv.setCompoundDrawables(drawable, null, null, null);
        }
    }
}
