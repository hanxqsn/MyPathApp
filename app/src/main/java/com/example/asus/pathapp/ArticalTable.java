package com.example.asus.pathapp;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

public class ArticalTable extends BmobObject {
    private BmobFile prePic;
    private String title;
    private String detail;
    private String context;
    private String mobilePhoneNumber;

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public BmobFile getPrePic() {
        return prePic;
    }

    public void setPrePic(BmobFile prePic) {
        this.prePic = prePic;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
