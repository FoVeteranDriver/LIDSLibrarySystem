package com.lids.controller;

import com.alibaba.fastjson.JSONObject;
import com.lids.common.BaseController;
import com.lids.po.User;
import com.lids.service.UserService;
import com.lids.util.HttpClientUtil;
import com.lids.util.ProjectProperties;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author yaoyou
 * @description 静默登陆
 */
@Controller
public class WechatOAuth extends BaseController {

    @Resource
    private UserService userService;

    //静默登陆回调函数获取openid
    @RequestMapping(value = "/wechatOAuth")
    public void getCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String code = request.getParameter("code");
        if (code != null){
            String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            getOpenIdUrl=getOpenIdUrl.replace("APPID", ProjectProperties.appid);
            getOpenIdUrl=getOpenIdUrl.replace("SECRET",ProjectProperties.secret);
            String requestUrl = getOpenIdUrl.replace("CODE",code);

//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(requestUrl);
//            ResponseHandler<String> responseHandler = new BasicResponseHandler();
//            //向微信发送请求并获取结果，从中取出用户的openid
//            String sendResponse = httpClient.execute(httpGet,responseHandler);
            //替代上面注释部分
            String sendResponse = HttpClientUtil.doGet(requestUrl,null);

            JSONObject jsonObject = JSONObject.parseObject(sendResponse);
            String openid = jsonObject.getString("openid");

            User user = userService.selectUserByOpenId(openid);
            /*
            1：用户没有与系统绑定，引导用户进入信息填充页
            2：用户已绑定借书证，直接进入主页
             */
            String redirectUrl = ProjectProperties.myDomain;
            HttpSession session=request.getSession();
            if (user == null){
                //设置用户session，缓存openid
                logger.debug("用户"+openid+"进行绑定");
                session.setAttribute("bindOpenid", openid);
                redirectUrl += "signUp.html";
            }else{
                logger.debug("用户"+openid+"登陆");
//                session.setAttribute("signInOpenid",openid);
                //利用shiro进行登录
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(),user.getPassword());
                SecurityUtils.getSubject().login(token);
                redirectUrl += "index.html";
            }
            response.sendRedirect(redirectUrl);
        }
    }
}
