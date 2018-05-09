package com.lids.controller;

import com.lids.service.impl.QuartzService;
import com.lids.util.ProjectProperties;
import com.lids.util.QrGenUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.cache.ehcache.EhCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(QRCodeController.class);

    public static Cache uuidCache;
    public static Map<String,String> cache = new HashMap<String, String>();
    public static final String one = "未扫码";

    static {
        InputStream in = EhCache.class.getClassLoader().getResourceAsStream("/ehcache/uuidCache.xml");
        CacheManager cacheManager = CacheManager.create(in);
        uuidCache =cacheManager.getCache("uuidCache");
    }

    /**
     * 展示扫码登陆的二维码
     * @param req
     * @param resp
     * @return
     * @throws ServletException
     * @throws IOException
     */
    @RequestMapping("/showLoginQRCode")
    @ResponseBody
    protected CommomDTO showQrGen(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //生成UUID随机数
        UUID randomUUID = UUID.randomUUID();

//        cache.put(randomUUID.toString(), one);
        Element element = new Element(randomUUID.toString(), one);
        uuidCache.put(element);

        //二维码图片扫描后的链接
//        String url = "http://iyou.s1.natapp.cc/lidsLibrary/user/loginByQRCode?uuid="+randomUUID;
        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx463f559a1afd2cd4&redirect_uri=http%3a%2f%2f"
                + ProjectProperties.url
                +"%2flidsLibrary%2fuser%2floginByQRCode%3fuuid%3d"
                +randomUUID
                +"&response_type=code&scope=snsapi_base&state=123#wechat_redirect";
        //生成二维码图片
        ByteArrayOutputStream qrOut = QrGenUtil.createQrGen(url);
        String fileName = randomUUID+ ".jpg";
        OutputStream os = new FileOutputStream(new File(ProjectProperties.QRCODEURL,fileName));
        os.write(qrOut.toByteArray());
        os.flush();
        os.close();
        String imgURL = ProjectProperties.myDomain+"jpg/"+fileName;
        //TODO 可以使用spring aop进行分离
        QuartzService.addJobForQrCodeDelete(fileName,randomUUID.toString());

        CommomDTO commomDTO = new CommomDTO();
        Map<String,String> map = new HashMap<String, String>();
        map.put("uuid",randomUUID.toString());
        map.put("QRCodeUrl",imgURL);
        commomDTO.setInfo(ResultEnum.SUCCESS,map);
        return commomDTO;
    }

}
