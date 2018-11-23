package com.huicheng.alarmremind;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.huicheng.alarmremind.baidu.face.Base64Util;
import com.huicheng.alarmremind.baidu.face.FaceUtils;
import com.huicheng.alarmremind.toos.camera.MySurfaceView;
import com.huicheng.alarmremind.utils.MyLogUtils;
import com.huicheng.alarmremind.utils.SpUtil;
import com.huicheng.alarmremind.utils.ToastUtil;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


import static java.lang.Thread.*;

@ContentView(R.layout.activity_camera)
public class CameraActivity extends BaseActivity {


    @ViewInject(R.id.camera_preview)
    private FrameLayout mPreview;


    private MySurfaceView mySurfaceView;
    private Camera mCamera;
    private byte[] buffer = null;
    //用来区分是登录还是注册的变量
    private static final int ACTION_LOGIN = 1;
    private static final int ACTION_REGSTER = 0;
    private int mAction = ACTION_REGSTER;//默认是注册
    //相机
    private Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            if (data == null) {
                Log.i("MyPicture", "picture taken data: null");

            } else {
                Log.i("MyPicture", "picture taken data: " + data.length);
            }

            buffer = new byte[data.length];
            buffer = data.clone();
            //执行线程任务
            x.task().run(new Runnable() {
                @Override
                public void run() {
                    //上传图片查看是否有脸
                   if (buffer!=null)
                   {
                       doAction(buffer);
                   }
                    //开始扫描获取图片，上传检测

                }
            });

        }
    };

    //根据mAction来执行注册还是登录
    private void doAction(byte[] imageByte){

        //根据具体的值判断是登陆还是注册，如果是注册那么，长传值，如果是登陆那么检测值

        /**
         * 注册成功或者失败返回值信息
         */

        //上传图片查看是否有脸

        //如果有脸那么开始注册到百度云,注册成功的用户将userid存储到本地，公国userid获取比对

        int faceNum = FaceUtils.getFaceNum(MyApp.shareAccessToken(), Base64Util.encode(imageByte));
        //如果脸的数目不等于1那么就有错误
        Message msg = mHandler.obtainMessage();//传递消息，没有人脸或者人脸数目不是一直接检测错误
        if (faceNum != 1) {
            //判断是登陆还是注册，如果是注册提示注册信息，如果是登陆提示登陆信息
            switch (mAction) {
                case ACTION_LOGIN: {
                    msg.what=LOGIN_ERROR;//登录失败
                }
                break;
                case ACTION_REGSTER: {
                    msg.what=REGSTER_ERROR;//注册失败
                }
                break;
            }
            msg.sendToTarget();

        }
        else{
            //说明人脸数目为1开始检索或者注册人脸，停止扫描
            msg.what=LOGIN_OK;
            msg.sendToTarget();

        }




        //根据具体的值判断是登陆还是注册，如果是注册那么，长传值，如果是登陆那么检测值

        /**
         * 注册成功或者失败返回值信息
         */
    }

    //登陆注册状态码
    private final static int LOGIN_OK = 0;
    private final static int LOGIN_ERROR = 1;
    private final static int REGSTER_OK = 10;
    private final static int REGSTER_ERROR = 11;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //根据消息内容触发，信息
            switch (msg.what) {
                case LOGIN_OK: {
                    //登陆成功
                    ToastUtil.showMessage("登录成功！");
                    //登录成功，停止扫脸
                    turn2ActivityWithJsonData(ReminderList.class,"");//跳转到对应的列表界面
                }
                break;
                case LOGIN_ERROR: {
                    //登陆失败，弹出失败原因
                    ToastUtil.showMessage("登录失败");

                    mCamera.startPreview();
                }
                break;
                case REGSTER_OK: {
                    //注册成功
                    ToastUtil.showMessage("注册成功");
                }
                break;
                case REGSTER_ERROR: {
                    ToastUtil.showMessage("注册失败");
                    mCamera.startPreview();
                    scanFace();//当返回失败结果是再次扫描！
                }
                break;
            }

            super.handleMessage(msg);
        }
    };

    /***
     * 数据初始化
     */
    @Override
    protected void initData() {


    }

    @Override
    protected void initView() {


    }

    @Override
    protected void initEvent() {

        //更具不同的参数，调用不同的功能




    }

    private  void scanFace(){

        //有结果了再调用，而不是一直调用扫描
        x.task().run(new Runnable() {
            @Override
            public void run() {
                //异步代码
                try {
                        sleep(4000);//4秒后开始扫描界面
                        if (mCamera!=null)
                        {
                            mCamera.takePicture(null, null, pictureCallback);
                        }

                } catch (InterruptedException e) {
                    MyLogUtils.Log("抓到錯誤了");
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        if (mySurfaceView != null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);
            mPreview.removeView(mySurfaceView);
            mySurfaceView = null;

        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub

        super.onResume();

        if (mySurfaceView == null) {
            mCamera = getCameraInstance();
            //直接结束
            if (mCamera == null) {
                this.finish();
                return;
            }
            mCamera.startPreview();
            mySurfaceView = new MySurfaceView(this, mCamera);
            mySurfaceView.setFocusable(true);
			mySurfaceView.setFocusableInTouchMode(true);
			mySurfaceView.setClickable(true);
//			mySurfaceView.setOnClickListener(this);
            mPreview.addView(mySurfaceView);
        }
        //必须放在onResume中，不然会出现Home键之后，再回到该APP，黑屏
        scanFace();//初始化完后，开始扫脸

    }


    /*得到一相机对象*/
    public final static int CAMERA_REQUEST_CODE = 3;
    @Nullable
    private Camera getCameraInstance() {
        Camera camera = null;
        try {
            if (!hasCamera())
                return null;//表示相机不可用
            //如果相机不能用，请求相机权限
            if (!isCameraUseable()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
                }
            }
            //调用前置摄像头
            //camera = Camera.open(1);
            // 打开前置摄像头
            int cameraCount = 0;
            @SuppressWarnings("unused")
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            cameraCount = Camera.getNumberOfCameras(); // get cameras number
            for ( int camIdx = 0; camIdx < cameraCount;camIdx++ ) {
                Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    //代表摄像头的方位，目前有定义值两个分别为CAMERA_FACING_FRONT前置和CAMERA_FACING_BACK后置
                    try {
                        camera = Camera.open(camIdx);
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;//say no camera
        }
        return camera;
    }
    @Override
    public void onDestroy() {

        if (mCamera!=null)
        {
            mCamera=null;
        }
        super.onDestroy();
    }

    //判断有没有相机
    public static  boolean hasCamera()
    {
        return MyApp.shareApplication().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

    }
    /**
     * 相机权限检测
     */
    public static boolean isCameraUseable() {

        boolean canUse =true;

        Camera mCamera =null;



        try{

            mCamera = Camera.open();

        // setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null
            Camera.Parameters mParameters = mCamera.getParameters();
            mCamera.setParameters(mParameters);

        }catch(Exception e) {

            canUse =false;

        }

        if(mCamera !=null) {

            mCamera.release();

        }
        return canUse;

    }

}
