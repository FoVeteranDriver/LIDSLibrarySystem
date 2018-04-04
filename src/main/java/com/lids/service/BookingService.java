package com.lids.service;

import com.lids.po.BookingRecord;

import java.util.List;
import java.util.Map;

public interface BookingService {

    boolean addNewBooking(BookingRecord bookingRecord);

    boolean addNewBooking(BookingRecord bookingRecord,String[] partners);

    List<Map<String,String>> getTodayRecords();

}
