package com.example.asus.pathapp;

import cn.bmob.v3.BmobObject;

public class Artical extends BmobObject {
    private  int avatarId;
    private  String accountName;
    private  String description;
    private  int articalPicId;
    private  String title;
    private  String detail;
    //构造方法
    public Artical(int avatarId, String accountName,String description,
                   int articalPicId, String title, String detail) {
        this.avatarId = avatarId;
        this.accountName = accountName;
        this.description = description;
        this.articalPicId = articalPicId;
        this.title = title;
        this.detail = detail;
    }

    public void setAvatarId(int avatarId) {
        this.avatarId = avatarId;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setArticalPicId(int articalPicId) {
        this.articalPicId = articalPicId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }



    public  int getAvatarId() {
        return avatarId;
    }

    public  String getAccountName() {
        return accountName;
    }

    public  String getDescription() {
        return description;
    }

    public  int getArticalPicId() {
        return articalPicId;
    }

    public  String getTitle() {
        return title;
    }

    public  String getDetail() {
        return detail;
    }
}
