package com.lids.controller;

import com.lids.common.BaseController;
import com.lids.po.BookingRecord;
import com.lids.po.User;
import com.lids.service.BookingService;
import com.lids.service.SpaceService;
import com.lids.service.UserService;
import com.lids.util.TimeUtil;
import com.lids.util.WechatUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("booking")
public class BookingController extends BaseController{

    @Resource
    private BookingService bookingService;
    @Resource
    private UserService userService;
    @Resource
    private SpaceService spaceService;

    /**
     * 预定座位
     * @param params
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addNewBooking",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addNewBooking(@RequestBody Map<String,String> params,HttpServletRequest request) throws Exception{

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

        //判断选中的时间段是否有预约
        if (bookingService.getBookingNowByTime(bookingRecord.getDate(),
                bookingRecord.getBeginTime(),
                bookingRecord.getEndTime(),
                bookingRecord.getSpaceId())){
            return new CommomDTO(ResultEnum.HAS_BOOKING);
        }

        //判断用户积分是否被禁用
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        if (userService.getBanStatus(user.getId()) == 1){
            return new CommomDTO(ResultEnum.SCORE_ZERO);
        }

        //获取传输的伙伴名单
        String partnersString = params.get("partners");
        boolean result = false;
        if ( partnersString ==null || partnersString.equals("")){
            result = bookingService.addNewBooking(bookingRecord);
        }else{
            String[] partnersId = partnersString.split(":");
            result = bookingService.addNewBooking(bookingRecord,partnersId);
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
    @RequestMapping(value = "/todayBookRecords")
    @ResponseBody
    public CommomDTO getBookRecords(HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Map<String,String>> result = bookingService.getTodayRecords(session);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 扫描二维码签到的微信回调接口
     * 二维码生成说明：https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx463f559a1afd2cd4
     *                 &redirect_uri=http%3a%2f%2fiyou.s1.natapp.cc%2flidsLibrary%2fbooking%2fcheckIn
     *                 &response_type=code
     *                 &scope=snsapi_base
     *                 &state=xxx#wechat_redirect
     *                 (state为spaceId)
     * @param request
     * @return
     */
    @RequestMapping("/checkIn")
    @ResponseBody
    public CommomDTO checkIn(HttpServletRequest request){
        String code = request.getParameter("code");
        String spaceId = request.getParameter("state");
        if(code==null){
            return new CommomDTO(ResultEnum.FAILED);
        }

        //检查用户是否存在
        String openId = WechatUtil.getOpenId(code);
        User user = userService.selectUserByOpenId(openId);
        if (user == null){
            return new CommomDTO(ResultEnum.NO_BINDING);
        }

        //检查座位是否被占用
        boolean isOccupied = spaceService.isOccupied(Integer.valueOf(spaceId));
        if (isOccupied){
            return new CommomDTO(ResultEnum.HAS_OCCUPIED);
        }

        //获取当前的预约
        BookingRecord bookingRecord = bookingService.getNowBooking(Integer.valueOf(spaceId));
        if (bookingRecord == null){
            return new CommomDTO(ResultEnum.NO_BOOKING);
        }else if (bookingRecord.getUserId() == user.getId()){
            return new CommomDTO(ResultEnum.CHECKIN_SUCCESS);
        }else {
            return new CommomDTO(ResultEnum.NO_YOU);
        }
    }

}
