package com.lids.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.dao.ScheduleDao;
import com.lids.dao.SpaceDao;
import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("SpaceService")
public class SpaceServiceImpl implements SpaceService{

    @Resource
    private SpaceDao spaceDao;

    @Resource
    private ScheduleDao scheduleDao;

    public List<Space> getSpacesByArea(String areaName){
        List<Space> spaces = spaceDao.selectSpacesByArea(areaName);
        return spaces;
    }

    public List<Area> getAreas() {
        return spaceDao.getAllAreas();
    }

    public List<Map<String,String>> getStudyRoomSchedulers(Date date) {
        List<Map<String,String>> schedulers = scheduleDao.getRoomSchedulers(date);
        return schedulers;
    }

    public List<Space> getSpacesStatus() {
        List<Space> spaces = spaceDao.getSpacesStatus();
        return spaces;
    }

    public List<Map<String,String>> getSeatStatus(int spaceId) {
        List<Map<String,String>> schedulers = scheduleDao.getSeatSchedulers(spaceId);
        return schedulers;
    }

    public List<HashMap<String, String>> getRoomsBooking(Date date) {
        List<HashMap<String,String>> recentsBooking = spaceDao.getRoomsSlot(date);

        //去除开始时间和结束时间的秒数
        for (Map one : recentsBooking){
            String beginTime = one.get("beginTime").toString();
            String endTime = (String)one.get("endTime").toString();
            one.put("beginTime",beginTime.substring(0,5));
            one.put("endTime",endTime.substring(0,5));
        }

        return recentsBooking;
    }

    public HashMap getSeatBooking(int spaceId) {
        HashMap recentBookig  = spaceDao.getSeatSlot(spaceId);
        return recentBookig;
    }

    /**
     * 检查当前座位是否被占用
     * @param spaceId
     * @return
     */
    public boolean isOccupied(int spaceId) {
        Space space = spaceDao.getSeatById(spaceId);
        if (space.getIsOccupied() == 1){
            return true;
        }else{
            return false;
        }
    }

    public List<HashMap<String, String>> getSeatNameAndId() {
        return spaceDao.getSeatNameAndId();
    }
}
