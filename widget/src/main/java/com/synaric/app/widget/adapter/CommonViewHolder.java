package com.synaric.app.widget.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synaric.app.widget.R;

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

    public void setText(int viewId, CharSequence text) {
        TextView tv = fetchView(viewId);
        tv.setText(text);
    }

    public void setImageResource(int viewId, int resId) {
        ImageView iv = fetchView(viewId);
        iv.setImageResource(resId);
    }

    public void setImageUri(Context context, int viewId, Uri uri) {
        setImageUri(context, viewId, uri, 0);
    }

    public void setImageUri(Context context, int viewId, Uri uri, int placeHolderId) {
        Picasso
            .with(context)
            .load(uri)
            .placeholder(placeHolderId <= 0 ? R.color.img_error : placeHolderId)
            .into((ImageView) fetchView(viewId));
    }
}
