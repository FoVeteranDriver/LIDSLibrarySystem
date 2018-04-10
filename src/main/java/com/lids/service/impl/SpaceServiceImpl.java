package com.lids.service.impl;

import com.lids.dao.ScheduleDao;
import com.lids.dao.SpaceDao;
import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

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

    public List<Scheduler> getStudyRoomSchedulers() {
        List<Scheduler> schedulers = scheduleDao.getRoomSchedulers();
        return schedulers;
    }

    public List<Space> getSpacesStatus() {
        List<Space> spaces = spaceDao.getSpacesStatus();
        return spaces;
    }

    public List<Scheduler> getSeatStatus(int spaceId) {
        List<Scheduler> schedulers = scheduleDao.getSeatSchedulers(spaceId);
        return schedulers;
    }

    public List<HashMap<String, String>> getRoomsBooking() {
        List<HashMap<String,String>> recentsBooking = spaceDao.getRoomsSlot();
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
}
