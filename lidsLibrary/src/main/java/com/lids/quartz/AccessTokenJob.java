package com.lids.quartz;

import com.lids.util.AccessTokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yaoyou
 * @description 定期更新accesstoken
 */
public class AccessTokenJob{

    private static Logger logger = LoggerFactory.getLogger(AccessTokenJob.class);

    public void execute(){
        logger.debug("执行从微信服务器获取accesstoken的任务");
        AccessTokenUtil accessTokenUtil = AccessTokenUtil.getInstance();
        accessTokenUtil.getAccessTokenFromWechat();
    }

}
