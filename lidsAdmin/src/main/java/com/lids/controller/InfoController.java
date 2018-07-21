package com.lids.controller;

import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class InfoController {

    @RequestMapping("/info/login")
    @ResponseBody
    public CommomDTO loginInfo(){
        return new CommomDTO(ResultEnum.NO_LOGIN);
    }

    @RequestMapping("/info/unauth")
    @ResponseBody
    public CommomDTO unpermissionInfo(){
        return new CommomDTO(ResultEnum.NO_PERMISSION);
    }

}
