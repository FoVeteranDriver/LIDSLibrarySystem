package com.lids.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.po.User;
import com.lids.service.UserService;
import com.lids.util.HttpClientUtil;
import com.lids.util.ProjectProperties;
import com.lids.util.WechatUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import net.sf.ehcache.Element;
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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            User getUser = userService.selectUserByLibraryCardNumber(user.getLibraryCardNumber());
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

    /**
     * 利用二维码登陆,提供给微信扫码后跳转
     * @param uuid
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/loginByQRCode",method = RequestMethod.GET)
    @ResponseBody
    public String doLogin(@RequestParam String uuid,HttpServletRequest request) throws Exception{
        String code = request.getParameter("code");
        if (QRCodeController.uuidCache.get(uuid)==null){
            return "二维码失效";
        }
        if(code==null){
            return "code为空";
        }
        String openid = WechatUtil.getOpenId(code);
        if (openid.equals("false")){
            return "用户未关注公众号";
        }
        User user = userService.selectUserByOpenId(openid);
        if (user == null){
            return "用户尚未绑定";
        }

//        QRCodeController.cache.put(uuid,openid);
        QRCodeController.uuidCache.put(new Element(uuid,openid));

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
    public CommomDTO checkScan(@RequestParam String uuid){
//        String result = QRCodeController.cache.get(uuid);
        Element element = QRCodeController.uuidCache.get(uuid);
        String result = (String)element.getValue();

        if (result == null || result.equals("")) {
            return new CommomDTO(ResultEnum.QRCODE_FAILURE);
        }
        if (result.equals(QRCodeController.one)){
            return new CommomDTO(ResultEnum.NOT_SCAN);
        }else{
            String openId = result;

            User user = userService.selectUserByOpenId(openId);

            if (user == null){
                return new CommomDTO(ResultEnum.NO_BINDING);
            }else{
                logger.debug("用户"+openId+"登陆");
                UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(),user.getPassword());
                SecurityUtils.getSubject().login(token);
            }
//            QRCodeController.cache.remove(uuid);
            QRCodeController.uuidCache.remove(uuid);

            User getUser = userService.selectUserByLibraryCardNumber(user.getLibraryCardNumber());
            Map<String,String> map = new HashMap<String, String>();
            map.put("name",getUser.getName());
            CommomDTO commomDTO = new CommomDTO();
            commomDTO.setInfo(ResultEnum.SUCCESS,map);

            return commomDTO;
        }
    }

    @RequestMapping(value = "/checkScanEvent")
    public void checkScanEvent(@RequestParam String uuid,HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();

        CommomDTO commomDTO = new CommomDTO();

        while (commomDTO.getMsg() == null || commomDTO.getMsg().equals("")) {
            Element element = QRCodeController.uuidCache.get(uuid);
            String result = (String) element.getValue();
            if (result == null || result.equals("")) {
                logger.debug("二维码错误");
                commomDTO.setInfo(ResultEnum.QRCODE_FAILURE);
            } else if (result.equals(QRCodeController.one)) {
//                logger.debug("用户未扫码");
            } else {
                String openId = result;
                logger.debug("用户openid:" + openId + "已扫码");
                User user = userService.selectUserByOpenId(openId);

                if (user == null) {
                    commomDTO.setInfo(ResultEnum.NO_BINDING);
                } else {
                    logger.debug("用户" + openId + "登陆");
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getLibraryCardNumber(), user.getPassword());
                    SecurityUtils.getSubject().login(token);
                    User getUser = userService.selectUserByLibraryCardNumber(user.getLibraryCardNumber());
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("name", getUser.getName());
                    commomDTO.setInfo(ResultEnum.SUCCESS, map);
                }
                QRCodeController.uuidCache.remove(uuid);
            }
        }
            String json = JSON.toJSONString(commomDTO);
            out.println(json);
            out.println();
            out.flush();
            out.close();
    }



    /**
     * 模糊查询用户
     * @param key 关键字
     * @return
     */
    @RequestMapping("/searchUser")
    @ResponseBody
    public CommomDTO fuzzySearch(@RequestParam String key){
        List<Map<String,String>> result = userService.fuzzySearch(key);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 返回用户近三个月的预约记录,带分页
     * @return
     */
    @RequestMapping("/userRecords")
    @ResponseBody
    public CommomDTO getUserBookingRecords(@RequestParam(defaultValue = "1") Integer page,@RequestParam(defaultValue = "new") String type){
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");

        //TODO 去掉权限测试
        if (user == null){
            user = new User();
            user.setId(28);
        }

        List<Map<String,String>> result;
        if (type.equals("new")){
            result = userService.selectNewBookingByUser(user.getId(),page);
        }else if (type.equals("old")){
            result = userService.selectBookingRecordsByUser(user.getId(),page);
        }else if (type.equals("credit")){
            result = userService.selectCreditRecordsByUser(user.getId(),page);
        }else {
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }

        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }


    /**
     * 返回用户近三个月的预约记录
     * @return
     */
    @RequestMapping("/userBookingRecords")
    @ResponseBody
    public CommomDTO getUserBookingRecords(){
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");

        //TODO 去掉权限测试
        if (user == null){
            user = new User();
            user.setId(28);
        }

        List<Map<String,String>> result = userService.selectAllBookingRecordsByUser(user.getId());

        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
