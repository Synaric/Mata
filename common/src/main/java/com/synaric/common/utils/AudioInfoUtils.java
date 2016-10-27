package com.synaric.common.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import com.synaric.common.entity.AudioInfo;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * 本地歌曲扫描工具类。
 * <br/><br/>Created by Synaric on 2016/10/21 0021.
 */
public class AudioInfoUtils {

    /**
     * 扫描所有本地音乐信息。
     */
    public static List<AudioInfo> findAllInExternalDir(Context context, ScanConfig config){
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

                //歌曲大小和时长要符合要求
                if(config.validate(info)) audioInfoList.add(info);

            } catch (IllegalArgumentException e) {
                //不处理，继续扫描
            }
        }
        cursor.close();

        return audioInfoList;
    }

    /**
     * 根据音乐文件路径获取专辑封面缩略图。
     * @param filePath 音乐文件路径。
     * @return 封面缩略图。
     */
    @Deprecated
    public static Bitmap getCoverByPath(String filePath){
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        byte[] embedPic = retriever.getEmbeddedPicture();

        try {

            retriever.setDataSource(filePath);
            bitmap = BitmapFactory.decodeByteArray(embedPic, 0, embedPic.length);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    /**
     * 根据歌曲id获取专辑封面缩略图。
     * @return 封面图。
     */
    public static Bitmap getCoverFromFile(Context context, long id){
        Bitmap cover = null;

        if(id < 0){
            throw new IllegalArgumentException("id must > 0. id = " + id);
        }

        try {

            Uri uri = Uri.parse("content://media/external/audio/media/" + id + "/albumart");
            ParcelFileDescriptor pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if(pfd != null){
                FileDescriptor fd = pfd.getFileDescriptor();
                cover = BitmapFactory.decodeFileDescriptor(fd);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cover;
    }

    public static class ScanConfig {

        //歌曲最少时长
        public int minDuration;

        //歌曲最小大小
        public long minSize;

        public boolean validate(AudioInfo info) {
            return validate(info.getDuration(), info.getSize());
        }

        public boolean validate(int duration, long size) {
            return duration >= minDuration && size >= minSize;
        }
    }
}
