package com.lids.controller;

import com.alibaba.fastjson.JSON;
import com.lids.dao.BookingDao;
import com.lids.dao.CreditDao;
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
    @Resource
    private CreditDao creditDao;

    @RequestMapping("/test")
    @ResponseBody
    public String getBooking(){
        return creditDao.getUserTypeName(28);
    }

}
