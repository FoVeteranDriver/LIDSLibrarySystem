package com.lids.controller;

import com.lids.service.RuleService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
public class RuleController {

    @Resource
    private RuleService ruleService;

    @RequestMapping(value = "/allRules",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getAllRules(){
        List<Map> result = ruleService.getAllRules();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
