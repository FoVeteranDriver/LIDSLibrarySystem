package com.lids.service.impl;

import com.lids.dao.BookingDao;
import com.lids.dao.SpaceDao;
import com.lids.po.BookingRecord;
import com.lids.po.User;
import com.lids.quartz.CreditDetectJob;
import com.lids.service.BookingService;
import com.lids.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("BookingService")
public class BookingServiceImpl implements BookingService{

    private static Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Resource
    private BookingDao bookingDao;
    @Resource
    private SpaceDao spaceDao;

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

        //TODO 可以使用spring aop进行分离
        //添加定时任务判断是否违约
        String time = TimeUtil.formatDate(bookingRecord.getDate().getTime())
                +" "
                +TimeUtil.formatTime(bookingRecord.getEndTime().getTime());
        QuartzService.addJob(time, CreditDetectJob.class,recordId+"");

        //根据是否有同伴决定是否预定研习间
        if (partners == null || spaceDao.getSeatById(bookingRecord.getSpaceId()).getSpaceTypeName().equals("标准座位")){
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

    /**
     * 检查是否有预约
     * @param date
     * @param beginTime
     * @param endTime
     * @param spaceId
     * @return 返回true代表有预约，返回false代表没有预约
     */
    public boolean getBookingNowByTime(Date date, Date beginTime, Date endTime, int spaceId) {
        List<Integer> bookingId = bookingDao.getBookingNowByTime(date,beginTime,endTime,spaceId);
        if (bookingId == null || bookingId.isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}
