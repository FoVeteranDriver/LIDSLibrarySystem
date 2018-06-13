package com.lids.service.impl;


import com.lids.common.BaseService;
import com.lids.dao.BookingDao;
import com.lids.dao.ScheduleDao;
import com.lids.dao.SpaceDao;
import com.lids.po.Area;
import com.lids.po.BookingRecord;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import com.lids.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("SpaceService")
public class SpaceServiceImpl extends BaseService implements SpaceService{

    @Resource
    private SpaceDao spaceDao;

    @Resource
    private ScheduleDao scheduleDao;

    @Resource
    private BookingDao bookingDao;

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

    @Override
    public List<Space> getSpacesStatusByTime(Date date, Date beginTime, Date endTime){
        List<Space> spaces = spaceDao.getSpacesStatus();
        List<Integer> bookingRecords = bookingDao.getSeatBookingRecordByTime(date,beginTime,endTime);
        List<Integer> schedules = scheduleDao.getAllSeatSchedulers(date,beginTime,endTime);

        //判断是否开放
        for (Space space:spaces){
            int id = space.getId();
            boolean isOpen = false;
            for (Integer i:schedules){
                if (id == i){
                    isOpen = true;
                }
            }
            if (isOpen){
                space.setIsOpen(1);
            }else {
                space.setIsOpen(0);
            }
        }

        //判断是否有预定
        for (Space space:spaces){
            int id = space.getId();
            boolean isBooking = false;
            for (Integer i:bookingRecords){
                if (id == i){
                    isBooking = true;
                }
            }
            if (isBooking && space.getIsOpen() == 1){
                space.setIsOccupied(1);
            }else {
                space.setIsOccupied(0);
            }
        }

        return spaces;
    }
}
