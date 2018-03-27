package com.lids.dao;

import com.lids.po.Area;
import com.lids.po.Space;

import java.util.List;

public interface SpaceDao {

    List<Space> selectSpacesByArea(String areaName);

    List<Area> getAllAreas();

}
