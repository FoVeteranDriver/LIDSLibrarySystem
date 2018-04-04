package com.lids.service.impl;

import com.lids.dao.NoticeDao;
import com.lids.po.Notice;
import com.lids.service.NoticeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService{

    @Resource
    private NoticeDao noticeDao;

    public Notice getLatest() {
        return noticeDao.getLatestNotice();
    }
}
