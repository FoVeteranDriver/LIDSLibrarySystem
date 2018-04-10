package com.lids.controller;

import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public CommomDTO getStudyRoomScheduler(){
        List<Map<String,String>> studyRoomSchedulers = spaceService.getStudyRoomSchedulers();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,studyRoomSchedulers);
        return commomDTO;
    }

    /**
     * 获取所有研习间的预约信息
     * @return
     */
    @RequestMapping("/roomBookings")
    @ResponseBody
    public CommomDTO getRoomsBooking(){
        List<HashMap<String,String>> roomsBooking = spaceService.getRoomsBooking();
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

}
