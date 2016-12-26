package com.charryteam.charryproject.javabean;

/**
 * Created by Administrator on 15-12-9.
 */
public class Crossover_musicInfo {
    private String cover_path;//播放音乐的图片路径
    private String file_path;//MP3音乐的路径
    private String lrc_path;//歌词
    private String music_name;//歌名子
    private String artist;//歌的作曲人

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getLrc_path() {
        return lrc_path;
    }

    public void setLrc_path(String lrc_path) {
        this.lrc_path = lrc_path;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }
}
