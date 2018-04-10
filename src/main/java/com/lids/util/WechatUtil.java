package com.lids.util;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

public class WechatUtil {

    /**
     * 获取用户的openid
     * @param code 用户同意授权获取的code
     * @return
     */
    public static String getOpenId(String code) {
        String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        getOpenIdUrl=getOpenIdUrl.replace("APPID", ProjectProperties.appid);
        getOpenIdUrl=getOpenIdUrl.replace("SECRET",ProjectProperties.secret);
        String requestUrl = getOpenIdUrl.replace("CODE",code);
        String sendResponse = null;
        try{
            sendResponse = HttpClientUtil.doGet(requestUrl,null);
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject jsonObject = JSONObject.parseObject(sendResponse);
        String openid = jsonObject.getString("openid");
        return openid;
    }

}
