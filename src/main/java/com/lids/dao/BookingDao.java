package com.lids.dao;

import com.lids.po.BookingRecord;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingDao {

    void addNewBooking(BookingRecord bookingRecord);

    Integer addParnter(Map<String,Integer> map);

    List<Map<String,String>> getTodayRecords(Date todayDate);

    BookingRecord getBookingNow(int spaceId);
}
