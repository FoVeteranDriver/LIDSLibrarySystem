package com.lids.dao;

import com.lids.po.Scheduler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface ScheduleDao {

    Integer generateSchedule(Date date);

    List<Scheduler> getRoomSchedulers();

    List<Scheduler> getSeatSchedulers(int spaceId);

}
