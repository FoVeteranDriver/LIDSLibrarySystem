package com.lids.quartz;

import com.lids.controller.QRCodeController;
import com.lids.util.ProjectProperties;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class QRCodeDeleteJob implements Job{

    private static Logger logger = LoggerFactory.getLogger(QRCodeDeleteJob.class);

    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobKey key = jobExecutionContext.getJobDetail().getKey();
        JobDataMap dataMap = jobExecutionContext.getMergedJobDataMap();
        String fileName = dataMap.getString("fileName");
        String randomUUID = dataMap.getString("randomUUID");

        File file = new File(ProjectProperties.QRCODEURL,fileName);
        if (file.delete()){
            logger.debug(fileName+"被成功删除");
            QRCodeController.uuidCache.remove(randomUUID);
        }else {
            logger.debug(fileName+"删除失败");
        }

    }
}
