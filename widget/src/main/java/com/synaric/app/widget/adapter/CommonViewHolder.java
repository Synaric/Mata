package com.synaric.app.widget.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.synaric.common.utils.ImageUtils;

/**
 * <br/><br/>Created by Synaric on 2016/11/1 0001.
 */
public class CommonViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> views;
    private View root;
    private Context context;

    public CommonViewHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        views = new SparseArray<>();
        root = itemView;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T fetchView(int viewId) {
        View view = views.get(viewId);
        if(view == null) {
            view = root.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public void setOnClickListener(View.OnClickListener listener) {
        if(root != null) root.setOnClickListener(listener);
    }

    public CommonViewHolder setText(int viewId, CharSequence text) {
        TextView tv = fetchView(viewId);
        tv.setText(text);
        return this;
    }

    /**
     * 按照一定格式，设置TextView的值。
     * @param formatId string id，包含格式信息。
     */
    public CommonViewHolder setFormatText(int viewId, int formatId, Object... args){
        if(viewId <= 0 || formatId <= 0){
            Logger.e("Illegal argument for CommonAdapter.setFormatText(). please check.");
            return this;
        }

        if(args == null || args.length <= 0){
            return this;
        }

        final String text = context.getResources().getString(formatId, args);
        return setText(viewId, text);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView iv = fetchView(viewId);
        iv.setImageResource(resId);
    }

    public void setImagePath(Context context, int viewId, String filePath, int placeHolderId) {
        ImageView iv = fetchView(viewId);
        ImageUtils.loadInto(context, filePath, placeHolderId, iv);
    }

    public void setImageUri(Context context, int viewId, Uri uri) {
        setImageUri(context, viewId, uri, 0);
    }

    public void setImageUri(Context context, int viewId, Uri uri, int placeHolderId) {
        ImageUtils.loadInto(context, uri, placeHolderId, (ImageView) fetchView(viewId));
    }
}
