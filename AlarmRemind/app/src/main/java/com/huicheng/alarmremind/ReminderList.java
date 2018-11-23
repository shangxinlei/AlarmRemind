package com.huicheng.alarmremind;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;


@ContentView(R.layout.activity_reminder_list)
public class ReminderList extends BaseActivity {

    @ViewInject(R.id.reminderList)
    private ListView mReminderList;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        String[] data={"该吃药了","该睡觉了","该去洗澡了"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1 ,data);
        mReminderList.setAdapter(adapter);
    }

    @Override
    protected void initEvent() {

    }


}
