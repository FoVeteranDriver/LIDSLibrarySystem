package com.lids.dao;

import com.lids.po.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    Integer signUp(User user);

    User selectUserByOpenId(String openid);

    User selectUserByLibraryCardNumber(String libraryCardNumber);

    List<Map<String,String>> fuzzySearch(Map<String,String> key);

    List<Map<String,String>> selectThreeMonthBookingRecordsByUser(int userId);
}
