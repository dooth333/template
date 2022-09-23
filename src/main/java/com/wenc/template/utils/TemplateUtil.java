package com.wenc.template.utils;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import com.alibaba.fastjson.JSONObject;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TemplateUtil {

    /***
     * 生成随机字符串
     * 主要用于生成密码的salt
     * @return
     */
    public static String generateUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /***
     * MD5加密
     * MD5加密，只能加密不能解密，同一密码每次加密结果相同，所以不能设置太简单的密码，我们要为密码后加随机字符串
     * @param key
     * @return
     */
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }

    /***
     * 转化成json
     * @param code
     * @param msg
     * @param map
     * @return
     */
    public static String getJSONString(int code, String msg, Map<String,Object> map){

        JSONObject json = new JSONObject();
        json.put("code",code);
        json.put("msg",msg);
        if (map != null){
            for (String key : map.keySet()){
                json.put(key,map.get(key));
            }
        }
        return json.toJSONString();
    }

    /***
     * 转化成json
     * @param code
     * @param msg
     * @return
     */
    public static String getJSONString(int code, String msg){

        return getJSONString(code, msg,null);
    }

    /***
     * 转化成json
     * @param code
     * @return
     */
    public static String getJSONString(int code){

        return getJSONString(code, null,null);
    }

    /***
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("name","张三");
        map.put("age",18);
        System.out.println(getJSONString(0,"ok",map));
    }
}
