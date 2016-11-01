package com.synaric.app.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.synaric.app.widget.adapter.CommonAdapter;

import java.util.List;

/**
 * 纵向列表视图。
 * <ul>
 *     <li>支持下拉刷新，上拉加载更多。</li>
 *     <li>支持显示头布局headerView，尾布局footerView。</li>
 *     <li>支持显示空布局（加载的数据为空）emptyView，失败布局（加载数据失败）errorView。</li>
 *     <li>支持列表局部更新，无需使用RecyclerView的刷新方法。</li>
 * </ul>
 *
 * <br/><br/>Created by Synaric on 2016/10/31 0031.
 */
public class CompoundRecyclerView extends FrameLayout {

    private View emptyView;
    private View errorView;
    private RecyclerView contentView;

    private CommonAdapter adapter;

    public CompoundRecyclerView(Context context) {
        this(context, null);
    }

    public CompoundRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompoundRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompoundRecyclerView);
        int emptyId = typedArray.getResourceId(R.styleable.CompoundRecyclerView_empty_view, 0);
        int errorId = typedArray.getResourceId(R.styleable.CompoundRecyclerView_error_view, 0);
        if(emptyId != 0) {
            emptyView = findViewById(emptyId);
        }
        if(errorId != 0) {
            errorView = findViewById(errorId);
        }

        FrameLayout.LayoutParams params = new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        contentView = new RecyclerView(context);
        contentView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        typedArray.recycle();

        addView(contentView, params);
    }

    public void toggleEmpty() {
        if(emptyView != null) emptyView.setVisibility(VISIBLE);
        if(errorView != null) errorView.setVisibility(GONE);
        contentView.setVisibility(GONE);
    }

    public void toggleError() {
        if(emptyView != null) emptyView.setVisibility(GONE);
        if(errorView != null) errorView.setVisibility(VISIBLE);
        contentView.setVisibility(GONE);
    }

    public void toggleContent() {
        if(emptyView != null) emptyView.setVisibility(GONE);
        if(errorView != null) errorView.setVisibility(GONE);
        contentView.setVisibility(VISIBLE);
    }

    public void setAdapter(CommonAdapter<?> adapter) {
        contentView.setAdapter(adapter);
        this.adapter = adapter;
        notifyDataSetChanged(null, null, false, null);
    }

    /**
     * 开启线程比较数据，并将更新异步通知adapter。
     * 数据为空或者数据加载失败，会显示相应的提示视图（如果有的话）。
     * 默认非强制刷新，参考{@link #notifyDataSetChanged(List, List, boolean, BaseDiffCallBack.OnItemCompare)}。
     * @param oldData 旧数据。
     * @param newData 新数据。
     * @param onItemCompare 比较规则。
     */
    public <T> void  notifyDataSetChanged(
            final List<T> oldData,
            final List<T> newData,
            BaseDiffCallBack.OnItemCompare<T> onItemCompare) {
        notifyDataSetChanged(oldData, newData, false, onItemCompare);
    }

    /**
     * 开启线程比较数据，并将更新异步通知adapter。
     * 数据为空或者数据加载失败，会显示相应的提示视图（如果有的话）。
     * @param oldData 旧数据。
     * @param newData 新数据。
     * @param forceUpdate 是否强制刷新数据。开启强制刷新时，如果newData为空，则显示emptyView（如果有
     *                    的话）;反之，显示contentView，并保留上一次的数据，并且不刷新adapter。
     * @param onItemCompare 比较规则。
     */
    public <T> void  notifyDataSetChanged(
            final List<T> oldData,
            final List<T> newData,
            boolean forceUpdate,
            BaseDiffCallBack.OnItemCompare<T> onItemCompare) {
        if(oldData == null && newData == null) {
            toggleEmpty();
            return;
        }

        final int itemCount = newData == null ? 0 : newData.size();
        if(itemCount <= 0 && forceUpdate) {
            toggleEmpty();
        } else {
            toggleContent();
        }

        if(itemCount > 0 || forceUpdate)
            ViewUtils.calculateDiff(adapter, oldData, newData, onItemCompare);
    }
}
