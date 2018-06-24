package com.lids.dao;

import com.lids.po.BookingRule;

import java.util.List;
import java.util.Map;

public interface BookingRuleDao {

    int insert(BookingRule bookingRule);

    BookingRule selectById(int bookingRuleId);

    int delete(int bookRuleId);

    List<Map> getAllRule();
}
