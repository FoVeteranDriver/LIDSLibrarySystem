package com.lids.service.impl;

import com.lids.quartz.CreditDetectJob;
import com.lids.quartz.LiftBanJob;
import com.lids.quartz.QRCodeDeleteJob;
import com.lids.util.TimeUtil;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

import static org.quartz.DateBuilder.dateOf;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.JobBuilder.newJob;

@Component
public class QuartzService {

    private static SchedulerFactoryBean schedulerFactoryBean;
    @Autowired
    public void setSchedulerFactoryBean(SchedulerFactoryBean schedulerFactoryBean) {
        QuartzService.schedulerFactoryBean = schedulerFactoryBean;
        try {
            scheduler = schedulerFactoryBean.getScheduler();
            scheduler.start();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
//    private static BookingDao bookingDao;
//    @Autowired
//    public void setBookingDao(BookingDao bookingDao) {
//        QuartzService.bookingDao = bookingDao;
//    }

    private static Logger logger = LoggerFactory.getLogger(QuartzService.class);
    private static Scheduler scheduler;
    private static String jobGroupName = "CreditDetectGroup";
    private static String jobGroupName1 = "QrCodeDeleteGroup";

    /**
     * 添加信用分减少的job
     * @param time 执行时间 yyyy-mm-dd hh:mm:ss
     * @param jobClass
     * @param bookingRecordId
     */
    public static void addJob(String time,Class<? extends Job> jobClass, String bookingRecordId){
        UUID randomUUID = UUID.randomUUID();
        JobDetail job = newJob(CreditDetectJob.class)
                .withIdentity(randomUUID.toString(), jobGroupName)
                .usingJobData("bookingRecordId", bookingRecordId)
                .build();

        Date date = null;
        try {
            date = TimeUtil.parseDateAndTime(time);
        }catch (Exception e){
            e.printStackTrace();
        }

        SimpleTrigger trigger = (SimpleTrigger) newTrigger()
                .withIdentity(randomUUID.toString(), jobGroupName)
                .startAt(date)
                .forJob(randomUUID.toString(), jobGroupName)
                .build();

//        //简略测试
//        SimpleTrigger  trigger = newTrigger()
//                .withIdentity(jobName, jobGroupName)
//                .withSchedule(simpleSchedule()
//                        .withIntervalInMinutes(1)
//                        .repeatForever())
//                .endAt(dateOf(22, 0, 0))
//                .build();

        try{
            scheduler.scheduleJob(job, trigger);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addJobForQrCodeDelete(String fileName,String randomUUID){
        JobDetail job = newJob(QRCodeDeleteJob.class)
                .withIdentity(randomUUID, jobGroupName1)
                .usingJobData("fileName", fileName)
                .usingJobData("randomUUID",randomUUID)
                .build();

        logger.debug("1分钟后删除"+fileName);

        SimpleTrigger  trigger = (SimpleTrigger)newTrigger()
                .withIdentity(randomUUID, jobGroupName)
                .startAt(futureDate(1, DateBuilder.IntervalUnit.MINUTE))
                .build();

        try{
            scheduler.scheduleJob(job, trigger);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 解除用户禁用
     * @param userId 解禁用户id
     * @param time 解禁时间
     */
    public static void addJobForLiftBan(int userId,String time){
        UUID randomUUID = UUID.randomUUID();
        JobDetail job = newJob(LiftBanJob.class)
                .withIdentity(randomUUID.toString(), jobGroupName)
                .usingJobData("userId", userId)
                .build();

        SimpleTrigger  trigger = (SimpleTrigger)newTrigger()
                .withIdentity(randomUUID.toString(), jobGroupName)
                .startAt(futureDate(3, DateBuilder.IntervalUnit.MINUTE))
                .build();

        try{
            scheduler.scheduleJob(job, trigger);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
