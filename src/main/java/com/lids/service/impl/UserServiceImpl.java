package com.lids.service.impl;

import com.lids.dao.UserDao;
import com.lids.po.User;
import com.lids.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService{

    @Resource
    private UserDao userDao;

    public boolean signUp(User user){
        int result =userDao.signUp(user);
        if (result >= 0){
            return true;
        }else {
            return false;
        }

    }

    public User selectUserByOpenId(String openId) {
        User user = userDao.selectUserByOpenId(openId);
        return user;
    }

}
