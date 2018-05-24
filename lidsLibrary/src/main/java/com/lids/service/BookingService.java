package com.lids.service;

import com.lids.po.BookingRecord;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BookingService {

    List<Map<String,String>> getTodayRecords(HttpSession session);

    BookingRecord getNowBooking(int spaceId);

    String addNewBooking(BookingRecord bookingRecord,String[] partners);
}
