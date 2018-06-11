package com.lids.po;

public class OpenRule {

    private int id;
    private String name;
    private int forStudent;
    private int forStaff;
    private int forTeacher;
    private int planOneId;
    private int planTwoId;
    private String applicationPlan;
    private OpenTime one;
    private OpenTime two;

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

    public OpenTime getOne() {
        return one;
    }

    public void setOne(OpenTime one) {
        this.one = one;
    }

    public OpenTime getTwo() {
        return two;
    }

    public void setTwo(OpenTime two) {
        this.two = two;
    }

    public int getPlanOneId() {
        return planOneId;
    }

    public void setPlanOneId(int planOneId) {
        this.planOneId = planOneId;
    }

    public int getPlanTwoId() {
        return planTwoId;
    }

    public void setPlanTwoId(int planTwoId) {
        this.planTwoId = planTwoId;
    }

    public String getApplicationPlan() {
        return applicationPlan;
    }

    public void setApplicationPlan(String applicationPlan) {
        this.applicationPlan = applicationPlan;
    }
}
