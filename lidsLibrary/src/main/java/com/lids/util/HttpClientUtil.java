package com.lids.util;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

public class HttpClientUtil {

    public static String doGet(String url, HashMap<String,String> params) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = httpClient.execute(httpGet,responseHandler);
        return response;
    }

    public static String doPost(String url,String jsonString)throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        httpPost.setEntity(new StringEntity(jsonString, Charset.forName("UTF-8")));
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String tse = httpClient.execute(httpPost,responseHandler);
        return tse;
    }

}
