package com.charryteam.charryproject.javabean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by xiabaikui on 2015/12/7.
 */
@Table(name = "TopMusic")
public class TopMusics {

    @Column(column = "id")
    @Id(column = "_id")
    private int id;

    @Column(column = "artist_id")

    private int artist_id;

    @Column(column = "music_id")
    private String music_id;

    @Column(column = "hascopyright")
    private int hascopyright;

    @Column(column = "music_name")
    private String music_name;

    @Column(column = "cover_path")
    private String cover_path;

    @Column(column = "artist")
    private String artist;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getMusic_id() {
        return music_id;
    }

    public void setMusic_id(String music_id) {
        this.music_id = music_id;
    }

    public int getHascopyright() {
        return hascopyright;
    }

    public void setHascopyright(int hascopyright) {
        this.hascopyright = hascopyright;
    }

    public String getMusic_name() {
        return music_name;
    }

    public void setMusic_name(String music_name) {
        this.music_name = music_name;
    }

    public String getCover_path() {
        return cover_path;
    }

    public void setCover_path(String cover_path) {
        this.cover_path = cover_path;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "TopMusics{" +
                "id=" + id +
                ", artist_id=" + artist_id +
                ", music_id=" + music_id +
                ", hascopyright=" + hascopyright +
                ", music_name='" + music_name + '\'' +
                ", cover_path='" + cover_path + '\'' +
                ", artist='" + artist + '\'' +
                '}';
    }
}
