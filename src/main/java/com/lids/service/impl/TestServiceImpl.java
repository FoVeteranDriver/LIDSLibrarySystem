package com.lids.service.impl;

import com.lids.dao.TestDao;
import com.lids.service.TestService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("testService")
public class TestServiceImpl implements TestService{

    @Resource
    private TestDao testDao;

    public String test() {
        return testDao.getTest();
    }
}
