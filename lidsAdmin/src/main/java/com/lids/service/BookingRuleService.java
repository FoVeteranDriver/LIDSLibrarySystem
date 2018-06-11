package com.lids.service;

import com.lids.po.BookingRule;

public interface BookingRuleService {

    boolean addNewBookingRule(BookingRule bookingRule);

    BookingRule getRule(int bookingRuleId);

    boolean editRule(BookingRule bookingRule);
}
