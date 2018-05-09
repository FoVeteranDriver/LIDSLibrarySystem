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
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("user")
public class AdminUser {

    @Resource
    private UserService userService;

    /**
     * 管理员登陆
     * @param user
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO login(@RequestBody User user){
        User getUser = userService.selectUserByLibraryCardNumber(user.getLibraryCardNumber());
        if (getUser == null || !getUser.getUserTypeName().equals("管理员")){
            return new CommomDTO(ResultEnum.FAILED);
        }else{
            UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(),user.getPassword());
            try{
                SecurityUtils.getSubject().login(token);
                Map<String,String> map = new HashMap<String, String>();
                map.put("name",getUser.getName());
                CommomDTO commomDTO = new CommomDTO();
                commomDTO.setInfo(ResultEnum.SUCCESS,map);
                return commomDTO;
            }catch(UnknownAccountException e){
                e.printStackTrace();
                return new CommomDTO(ResultEnum.UNKNOWN_ACCOUNT_ERROR);
            }catch(IncorrectCredentialsException e){
                e.printStackTrace();
                return new CommomDTO(ResultEnum.INCORRECT_CREDENTIALS_ERROR);
            }catch(LockedAccountException e){
                e.printStackTrace();
                return new CommomDTO(ResultEnum.INCORRECT_STATE_ERROR);
            }catch (Exception e) {
                e.printStackTrace();
                return new CommomDTO(ResultEnum.OTHER_LOGIN_ERRROR);
            }
        }
    }

}
