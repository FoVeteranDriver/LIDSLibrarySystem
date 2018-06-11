package com.lids.dao;

import com.lids.po.OpenTime;

public interface OpenTimeDao {

    int insert(OpenTime openTime);

    OpenTime selectById(int id);

    int delete(int id);
}
