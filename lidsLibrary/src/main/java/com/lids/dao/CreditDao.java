package com.lids.dao;

import java.util.Map;

public interface CreditDao {

    void newCreditRecord(int userId, int recordId, int type);

    String getUserTypeName(int userId);

    Integer getScoreRedule(String userTypeName);

    String getCreditRuleName(String userTypeName);

    Integer insertCreditRecord(int userId,int bookingRecordId,int scoreReduce,String creditRuleName);

    Integer updateUserScore(int userId,int scoreReduce);

    Integer selectUserScore(int userId);

    Integer updateUserBan(int userId);

    Integer liftUserBan(int userId);

    Integer getUserBan(int userId);
}
