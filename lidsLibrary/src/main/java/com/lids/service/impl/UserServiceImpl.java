package com.lids.service.impl;

import com.lids.common.BaseService;
import com.lids.dao.CreditDao;
import com.lids.dao.UserDao;
import com.lids.po.User;
import com.lids.service.UserService;
import org.apache.shiro.SecurityUtils;
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

    public List<Map<String, String>> selectAllBookingRecordsByUser(int userId) {
        return userDao.selectAllThreeMonthBookingRecordsByUser(userId);
    }

    @Override
    public List<Map<String, String>> getRecords(String type,int page) {
        List<Map<String,String>> result = null;
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        if (user != null){
            int userId = user.getId();
            if (type.equals("new")){
                result = userDao.selectNewBookingRecords(userId,(page-1)*numPerPage,numPerPage);
            }else if (type.equals("old")){
                result = userDao.selectThreeMonthBookingRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
            }else if (type.equals("credit")){
                result = userDao.selectThreeMonthCreditRecordsByUser(userId,(page-1)*numPerPage,numPerPage);
                for (Map one : result){
                    one.remove("bookingRecordId");
                }
            }
        }
        for (Map one : result){
            String beginTime = one.get("begin_time").toString();
            String endTime = (String)one.get("end_time").toString();
            one.put("begin_time",beginTime.substring(0,5));
            one.put("end_time",endTime.substring(0,5));

            if ((Boolean) one.get("is_room")){
                int recordId;
                if (one.get("id") instanceof Integer){
                    recordId = ((Integer) one.get("id")).intValue();
                }else {
                    recordId = ((Long) one.get("id")).intValue();
                }
                List<Map> partner = userDao.selectPartnerByRecordId(recordId);
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

    public boolean updateUserInfo(String telephone, String email) {
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        int result = userDao.updateUserInfo(user.getId(),telephone,email);
        if (result == 1){
            return true;
        }
        return false;
    }

    public boolean deleteBookingRecord(int bookingRecordId) {
        int result = userDao.deleteBookingRecord(bookingRecordId);
        if (result == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Map<String, Object> getScore() {
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        Map<String,Object> userInfo = new HashMap<>();
        String userNowScore = userDao.selectUserScore(user.getLibraryCardNumber());
        userInfo.put("userScore",userNowScore);
        String totalScore = userDao.selectTotalScore(user.getUserTypeName());
        userInfo.put("totalScore",totalScore);

        List<Map<String,String>> creditRecord = userDao.selectDeductionRecord(user.getId());
        //格式化时间
        for (Map one : creditRecord){
            String credit_time = one.get("credit_time").toString();
            one.put("credit_time",credit_time.substring(0,10));
        }
        userInfo.put("record",creditRecord);
        return userInfo;
    }
}
