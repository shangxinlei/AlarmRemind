package com.huicheng.alarmremind.utils;


import android.util.Log;
public class MyLogUtils {

	//debug 的开关
	private static final Boolean debug_mode = true;

	private static final String TAG = "MyLogUtils";
	/**
	 *
	 * 普通的log输出
	 * @param msg
	 */
	public static void Log(String msg)
	{
		if (debug_mode) {
			Log.d(TAG,msg);
		}
	}

	/**
	 * 对象方法里的 log
	 *
	 * @param obj
	 * @param msg
	 */
	public static void Log(Object obj,String msg)
	{
		if (debug_mode) {
			String objName= obj.getClass().getName();
			Log.d(objName,msg);
		}
	}

	/**
	 * 对象的log
	 * @param obj
	 */
	public static void Log(Object obj)
	{
		if (debug_mode) {
			Log.d(TAG,"log:"+obj.toString()+"hash"+obj.hashCode());
			System.out.print("system print:"+obj.hashCode());
		}
	}



}
