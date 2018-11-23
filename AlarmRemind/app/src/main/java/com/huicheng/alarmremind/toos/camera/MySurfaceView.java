package com.huicheng.alarmremind.toos.camera;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback{


	private Camera camera = null;
	private SurfaceHolder surfaceHolder = null;
	private Activity mActivity;
	public MySurfaceView(Activity context, Camera camera) {
		super(context);
		mActivity=context;
		this.camera = camera;
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	public MySurfaceView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try{
			camera.setDisplayOrientation(getPreviewDegree(mActivity));
			camera.setPreviewDisplay(surfaceHolder);
			Parameters params = camera.getParameters();
			params.setPictureFormat(PixelFormat.JPEG);//图片格式
			params.setJpegQuality(100); // 设置照片质量
			camera.startPreview();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	// 提供一个静态方法，用于根据手机方向获得相机预览画面旋转的角度
	public static int getPreviewDegree(Activity activity) {
		// 获得手机的方向
		int rotation = activity.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degree = 0;
		// 根据手机的方向计算相机预览画面应该选择的角度
		switch (rotation) {
			case Surface.ROTATION_0:
				degree = 90;
				break;
			case Surface.ROTATION_90:
				degree = 0;
				break;
			case Surface.ROTATION_180:
				degree = 270;
				break;
			case Surface.ROTATION_270:
				degree = 180;
				break;
		}
		return degree;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
							   int height) {



		//根本没有可处理的SurfaceView
		if (surfaceHolder.getSurface() == null){
			return ;
		}

		//先停止Camera的预览
		try{
			camera.stopPreview();

			//sdk 大于2.2的
			if (android.os.Build.VERSION.SDK_INT > 8) {
				Parameters params =camera.getParameters();
				float camerabili=(float)width/(float)height;
				Log.v("比例", width+"/"+height+"="+camerabili);
				List<Camera.Size>  supportPictureSizes=params.getSupportedPictureSizes();
				Camera.Size tSize=getOptimalPreviewSize(supportPictureSizes, width, height);
				Log.v("比例", tSize.width+"/"+tSize.height);
				params.setPreviewSize(tSize.width, tSize.height);
				params.setPictureSize(tSize.width, tSize.height);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		//这里可以做一些我们要做的变换。

		//重新开启Camera的预览功能
		try{
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private Size getOptimalPreviewSize(List<Size> sizes, int w, int h) {
		final double ASPECT_TOLERANCE = 0.05;
		double targetRatio = (double) w / h;
		if (sizes == null)
			return null;
		Size optimalSize = null;
		double minDiff = Double.MAX_VALUE;
		int targetHeight = h;
		// Try to find an size match aspect ratio and size
		for (Size size : sizes) {
			double ratio = (double) size.width / size.height;
			if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE)
				continue;
			if (Math.abs(size.height - targetHeight) < minDiff) {
				optimalSize = size;
				minDiff = Math.abs(size.height - targetHeight);
			}
		}
		// Cannot find the one match the aspect ratio, ignore the requirement
		if (optimalSize == null) {
			minDiff = Double.MAX_VALUE;
			for (Size size : sizes) {
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
		}
		return optimalSize;
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

		//当surfaceview关闭时，关闭预览并释放资源
		if (camera!=null) {
			camera.release();
			camera = null;
			holder=null;
		}

	}


}
