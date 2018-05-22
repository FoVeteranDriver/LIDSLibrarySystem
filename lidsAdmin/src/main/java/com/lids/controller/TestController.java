package com.lids.controller;

import com.lids.po.AdminUser;
import com.lids.service.AdminUserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class TestController {

    @Resource
    private AdminUserService adminUserService;

    @RequestMapping("/test")
    @ResponseBody
    public Map test(){
        return adminUserService.selectUserPermission("ss");
    }

    @RequestMapping("perm")
    @ResponseBody
    public String perm(){
        return "success";
    }

}
