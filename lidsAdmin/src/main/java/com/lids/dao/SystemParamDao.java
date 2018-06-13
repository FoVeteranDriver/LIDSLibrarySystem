package com.lids.dao;

import com.lids.po.SystemParam;

import java.util.List;

public interface SystemParamDao {

    int insert(SystemParam systemParam);

    List<SystemParam> selectByGroup(String groupName,String maxType);

    String MaxTypeByGroup(String groupName);
}
