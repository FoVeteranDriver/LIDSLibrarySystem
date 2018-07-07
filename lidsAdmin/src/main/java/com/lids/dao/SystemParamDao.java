package com.lids.dao;

import com.lids.po.SpaceNoticeFile;
import com.lids.po.SystemParam;

import java.util.List;

public interface SystemParamDao {

    int insert(SystemParam systemParam);

    List<SystemParam> select(String groupName);

    int insertFile(SpaceNoticeFile file);

    List<SpaceNoticeFile> getFiles(String type,int spaceNoticeId);

    SystemParam getSpaceNotice(String groupName);
}
