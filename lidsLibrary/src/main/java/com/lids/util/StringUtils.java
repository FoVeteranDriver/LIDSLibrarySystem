package com.lids.util;

import javax.servlet.http.HttpServletRequest;

public class StringUtils{

    public static String getRemoteAddr(HttpServletRequest httpServletRequest){
        String remoteAddr = httpServletRequest.getHeader("X-Real-IP");
        if (remoteAddr == null || remoteAddr.equals("")){
            remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        }else if (remoteAddr == null || remoteAddr.equals("")){
            remoteAddr = httpServletRequest.getHeader("X-Forwarded-For");
        }else if (remoteAddr == null || remoteAddr.equals("")){
            remoteAddr = httpServletRequest.getHeader("WL-Proxy-Client-IP");
        }
        return remoteAddr !=null ? remoteAddr : httpServletRequest.getRemoteAddr();
    }

}
