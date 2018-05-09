package com.lids.wx;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

@Controller
@RequestMapping("auth")
public class OAuthController {

    @RequestMapping
    public String oauth(){
        return null;
    }

    @RequestMapping("/receive")
    @ResponseBody
    public String receive(HttpServletRequest request)throws IOException{
        InputStream inputStream = request.getInputStream();
        int ava = inputStream.available();
        byte[] text= new byte[ava];
        inputStream.read(text);
        System.out.println(text.toString());
        return "success";
    }

}
