package com.huicheng.alarmremind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.huicheng.alarmremind.constants.MyConstants;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.btn_login)
    private Button btn_login;
    @ViewInject(R.id.btn_register)
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turn2ActivityWithJsonData(CameraActivity.class,null);
            }
        });

    }


    @Override
    protected void initEvent() {

    }

    /**
     * 跳转到注册页面
     */
    private  void turn2Register()
    {
        Intent intent=new Intent(this,CameraActivity.class);
        //放入表示字段
        intent.putExtra(MyConstants.loginTag,MyConstants.TAG_FOR_REGISTER);//注册
        startActivity(intent);
        this.finish();

    }
    /**
     * 跳转到登陆页面
     */
    private  void turn2Login()
    {
        Intent intent=new Intent(this,CameraActivity.class);
        //放入表示字段
        intent.putExtra(MyConstants.loginTag,MyConstants.TAG_FOR_LOGIN);//登录
        startActivity(intent);
        this.finish();
    }
}
