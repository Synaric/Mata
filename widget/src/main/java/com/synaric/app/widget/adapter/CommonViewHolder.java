package com.synaric.app.widget.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * <br/><br/>Created by Synaric on 2016/11/1 0001.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private View root;

    public CommonViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
        root = itemView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T get(int id) {
        View view = views.get(id);
        if(view == null) {
            view = root.findViewById(id);
            views.put(id, view);
        }

        return (T) view;
    }
}
