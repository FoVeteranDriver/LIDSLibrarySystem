package com.lids.dao;

import com.lids.po.OpenRule;

public interface OpenRuleDao {

    int insert(OpenRule openRule);

    OpenRule selectById(int id);

    int delete(int id);
}
