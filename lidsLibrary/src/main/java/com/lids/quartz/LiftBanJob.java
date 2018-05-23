package com.lids.quartz;

import com.lids.dao.CreditDao;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 解除用户禁用Job
 */
@Component
public class LiftBanJob implements Job{

    private static Logger logger = LoggerFactory.getLogger(LiftBanJob.class);

    @Resource
    private CreditDao creditDao;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        int userId = dataMap.getInt("userId");

        creditDao.liftUserBan(userId);

        logger.debug("解禁用户:"+userId);
    }

}
