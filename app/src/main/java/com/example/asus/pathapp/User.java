package com.example.asus.pathapp;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

public class User extends BmobUser {
    private BmobFile userAvatar;
    private String userDes;

    public BmobFile getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(BmobFile userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserDes() {
        return userDes;
    }

    public void setUserDes(String userDes) {
        this.userDes = userDes;
    }
}
