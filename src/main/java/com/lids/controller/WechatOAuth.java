package com.lids.controller;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class WechatOAuth {

    @RequestMapping(value = "/wechatOAuth")
    @ResponseBody
    public String getCode(HttpServletRequest request) throws Exception{
        String result=null;
        String code = request.getParameter("code");
        System.out.print(code);
        if (code != null){
            String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx463f559a1afd2cd4&secret=bf6e4b74e09f79a5040efebc1adef9f8&code=CODE&grant_type=authorization_code";
            String requestUrl = getOpenIdUrl.replace("CODE",code);
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            //向微信发送请求并获取response
            String response = httpClient.execute(httpGet,responseHandler);
            System.out.println("=========================获取token===================");
            System.out.println(response);
            result = response;
        }
        return result;
    }
}


