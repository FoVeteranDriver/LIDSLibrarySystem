package com.lids.dao;

import com.lids.po.OpenRule;

import java.util.List;
import java.util.Map;

public interface OpenRuleDao {

    int insert(OpenRule openRule);

    OpenRule selectById(int id);

    int delete(int id);

    List<Map> getAllRule();
}
