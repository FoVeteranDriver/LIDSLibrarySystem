package com.lids.service.impl;

import com.lids.dao.BookingDao;
import com.lids.po.BookingRecord;
import com.lids.service.BookingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("BookingService")
public class BookingServiceImpl implements BookingService{

    @Resource
    private BookingDao bookingDao;

    public void addNewBooking(BookingRecord bookingRecord) {
        bookingDao.addNewBooking(bookingRecord);
    }

}
