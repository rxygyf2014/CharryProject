package com.charryteam.charryproject.javabean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by xiabaikui on 2015/12/6.
 */
@Table(name = "discoverBestList")
public class DiscoverBestList {

    //映射到数据库的主键名
    @Column(column = "id")
    @Id(column = "_id")
    private int id;

    @Column(column = "best_id")
    private int best_id;

    @Column(column = "content")
    private String content;

    @Column(column = "img")
    private String img;

    @Column(column = "listen_count")
    private int listen_count;

    @Column(column = "title")
    private String title;


    public int getBest_id() {
        return best_id;
    }

    public void setBest_id(int best_id) {
        this.best_id = best_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getListen_count() {
        return listen_count;
    }

    public void setListen_count(int listen_count) {
        this.listen_count = listen_count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DiscoverBestList{" +
                "id=" + id +
                ", best_id=" + best_id +
                ", content='" + content + '\'' +
                ", img='" + img + '\'' +
                ", listen_count=" + listen_count +
                ", title='" + title + '\'' +
                '}';
    }
}
