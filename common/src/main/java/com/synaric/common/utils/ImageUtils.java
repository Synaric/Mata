package com.synaric.common.utils;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.synaric.app.common.R;

/**
 * <br/><br/>Created by Synaric on 2016/11/9 0009.
 */
public class ImageUtils {

    /**
     * 不将加载结果显示在ImageView上，而是回调返回。
     */
    public static void fetch(Context context, Uri uri, int placeHolderId, Callback callback) {
        Picasso
            .with(context)
            .load(uri)
            .placeholder(placeHolderId)
            .fetch(callback);
    }

    /**
     * 异步加载图片并显示到一个ImageView上。
     */
    public static void loadInto(Context context, Uri uri, int placeHolderId, ImageView iv) {
        Picasso
            .with(context)
            .load(uri)
            .placeholder(placeHolderId <= 0 ? R.color.img_error : placeHolderId)
            .into(iv);
    }

    /**
     * 异步加载图片并显示到一个Target上。
     */
    public static void loadInto(Context context, Uri uri, int placeHolderId, Target target) {
        Picasso
            .with(context)
            .load(uri)
            .placeholder(placeHolderId <= 0 ? R.color.img_error : placeHolderId)
            .into(target);
    }
}
