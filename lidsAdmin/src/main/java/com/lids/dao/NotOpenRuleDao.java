package com.lids.dao;

import com.lids.po.NotOpenRulePlan;

public interface NotOpenRuleDao {

    int insert(NotOpenRulePlan notOpenRulePlan);

    NotOpenRulePlan selectById(int id);

    int delete(int id);
}
