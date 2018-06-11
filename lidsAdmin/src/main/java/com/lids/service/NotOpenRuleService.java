package com.lids.service;

import com.lids.po.NotOpenRulePlan;

public interface NotOpenRuleService {

    boolean addNewOpenRule(NotOpenRulePlan notOpenRulePlan);

    NotOpenRulePlan getRule(int id);

    boolean editRule(NotOpenRulePlan notOpenRulePlan);
}
