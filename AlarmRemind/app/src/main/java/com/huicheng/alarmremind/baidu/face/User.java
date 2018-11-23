package com.huicheng.alarmremind.baidu.face;

import com.google.gson.annotations.Expose;
import com.huicheng.alarmremind.MyApp;
import com.huicheng.alarmremind.constants.MyConstants;

import java.util.UUID;

public class User {

    public  User(){
        //默认自动生成userid uuid
        mUserId=UUID.randomUUID().toString();//
        mGroupId=MyConstants.appName;//默认为appName

    };

    public String getmUserId() {
        return mUserId;
    }

    public void setmUserId(String mUserId) {
        this.mUserId = mUserId;
    }

    public String getmFaceToken() {
        return mFaceToken;
    }

    public void setmFaceToken(String mFaceToken) {
        this.mFaceToken = mFaceToken;
    }

    public String getmCreateTime() {
        return mCreateTime;
    }

    public void setmCreateTime(String mCreateTime) {
        this.mCreateTime = mCreateTime;
    }

    public String getmUserInfo() {
        return mUserInfo;
    }

    public void setmUserInfo(String mUserInfo) {
        this.mUserInfo = mUserInfo;
    }

    public String getmGroupId() {
        return mGroupId;
    }

    public void setmGroupId(String mGroupId) {
        this.mGroupId = mGroupId;
    }
    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    //用户id
    @Expose
    private String mUserId;
    //facetoken
    @Expose
    private  String mFaceToken;


    //用用户图片，字符串格式
    @Expose
    private  String mImage;

    //userinfo
    @Expose
    private  String mUserInfo;
    //groupid
    @Expose
    private  String mGroupId;
    //创建时间
    @Expose
    private  String mCreateTime;

    //

}
