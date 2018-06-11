package com.lids.dao;

import com.lids.po.NotOpenTime;

import java.util.List;

public interface NotOpenTimeDao {

    int insert(NotOpenTime notOpenTime);

    NotOpenTime selectById(int id);

    List<NotOpenTime> selectList(int planId);

    int delete(int id);
}
