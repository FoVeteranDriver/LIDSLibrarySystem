package com.lids.dao;

import com.lids.po.BookingRecord;

import java.util.Map;

public interface BookingDao {

    void addNewBooking(BookingRecord bookingRecord);

    Integer addParnter(Map<String,Integer> map);

}
