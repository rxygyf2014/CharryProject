package com.charryteam.charryproject.javabean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by ChunLin on 2015/12/8.
 *
 *最终歌词路径：http://image02.cdn.chrrs.com/7/1/3/102806713.lrc
 *
 * {
 "state": 0,
 "data": {
 "artist_id": 43516,
 "cover_path": "\/N\/Z\/003JgO5e3q3ENZ.jpg",
 "create_time": "1440691200",
 "file_path": "http:\/\/stream03.chrrs.com\/9\/5\/0\/103751950.mp3",
 "has_copyright": 1,
 "is_translated": 0,
 "lang_id": "1",
 "listen_count": "4815492",
 "lrc_path": "\/9\/5\/0\/103751950.lrc",
 "server_host": 3,
 "title": "What Do You Mean?",
 "trans_path": "",
 "listen_time": 0
 }
 }
 */
@Table(name = "TopArtists")
public class PlaySong {
    @Column(column = "_id")
    @Id(column = "_id")
    private  int _id;
    @Column(column = "artist_id")
    private int artist_id;   //作家id
    @Column(column = "cover_path")
    private int cover_path;   //图片路径
    @Column(column = "create_time")
    private String create_time;      //创建时间
    @Column(column = "file_path")
  private int file_path; //文件路径
    @Column(column = "has_copyright")
    private int has_copyright;//版权
    @Column(column = "is_translated")
    private int is_translated;//是否被翻译过
    @Column(column = "lang_id")
    private String lang_id;
    @Column(column = "listen_count")
       private String listen_count;//听取的次数
    @Column(column = "lrc_path")
    private String lrc_path;//歌词路径，
    @Column(column = "server_host")
    private int server_host;
    @Column(column = "title")
    private String title;//歌曲名
    @Column(column = "trans_path")
    private String trans_path;//标题
    @Column(column = "listen_time")
    private int listen_time;//听歌次数

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public int getCover_path() {
        return cover_path;
    }

    public void setCover_path(int cover_path) {
        this.cover_path = cover_path;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getFile_path() {
        return file_path;
    }

    public void setFile_path(int file_path) {
        this.file_path = file_path;
    }

    public int getHas_copyright() {
        return has_copyright;
    }

    public void setHas_copyright(int has_copyright) {
        this.has_copyright = has_copyright;
    }

    public int getIs_translated() {
        return is_translated;
    }

    public void setIs_translated(int is_translated) {
        this.is_translated = is_translated;
    }

    public String getLang_id() {
        return lang_id;
    }

    public void setLang_id(String lang_id) {
        this.lang_id = lang_id;
    }

    public String getListen_count() {
        return listen_count;
    }

    public void setListen_count(String listen_count) {
        this.listen_count = listen_count;
    }

    public String getLrc_path() {
        return lrc_path;
    }

    public void setLrc_path(String lrc_path) {
        this.lrc_path = lrc_path;
    }

    public int getServer_host() {
        return server_host;
    }

    public void setServer_host(int server_host) {
        this.server_host = server_host;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrans_path() {
        return trans_path;
    }

    public void setTrans_path(String trans_path) {
        this.trans_path = trans_path;
    }

    public int getListen_time() {
        return listen_time;
    }

    public void setListen_time(int listen_time) {
        this.listen_time = listen_time;
    }

    public PlaySong() {
    }

    public PlaySong(int _id, int artist_id, int cover_path, String create_time, int file_path, int has_copyright, int is_translated, String lang_id, String listen_count, String lrc_path, int server_host, String title, String trans_path, int listen_time) {
        this._id = _id;
        this.artist_id = artist_id;
        this.cover_path = cover_path;
        this.create_time = create_time;
        this.file_path = file_path;
        this.has_copyright = has_copyright;
        this.is_translated = is_translated;
        this.lang_id = lang_id;
        this.listen_count = listen_count;
        this.lrc_path = lrc_path;
        this.server_host = server_host;
        this.title = title;
        this.trans_path = trans_path;
        this.listen_time = listen_time;
    }
}