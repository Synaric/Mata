package com.synaric.app.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.synaric.app.rxmodel.utils.RxUtils;
import com.synaric.app.widget.adapter.CommonAdapter;
import com.synaric.app.widget.other.BaseDiffCallBack;
import com.synaric.common.utils.SystemUtils;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import rx.functions.Action1;

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
     * 开启线程比较数据变动，并将更新异步通知adapter。
     */
    public static <T> void calculateDiff(
            final CommonAdapter adapter,
            final List<T> oldData,
            final List<T> newData,
            final BaseDiffCallBack.OnItemCompare<T> onItemCompare) {

        RxUtils.makeModelObservable(new Callable<DiffUtil.DiffResult>() {
            @Override
            public DiffUtil.DiffResult call() throws Exception {
                return DiffUtil.calculateDiff(new BaseDiffCallBack(oldData, newData) {
                    @Override
                    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                        return Objects.equals(
                                onItemCompare.getPrimaryKey(oldData.get(oldItemPosition)),
                                onItemCompare.getPrimaryKey(newData.get(newItemPosition))
                        );
                    }
                });
            }
        }).subscribe(new Action1<DiffUtil.DiffResult>() {
            @SuppressWarnings("unchecked")
            @Override
            public void call(DiffUtil.DiffResult diffResult) {
                adapter.setData(newData);
                diffResult.dispatchUpdatesTo(adapter);
            }
        });
    }

    /**
     * 创建纵向列表LayoutManager，并解决偶发崩溃问题。
     */
    public static RecyclerView.LayoutManager defaultLayoutManager(Context context) {
        return new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
                try {
                    super.onLayoutChildren(recycler, state);
                } catch (Exception e) {
                    //RecyclerView的一个BUG，偶然性发生的崩溃
                }
            }
        };
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
     * 适配drawableLeft大小，单位dp。
     */
    public static void resizeDrawableLeft(Context context, TextView tv, int id, int size) {
        resizeDrawableLeft(context, tv, id, size, size);
    }

    /**
     * 适配drawableLeft大小，单位dp。
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
