package com.lids.controller;

import com.lids.common.BaseController;
import com.lids.po.Notice;
import com.lids.service.NoticeService;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class NoticeController extends BaseController {

    @Resource
    private NoticeService noticeService;

    /**
     * 获取最新的通知
     * @return
     */
    @RequestMapping("/notice")
    @ResponseBody
    public CommomDTO getLatestNotice(){
        Notice notice = noticeService.getLatest();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,notice);
        return  commomDTO;
    }

}
