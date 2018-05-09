package com.lids.dao;

import com.lids.po.User;

import java.util.List;
import java.util.Map;

public interface UserDao {

    Integer signUp(User user);

    User selectUserByOpenId(String openid);

    User selectUserByLibraryCardNumber(String libraryCardNumber);

    List<Map<String,String>> fuzzySearch(Map<String, String> key);

    List<Map<String,String>> selectThreeMonthBookingRecordsByUser(int userId, int page, int numPerPage);

    List<Map<String,String>> selectThreeMonthCreditRecordsByUser(int userId, int page, int numPerPage);

    List<Map<String,String>> selectNewBookingRecords(int userId, int page, int numPerPage);

    List<Map<String,String>> selectAllThreeMonthBookingRecordsByUser(int userId);

    Map<String,String> selectUserInfo(String libraryCardNumber);

    String selectUserScore(String libraryCardNumber);

    void updateUserInfo(int userId, String telephone, String email);

    String selectTotalScore(String userTypeName);

    List<Map<String,String>> selectDeductionRecord(int userId);

    List<Map> selectPartnerByRecordId(int bookingRecordId);

    void deleteBookingRecord(int bookingRecordId);
}
