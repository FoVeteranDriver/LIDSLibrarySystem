package com.lids.controller;

import com.lids.po.User;
import com.lids.service.UserService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO doLogin(@RequestBody User user){
        UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(),user.getPassword());
        try{
            SecurityUtils.getSubject().login(token);
            return new CommomDTO(ResultEnum.SUCCESS);
        }catch(UnknownAccountException e){
            return new CommomDTO(ResultEnum.UNKNOWN_ACCOUNT_ERROR);
        }catch(IncorrectCredentialsException e){
            return new CommomDTO(ResultEnum.INCORRECT_CREDENTIALS_ERROR);
        }catch(LockedAccountException e){
            return new CommomDTO(ResultEnum.INCORRECT_STATE_ERROR);
        }catch (Exception e) {
            return new CommomDTO(ResultEnum.OTHER_LOGIN_ERRROR);
        }
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public CommomDTO userLogout(){
        SecurityUtils.getSubject().logout();
        return new CommomDTO(ResultEnum.SUCCESS);
    }

    @RequestMapping(value = "/userInfo")
    @ResponseBody
    public CommomDTO getUserInfo(){
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,user);
        return commomDTO;
    }

}
