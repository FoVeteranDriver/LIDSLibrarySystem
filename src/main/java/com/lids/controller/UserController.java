package com.lids.controller;

import com.alibaba.fastjson.JSONObject;
import com.lids.po.User;
import com.lids.service.UserService;
import com.lids.util.HttpClientUtil;
import com.lids.util.ProjectProperties;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserService userService;

    /**
     * 用户公众号与借书证绑定接口
     * @param user
     * @param request
     * @return
     */
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

    /**
     * 账号密码登陆
     * @param user
     * @return
     */
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

    /**
     * 利用二维码登陆
     * @param uuid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginByQRCode",method = RequestMethod.GET)
    @ResponseBody
    public String doLogin(@RequestParam String uuid,HttpServletRequest request) throws Exception{
        String code = request.getParameter("code");
        if (!QRCodeController.cache.containsKey(uuid)){
            return "二维码失效";
        }
        if(code==null){
            return "code为空";
        }
        String getOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        getOpenIdUrl=getOpenIdUrl.replace("APPID", ProjectProperties.appid);
        getOpenIdUrl=getOpenIdUrl.replace("SECRET",ProjectProperties.secret);
        String requestUrl = getOpenIdUrl.replace("CODE",code);

        String sendResponse = HttpClientUtil.doGet(requestUrl,null);

        JSONObject jsonObject = JSONObject.parseObject(sendResponse);
        String openid = jsonObject.getString("openid");

        QRCodeController.cache.put(uuid,openid);

        return "扫码成功";
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

    /**
     * PC端不断进行轮询检查的方法
     * @param uuid
     * @return
     */
    @RequestMapping(value = "/checkScan")
    @ResponseBody
    public String checkScan(@RequestParam String uuid){
        String result = QRCodeController.cache.get(uuid);
        if (result == null || result.equals("")) {
            return "请重新扫描";
        }
        if (result.equals(QRCodeController.one)){
            return QRCodeController.one;
        }else{
            String openId = result;

            User user = userService.selectUserByOpenId(openId);

            if (user == null){
                return "用户与借书证尚未绑定";
            }else{
                logger.debug("用户"+openId+"登陆");
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(),user.getPassword());
                SecurityUtils.getSubject().login(token);
            }
            QRCodeController.cache.remove(uuid);
            return "登陆成功";
        }
    }

}
