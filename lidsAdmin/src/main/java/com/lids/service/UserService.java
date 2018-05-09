package com.lids.service;

import com.lids.po.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    boolean signUp(User user);

    User selectUserByOpenId(String openId);

    User selectUserByLibraryCardNumber(String libraryCardNumber);

    List<Map<String,String>> fuzzySearch(String key);

    List<Map<String,String>> selectBookingRecordsByUser(int userId, int page);

    List<Map<String,String>> selectAllBookingRecordsByUser(int userId);

    List<Map<String,String>> selectCreditRecordsByUser(int userId, int page);

    List<Map<String,String>> selectNewBookingByUser(int userId, int page);

    Map<String,String> selectUserInfo(String libraryCardNumber);

    Map<String,String> selectUserScore(String libraryCardNumber);

    void updateUserInfo(int userId, String telephone, String email);
}
