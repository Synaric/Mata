package com.synaric.app.mata.module.main.my.local;

import android.content.Context;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.common.entity.AudioInfo;
import com.synaric.common.utils.AudioInfoUtils;
import com.synaric.mvp.View;

import java.util.List;

import rx.Observable;

/**
 * <br/><br/>Created by Synaric on 2016/10/27 0027.
 */
public class LocalAudiosPresenter extends BasePresenter {

    /**
     * 扫描本地歌曲。
     */
    public void scanLocalAudios(View<?> view, Context context) {
        Observable<List<AudioInfo>> result = AudioInfoUtils.findAllInExternalDir(context);
        accessData(view, new SimpleApiCallback<>(result));
    }
}
