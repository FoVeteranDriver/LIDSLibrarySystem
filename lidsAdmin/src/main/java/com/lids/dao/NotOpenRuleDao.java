package com.lids.dao;

import com.lids.po.NotOpenRulePlan;

import java.util.List;
import java.util.Map;

public interface NotOpenRuleDao {

    int insert(NotOpenRulePlan notOpenRulePlan);

    NotOpenRulePlan selectById(int id);

    int delete(int id);

    List<Map> getAllRule();
}
