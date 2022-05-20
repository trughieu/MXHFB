package com.example.nhom04_nguyentrunghieu_caotuankiet_ngothithanhngan.model;

import java.io.Serializable;

public class Notification implements Serializable {

    int profile;
    String name, notification;
    long time;

    public Notification() {
    }

    public Notification(int profile, String name, String notification, long time) {
        this.profile = profile;
        this.name = name;
        this.notification = notification;
        this.time = time;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
