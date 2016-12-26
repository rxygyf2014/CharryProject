package com.charryteam.charryproject.javabean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by xiabaikui on 2015/12/7.
 */
@Table(name = "TopArtists")
public class TopArtists {

    @Column(column = "id")
    @Id(column = "_id")
    private int id;

    @Column(column = "artist_id")
    private int artist_id;

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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public String toString() {
        return "TopArtists{" +
                "id=" + id +
                ", artist_id=" + artist_id +
                ", artist='" + artist + '\'' +
                '}';
    }
}
