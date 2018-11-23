package com.huicheng.alarmremind.constants;

import android.os.Environment;

import java.io.File;

public class MyConstants {
    public  static  String appName="alarmRemind";

    //异常文件保存地址
    public  static  String UNCATCH_EXCEPTION_PATH = Environment.getExternalStorageDirectory().toString()
            + File.separator
            + appName + File.separator ;

    public final static String  ACESS_TOKEN="access_token";

    //返回数据成功
    public  final static Integer RESULT_SUCESS=1;
    public  final static Integer RESULT_FAILTURE=-1;

    // 图片在SD卡中的缓存路径
    public static final String IMAGE_PATH = Environment
            .getExternalStorageDirectory().toString()
            + File.separator
            + appName+ File.separator + "images" + File.separator;

    //登录界面还是注册界面的标识
    public final static String loginTag="login_tag";//
    public final static int TAG_FOR_LOGIN=1;//登录标识
    public final static int TAG_FOR_REGISTER=2;//注册标识
}
