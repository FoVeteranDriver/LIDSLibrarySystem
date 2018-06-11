package com.lids.service;

import com.lids.po.OpenRule;

public interface OpenRuleService {

    boolean addNewOpenRule(OpenRule openRule);

    OpenRule getRule(int id);

    boolean editRule(OpenRule openRule);
}
