package com.synaric.app.widget.other;

import android.support.v7.util.DiffUtil;

import java.util.List;
import java.util.Objects;

public abstract class BaseDiffCallBack extends DiffUtil.Callback {

    private List<?> oldData;
    private List<?> newData;

    public BaseDiffCallBack(List<?> oldData, List<?> newData) {
        this.oldData = oldData;
        this.newData = newData;
    }

    @Override
    public int getOldListSize() {
        return oldData == null ? 0 : oldData.size();
    }

    @Override
    public int getNewListSize() {
        return newData == null ? 0 : newData.size();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return Objects.equals(oldData.get(oldItemPosition), newData.get(newItemPosition));
    }

    public interface OnItemCompare<T> {

        Object getPrimaryKey(T item);
    }
}