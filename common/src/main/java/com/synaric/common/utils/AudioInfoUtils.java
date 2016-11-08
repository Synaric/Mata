package com.synaric.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.orhanobut.logger.Logger;
import com.synaric.app.rxmodel.utils.RxUtils;
import com.synaric.common.entity.AudioInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;

/**
 * 本地歌曲扫描工具类。
 * <br/><br/>Created by Synaric on 2016/10/21 0021.
 */
public class AudioInfoUtils {

    /**
     * 扫描所有本地音乐信息。
     */
    public static Observable<List<AudioInfo>> findAllInExternalDir(final Context context) {
        return RxUtils.makeModelObservable(new Callable<List<AudioInfo>>() {
            @Override
            public List<AudioInfo> call() throws Exception {
                return findAllInternal(context);
            }
        });
    }

    /**
     * 扫描所有本地音乐信息。
     */
    private static List<AudioInfo> findAllInternal(Context context) {
        return findAllInternal(context, new ScanConfig());
    }

    /**
     * 扫描所有本地音乐信息。
     */
    private static List<AudioInfo> findAllInternal(Context context, ScanConfig config){
        List<AudioInfo> audioInfoList = new ArrayList<>();

        ContentResolver resolver = context.getContentResolver();
        String[] projection = {
                MediaStore.Audio.AudioColumns._ID,      //id
                MediaStore.Audio.AudioColumns.TITLE,    //歌曲名称
                MediaStore.Audio.AudioColumns.ARTIST,   //歌手名
                MediaStore.Audio.AudioColumns.ALBUM,    //专辑
                MediaStore.Audio.AudioColumns.DURATION, //时长
                MediaStore.Audio.AudioColumns.COMPOSER, //作曲
                MediaStore.Audio.AudioColumns.DATA,     //文件路径
                MediaStore.Audio.AudioColumns.SIZE,     //文件大小
                MediaStore.Audio.Media.DATE_MODIFIED,   //文件修改时间
                MediaStore.Audio.Media.TITLE_KEY        //歌曲标识
        };
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if(cursor == null){
            return audioInfoList;
        }

        while(cursor.moveToNext()){
            AudioInfo info = new AudioInfo();

            try {

                info.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
                info.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
                info.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
                info.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
                info.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
                info.setComposer(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER)));
                info.setData(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
                info.setSize(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
                info.setModified(cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_MODIFIED)));
                info.setTitleKey(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE_KEY)));

                //歌曲大小和时长要符合要求
                if(config.validate(info)) audioInfoList.add(info);

            } catch (IllegalArgumentException e) {
                //不处理，继续扫描
            }
        }
        cursor.close();

        //按照修改时间排序
        Collections.sort(audioInfoList, new Comparator<AudioInfo>() {
            @Override
            public int compare(AudioInfo o1, AudioInfo o2) {
                return o1.getModified() - o2.getModified() > 0 ? -1 : 1;
            }
        });
        Logger.d("finish scanning. total files: " + audioInfoList.size());

        return audioInfoList;
    }

    /**
     * 根据歌曲id获取专辑封面缩略图Uri。
     * @return 封面图。
     */
    public static Uri getUriFromId(long id){
        if(id < 0){
            throw new IllegalArgumentException("id must > 0. id = " + id);
        }
        return Uri.parse("content://media/external/audio/media/" + id + "/albumart");
    }

    public static class ScanConfig {

        //歌曲最少时长
        public int minDuration = 60000;

        //歌曲最小大小
        public long minSize = 500000;

        public boolean validate(AudioInfo info) {
            return validate(info.getDuration(), info.getSize());
        }

        public boolean validate(int duration, long size) {
            return duration >= minDuration && size >= minSize;
        }
    }
}
