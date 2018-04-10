package com.lids.service.impl;

import com.lids.dao.UserDao;
import com.lids.po.User;
import com.lids.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public User selectUserByLibraryCardNumber(String libraryCardNumber) {
        return userDao.selectUserByLibraryCardNumber(libraryCardNumber);
    }

    public List<Map<String,String>> fuzzySearch(String key) {
        Map keyMap = new HashMap<String,String>();
        keyMap.put("key",key);
        List<Map<String,String>> result = userDao.fuzzySearch(keyMap);
        return result;
    }

    public List<Map<String, String>> selectBookingRecordsByUser(int userId) {
        return userDao.selectThreeMonthBookingRecordsByUser(userId);
    }
}
