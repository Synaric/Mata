package com.synaric.common.entity;

import com.synaric.common.utils.OrderedItem;

/**
 * 描述专辑信息的封装类。
 * <br/><br/>Created by Synaric on 2016/11/18 0018.
 */
@SuppressWarnings("unused")
public class AlbumInfo extends OrderedItem {

    private String album;

    private String albumKey;

    private String albumArt;

    private String artist;

    private long firstYear;

    private int songs;

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumKey() {
        return albumKey;
    }

    public void setAlbumKey(String albumKey) {
        this.albumKey = albumKey;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getFirstYear() {
        return firstYear;
    }

    public void setFirstYear(long firstYear) {
        this.firstYear = firstYear;
    }

    public int getSongs() {
        return songs;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }

    @Override
    public String orderBy() {
        return album;
    }
}
