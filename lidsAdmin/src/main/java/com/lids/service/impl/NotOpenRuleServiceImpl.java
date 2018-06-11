package com.lids.service.impl;

import com.lids.dao.NotOpenRuleDao;
import com.lids.dao.NotOpenTimeDao;
import com.lids.po.NotOpenRulePlan;
import com.lids.po.NotOpenTime;
import com.lids.service.BaseService;
import com.lids.service.NotOpenRuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("NotOpenRuleService")
public class NotOpenRuleServiceImpl extends BaseService implements NotOpenRuleService {

    @Resource
    private NotOpenRuleDao notOpenRuleDao;
    @Resource
    private NotOpenTimeDao notOpenTimeDao;

    @Override
    public boolean addNewOpenRule(NotOpenRulePlan notOpenRulePlan) {
        int result = notOpenRuleDao.insert(notOpenRulePlan);
        if (result != 0){
            int planId = notOpenRulePlan.getId();
            for (NotOpenTime notOpenTime:notOpenRulePlan.getTimeList()){
                notOpenTime.setPlanId(planId);
                notOpenTimeDao.insert(notOpenTime);
            }
            return true;
        }
        return false;
    }

    @Override
    public NotOpenRulePlan getRule(int id) {
        NotOpenRulePlan notOpenRulePlan = notOpenRuleDao.selectById(id);
        List<NotOpenTime> timeList = notOpenTimeDao.selectList(id);
        notOpenRulePlan.setTimeList(timeList);
        return notOpenRulePlan;
    }

    @Override
    public boolean editRule(NotOpenRulePlan notOpenRulePlan) {
        int deletePlan = notOpenRuleDao.delete(notOpenRulePlan.getId());
        if (deletePlan > 0){
            int deleteTime = notOpenTimeDao.delete(notOpenRulePlan.getId());
            if (deleteTime > 0){
                return addNewOpenRule(notOpenRulePlan);
            }
        }
        return false;
    }
}
