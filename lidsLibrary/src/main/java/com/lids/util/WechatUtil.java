package com.lids.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WechatUtil {

    private static Logger logger = LoggerFactory.getLogger(WechatUtil.class);

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

        if (isSubscribe(openid)){
            return openid;
        }else {
            logger.debug("用户未关注");
            return "false";
        }
    }

    /**
     * 根据openid判断用户是否关注公众号
     * @param openId
     * @return 返回true代表关注，false代表没有关注
     */
    public static boolean isSubscribe(String openId){
        String getUserInfoURL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
        getUserInfoURL = getUserInfoURL.replace("ACCESS_TOKEN",AccessTokenUtil.getInstance().getAccessToken());
        getUserInfoURL = getUserInfoURL.replace("OPENID",openId);
        String response = null;
        try{
            response = HttpClientUtil.doGet(getUserInfoURL,null);
        }catch (IOException e){
            e.printStackTrace();
        }
        JSONObject jsonObject1 = JSONObject.parseObject(response);
        String subscribe = jsonObject1.getString("subscribe");
        if (subscribe.equals("0")){
            return false;
        }else {
            return true;
        }
    }

}
