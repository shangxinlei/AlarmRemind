package com.huicheng.alarmremind;
import org.xutils.x;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.huicheng.alarmremind.baidu.face.AuthService;
import com.huicheng.alarmremind.baidu.face.FaceToken;
import com.huicheng.alarmremind.constants.MyConstants;
import com.huicheng.alarmremind.utils.DateUtil;
import com.huicheng.alarmremind.utils.JSONUtils;
import com.huicheng.alarmremind.utils.MyLogUtils;
import com.huicheng.alarmremind.utils.SpUtil;
import com.huicheng.alarmremind.utils.StringUtils;

import java.util.Date;

public class MyApp extends Application {

    //一个从后台获取accessToken的方法
   private AsyncTask tokenTask=new AsyncTask() {


        @Override
        protected Object doInBackground(Object[] objects) {
            getAccessToken();
            //如果获取失败那么需要，弹出错误信息
            if (mShareAccessToken!=null)
            {
                return MyConstants.RESULT_SUCESS;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            if (o!=null)
            {

                Toast.makeText(mShareApplication,"获取AccessToken成功！",Toast.LENGTH_SHORT).show();
            }
            else
            {

                Toast.makeText(mShareApplication,"获取AccessToken失败！",Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(o);
        }
    };



   //保存accesstoken到sharedata
    public static void saveAccessToken(String token){
        FaceToken faceToken=new FaceToken();
        faceToken.setmToken(token);
        faceToken.setmCreateTime(DateUtil.date2Str(new Date()));
        String jsonFaceToken=JSONUtils.toJson(faceToken);
        SpUtil.put(shareApplication(),MyConstants.ACESS_TOKEN,jsonFaceToken);
        MyLogUtils.Log("保存accessToken成功"+jsonFaceToken);
    }

    /**
     * 获取可用的accessToken
     * @return
     */
    public static void getAccessToken()
    {
        //先获取从本地获取accessToken
        Object accessToken=SpUtil.get(shareApplication(),MyConstants.ACESS_TOKEN,"");
        if (accessToken!=null&&accessToken instanceof String)
        {
            //获取成功
            FaceToken token=JSONUtils.fromJson((String)accessToken,FaceToken.class);
            //获取成功后检验是否过期，如果过期那么重新获取，如果没有过期直接使用
            if (isExpire(token))
            {
                //获取失败，从网络上获取
                mShareAccessToken=AuthService.getAuth();
                saveAccessToken(mShareAccessToken);
            }else{
                mShareAccessToken=token.getmToken();
            }




        }
        else{
            //获取失败，从网络上获取
            mShareAccessToken=AuthService.getAuth();
            //获取后保存到文件中，方便取用
            saveAccessToken(mShareAccessToken);//保存到本地

        }
    }

    /****
     * 判断token是否过期
     * @return
     */
    public static boolean isExpire(FaceToken faceToken){

        try {
            Date date=DateUtil.str2Date(faceToken.getmCreateTime());
            return DateUtil.getDayDiff(date, new Date()) >= 30;
        } catch (Exception e) {
            e.printStackTrace();
            return true;//返回
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mShareApplication=this;
        tokenTask.execute();
        x.Ext.init(this);
        x.Ext.setDebug(false); //输出debug日志，开启会影响性能
        // 全局默认信任所有https域名 或 仅添加信任的https域名

        // 使用RequestParams#setHostnameVerifier(...)方法可设置单次请求的域名校验
        x.Ext.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
    }

    //获取当前当前实例
    private static  Application mShareApplication;
    public static Application shareApplication()
    {
        return  mShareApplication;
    }
    private static  String mShareAccessToken;
    public static String shareAccessToken(){
        return mShareAccessToken;
    }


}

