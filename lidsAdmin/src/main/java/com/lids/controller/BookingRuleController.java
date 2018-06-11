package com.lids.controller;

import com.lids.po.BookingRule;
import com.lids.service.BookingRuleService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/bookingRule")
public class BookingRuleController {

    @Resource
    private BookingRuleService bookingRuleService;

    @RequestMapping("/newRule")
    @ResponseBody
    public CommomDTO addNewBookingRule(@RequestBody BookingRule bookingRule){
        if (bookingRuleService.addNewBookingRule(bookingRule)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    @RequestMapping(value = "/getRule",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getRule(@RequestParam int bookingRuleId){
        BookingRule result = bookingRuleService.getRule(bookingRuleId);
        CommomDTO commomDTO = new CommomDTO(ResultEnum.SUCCESS);
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping("/editRule")
    @ResponseBody
    public CommomDTO editRule(@RequestBody BookingRule bookingRule){
        if (bookingRuleService.editRule(bookingRule)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

}
