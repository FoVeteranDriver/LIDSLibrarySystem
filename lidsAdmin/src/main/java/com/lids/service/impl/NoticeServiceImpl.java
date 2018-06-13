package com.lids.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.dao.NoticeDao;
import com.lids.dao.SystemParamDao;
import com.lids.po.File;
import com.lids.po.Notice;
import com.lids.po.SystemParam;
import com.lids.service.BaseService;
import com.lids.service.NoticeService;
import com.lids.util.fileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("NoticeService")
public class NoticeServiceImpl extends BaseService implements NoticeService{

    @Resource
    private SystemParamDao systemParamDao;
    @Resource
    private NoticeDao noticeDao;


    private String homePage="homePage";
    private String usinghelp = "usinghelp";


    @Override
    public boolean saveHomePage(String fileUrl, JSONObject json) {
        save(fileUrl,json,homePage);
        return true;
    }

    @Override
    public JSONObject getHomePage() {
        String maxType = systemParamDao.MaxTypeByGroup(homePage);
        List<SystemParam> result = systemParamDao.selectByGroup(homePage,maxType);
        JSONObject jsonObject = new JSONObject();
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("jsonString")){
                jsonObject = JSON.parseObject(systemParam.getParamValue());
            }
        }
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("mainPic")){
                jsonObject.put("mainPic",systemParam.getParamValue());
            }
        }
        return jsonObject;
    }

    @Override
    public boolean saveUsingHelp(String fileUrl, JSONObject json) {
        save(fileUrl,json,usinghelp);
        return true;
    }

    @Override
    public JSONObject getUsingHelp() {
        String maxType = systemParamDao.MaxTypeByGroup(usinghelp);
        List<SystemParam> result = systemParamDao.selectByGroup(usinghelp,maxType);
        JSONObject jsonObject = new JSONObject();
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("jsonString")){
                jsonObject = JSON.parseObject(systemParam.getParamValue());
            }
        }
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("mainPic")){
                jsonObject.put("mainPic",systemParam.getParamValue());
            }
        }
        return jsonObject;
    }

    private void save(String fileUrl, JSONObject json, String groupName){
        String maxType = systemParamDao.MaxTypeByGroup(groupName);
        String nextType;
        if (maxType == null){
            nextType = "1";
        }else {
            nextType = Integer.valueOf(maxType)+1+"";
        }
        SystemParam systemParam = new SystemParam();
        systemParam.setInfo(groupName,"mainPic",fileUrl,nextType,null);
        systemParamDao.insert(systemParam);
        systemParam.setInfo(groupName,"jsonString",json.toString(),nextType,null);
        systemParamDao.insert(systemParam);
    }

    @Override
    public boolean addNotice(Notice notice, MultipartFile imageFile, List<MultipartFile> affixFiles) {
        if (imageFile != null){
            String imgUrl = fileUtil.saveFile(imageFile);
            notice.setImageFile(imgUrl);
        }
        noticeDao.insert(notice);
        int noticeId = notice.getId();
        for (MultipartFile file:affixFiles){
            String name = file.getOriginalFilename();
            String url = fileUtil.saveFile(file);
            File fileToSave = new File();
            fileToSave.setFileName(name);
            fileToSave.setFileLink(url);
            fileToSave.setNoticeId(noticeId);
            noticeDao.insertFile(fileToSave);
        }
        return true;
    }

    @Override
    public List<Map> getNoticeList() {
        return noticeDao.selectNoticeListAdmin();
    }

    @Override
    public int delete(int id) {
        return noticeDao.delete(id);
    }

    @Override
    public Notice get(int id) {
        Notice notice = noticeDao.selectById(id);
        List<File> fileList = noticeDao.selectFileByNoticeId(id);
        notice.setAffixFiles(fileList);
        return notice;
    }

    @Override
    public List<Map> searchNoticeList(String key) {
        Map search = new HashMap();
        search.put("key",key);
        return noticeDao.searchNoticeList(search);
    }
}
