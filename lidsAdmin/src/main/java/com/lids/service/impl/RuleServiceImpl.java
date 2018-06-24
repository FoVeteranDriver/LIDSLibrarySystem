package com.lids.service.impl;

import com.lids.dao.BookingRuleDao;
import com.lids.dao.NotOpenRuleDao;
import com.lids.dao.OpenRuleDao;
import com.lids.po.NotOpenRulePlan;
import com.lids.service.BaseService;
import com.lids.service.RuleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("RuleService")
public class RuleServiceImpl extends BaseService implements RuleService{

    @Resource
    private OpenRuleDao openRuleDao;
    @Resource
    private NotOpenRuleDao notOpenRuleDao;
    @Resource
    private BookingRuleDao bookingRuleDao;

    @Override
    public List<Map> getAllRules() {
        List<Map> result = new ArrayList<>();

        List<Map> open = openRuleDao.getAllRule();
        List<Map> notOpen = notOpenRuleDao.getAllRule();
        List<Map> booking = bookingRuleDao.getAllRule();
        for (Map openMap : open){
            openMap.put("type","总体规则");
            openMap.put("remark","");
            result.add(openMap);
        }
        for (Map notOpenMap : notOpen){
            notOpenMap.put("type","不开放规则");
            notOpenMap.put("remark","");
            result.add(notOpenMap);
        }
        for (Map bookingMap : booking){
            bookingMap.put("type","预约规则");
            bookingMap.put("remark","");
            result.add(bookingMap);
        }

        return result;
    }
}
