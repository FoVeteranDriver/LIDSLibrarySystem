package com.lids.controller;

import com.lids.po.OpenRule;
import com.lids.service.OpenRuleService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/open")
public class OpenRuleController {

    @Resource
    private OpenRuleService openRuleService;

    @RequestMapping(value = "/newRule",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addRule(@RequestBody OpenRule openRule){
        if (openRuleService.addNewOpenRule(openRule)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    @RequestMapping(value = "/getRule",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getRule(@RequestParam int openRuleId){
        OpenRule result = openRuleService.getRule(openRuleId);
        CommomDTO commomDTO = new CommomDTO(ResultEnum.SUCCESS);
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping("/editRule")
    @ResponseBody
    public CommomDTO editRule(@RequestBody OpenRule openRule){
        if (openRuleService.editRule(openRule)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

}
