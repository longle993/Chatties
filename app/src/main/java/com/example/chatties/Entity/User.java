package com.example.chatties.Entity;

import java.util.ArrayList;

public class User {
    String id;
    String name;
    String email;
    String password;
    boolean status;
    String lasttimeonl;
    String avatar;
    String birthday;
    ArrayList<String> listfriends;
    ArrayList<String> friend_request;
    ArrayList<String> friend_send_request;

    public User(String id, String name, String email, String password, boolean status, String lasttimeonl, String avatar, String birthday, ArrayList<String> listfriends, ArrayList<String> friend_request, ArrayList<String> friend_send_request) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.lasttimeonl = lasttimeonl;
        this.avatar = avatar;
        this.birthday = birthday;
        this.listfriends = listfriends;
        this.friend_request = friend_request;
        this.friend_send_request = friend_send_request;
    }

    public  User(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getLasttimeonl() {
        return lasttimeonl;
    }

    public void setLasttimeonl(String lasttimeonl) {
        this.lasttimeonl = lasttimeonl;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public ArrayList<String> getListfriends() {
        return listfriends;
    }

    public void setListfriends(ArrayList<String> listfriends) {
        this.listfriends = listfriends;
    }

    public ArrayList<String> getFriend_request() {
        return friend_request;
    }

    public void setFriend_request(ArrayList<String> friend_request) {
        this.friend_request = friend_request;
    }

    public ArrayList<String> getFriend_send_request() {
        return friend_send_request;
    }

    public void setFriend_send_request(ArrayList<String> friend_send_request) {
        this.friend_send_request = friend_send_request;
    }
}
