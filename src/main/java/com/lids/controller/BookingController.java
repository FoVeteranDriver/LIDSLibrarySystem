package com.lids.controller;

import com.lids.po.BookingRecord;
import com.lids.service.BookingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@RequestMapping("/book")
public class BookingController {

    @Resource
    private BookingService bookingService;

    @RequestMapping("addNewBooking")
    @ResponseBody
    public String addNewBooking(){
        BookingRecord bookingRecord = new BookingRecord();
        bookingRecord.setUserId(123);
        bookingRecord.setSpaceId(1);
        bookingRecord.setDate(new Date());
        bookingRecord.setBeginTime(new Date());
        bookingRecord.setEndTime(new Date());
        bookingRecord.setApplication("测试");
        bookingService.addNewBooking(bookingRecord);
        return  null;
    }

}
