package com.lids.service.impl;

import com.lids.dao.OpenRuleDao;
import com.lids.dao.OpenTimeDao;
import com.lids.po.OpenRule;
import com.lids.service.BaseService;
import com.lids.service.OpenRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("OpenRuleService")
public class OpenRuleServiceImpl extends BaseService implements OpenRuleService{

    @Resource
    private OpenRuleDao openRuleDao;
    @Resource
    private OpenTimeDao openTimeDao;

    @Override
    public boolean addNewOpenRule(OpenRule openRule) {
        int one = openTimeDao.insert(openRule.getOne());
        if (one == 1){
            openRule.setPlanOneId(openRule.getOne().getId());
        }
        int two = openTimeDao.insert(openRule.getTwo());
        if (two == 1){
            openRule.setPlanTwoId(openRule.getTwo().getId());
        }
        int result = openRuleDao.insert(openRule);
        if (result > 0){
            return true;
        }
        return false;
    }

    @Override
    public OpenRule getRule(int id) {
        OpenRule result = openRuleDao.selectById(id);
        result.setOne(openTimeDao.selectById(result.getPlanOneId()));
        result.setTwo(openTimeDao.selectById(result.getPlanTwoId()));
        return result;
    }

    @Override
    public boolean editRule(OpenRule openRule) {
        int deleteOne = openTimeDao.delete(openRule.getOne().getId());
        int deleteTwo = openTimeDao.delete(openRule.getTwo().getId());
        int deletePlan = openRuleDao.delete(openRule.getId());
        if (deleteOne > 0 && deleteTwo > 0 && deletePlan >0){
            return addNewOpenRule(openRule);
        }
        return false;
    }
}
