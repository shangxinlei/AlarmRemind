package com.huicheng.alarmremind.baidu.face;

import android.content.Context;

import com.google.gson.JsonObject;
import com.huicheng.alarmremind.MyApp;
import com.huicheng.alarmremind.constants.MyConstants;
import com.huicheng.alarmremind.utils.SpUtil;

import java.util.HashMap;
import java.util.Map;

public class FaceUtils {

    //人脸识别帮助类


    /**
     * 从网络获取token
     * @return
     */
    public static  String  requestToken()
    {
        return AuthService.getAuth();//如果失败返回null
    }

    /**
     * 从本地获取token
     */
    public static String getTokenFromLocal(Context context)
    {
        //从本地获取数据，查看是否过期，默认三十天，这里取一天即可
        String token=null;

       token=(String) SpUtil.get(context,MyConstants.ACESS_TOKEN,null);


        return  token;
    }


    //检测人脸
    public boolean isContainFace(){
        boolean contain=false;



        return contain;
    }

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String detect(String accessToken,String imageBase64) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", imageBase64);
            map.put("face_field", "faceshape,facetype");
            map.put("image_type", "BASE64");
            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //检测人脸数量
    public static Integer getFaceNum(String accessToken,String imageBase64)
    {
        String resultStr=detect(accessToken,imageBase64);

        JsonObject jsonObject=GsonUtils.jsonString2jsonObject(resultStr);
        //获取code
        if (jsonObject.has("error_code"))
        {
            Integer code=jsonObject.get("error_code").getAsInt();
            if (code.equals(0))
            {

                //说明获取成功，获取result结果
                JsonObject result=jsonObject.get("result").getAsJsonObject();
                int faceNum=result.get("face_num").getAsInt();
                return faceNum;//返回脸的张数
            }
            //说明获取失败，这里可以考虑获取error_msg，说明原因
        }
        return  0;
    }



    //人脸注册
    public static String add(User user) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", user.getmImage());
            map.put("group_id", user.getmGroupId());
            map.put("user_id", user.getmUserId());
            map.put("user_info", user.getmUserInfo());
            map.put("liveness_control", "NORMAL");//默认添加用户时候要求 normal级别
            map.put("image_type", "BASE64");//默认通过BASE64添加
//            map.put("quality_control", "LOW");
//            quality_control	否	string	图片质量控制
//            NONE: 不进行控制
//            LOW:较低的质量要求
//            NORMAL: 一般的质量要求
//            HIGH: 较高的质量要求
//            默认 NONE
//            若图片质量不满足要求，则返回结果中会提示质量检测失败
//            liveness_control	否	string	活体检测控制
//            NONE: 不进行控制
//            LOW:较低的活体要求(高通过率 低攻击拒绝率)
//            NORMAL: 一般的活体要求(平衡的攻击拒绝率, 通过率)
//            HIGH: 较高的活体要求(高攻击拒绝率 低通过率)
//            默认NONE
//            若活体检测结果不满足要求，则返回结果中会提示活体检测失败

            String param = GsonUtils.toJson(map);
            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = MyApp.shareAccessToken();
            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            //获取的结果需要处理后才可以判断成功失败



            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //人脸搜索，通过图片搜索内容搜索
    public static String search() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "027d8308a2ec665acb1bdf63e513bcb9");
            map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group_repeat,group_233");
            map.put("image_type", "FACE_TOKEN");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = MyApp.shareAccessToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    //人脸搜索，通过userid搜索人脸



}
