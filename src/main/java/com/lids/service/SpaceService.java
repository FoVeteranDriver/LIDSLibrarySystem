package com.lids.service;

import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface SpaceService {

    List<Space> getSpacesByArea(String areaName);

    List<Area> getAreas();

    List<Map<String,String>> getStudyRoomSchedulers();

    List<Space> getSpacesStatus();

    List<Map<String,String>> getSeatStatus(int spaceId);

    List<HashMap<String,String>> getRoomsBooking();

    HashMap getSeatBooking(int spaceId);

    boolean isOccupied(int spaceId);

}
