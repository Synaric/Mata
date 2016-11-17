package com.synaric.common.entity;

/**
 * 描述艺术家信息的封装类。
 * <br/><br/>Created by Synaric on 2016/11/17 0017.
 */
public class ArtistInfo {

    private String id;

    private String artist;

    private String artistKey;

    private int albums;

    private int tracks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistKey() {
        return artistKey;
    }

    public void setArtistKey(String artistKey) {
        this.artistKey = artistKey;
    }

    public int getAlbums() {
        return albums;
    }

    public void setAlbums(int albums) {
        this.albums = albums;
    }

    public int getTracks() {
        return tracks;
    }

    public void setTracks(int tracks) {
        this.tracks = tracks;
    }
}
