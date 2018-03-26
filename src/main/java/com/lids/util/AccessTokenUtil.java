package com.lids.util;

import com.alibaba.fastjson.JSONObject;
import com.lids.controller.WechatOAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AccessTokenUtil {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);

    private static String accessToken;

    private static int expiresIn;

    private static class Holder{
        private static final AccessTokenUtil INSTANCE = new AccessTokenUtil();
    }

    private AccessTokenUtil(){
        String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
        getAccessTokenUrl = getAccessTokenUrl.replace("APPID", ProjectProperties.appid);
        getAccessTokenUrl = getAccessTokenUrl.replace("SECRET",ProjectProperties.secret);
        try{
            String sendResponse = HttpClientUtil.doGet(getAccessTokenUrl,null);
            JSONObject jsonObject = JSONObject.parseObject(sendResponse);
            accessToken = jsonObject.getString("access_token");
            expiresIn = Integer.valueOf(jsonObject.getString("expires_in"));
        }catch (IOException e){
            e.printStackTrace();
        }
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                String getAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=SECRET";
                getAccessTokenUrl = getAccessTokenUrl.replace("APPID", ProjectProperties.appid);
                getAccessTokenUrl = getAccessTokenUrl.replace("SECRET",ProjectProperties.secret);
                try{
                    String sendResponse = HttpClientUtil.doGet(getAccessTokenUrl,null);
                    JSONObject jsonObject = JSONObject.parseObject(sendResponse);
                    accessToken = jsonObject.getString("access_token");
                    expiresIn = Integer.valueOf(jsonObject.getString("expires_in"));
                    logger.debug("获取新的accessToken:"+accessToken);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        };
        Timer timer = new Timer(true);
        timer.schedule(task,7000000,7000000);
    }

    public static final AccessTokenUtil getInstance(){
        return Holder.INSTANCE;
    }

    public String getAccessToken() {
        return accessToken;
    }

}
