package com.lids.dao;

import com.lids.po.Scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ScheduleDao {

    Integer generateSchedule(Date date);

    List<Map<String,String>> getRoomSchedulers(Date date);

    List<Map<String,String>> getSeatSchedulers(int spaceId);

}
