package com.lids.controller;

import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import com.lids.util.TimeUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author yaoyou
 * @description 座位资源Controller
 */
@Controller
@RequestMapping("space")
public class SpaceController {

    @Resource
    private SpaceService spaceService;

//    @RequestMapping("/getSpaceByArea")
//    @ResponseBody
//    public CommomDTO getSpaceByArea(@RequestParam String areaName){
//        List<Space> spaces = spaceService.getSpacesByArea(areaName);
//        CommomDTO commomDTO = new CommomDTO();
//        commomDTO.setInfo(ResultEnum.SUCCESS,spaces);
//        return commomDTO;
//    }


    @RequestMapping("/areas")
    @ResponseBody
    public CommomDTO getAllAreas(){
        List<Area> areas = spaceService.getAreas();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,areas);
        return commomDTO;
    }

    /**
     * 获取所有的座位当前状态
     * @return
     */
    @RequestMapping("/spaceStatus")
    @ResponseBody
    public CommomDTO getSpaceByArea(){
        List<Space> spaces = spaceService.getSpacesStatus();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,spaces);
        return commomDTO;
    }

    /**
     * 获取指定座位的可预约时间
     * @param spaceId
     * @return
     */
    @RequestMapping("/seatSchedulers")
    @ResponseBody
    public CommomDTO getSpacesSchedulers(@RequestParam int spaceId){
        List<Map<String,String>> schedulers = spaceService.getSeatStatus(spaceId);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,schedulers);
        return  commomDTO;
    }

    /**
     * 获取研习间的可预约时间
     * @return
     */
    @RequestMapping("/roomSchedulers")
    @ResponseBody
    public CommomDTO getStudyRoomScheduler(@RequestParam String date){
        Date dateObject = null;
        try{
            dateObject = TimeUtil.parseDate(date);
        }catch (Exception e){
            e.printStackTrace();
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        List<Map<String,String>> studyRoomSchedulers = spaceService.getStudyRoomSchedulers(dateObject);
        CommomDTO commomDTO = new CommomDTO();
        if (studyRoomSchedulers == null || studyRoomSchedulers.size() == 0){
            commomDTO.setInfo(ResultEnum.NOT_BOOKING);
        }else {
            commomDTO.setInfo(ResultEnum.SUCCESS,studyRoomSchedulers);
        }
        return commomDTO;
    }

    /**
     * 获取所有研习间的预约信息
     * @return
     */
    @RequestMapping("/roomBookings")
    @ResponseBody
    public CommomDTO getRoomsBooking(@RequestParam String date){
        Date dateObject = null;
        try{
            dateObject = TimeUtil.parseDate(date);
        }catch (Exception e){
            e.printStackTrace();
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        List<HashMap<String,String>> roomsBooking = spaceService.getRoomsBooking(dateObject);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,roomsBooking);
        return commomDTO;
    }

    @RequestMapping("/seatBookings")
    @ResponseBody
    public CommomDTO getseatBooking(@RequestParam int spaceId){
        HashMap<String,String> seatBooking = spaceService.getSeatBooking(spaceId);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,seatBooking);
        return commomDTO;
    }

    /**
     * 三合一接口
     * @return
     */
    @RequestMapping("/roomInfo")
    @ResponseBody
    public CommomDTO roomInfo(@RequestParam String date){
        Date dateObject = null;
        try{
            dateObject = TimeUtil.parseDate(date);
        }catch (Exception e){
            e.printStackTrace();
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        List<HashMap<String,String>> roomsBooking = spaceService.getRoomsBooking(dateObject);
        List<Map<String,String>> studyRoomSchedulers = spaceService.getStudyRoomSchedulers(dateObject);

        Date time = new Date();
        String timeS = TimeUtil.formatHHMM(time);
        List<String> nowTime= new ArrayList<String>();
        nowTime.add(timeS);

        Map<String,List> result = new HashMap<String, List>();
        result.put("serverTime",nowTime);
        result.put("roomScheduler",studyRoomSchedulers);
        result.put("roomBooking",roomsBooking);

        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
