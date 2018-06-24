package com.lids.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lids.dao.NoticeDao;
import com.lids.dao.SystemParamDao;
import com.lids.po.File;
import com.lids.po.Notice;
import com.lids.po.SpaceNoticeFile;
import com.lids.po.SystemParam;
import com.lids.service.BaseService;
import com.lids.service.NoticeService;
import com.lids.util.fileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
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


    @Override
    public boolean addSpaceNotice(JSONObject jsonObject, List<MultipartFile> albumFiles, List<MultipartFile> deployFiles, String type) {
        String maxType = null;
        String nextType = null;
        String fileUrl = null;
        String fileName = null;
        SystemParam systemParam = new SystemParam();
        SpaceNoticeFile spaceNoticeFile = new SpaceNoticeFile();
        int result = 0;
        switch (type){
            case "seat":
//                try {
                    maxType = systemParamDao.MaxTypeByGroup("seat");
//                }catch (Exception e){
//                    maxType = "0";
//                }

                nextType = Integer.valueOf(maxType)+1+"";
                systemParam.setParamGroupp("seat");
                systemParam.setParamName("jsonString");
                systemParam.setParamValue(jsonObject.toString());
                systemParam.setParamType(nextType);
                result = systemParamDao.insert(systemParam);
                //TODO 添加照片和附件

                for (MultipartFile file:
                     albumFiles) {
                    fileUrl = fileUtil.saveFile(file);
                    fileName = file.getOriginalFilename();
                    spaceNoticeFile.setFileLink(fileUrl);
                    spaceNoticeFile.setFileName(fileName);
                    spaceNoticeFile.setSpaceNoticeId(systemParam.getId());
                    spaceNoticeFile.setType("image");
                    systemParamDao.insertFile(spaceNoticeFile);
                }
                for (MultipartFile file:
                        deployFiles){
                    fileUrl = fileUtil.saveFile(file);
                    fileName = file.getOriginalFilename();
                    spaceNoticeFile.setFileLink(fileUrl);
                    spaceNoticeFile.setFileName(fileName);
                    spaceNoticeFile.setSpaceNoticeId(systemParam.getId());
                    spaceNoticeFile.setType("deploy");
                    systemParamDao.insertFile(spaceNoticeFile);
                }
                return true;
            case "studyRoom":
                try {
                    maxType = systemParamDao.MaxTypeByGroup("studyRoom");
                }catch (Exception e){
                    maxType = "0";
                }

                nextType = Integer.valueOf(maxType)+1+"";
                systemParam.setParamGroupp("studyRoom");
                systemParam.setParamName("jsonString");
                systemParam.setParamValue(jsonObject.toString());
                systemParam.setParamType(nextType);
                result = systemParamDao.insert(systemParam);

                for (MultipartFile file:
                        albumFiles) {
                    fileUrl = fileUtil.saveFile(file);
                    fileName = file.getOriginalFilename();
                    spaceNoticeFile.setFileLink(fileUrl);
                    spaceNoticeFile.setFileName(fileName);
                    spaceNoticeFile.setSpaceNoticeId(systemParam.getId());
                    spaceNoticeFile.setType("image");
                    systemParamDao.insertFile(spaceNoticeFile);
                }
                for (MultipartFile file:
                        deployFiles){
                    fileUrl = fileUtil.saveFile(file);
                    fileName = file.getOriginalFilename();
                    spaceNoticeFile.setFileLink(fileUrl);
                    spaceNoticeFile.setFileName(fileName);
                    spaceNoticeFile.setSpaceNoticeId(systemParam.getId());
                    spaceNoticeFile.setType("deploy");
                    systemParamDao.insertFile(spaceNoticeFile);
                }

                return true;
            default:
                return false;
        }

    }

    @Override
    public JSONObject getSpaceNotice(String type) {
        if (type.equals("seat")){
            String maxType = systemParamDao.MaxTypeByGroup("seat");
            List<SystemParam> systemParam = systemParamDao.selectByGroup("seat",maxType);
            SystemParam result = systemParam.get(0);
            JSONObject jsonObject = JSON.parseObject(result.getParamValue());

            List<SpaceNoticeFile> album = systemParamDao.getFiles("image",result.getId());
            List<SpaceNoticeFile> deploy = systemParamDao.getFiles("deploy",result.getId());
            jsonObject.put("albumFiles",JSON.toJSONString(album));
            jsonObject.put("deployFiles",JSON.toJSONString(deploy));

            return jsonObject;
        }else {
            String maxType = systemParamDao.MaxTypeByGroup("studyRoom");
            List<SystemParam> systemParam = systemParamDao.selectByGroup("studyRoom",maxType);
            SystemParam result = systemParam.get(0);
            JSONObject jsonObject = JSON.parseObject(result.getParamValue());

            List<SpaceNoticeFile> album = systemParamDao.getFiles("image",result.getId());
            List<SpaceNoticeFile> deploy = systemParamDao.getFiles("deploy",result.getId());
            jsonObject.put("albumFiles",JSON.toJSONString(album));
            jsonObject.put("deployFiles",JSON.toJSONString(deploy));

            return jsonObject;
        }
    }
}
