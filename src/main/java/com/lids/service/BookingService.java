package com.lids.service;

import com.lids.po.BookingRecord;

public interface BookingService {

    boolean addNewBooking(BookingRecord bookingRecord);

    boolean addNewBooking(BookingRecord bookingRecord,String[] partners);

}
