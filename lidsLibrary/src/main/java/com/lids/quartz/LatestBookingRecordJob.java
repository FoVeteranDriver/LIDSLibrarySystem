package com.lids.quartz;

import com.alibaba.fastjson.JSON;
import com.lids.dao.BookingDao;
import com.lids.util.RedisPoolUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class LatestBookingRecordJob{

    private static Logger logger = LoggerFactory.getLogger(LatestBookingRecordJob.class);

    public static boolean init = false;
    //分组数目
    public static String bookingRecordGroup = "bookingRecordGroup";
    //分组前缀
    public final static String getKey = "LatestBookingRecord";
    //零散前缀
    public final static String prefix = "BookingRecord";
    //map里最高的id
    private final static String maxBookingRecordId = "MaxBookingRecordId";
    //数据库里最高的id
    public final static String newBookingRecordId = "NewBookingRecordId";



    @Resource
    private BookingDao bookingDao;

    public void execute() throws Exception{
        logger.debug("开始更新redis");
        //从数据库获取现在为止最新的预定记录
        List<Map<String,String>> result = bookingDao.getAllTodayRecords();
        int newBookingRecordIdInt = bookingDao.getTopId();
        //全部转换为字符串
        for (Map one:result ) {
            for (Object set:one.entrySet()) {
                Map.Entry setEntry = (Map.Entry) set;
                String key = (String)setEntry.getKey();
                String value = setEntry.getValue().toString();
                one.put(key,value);
            }
        }

        Jedis jedis = null;
        String oldMaxBookingRecordId;
        String oldNewBookingRecordId;
        try{
            jedis = RedisPoolUtil.getJedis();
            oldMaxBookingRecordId = jedis.get(maxBookingRecordId);
            oldNewBookingRecordId = jedis.get(newBookingRecordId);
            jedis.set(newBookingRecordId,newBookingRecordIdInt+"");
            jedis.set(maxBookingRecordId,newBookingRecordIdInt+"");

            //清除旧分组
            String countS = jedis.get(bookingRecordGroup);
            if (countS ==null || countS.equals("")){
            }else {
                int count = Integer.valueOf(countS);
                for (int i=1;i<=count;i++){
                    jedis.del(getKey+count);
                }
            }

            //按十个一组进行分组
            int group = result.size()/10;
            for (int i=1;i<=group;i++){
                int c = 0;
                List<Map> pushList = new ArrayList<Map>();
                List<Map> removeList = new ArrayList<Map>();
                for (Map one:result ) {
                    pushList.add(one);
                    //迭代器中删除异常
//                    result.remove(one);
                    removeList.add(one);
                    c++;
                    if (c>=10){
                        break;
                    }
                }
                result.removeAll(removeList);
                jedis.set(getKey+i,JSON.toJSONString(pushList));

            }
            if (result.size()!=0){
                List<Map> pushList = new ArrayList<Map>();
                for (Map one:result){
                    pushList.add(one);
                }
                group++;
                jedis.set(getKey+group,JSON.toJSONString(pushList));
            }

            jedis.set(bookingRecordGroup,group+"");

//            jedis.set(getKey, JSON.toJSONString(result));

            //删除旧的零散记录
            if (oldMaxBookingRecordId != null && oldNewBookingRecordId != null){
                int max = Integer.valueOf(oldMaxBookingRecordId);
                int newId = Integer.valueOf(oldNewBookingRecordId);
                for (;max<=newId;max++){
                    String delete = prefix+max;
                    jedis.del(delete);
                }
            }

        }finally {
            RedisPoolUtil.returnResource(jedis);
        }

    }

}
