package com.lids.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
        return get(homePage);
    }

    @Override
    public boolean saveUsingHelp(String fileUrl, JSONObject json) {
        save(fileUrl,json,usinghelp);
        return true;
    }

    @Override
    public JSONObject getUsingHelp() {
        return get(usinghelp);
    }

    private void save(String fileUrl, JSONObject json, String groupName){
        SystemParam systemParam = new SystemParam();
        systemParam.setInfo(groupName,"mainPic",fileUrl,null,null);
        systemParamDao.insert(systemParam);
        systemParam.setInfo(groupName,"jsonString",json.toString(),null,null);
        systemParamDao.insert(systemParam);
    }

    //获取主页展示和使用帮助，type取值为homepage或usingHelp
    private JSONObject get(String type){
        List<SystemParam> result = systemParamDao.select(type);
        JSONObject jsonObject = new JSONObject();
        //取出结果中的JSON字符串
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("jsonString")){
                jsonObject = JSON.parseObject(systemParam.getParamValue());
            }
        }
        //取出结果中的图片
        for (SystemParam systemParam:result){
            if (systemParam.getParamName().equals("mainPic")){
                jsonObject.put("mainPic",systemParam.getParamValue());
            }
        }
        return jsonObject;
    }

    //添加通知
    @Override
    public boolean addNotice(Notice notice, MultipartFile imageFile, List<MultipartFile> affixFiles) {
        //保存图片文件并将URL保存到notice中
        if (imageFile != null){
            String imgUrl = fileUtil.saveFile(imageFile);
            notice.setImageFile(imgUrl);
        }
        noticeDao.insert(notice);
        int noticeId = notice.getId();
        //添加附件文件
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

    //添加空间配置
    @Override
    public boolean addSpaceNotice(JSONObject jsonObject, List<MultipartFile> albumFiles, List<MultipartFile> deployFiles, String type) {
        SystemParam systemParam = new SystemParam();
        int result = 0;
        switch (type){
            case "seat":
                systemParam.setParamGroupp("seat");
                systemParam.setParamName("jsonString");
                systemParam.setParamValue(jsonObject.toString());
                systemParam.setParamType("");
                result = systemParamDao.insert(systemParam);
                saveFile(albumFiles,deployFiles,systemParam);
                return true;
            case "studyRoom":
                systemParam.setParamGroupp("studyRoom");
                systemParam.setParamName("jsonString");
                systemParam.setParamValue(jsonObject.toString());
                result = systemParamDao.insert(systemParam);
                saveFile(albumFiles,deployFiles,systemParam);
                return true;
            default:
                return false;
        }

    }

    //保存空间配置的图片文件
    private void saveFile(List<MultipartFile> albumFiles, List<MultipartFile> deployFiles,SystemParam systemParam){
        String fileUrl;
        String fileName;
        SpaceNoticeFile spaceNoticeFile = new SpaceNoticeFile();
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
    }

    //获取空间配置type取值为seat或studyRoom
    @Override
    public JSONObject getSpaceNotice(String type) {
        if (type.equals("seat")){
            SystemParam result = systemParamDao.getSpaceNotice("seat");
            JSONObject jsonObject = JSON.parseObject(result.getParamValue());
            //获取文件
            List<SpaceNoticeFile> album = systemParamDao.getFiles("image",result.getId());
            List<SpaceNoticeFile> deploy = systemParamDao.getFiles("deploy",result.getId());
            JSONArray albumFiles = JSONArray.parseArray(JSON.toJSONString(album));
            JSONArray deployFiles = JSONArray.parseArray(JSON.toJSONString(deploy));
            //TODO
//            for (JSON j :albumFiles) {
//
//            }
            jsonObject.put("albumFiles",albumFiles);
            jsonObject.put("deployFiles",deployFiles);

            return jsonObject;
        }else {
            SystemParam result = systemParamDao.getSpaceNotice("studyRoom");
            JSONObject jsonObject = JSON.parseObject(result.getParamValue());

            List<SpaceNoticeFile> album = systemParamDao.getFiles("image",result.getId());
            List<SpaceNoticeFile> deploy = systemParamDao.getFiles("deploy",result.getId());

            JSONArray albumFiles = JSONArray.parseArray(JSON.toJSONString(album));
            JSONArray deployFiles = JSONArray.parseArray(JSON.toJSONString(deploy));
            jsonObject.put("albumFiles",albumFiles);
            jsonObject.put("deployFiles",deployFiles);

            return jsonObject;
        }
    }
}
