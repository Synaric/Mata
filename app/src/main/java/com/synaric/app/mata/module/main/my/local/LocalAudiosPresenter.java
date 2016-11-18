package com.synaric.app.mata.module.main.my.local;

import android.content.Context;

import com.synaric.app.mata.mvp.BasePresenter;
import com.synaric.common.entity.AlbumInfo;
import com.synaric.common.entity.ArtistInfo;
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
     * 扫描所有本地歌曲。
     */
    public void scanLocalAudios(View<?> view, Context context) {
        Observable<List<AudioInfo>> result = AudioInfoUtils.findAllAudioInfo(context);
        accessData(view, new SimpleApiCallback<>(result));
    }

    /**
     * 扫描本地所有艺术家。
     */
    public void scanLocalArtists(View<?> view, Context context) {
        Observable<List<ArtistInfo>> result = AudioInfoUtils.findAllArtistInfo(context);
        accessData(view, new SimpleApiCallback<>(result));
    }

    /**
     * 扫描本地所有专辑信息。
     */
    public void scanLocalAlbums(View<?> view, Context context) {
        Observable<List<AlbumInfo>> result = AudioInfoUtils.findAllAlbumInfo(context);
        accessData(view, new SimpleApiCallback<>(result));
    }
}
