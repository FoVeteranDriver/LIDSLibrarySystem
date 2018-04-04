package com.lids.util;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author yaoyou
 * @description 项目属性
 */
public class ProjectProperties implements ServletContextListener{

    public static String appid;
    public static String secret;
    public static String token;
    public static String myDomain;
    public static String url;
    public static String QRCODEURL;

    public void contextInitialized(ServletContextEvent contextEvent){
        InputStream inputStream = this.getClass().getResourceAsStream("/project.properties");
        Properties properties = new Properties();
        try{
            properties.load(inputStream);
        }catch (IOException e) {
            e.printStackTrace();
        }
        appid = properties.getProperty("appid");
        secret = properties.getProperty("secret");
        token = properties.getProperty("token");
        myDomain=properties.getProperty("myDomain");
        url = properties.getProperty("URL");
        QRCODEURL = properties.getProperty("QRCODEURL");
    }

    public void contextDestroyed(ServletContextEvent sce) {

    }

}
