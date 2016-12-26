package com.charryteam.charryproject.javabean;

/**
 * Created by Administrator on 15-12-6.
 */
public class Crossover_person {
    private String user_uid;  //用户的uid
    private String user_nick; //用户的昵称
    private String user_head;//用户的头像
    private  boolean isZan; //用户是否点赞


    public String getUser_head() {
        return user_head;
    }

    public void setUser_head(String user_head) {
        this.user_head = user_head;
    }

    public boolean isZan() {
        return isZan;
    }

    public void setIsZan(boolean isZan) {
        this.isZan = isZan;
    }

    public String getUser_uid() {
        return user_uid;
    }

    public void setUser_uid(String user_uid) {
        this.user_uid = user_uid;
    }

    public String getUser_nick() {
        return user_nick;
    }

    public void setUser_nick(String user_nick) {
        this.user_nick = user_nick;
    }


}
