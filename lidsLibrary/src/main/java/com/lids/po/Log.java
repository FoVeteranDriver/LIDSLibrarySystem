package com.lids.po;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 日志实体
 */
public class Log {

    private int id;
    private String title;
    private String remoteAddr;
    private String requestUri;
    private String method;
    private String params;
    private String userAgent;
    private String exception;
    private String userType;
    private String userName;
    private int userId;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRemoteAddr() {
        return remoteAddr;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(Map params) {
        JSONObject jsonObject = new JSONObject();
        for (Object o:params.entrySet()){
            Map.Entry entry = (Map.Entry)o;
            jsonObject.put((String)entry.getKey(),entry.getValue());
        }
        this.params = jsonObject.toJSONString();
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

}
