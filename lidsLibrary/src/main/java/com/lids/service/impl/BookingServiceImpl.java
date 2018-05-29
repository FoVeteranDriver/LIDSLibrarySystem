package com.lids.service.impl;

import com.alibaba.fastjson.JSON;
import com.lids.common.BaseService;
import com.lids.dao.BookingDao;
import com.lids.dao.CreditDao;
import com.lids.dao.SpaceDao;
import com.lids.dao.UserDao;
import com.lids.po.BookingRecord;
import com.lids.po.Space;
import com.lids.po.User;
import com.lids.quartz.CreditDetectJob;
import com.lids.quartz.LatestBookingRecordJob;
import com.lids.service.BookingService;
import com.lids.util.RedisPoolUtil;
import com.lids.util.TimeUtil;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

@Service("BookingService")
public class BookingServiceImpl extends BaseService implements BookingService{

    @Resource
    private BookingDao bookingDao;
    @Resource
    private SpaceDao spaceDao;
    @Resource
    private CreditDao creditDao;
    @Resource
    private LatestBookingRecordJob latestBookingRecordJob;

    public List<Map<String, String>> getTodayRecords(HttpSession session) {
//        Integer offset = (Integer)session.getAttribute("offset");
//        Integer count = (Integer)session.getAttribute("count");
//        //TODO 新预定优先返回
//        if (offset == null || offset == 0){
//            Integer maxId = bookingDao.getTopId();
//            count = bookingDao.getCount();
//            offset = 0;
//            session.setAttribute("maxId",maxId);
//            session.setAttribute("count",count);
//            session.setAttribute("offset",10);
//        }else if (offset >= count){
//            session.setAttribute("offset",0);
//            offset = 0;
//        }else {
//            session.setAttribute("offset",offset+10);
//        }
//        return bookingDao.getTodayRecords(TimeUtil.getTodayDate(),Integer.valueOf(offset));
        //初始化redis中的记录
        if (!LatestBookingRecordJob.init){
            try {
                latestBookingRecordJob.execute();
            }catch (Exception e){
                e.printStackTrace();
            }

            LatestBookingRecordJob.init = true;
        }

        Jedis jedis = null;
        List<Map<String,String>> resultList;
        String result;
        Integer newBookingRecordId;

        try {
            jedis = RedisPoolUtil.getJedis();
            newBookingRecordId = Integer.valueOf(jedis.get(LatestBookingRecordJob.newBookingRecordId));
        }finally {
            RedisPoolUtil.returnResource(jedis);
        }

        Integer group = (Integer)session.getAttribute("group");
        Integer maxIdThisExploer = (Integer)session.getAttribute("maxIdThisExploer");

//        if (maxIdThisExploer == null || newBookingRecordId > maxIdThisExploer){
        if (false){
            resultList = null;
        }else {
            //第一次请求则请求第一组
            if (group == null){
                group = 1;
            }

            //从redis获取数据
            try{
                jedis = RedisPoolUtil.getJedis();
                int gourpMax = Integer.valueOf(jedis.get(LatestBookingRecordJob.bookingRecordGroup));
                if (group > gourpMax){
                    group = 1;
                }
                result = jedis.get(LatestBookingRecordJob.getKey+group);
            }finally {
                RedisPoolUtil.returnResource(jedis);
            }
            resultList = JSON.parseObject(result,List.class);

            group ++ ;
            session.setAttribute("group",group);
        }

        return resultList;
    }

    public BookingRecord getNowBooking(int spaceId) {
        return bookingDao.getBookingNow(spaceId);
    }


    /**
     * 添加新预约
     * @param bookingRecord
     * @param partners
     * @return hasBooking已经有预定,scoreZERO用户积分为零,true成功,false失败
     */
    @Override
    public String addNewBooking(BookingRecord bookingRecord,String[] partners) {
        //判断选中的时间段是否有预约
        List<Integer> bookingId = bookingDao.getBookingNowByTime(bookingRecord.getDate(),bookingRecord.getBeginTime(),bookingRecord.getEndTime(),bookingRecord.getSpaceId());
        if (bookingId != null && !bookingId.isEmpty()){
            return "hasBooking";
        }

        //判断用户是否被禁用
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        int banStatus = creditDao.getUserBan(user.getId());
        if (banStatus == 1){
            return "scoreZERO";
        }

        //获取要预定的座位，判断是座位还是研习间
        Space space2Book = spaceDao.getSeatById(bookingRecord.getSpaceId());
        boolean isSeat = space2Book.getSpaceTypeName().equals("标准座位");
        if (isSeat){
            bookingRecord.setApplication("");
        }

        //插入预定记录，返回预定记录ID
        bookingRecord.setUserId(user.getId());
        bookingDao.addNewBooking(bookingRecord);
        int recordId = bookingRecord.getId();
        if (recordId == 0){
            return "false";
        }

        //TODO 可以使用spring aop进行分离
        //添加定时任务判断是否违约
        String time = TimeUtil.formatDate(bookingRecord.getDate().getTime())
                +" "
                +TimeUtil.formatTime(bookingRecord.getEndTime().getTime());
        QuartzService.addCreditJob(time,recordId+"");


        //根据是否有同伴决定是否预定研习间
        if (partners == null || isSeat){
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
                    return "false";
                }
            }
        }
        return "true";
    }
}
