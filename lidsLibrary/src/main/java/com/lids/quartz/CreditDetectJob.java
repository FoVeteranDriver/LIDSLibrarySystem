package com.lids.quartz;

import com.lids.dao.BookingDao;
import com.lids.dao.CreditDao;
import com.lids.po.BookingRecord;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查是否违约记录
 * @author yaoyou
 */
@Component
public class CreditDetectJob implements Job{

    private static Logger logger = LoggerFactory.getLogger(CreditDetectJob.class);

    @Resource
    private  BookingDao bookingDao;
    @Resource
    private CreditDao creditDao;

//    private String bookingRecordId;
//    public void setBookingRecordId(String bookingRecordId) {
//        this.bookingRecordId = bookingRecordId;
//    }

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String bookingRecordId = dataMap.getString("bookingRecordId");

        BookingRecord bookingRecord = bookingDao.getBookingRecordById(Integer.valueOf(bookingRecordId));
//        BookingRecord bookingRecord = bookingDao.getBookingRecordById(80);
        if (bookingRecord == null) {
            logger.debug("预定记录为空");
        }else {
            int hasCheckIn = bookingRecord.getHasCheckIn();
            if (hasCheckIn == 0){
                Map<String,String> map = new HashMap<String, String>();
                map.put("userId",bookingRecord.getUserId()+"");
                map.put("bookingRecordId",bookingRecord.getId()+"");
                map.put("creditType","1");
                //TODO 调用数据库出错
//                creditDao.newCreditRecord(bookingRecord.getUserId(),bookingRecord.getId(),1);
                logger.debug("添加新的违约记录");
                return;
            }
            logger.debug("没有违约");
        }
    }

}
