package com.lids.service;

import com.lids.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean signUp(User user);

    User selectUserByOpenId(String openId);

    User selectUserByLibraryCardNumber(String libraryCardNumber);

    List<Map<String,String>> fuzzySearch(String key);

    List<Map<String,String>> selectAllBookingRecordsByUser(int userId);

    Map<String,String> selectUserInfo(String libraryCardNumber);

    boolean updateUserInfo(String telephone, String email);

    boolean deleteBookingRecord(int bookingRecordId);

    List<Map<String,String>> getRecords(String type,int page);

    Map<String,Object> getScore();
}
