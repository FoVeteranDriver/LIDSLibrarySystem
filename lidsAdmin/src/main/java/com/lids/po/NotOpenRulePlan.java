package com.lids.po;

import java.util.List;

public class NotOpenRulePlan {

    private int id;
    private String name;
    private int forStudent;
    private int forStaff;
    private int forTeacher;
    private int priority;
    private List<NotOpenTime> timeList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForStudent() {
        return forStudent;
    }

    public void setForStudent(int forStudent) {
        this.forStudent = forStudent;
    }

    public int getForStaff() {
        return forStaff;
    }

    public void setForStaff(int forStaff) {
        this.forStaff = forStaff;
    }

    public int getForTeacher() {
        return forTeacher;
    }

    public void setForTeacher(int forTeacher) {
        this.forTeacher = forTeacher;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public List<NotOpenTime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<NotOpenTime> timeList) {
        this.timeList = timeList;
    }
}
