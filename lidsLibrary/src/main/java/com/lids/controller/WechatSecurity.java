package com.lids.controller;

import com.lids.common.BaseController;
import com.lids.util.SignUtil;
import com.lids.util.WechatMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author yaoyou
 * @Description 微信接口，用于微信验证与接收微信服务器传送的数据
 */
@Controller
@RequestMapping("/wechat")
public class WechatSecurity extends BaseController {

    @RequestMapping(value = "security",method = RequestMethod.GET)
    public void doGet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "signature", required = true) String signature,
            @RequestParam(value = "timestamp", required = true) String timestamp,
            @RequestParam(value = "nonce", required = true) String nonce,
            @RequestParam(value = "echostr", required = true) String echostr) {
        try {
            if (SignUtil.checkSignature(signature, timestamp, nonce)) {
                PrintWriter out = response.getWriter();
                out.print(echostr);
                out.close();
            } else {
                logger.debug("这里存在非法请求");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "security", method = RequestMethod.POST)
    public void DoPost(PrintWriter out, HttpServletRequest request, HttpServletResponse response) {
        Map<String, String> map = WechatMessageUtil.xmlToMap(request);
        out.print("success");
        out.flush();
    }
}
