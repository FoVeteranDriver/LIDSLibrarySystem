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

/**
 * @author yaoyou
 * 通知展示相关控制器
 */
@Controller
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * 上传主页展示
     * @param mainPic 主页展示图片文件
     * @param mainInfo 空间简介
     * @param menu
     * @return
     */
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

    /**
     * 获取主页展示
     * @return
     */
    @RequestMapping(value = "/homePage",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getHomePage(){
        JSONObject jsonObject = noticeService.getHomePage();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,jsonObject);
        return  commomDTO;
    }

    /**
     * 上传使用帮助
     * @param mainPic 图片文件
     * @param prolusion 前言
     * @param tips
     * @return
     */
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

    /**
     * 获取使用帮助
     * @return
     */
    @RequestMapping(value = "/usinghelp",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getusinghelp(){
        JSONObject jsonObject = noticeService.getUsingHelp();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,jsonObject);
        return  commomDTO;
    }

    /**
     * 发布通知
     * @param noticeTitle 通知名称
     * @param noticeText 通知正文
     * @param publisher 发布者
     * @param imageFile 图片文件
     * @param affixFiles 附件文件
     * @return
     */
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

    /**
     * 获取通知列表
     * @return
     */
    @RequestMapping(value = "/noticeList",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getNoticeList(){
        List<Map> result = noticeService.getNoticeList();
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 删除历史通知
     * @param id 通知ID
     * @return
     */
    @RequestMapping(value = "/deleteNotice",method = RequestMethod.DELETE)
    @ResponseBody
    public CommomDTO deleteNotice(@RequestParam int id){
        noticeService.delete(id);
        return new CommomDTO(ResultEnum.SUCCESS);
    }

    /**
     * 获取通知详情
     * @param id
     * @return
     */
    @RequestMapping(value = "/notice",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO getNotice(@RequestParam int id){
        Notice result = noticeService.get(id);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 根据关键字搜索通知
     * @param key
     * @return
     */
    @RequestMapping(value = "/searchNotice",method = RequestMethod.GET)
    @ResponseBody
    public CommomDTO searchNotice(@RequestParam String key){
        List<Map> result = noticeService.searchNoticeList(key);
        CommomDTO commomDTO = new CommomDTO();
        commomDTO.setInfo(ResultEnum.SUCCESS,result);
        return commomDTO;
    }

    /**
     * 上传空间配置
     * @param notice 预约须知
     * @param introduce 空间介绍
     * @param albumFiles 相册展示
     * @param deployFiles 硬件配置文件
     * @param type 空间类型
     * @return
     */
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

    /**
     * 获取空间使用须知
     * @param type 空间类型 studyRoom代表研习间，seat代表学习间
     * @return
     */
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
