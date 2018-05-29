package com.lids.quartz;

import com.lids.dao.BookingDao;
import com.lids.dao.CreditDao;
import com.lids.po.BookingRecord;
import com.lids.service.impl.QuartzService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

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

    @Transactional
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取预定记录ID
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String bookingRecordId = dataMap.getString("bookingRecordId");

        //根据记录ID获取预约记录
        BookingRecord bookingRecord = bookingDao.getBookingRecordById(Integer.valueOf(bookingRecordId));
//        BookingRecord bookingRecord = bookingDao.getBookingRecordById(80);
        if (bookingRecord == null) {
            logger.debug("预定记录为空");
        }else {
            //判断用户是否进行了签到
            int hasCheckIn = bookingRecord.getHasCheckIn();
            if (hasCheckIn == 0){
                /**
                 * 1：更新预约记录状态为违约
                 * 2: 查询用户扣分
                 * 3：查询违约规则名称
                 * 4：进行扣分
                 * 5：插入违约记录
                 * 6：判断用户是否应该被禁用（禁用要设置解禁时间作业）
                 */
                bookingDao.setCredit(Integer.valueOf(bookingRecordId));
                String userTypeName = creditDao.getUserTypeName(bookingRecord.getUserId());
                int scoreRedule = creditDao.getScoreRedule(userTypeName);
                String creditRuleName = creditDao.getCreditRuleName(userTypeName);
                int scoreResult = creditDao.updateUserScore(bookingRecord.getUserId(),scoreRedule);
                int result = creditDao.insertCreditRecord(bookingRecord.getUserId(),Integer.valueOf(bookingRecordId),scoreRedule,creditRuleName);

                //用户积分小于零被禁用
                int score = creditDao.selectUserScore(bookingRecord.getUserId());
                if (score <= 0){
                    int banResult = creditDao.updateUserBan(bookingRecord.getUserId());
                    //添加解禁任务
                    QuartzService.addJobForLiftBan(bookingRecord.getUserId(),"time");
                    logger.debug("禁用用户:"+bookingRecord.getUserId());
                }

                //调用存储过程
//                Map<String,String> map = new HashMap<String, String>();
//                map.put("userId",bookingRecord.getUserId()+"");
//                map.put("bookingRecordId",bookingRecord.getId()+"");
//                map.put("creditType","1");
//                //TODO 调用数据库出错
//                creditDao.newCreditRecord(bookingRecord.getUserId(),bookingRecord.getId(),1);

                logger.debug("添加新的违约记录");
                return;
            }
            logger.debug("没有违约");
        }
    }

}
