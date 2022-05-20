package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model;

import java.io.Serializable;

public class Friend implements Serializable {
    String user,FriendOf;
    int img;
    long time;


    public Friend() {
    }

    public Friend(String user, long time, String friendOf, int img) {
        this.user = user;
        this.time = time;
        FriendOf = friendOf;
        this.img = img;
    }

    public Friend(String user, int img, long time) {
        this.user = user;
        this.img = img;
        this.time = time;
    }

    public String getFriendOf() {
        return FriendOf;
    }

    public void setFriendOf(String friendOf) {
        FriendOf = friendOf;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
