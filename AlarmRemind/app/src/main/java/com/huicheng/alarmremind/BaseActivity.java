package com.huicheng.alarmremind;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.xutils.x;

/**
 * Created by wyouflf on 15/11/4.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        //初始化界面数据和事件
        init();
    }



    private void init() {
        initData();
        initView();
        initEvent();
    }

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 加载视图，和试图上的数据
     */
    protected abstract void initView();

    /**
     * 初始化控件事件
     */
    protected abstract void initEvent();

    //跳转到指定的Activity,并传递对应的数据
    protected  void turn2ActivityWithJsonData(Class activityClass,String jsonString){

        Intent intent=new Intent(BaseActivity.this,activityClass);
        if (jsonString!=null&&jsonString.equals("")) {//判断字符串是否为空
            intent.putExtra("jsodata",jsonString);//传递页面跳转传递的参数
        }
        startActivity(intent);

    }

}
