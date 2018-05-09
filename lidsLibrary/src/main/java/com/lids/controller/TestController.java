package com.lids.controller;

import com.alibaba.fastjson.JSON;
import com.lids.dao.BookingDao;
import com.lids.util.RedisPoolUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class TestController {

    @Resource
    private BookingDao bookingDao;

//    @RequestMapping("/setBooking")
//    @ResponseBody
//    public CommomDTO setBooking(){
//        List<Map<String,String>> result = bookingDao.getAllTodayRecords();
//        for (Map one:result ) {
//            for (Object set:one.entrySet()) {
//                Map.Entry setEntry = (Map.Entry) set;
//                String key = (String)setEntry.getKey();
//                String value = setEntry.getValue().toString();
//                one.put(key,value);
//            }
//        }
//        Jedis jedis = null;
//        try{
//            jedis = RedisPoolUtil.getJedis();
//            jedis.set("bookingToday", JSON.toJSONString(result));
//        }finally {
//            RedisPoolUtil.returnResource(jedis);
//        }
//        CommomDTO commomDTO = new CommomDTO();
//        commomDTO.setInfo(ResultEnum.SUCCESS,result);
//        return commomDTO;
//    }

    @RequestMapping("/getBooking")
    @ResponseBody
    public CommomDTO getBooking(){
        String result;
        Jedis jedis = null;
        try{
            jedis = RedisPoolUtil.getJedis();
            result = jedis.get("LatestBookingRecord");
        }finally {
            RedisPoolUtil.returnResource(jedis);
        }
        List<Map<String,String>> resultList = JSON.parseObject(result,List.class);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,resultList);
        return commomDTO;
    }

}
