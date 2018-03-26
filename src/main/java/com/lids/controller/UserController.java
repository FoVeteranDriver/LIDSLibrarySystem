package com.lids.controller;

import com.lids.po.User;
import com.lids.service.UserService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author yaoyou
 * @description 用户相关控制器
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Resource
    private UserService userService;

    //用户公众号与借书证绑定接口
    @RequestMapping("signUp")
    @ResponseBody
    public CommomDTO  signUp(User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        String openid = (String)session.getAttribute("bindOpenid");
        session.removeAttribute("bindOpenid");
        CommomDTO result = new CommomDTO();
        if (openid!=null){
            user.setOpenId(openid);
            userService.signUp(user);
            result.setInfo(ResultEnum.SUCCESS);
        }else{
            result.setInfo(ResultEnum.FAILED);
        }
        return result;
    }

    @RequestMapping("getUserInfo")
    @ResponseBody
    public CommomDTO getUserInfo(HttpServletRequest request){
        HttpSession session = request.getSession();
        String openid = (String)session.getAttribute("signInOpenid");
        CommomDTO result = new CommomDTO();
        if (openid == null){
            result.setInfo(ResultEnum.FAILED);
        }else{
            User user = userService.selectUserByOpenId(openid);
            result.setInfo(ResultEnum.SUCCESS,user);
        }
        return result;
    }

}
