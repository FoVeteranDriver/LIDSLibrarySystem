package com.lids.service;

import com.lids.po.Area;
import com.lids.po.Space;

import java.util.List;

public interface SpaceService {

    List<Space> getSpacesByArea(String areaName);

    List<Area> getAreas();

}
