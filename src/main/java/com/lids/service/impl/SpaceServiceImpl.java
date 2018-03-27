package com.lids.service.impl;

import com.lids.dao.SpaceDao;
import com.lids.po.Area;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("SpaceService")
public class SpaceServiceImpl implements SpaceService{

    @Resource
    private SpaceDao spaceDao;

    public List<Space> getSpacesByArea(String areaName){
        List<Space> spaces = spaceDao.selectSpacesByArea(areaName);
        return spaces;
    }

    public List<Area> getAreas() {
        return spaceDao.getAllAreas();
    }
}
