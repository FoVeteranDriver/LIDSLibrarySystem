package com.lids.dao;

import com.lids.po.Area;
import com.lids.po.Space;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface SpaceDao {

    List<Space> selectSpacesByArea(String areaName);

    List<Area> getAllAreas();

    List<Space> getSpacesStatus();

    List<HashMap<String,String>> getRoomsSlot(Date date);

    HashMap<String,String> getSeatSlot(int spaceId);

    Space getSeatById(int spaceId);
}
