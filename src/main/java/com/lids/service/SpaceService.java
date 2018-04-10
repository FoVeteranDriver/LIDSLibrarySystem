package com.lids.service;

import com.lids.po.Area;
import com.lids.po.Scheduler;
import com.lids.po.Space;

import java.util.HashMap;
import java.util.List;

public interface SpaceService {

    List<Space> getSpacesByArea(String areaName);

    List<Area> getAreas();

    List<Scheduler> getStudyRoomSchedulers();

    List<Space> getSpacesStatus();

    List<Scheduler> getSeatStatus(int spaceId);

    List<HashMap<String,String>> getRoomsBooking();

    HashMap getSeatBooking(int spaceId);

    boolean isOccupied(int spaceId);

}
