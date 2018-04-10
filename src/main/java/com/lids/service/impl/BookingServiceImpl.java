package com.lids.service.impl;

import com.lids.dao.BookingDao;
import com.lids.po.BookingRecord;
import com.lids.po.User;
import com.lids.service.BookingService;
import com.lids.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BookingService")
public class BookingServiceImpl implements BookingService{

    private static Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Resource
    private BookingDao bookingDao;

    public boolean addNewBooking(BookingRecord bookingRecord) {
        return addNewBooking(bookingRecord,null);
    }

    public boolean addNewBooking(BookingRecord bookingRecord, String[] partners) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        bookingRecord.setUserId(user.getId());
        bookingDao.addNewBooking(bookingRecord);
        int recordId = bookingRecord.getId();
        if (recordId == 0){
            return false;
        }
        if (partners == null){
            logger.debug("用户"+user.getLibraryCardNumber()+"预定座位");
        }else {
            logger.debug("用户"+user.getLibraryCardNumber()+"预订研习间");
            Map<String,Integer> map = new HashMap<String, Integer>();
            for (String partnerId:partners) {
                map.put("userId",Integer.valueOf(partnerId));
                map.put("recordId",recordId);
                logger.debug("用户"+partnerId+"参与预订；预订记录："+recordId);
                int result = bookingDao.addParnter(map);
                logger.debug("影响了"+result+"行");
                if (recordId <= 0){
                    return false;
                }
            }
        }
        return true;
    }

    public List<Map<String, String>> getTodayRecords() {
        return bookingDao.getTodayRecords(TimeUtil.getTodayDate());
    }

    public BookingRecord getNowBooking(int spaceId) {
        return bookingDao.getBookingNow(spaceId);
    }
}
