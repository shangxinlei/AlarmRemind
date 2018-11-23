package com.huicheng.alarmremind.baidu.face;

public class FaceImage {
//    {
//        "image": "sfasq35sadvsvqwr5q...",
//            "image_type": "BASE64",
//            "face_type": "LIVE",
//            "quality_control": "LOW",
//            "liveness_control": "HIGH"
//    },
//    {
//        "image": "sfasq35sadvsvqwr5q...",
//            "image_type": "BASE64",
//            "face_type": "IDCARD",
//            "quality_control": "LOW",
//            "liveness_control": "HIGH"
//    }

    public FaceImage()
    {
        quality_control="NORMAL";//默认是nomal
        liveness_control="NORMAL";
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFace_type() {
        return face_type;
    }

    public void setFace_type(String face_type) {
        this.face_type = face_type;
    }

    public String getQuality_control() {
        return quality_control;
    }

    public void setQuality_control(String quality_control) {
        this.quality_control = quality_control;
    }

    public String getLiveness_control() {
        return liveness_control;
    }

    public void setLiveness_control(String liveness_control) {
        this.liveness_control = liveness_control;
    }
    public String getImage_type() {
        return image_type;
    }

    public void setImage_type(String image_type) {
        this.image_type = image_type;
    }
    private  String image;
    private  String face_type;
    private  String image_type;
    private  String quality_control;
    private  String liveness_control;
}
