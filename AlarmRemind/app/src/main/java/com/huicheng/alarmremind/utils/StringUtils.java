package com.huicheng.alarmremind.utils;

public class StringUtils {

    /**
     * 判断字符串是否相等
     * @param one
     * @param two
     * @return
     */
    public static boolean isEquals(String one,String two){
        if(one==null||two==null){
            return false;
        }
        return one.equals(two);
    }


    //判断数字字符串是否为null
    public static boolean isStrEquelNull(String str)
    {
        if (str!=null) {
            return str.equals("null");
        }
        return true;
    }

    /**
     * 判断字符串是否为空
     * @param one
     * @param two
     * @return
     */
    public static boolean isBlank(String text){
        return text == null || text.trim().equals("");
    }

    /**
     * 判断字符串是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        for (int i = str.length();--i>=0;){
            if (!Character.isDigit(str.charAt(i))){
                return false;
            }
        }
        return true;
    }



    /**
     *用户名解密
     *@param ssoToken 字符串
     *@return String 返回加密字符串
     */
    public static String decrypt(String ssoToken)
    {
        try
        {
            String name = new String();
            java.util.StringTokenizer st=new java.util.StringTokenizer(ssoToken,"%");
            while (st.hasMoreElements()) {
                int asc =  Integer.parseInt((String)st.nextElement()) - 27;
                name = name + (char)asc;
            }

            return name;
        }catch(Exception e)
        {
            e.printStackTrace() ;
            return null;
        }
    }

    /**
     *用户名加密
     *@param ssoToken 字符串
     *@return String 返回加密字符串
     */
    public static String encrypt(String ssoToken)
    {
        try
        {
            byte[] _ssoToken = ssoToken.getBytes("ISO-8859-1");
            String name = new String();
            // char[] _ssoToken = ssoToken.toCharArray();
            for (int i = 0; i < _ssoToken.length; i++) {
                int asc = _ssoToken[i];
                _ssoToken[i] = (byte) (asc + 27);
                name = name + (asc + 27) + "%";
            }
            return name;
        }catch(Exception e)
        {
            e.printStackTrace() ;
            return null;
        }
    }


}
