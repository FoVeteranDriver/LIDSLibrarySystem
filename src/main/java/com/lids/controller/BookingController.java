package com.lids.controller;

import com.lids.po.BookingRecord;
import com.lids.service.BookingService;
import com.lids.util.TimeUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
public class BookingController {

    private static Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Resource
    private BookingService bookingService;

    /**
     * 预定座位
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/book/addNewBooking",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addNewBooking(@RequestBody Map<String,String> params) throws Exception{
        BookingRecord bookingRecord = new BookingRecord();
        //验证输入参数
        String date = params.get("date");
        String beginTime = params.get("beginTime");
        String endTime = params.get("endTime");
        String spaceId = params.get("spaceId");
        String application = params.get("application");
        if (date == null || date.equals("")
                || beginTime==null || beginTime.equals("")
                || endTime==null || endTime.equals("")
                || spaceId==null || spaceId.equals("")){
            logger.debug("参数有空");
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        try {
            if (TimeUtil.compareDate(date) && TimeUtil.compareTime(endTime,beginTime)){
                bookingRecord.setDate(TimeUtil.parseDate(date));
                bookingRecord.setBeginTime(TimeUtil.parseTime(beginTime));
                bookingRecord.setEndTime(TimeUtil.parseTime(endTime));
            }else{
                logger.debug("时间错误");
                return new CommomDTO(ResultEnum.PARAMS_ERROR);
            }
        }catch (ParseException e){
            e.printStackTrace();
            logger.debug("时间转换失败");
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        bookingRecord.setSpaceId(Integer.valueOf(params.get("spaceId")));
        bookingRecord.setApplication(params.get("application"));

        //获取传输的伙伴名单
        String partnersString = params.get("partners");
        boolean result = false;
        if (partnersString.equals("") || partnersString!=null){
            String[] partnersId = partnersString.split(":");
            result = bookingService.addNewBooking(bookingRecord,partnersId);
        }else{
            result = bookingService.addNewBooking(bookingRecord);
        }
        if (result){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    /**
     * 返回今日的预约信息
     * @return
     */
    @RequestMapping(value = "/book/todayBookRecords")
    @ResponseBody
    public CommomDTO getBookRecords(){
        List<Map<String,String>> result = bookingService.getTodayRecords();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
