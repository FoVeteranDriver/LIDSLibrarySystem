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
public class UserServiceImpl implements UserService {

    private static final Integer numPerPage = 10;

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

    public List<Map<String, String>> selectBookingRecordsByUser(int userId,int page) {
        return userDao.selectThreeMonthBookingRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
    }

    public List<Map<String, String>> selectAllBookingRecordsByUser(int userId) {
        return userDao.selectAllThreeMonthBookingRecordsByUser(userId);
    }

    public List<Map<String, String>> selectCreditRecordsByUser(int userId, int page) {
        //TODO 查询用户近三个月违约记录
        return userDao.selectThreeMonthCreditRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
    }

    public List<Map<String, String>> selectNewBookingByUser(int userId, int page) {
        //TODO 查询用户新预约记录
        return userDao.selectNewBookingRecords(userId,(page-1)*numPerPage,numPerPage);
    }

    public Map<String, String> selectUserInfo(String libraryCardNumber) {
        return userDao.selectUserInfo(libraryCardNumber);
    }

    public Map<String, String> selectUserScore(String libraryCardNumber) {
        return userDao.selectUserScore(libraryCardNumber);
    }

    public void updateUserInfo(int userId, String telephone, String email) {
        userDao.updateUserInfo(userId,telephone,email);
    }
}
