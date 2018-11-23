package com.huicheng.alarmremind.utils;

import android.content.Context;
import android.widget.Toast;

import com.huicheng.alarmremind.MyApp;

public class ToastUtil {
    public static void showMessage(String message){
        Toast.makeText(MyApp.shareApplication(),message,Toast.LENGTH_SHORT).show();//展示消息
    }
}
