package com.synaric.app.mata.module.local;

import android.content.Context;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.app.rxmodel.DbModel;
import com.synaric.common.CommonUtilsConfiguration;
import com.synaric.common.entity.AudioInfo;
import com.synaric.common.utils.AudioInfoUtils;
import com.synaric.mvp.View;

import rx.Observable;

/**
 * <br/><br/>Created by Synaric on 2016/10/27 0027.
 */
public class LocalAudiosPresenter extends BasePresenter {

    private DbModel<AudioInfo> audioInfoDbModel = new DbModel<AudioInfo>(CommonUtilsConfiguration.rxModel) {
        @Override
        public String bindID(AudioInfo info) {
            return String.valueOf(info.getId());
        }
    };

    /**
     * 读取数据库保存的本地歌曲信息，
     */
    public void loadLocalAudios(View<?> view) {
        accessData(view, new SimpleApiCallback<>(audioInfoDbModel.queryAll()));
    }

    /**
     * 扫描本地歌曲并将扫描结果更新到数据库。
     */
    public void saveLocalAudios(View<?> view, Context context) {
        final Observable<Boolean> result = AudioInfoUtils
                .findAllInExternalDir(context)
                .map(audioInfo -> audioInfoDbModel.syncSaveAll(audioInfo));
        accessData(view, new SimpleApiCallback<>(result));
    }
}
