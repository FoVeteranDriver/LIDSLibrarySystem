package com.lids.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.po.Notice;
import com.lids.po.SystemParam;
import com.lids.service.NoticeService;
import com.lids.util.fileUtil;
import com.lids.vo.CommomDTO;
import com.lids.vo.ResultEnum;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Controller
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    @RequestMapping(value = "/homePage",method = RequestMethod.PUT)
    @ResponseBody
    public CommomDTO uploadHomePage(@RequestParam("mainPic") MultipartFile mainPic,
                                    @RequestParam String mainInfo,
                                    @RequestParam String menu){
        String fileUrl = fileUtil.saveFile(mainPic);
        JSONObject jsonObject = JSON.parseObject(menu);
        jsonObject.put("mainInfo",mainInfo);
        if (noticeService.saveHomePage(fileUrl,jsonObject)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }else {
            return new CommomDTO(ResultEnum.FAILED);
        }
    }

    @RequestMapping(value = "/homePage",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getHomePage(){
        JSONObject jsonObject = noticeService.getHomePage();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,jsonObject);
        return  commomDTO;
    }

    @RequestMapping(value = "/usinghelp",method = RequestMethod.PUT)
    @ResponseBody
    public CommomDTO uploadusinghelp(@RequestParam("mainPic") MultipartFile mainPic,
                                     @RequestParam String prolusion,
                                     @RequestParam String tips){
        String fileUrl = fileUtil.saveFile(mainPic);
        JSONObject jsonObject = JSON.parseObject(tips);
        jsonObject.put("prolusion",prolusion);
        if (noticeService.saveUsingHelp(fileUrl,jsonObject)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }else {
            return new CommomDTO(ResultEnum.FAILED);
        }
    }


    @RequestMapping(value = "/usinghelp",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getusinghelp(){
        JSONObject jsonObject = noticeService.getUsingHelp();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,jsonObject);
        return  commomDTO;
    }

    @RequestMapping(value = "/addNotice",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addNotice(@RequestParam(value = "noticeTitle") String noticeTitle,
                               @RequestParam(value = "noticeText") String noticeText,
                               @RequestParam(value = "publisher") String publisher,
                               @RequestParam(value = "imageFile",required = false) MultipartFile imageFile,
                               @RequestParam(value = "affixFiles",required = false) List<MultipartFile> affixFiles){

        Notice notice = new Notice();
        notice.setNoticeTitle(noticeTitle);
        notice.setNoticeText(noticeText);
        notice.setPublisher(publisher);
        if (noticeService.addNotice(notice,imageFile,affixFiles)){
            return new CommomDTO(ResultEnum.SUCCESS);
        }
        return new CommomDTO(ResultEnum.FAILED);
    }

    @RequestMapping(value = "/noticeList",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getNoticeList(){
        List<Map> result = noticeService.getNoticeList();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping(value = "/deleteNotice",method = RequestMethod.DELETE)
    @ResponseBody
    public CommomDTO deleteNotice(@RequestParam int id){
        noticeService.delete(id);
        return new CommomDTO(ResultEnum.SUCCESS);
    }

    @RequestMapping(value = "/notice",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getNotice(@RequestParam int id){
        Notice result = noticeService.get(id);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping(value = "/searchNotice",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO searchNotice(@RequestParam String key){
        List<Map> result = noticeService.searchNoticeList(key);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    @RequestMapping(value = "/spaceNotice",method = RequestMethod.POST)
    @ResponseBody
    public CommomDTO addSpaceNotice(@RequestParam(value = "notice") String notice,
                                    @RequestParam(value = "introduce") String introduce,
                                    @RequestParam(value = "albumFiles",required = false) List<MultipartFile> albumFiles,
                                    @RequestParam(value = "deployFiles",required = false) List<MultipartFile> deployFiles,
                                    @RequestParam(value = "type") String type){
        JSONObject jsonObject = JSON.parseObject(notice);
        jsonObject.put("introduce",introduce);
        noticeService.addSpaceNotice(jsonObject,albumFiles,deployFiles,type);

        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,null);
        return commomDTO;
    }

    @RequestMapping(value = "/spaceNotice",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getSpaceNotice(@RequestParam String type){
        if (type == null || type.equals("")){
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        if (!type.equals("studyRoom")&&!type.equals("seat")){
            return new CommomDTO(ResultEnum.PARAMS_ERROR);
        }
        JSONObject result = noticeService.getSpaceNotice(type);

        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

}
