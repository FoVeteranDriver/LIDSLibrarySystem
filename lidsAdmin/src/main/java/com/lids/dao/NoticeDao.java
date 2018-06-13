package com.lids.dao;

import com.lids.po.File;
import com.lids.po.Notice;

import java.util.List;
import java.util.Map;

public interface NoticeDao {

    int insert(Notice notice);

    int insertFile(File file);

    List<Map> selectNoticeListAdmin();

    int delete(int id);

    Notice selectById(int id);

    List<File> selectFileByNoticeId(int id);

    List<Map> searchNoticeList(Map key);
}
