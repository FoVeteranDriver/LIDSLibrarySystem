package com.lids.controller;

import com.lids.po.AdminUser;
import com.lids.service.AdminUserService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("user")
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    /**
     * 管理员登陆
     * @param params
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO login(@RequestBody Map<String,String> params){
        AdminUser getUser = adminUserService.selectAdminUserByUserName(params.get("account"));
        if (getUser == null){
            return new CommomDTO(ResultEnum.FAILED);
        }else{
            UsernamePasswordToken token = new UsernamePasswordToken(params.get("account"),params.get("password"));
            try{
                SecurityUtils.getSubject().login(token);
                Map map = new HashMap();
                map.put("account",getUser.getAccount());
                List accessList = adminUserService.generateMap(params.get("account"));
                //登陆2权限也要全部变成1，前端方便处理
                for (int i=0;i<3;i++){
                    Map one = (Map)accessList.get(i);
                    if ((Integer)one.get("access") == 2){
                        one.put("access",1);
                    }
                    List children = (List) one.get("children");
                    for (Object o:
                         children) {
                        Map child = (Map)o;
                        if ((Integer)child.get("access") == 2){
                            child.put("access",1);
                        }
                    }
                }

                map.put("accessList",accessList);

                CommomDTO commomDTO = new CommomDTO();
                commomDTO.setInfo(ResultEnum.SUCCESS,map);
                return commomDTO;
            }catch(UnknownAccountException e){
                e.printStackTrace();
                return new CommomDTO(ResultEnum.UNKNOWN_ACCOUNT_ERROR);
            }catch(IncorrectCredentialsException e){
                e.printStackTrace();
                return new CommomDTO(ResultEnum.INCORRECT_CREDENTIALS_ERROR);
            }catch (Exception e) {
                e.printStackTrace();
                return new CommomDTO(ResultEnum.OTHER_LOGIN_ERRROR);
            }
        }
    }

    /**
     * 管理员注销
     * @return
     */
    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO userLogout(){
        SecurityUtils.getSubject().logout();
        return new CommomDTO(ResultEnum.SUCCESS);
    }

    /**
     * 新建管理员
     * @param params
     * @return
     */
    @RequestMapping(value = "/addAdmin",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addAdmin(@RequestBody Map params){
        if (params.get("account") == null || params.get("account").equals("")
                ||params.get("password")==null || params.get("password").equals("")
                ||params.get("name")==null
                ||params.get("email")==null
                ||params.get("phone")==null
                ||params.get("accessList")==null){
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }

        AdminUser user = adminUserService.selectAdminUserByUserName((String)params.get("account"));
        if (user != null){
            return new CommomDTO(ResultEnum.ACCOUNT_EXIST);
        }

        if (adminUserService.addNewAdminUser(params)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    /**
     * 判断管理员账号是否可用
     * @param account
     * @return
     */
    @RequestMapping(value = "/isAccountExist",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO isAccountExist(@RequestParam String account){
        AdminUser user = adminUserService.selectAdminUserByUserName(account);
        if (user != null){
            return new CommomDTO(ResultEnum.ACCOUNT_USE);
        }
        return new CommomDTO(ResultEnum.ACCOUNT_NOTUSER);
    }

    /**
     * 获取管理员列表
     * @return
     */
    @RequestMapping(value = "/adminList",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO adminList(){
        List<AdminUser> result = adminUserService.selectAdminList();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 删除管理员
     * @param account
     * @return
     */
    @RequestMapping(value = "/deleteAdmin",method = RequestMethod.DELETE)
    @ResponseBody
    public CommomDTO deleteAdmin(@RequestParam String account){
        if (account==null || account.equals("")){
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        adminUserService.deleteAdmin(account);
        return new CommomDTO(ResultEnum.SUCCESS);
    }

    /**
     * 根据关键字搜索管理员
     * @param key 关键字
     * @return
     */
    @RequestMapping(value = "searchAdmin",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO searchAdmin(@RequestParam String key){
        List<AdminUser> result = adminUserService.searchAdmin(key);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
