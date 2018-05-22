package com.lids.util;

import com.lids.dao.LogDao;
import com.lids.po.Log;
import com.lids.po.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 日志工具类
 */
@Component
public class LogUtil {

    private static LogDao logDao;

    @Autowired
    public void setLogService(LogDao logDao) {
        LogUtil.logDao = logDao;
    }

    public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
        User user = (User)SecurityUtils.getSubject().getSession().getAttribute("user");
        if (user != null){
            Map r = request.getParameterMap();
            Log log = new Log();
            log.setTitle(title);
            log.setRemoteAddr(StringUtils.getRemoteAddr(request));
            log.setUserAgent(request.getHeader("user-agent"));
            log.setRequestUri(request.getRequestURI());
            log.setParams(request.getParameterMap());
            log.setMethod(request.getMethod());

            log.setUserId(user.getId());
            log.setUserType("用户");
            log.setUserName(user.getName());
            log.setException(ExceptionUtil.getStackTraceAsString(ex));

            // 异步保存日志
            new SaveLogThread(log,handler,ex).start();
        }
    }

    /**
     * 保存日志线程
     */
    public static class SaveLogThread extends Thread{

        private Log log;
        private Object handler;
        private Exception ex;

        public SaveLogThread(Log log, Object handler, Exception ex){
            super(SaveLogThread.class.getSimpleName());
            this.log = log;
            this.handler = handler;
            this.ex = ex;
        }

        @Override
        public void run() {
            logDao.insert(log);
        }
    }

}
