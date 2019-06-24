package com.example.asus.pathapp;

import cn.bmob.v3.BmobObject;

public class Artical extends BmobObject {
    private  String avatarId;
    private  String accountName;
    private  String description;
    private  String articalPicId;
    private  String title;
    private  String detail;
    private String username;

    public Artical() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    //构造方法
    public Artical(String avatarId, String accountName, String description,
                   String articalPicId, String title, String detail) {
        this.avatarId = avatarId;
        this.accountName = accountName;
        this.description = description;
        this.articalPicId = articalPicId;
        this.title = title;
        this.detail = detail;
    }




    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArticalPicId(String articalPicId) {
        this.articalPicId = articalPicId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    public String getAvatarId() {
        return avatarId;
    }

    public  String getAccountName() {
        return accountName;
    }

    public  String getDescription() {
        return description;
    }

    public  String getArticalPicId() {
        return articalPicId;
    }

    public  String getTitle() {
        return title;
    }

    public  String getDetail() {
        return detail;
    }
}
