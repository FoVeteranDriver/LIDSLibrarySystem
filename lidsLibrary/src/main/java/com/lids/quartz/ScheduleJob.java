package com.lids.quartz;


import com.lids.dao.ScheduleDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author yaoyou
 * @description 定期生成调度表
 */
public class ScheduleJob {

    private static Logger logger = LoggerFactory.getLogger(ScheduleJob.class);

    @Resource
    private ScheduleDao scheduleDao;

    public void execute(){
        Integer result = scheduleDao.generateSchedule(new Date());
        logger.debug("新建调度记录"+result+"条");
    }
    
}
