package com.lids.service;

import com.alibaba.fastjson.JSONObject;
import com.lids.po.Notice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface NoticeService {

    boolean saveHomePage(String fileUrl, JSONObject json);

    JSONObject getHomePage();

    boolean saveUsingHelp(String fileUrl, JSONObject json);

    JSONObject getUsingHelp();

    boolean addNotice(Notice notice,MultipartFile imageFile,List<MultipartFile> affixFiles);

    List<Map> getNoticeList();

    int delete(int id);

    Notice get(int id);

    List<Map> searchNoticeList(String key);

    boolean addSpaceNotice(JSONObject jsonObject,
                           List<MultipartFile> albumFiles,
                           List<MultipartFile> deployFiles,String type);

    JSONObject getSpaceNotice(String type);
}
