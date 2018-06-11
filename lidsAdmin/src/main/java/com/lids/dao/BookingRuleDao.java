package com.lids.dao;

import com.lids.po.BookingRule;

public interface BookingRuleDao {

    int insert(BookingRule bookingRule);

    BookingRule selectById(int bookingRuleId);

    int delete(int bookRuleId);
}
