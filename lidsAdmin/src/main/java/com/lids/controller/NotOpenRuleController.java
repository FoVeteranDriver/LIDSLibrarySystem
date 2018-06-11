package com.lids.controller;

import com.lids.po.NotOpenRulePlan;
import com.lids.service.NotOpenRuleService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/notOpen")
public class NotOpenRuleController {

    @Resource
    private NotOpenRuleService notOpenRuleService;

    @RequestMapping(value = "/newRule",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addRule(@RequestBody NotOpenRulePlan notOpenRulePlan){
        if (notOpenRuleService.addNewOpenRule(notOpenRulePlan)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    @RequestMapping(value = "/getRule",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getRule(@RequestParam int notOpenRuleId){
        NotOpenRulePlan result = notOpenRuleService.getRule(notOpenRuleId);
        CommomDTO commomDTO = new CommomDTO(ResultEnum.SUCCESS);
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping("/editRule")
    @ResponseBody
    public CommomDTO editRule(@RequestBody NotOpenRulePlan notOpenRulePlan){
        if (notOpenRuleService.editRule(notOpenRulePlan)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

}
