package com.lids.controller;

import com.lids.po.Area;
import com.lids.po.Space;
import com.lids.service.SpaceService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author yaoyou
 * @description 座位资源Controller
 */
@Controller
public class SpaceController {

    @Resource
    private SpaceService spaceService;

    @RequestMapping("/getSpaceByArea")
    @ResponseBody
    public CommomDTO getSpaceByArea(@RequestParam String areaName){
        List<Space> spaces = spaceService.getSpacesByArea(areaName);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,spaces);
        return commomDTO;
    }

    @RequestMapping("/areas")
    @ResponseBody
    public CommomDTO getAllAreas(){
        List<Area> areas = spaceService.getAreas();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,areas);
        return commomDTO;
    }

}
