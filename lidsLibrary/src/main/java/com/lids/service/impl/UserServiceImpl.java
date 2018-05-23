package com.lids.service.impl;

import com.lids.common.BaseService;
import com.lids.dao.CreditDao;
import com.lids.dao.UserDao;
import com.lids.po.User;
import com.lids.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl extends BaseService implements UserService{

    private static final Integer numPerPage = 10;

    @Resource
    private UserDao userDao;
    @Resource
    private CreditDao creditDao;

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
        List<Map<String, String>> result = userDao.selectThreeMonthBookingRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
        //去除开始时间和结束时间的秒数
        for (Map one : result){
            String beginTime = one.get("begin_time").toString();
            String endTime = (String)one.get("end_time").toString();
            one.put("begin_time",beginTime.substring(0,5));
            one.put("end_time",endTime.substring(0,5));

            if ((Boolean) one.get("is_room")){
                List<Map> partner = userDao.selectPartnerByRecordId(((Long)one.get("id")).intValue());
                one.put("partner",partner);
            }else {
                one.put("partner","");
            }
            one.remove("is_room");
        }
        return result;
    }

    public List<Map<String, String>> selectAllBookingRecordsByUser(int userId) {
        return userDao.selectAllThreeMonthBookingRecordsByUser(userId);
    }

    public List<Map<String, String>> selectCreditRecordsByUser(int userId, int page) {
        List<Map<String,String>> result = userDao.selectThreeMonthCreditRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
        for (Map one : result){
            String beginTime = one.get("begin_time").toString();
            String endTime = (String)one.get("end_time").toString();
            one.put("begin_time",beginTime.substring(0,5));
            one.put("end_time",endTime.substring(0,5));

            if ((Boolean) one.get("is_room")){
                List<Map> partner = userDao.selectPartnerByRecordId(((Long)one.get("bookingRecordId")).intValue());
                one.put("partner",partner);
            }else {
                one.put("partner","");
            }
            one.remove("is_room");
            one.remove("bookingRecordId");
        }
        return result;
    }

    public List<Map<String, String>> selectNewBookingByUser(int userId, int page) {
        //查询用户新预约记录
        List<Map<String, String>> result = userDao.selectNewBookingRecords(userId,(page-1)*numPerPage,numPerPage);
        //去除开始时间和结束时间的秒数
        for (Map one : result){
            String beginTime = one.get("begin_time").toString();
            String endTime = (String)one.get("end_time").toString();
            one.put("begin_time",beginTime.substring(0,5));
            one.put("end_time",endTime.substring(0,5));

            if ((Boolean) one.get("is_room")){
                List<Map> partner = userDao.selectPartnerByRecordId(((Long)one.get("id")).intValue());
                one.put("partner",partner);
            }else {
                one.put("partner","");
            }
            one.remove("is_room");
        }
        return result;
    }

    public Map<String, String> selectUserInfo(String libraryCardNumber) {
        return userDao.selectUserInfo(libraryCardNumber);
    }

    public String selectUserScore(String libraryCardNumber) {
        return userDao.selectUserScore(libraryCardNumber);
    }

    public void updateUserInfo(int userId, String telephone, String email) {
        userDao.updateUserInfo(userId,telephone,email);
    }

    public String selectTotalScore(String userTypeName) {
        return userDao.selectTotalScore(userTypeName);
    }

    public List<Map<String,String>> selectDeductionRecord(int userId) {
        List<Map<String,String>> result = userDao.selectDeductionRecord(userId);
        //格式化时间
        for (Map one : result){
            String credit_time = one.get("credit_time").toString();
            one.put("credit_time",credit_time.substring(0,10));
        }
        return result;
    }

    public void deleteBookingRecord(int bookingRecordId) {
        userDao.deleteBookingRecord(bookingRecordId);
    }

    @Override
    public Integer getBanStatus(int userId) {
        return creditDao.getUserBan(userId);
    }
}
