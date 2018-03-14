package com.lids.controller;

import com.lids.service.TestService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {

    @Resource
    private TestService testService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return testService.test();
    }

    @RequestMapping("/host")
    @ResponseBody
    public String hostTest(){
        return "yes";
    }

}
