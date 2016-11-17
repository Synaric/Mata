package com.synaric.app.widget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * <br/><br/>Created by Synaric on 2016/10/24 0024.
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {

    protected List<T> data;

    protected LayoutInflater inflater;

    protected Context context;

    private int resId;

    public CommonAdapter(Context context, List<T> data, int resId) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.resId = resId;
    }

    @Override
    public CommonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(resId, parent, false);
        return new CommonViewHolder(context, view);
    }

    @Override
    public void onBindViewHolder(CommonViewHolder holder, int position) {
        onBindViewHolder(holder, data.get(position));
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = new ArrayList<>(data);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract void onBindViewHolder(CommonViewHolder holder, T entity);
}
