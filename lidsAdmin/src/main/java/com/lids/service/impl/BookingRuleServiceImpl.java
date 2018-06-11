package com.lids.service.impl;

import com.lids.dao.BookingRuleDao;
import com.lids.po.BookingRule;
import com.lids.service.BaseService;
import com.lids.service.BookingRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("BookingRuleService")
public class BookingRuleServiceImpl extends BaseService implements BookingRuleService{

    @Resource
    private BookingRuleDao bookingRuleDao;

    @Override
    public boolean addNewBookingRule(BookingRule bookingRule) {
        int result = bookingRuleDao.insert(bookingRule);
        if (result == 1){
            return true;
        }
        return false;
    }

    @Override
    public BookingRule getRule(int bookingRuleId) {
        BookingRule result = bookingRuleDao.selectById(bookingRuleId);
        return result;
    }

    @Override
    public boolean editRule(BookingRule bookingRule) {
        if (bookingRuleDao.delete(bookingRule.getId()) == 1){
            return addNewBookingRule(bookingRule);
        }
        return false;
    }
}
