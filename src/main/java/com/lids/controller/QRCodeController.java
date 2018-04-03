package com.lids.controller;

import com.lids.po.User;
import com.lids.util.QrGenUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class QRCodeController {

    public static Map<String,String> cache = new HashMap<String, String>();
    public static final String one = "未扫码";

    @RequestMapping("/showQr")
    @ResponseBody
    protected CommomDTO showQrGen(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //生成UUID随机数
        UUID randomUUID = UUID.randomUUID();

        cache.put(randomUUID.toString(), one);

        //二维码图片扫描后的链接
//        String url = "http://iyou.s1.natapp.cc/lidsLibrary/user/loginByQRCode?uuid="+randomUUID;
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx463f559a1afd2cd4&redirect_uri=http%3a%2f%2fiyou.s1.natapp.cc%2flidsLibrary%2fuser%2floginByQRCode%3fuuid%3d"
                +randomUUID
                +"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";

        //生成二维码图片
        ByteArrayOutputStream qrOut = QrGenUtil.createQrGen(url);
        String fileName = randomUUID+ ".jpg";
        OutputStream os = new FileOutputStream(new File("I:\\other\\nginx-1.13.0\\html\\jpg",fileName));
        os.write(qrOut.toByteArray());
        os.flush();
        os.close();
        String imgURL = "http://iyou.s1.natapp.cc/jpg/"+fileName;

        //返回页面json字符串，uuid用于轮询检查时所带的参数， img用于页面图片显示
//        String jsonStr = "{\"uuid\":\"" + randomUUID + "\",\"img\":\"" + "http://iyou.s1.natapp.cc/jpg/"+fileName + "\"}";
//        OutputStream outStream = resp.getOutputStream();
//        outStream.write(jsonStr.getBytes());
//        outStream.flush();
//        outStream.close();
        CommomDTO commomDTO = new CommomDTO();
        Map<String,String> map = new HashMap<String, String>();
        map.put("uuid",randomUUID.toString());
        map.put("QRCodeUrl",imgURL);
        commomDTO.setInfo(ResultEnum.SUCCESS,map);
        return commomDTO;
    }

}
