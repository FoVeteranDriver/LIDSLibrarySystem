package com.lids.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.util.AccessTokenUtil;
import com.lids.util.HttpClientUtil;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yaoyou
 * @description 二维码相关
 */
@Controller
public class QRCodeLogin {

    // 临时二维码
    private final static String QR_SCENE = "QR_SCENE";
    // 永久二维码
    private final static String QR_LIMIT_SCENE = "QR_LIMIT_SCENE";
    // 永久二维码(字符串)
    private final static String QR_LIMIT_STR_SCENE = "QR_LIMIT_STR_SCENE";

    // 创建二维码
    private String create_ticket_path = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=";
    // 通过ticket换取二维码
    private String showqrcode_path = "https://mp.weixin.qq.com/cgi-bin/showqrcode";

    @RequestMapping("getQrcode")
    @ResponseBody
    public void getQrCode(@RequestParam(value = "sceneId")int sceneId, HttpServletResponse response) throws Exception{
        String ticket = createTempTicket("token","2592000",sceneId);
        response.sendRedirect(ticket);
    }

    public String createTempTicket(String accessToken,String expireSeconds,int sceneId)throws Exception{
        TreeMap<String,String> params = new TreeMap<String,String>();
        params.put("access_token", accessToken);
        Map<String,Integer> intMap = new HashMap<String,Integer>();
        intMap.put("scene_id",sceneId);
        Map<String,Map<String,Integer>> mapMap = new HashMap<String,Map<String,Integer>>();
        mapMap.put("scene", intMap);
        Map<String,Object> paramsMap = new HashMap<String,Object>();
        paramsMap.put("expire_seconds", expireSeconds);
        paramsMap.put("action_name", QR_SCENE);
        paramsMap.put("action_info", mapMap);
        JSONObject jsObject = JSONObject.parseObject(JSON.toJSONString(paramsMap));
        String data = jsObject.toString();

        String requestUrl = create_ticket_path + AccessTokenUtil.getInstance().getAccessToken();
//        String requestUrl = create_ticket_path+"7_L9e9S-LZAuZdMFG2gTWnzP1tnNwN7onLbaIxq5dPMpQYHGX-GCamkyQ_NtMkat3w_xsJ65N8NHpAgyuqn7SeznqX8-Wgk4Bh2w84tSJIgi-7Zunc2-xrLUw_g_cVCfUBqbSxWj-TWEP7ahWbHEKiAGASPV";
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        HttpPost httpPost = new HttpPost(requestUrl);
//        httpPost.addHeader("Content-type","application/json; charset=utf-8");
//        httpPost.setEntity(new StringEntity(data, Charset.forName("UTF-8")));
//        ResponseHandler<String> responseHandler = new BasicResponseHandler();
//        String tse = httpClient.execute(httpPost,responseHandler);
        ////替代上面注释部分//替代上面注释部分
        String tse = HttpClientUtil.doPost(requestUrl,data);

        JSONObject jsonObject = JSONObject.parseObject(tse);
        String redirectUrl = showqrcode_path+"?ticket="+jsonObject.getString("ticket");
        return redirectUrl;
    }

}
