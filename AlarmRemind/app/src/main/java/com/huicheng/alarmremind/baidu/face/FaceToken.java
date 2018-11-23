package com.huicheng.alarmremind.baidu.face;

import com.google.gson.annotations.Expose;

import java.util.Date;

public class FaceToken {
    @Expose
    private String mToken;
    @Expose
    private String mCreateTime;

    public String getmToken() {
        return mToken;
    }

    public void setmToken(String mToken) {
        this.mToken = mToken;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }






}
