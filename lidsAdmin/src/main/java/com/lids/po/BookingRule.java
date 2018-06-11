package com.lids.po;

import java.sql.Time;

public class BookingRule {

    private int id;
    private String bookingRuleName;
    private int allowStudent;
    private int allowTeacher;
    private int allowStaff;
    private String type;
    private int priority;
    private int needPermission;
    private int todayCreditAllow;
    private int needCheckIn;
    private int needCheckOut;
    private int isReserve;
    private int canRepeat;
    private int minDuration;
    private int maxDuration;
    private int minAdvance;
    private int maxAdvance;
    private int maxLate;
    private int minLate;
    private int maxEndBook;
    private int minEndBook;
    private int beginNotice;
    private int endNotice;
    private int minAllowPeople;
    private int maxAllowPeople;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookingRuleName() {
        return bookingRuleName;
    }

    public void setBookingRuleName(String bookingRuleName) {
        this.bookingRuleName = bookingRuleName;
    }

    public int getAllowStudent() {
        return allowStudent;
    }

    public void setAllowStudent(int allowStudent) {
        this.allowStudent = allowStudent;
    }

    public int getAllowTeacher() {
        return allowTeacher;
    }

    public void setAllowTeacher(int allowTeacher) {
        this.allowTeacher = allowTeacher;
    }

    public int getAllowStaff() {
        return allowStaff;
    }

    public void setAllowStaff(int allowStaff) {
        this.allowStaff = allowStaff;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getNeedPermission() {
        return needPermission;
    }

    public void setNeedPermission(int needPermission) {
        this.needPermission = needPermission;
    }

    public int getTodayCreditAllow() {
        return todayCreditAllow;
    }

    public void setTodayCreditAllow(int todayCreditAllow) {
        this.todayCreditAllow = todayCreditAllow;
    }

    public int getNeedCheckIn() {
        return needCheckIn;
    }

    public void setNeedCheckIn(int needCheckIn) {
        this.needCheckIn = needCheckIn;
    }

    public int getNeedCheckOut() {
        return needCheckOut;
    }

    public void setNeedCheckOut(int needCheckOut) {
        this.needCheckOut = needCheckOut;
    }

    public int getIsReserve() {
        return isReserve;
    }

    public void setIsReserve(int isReserve) {
        this.isReserve = isReserve;
    }

    public int getCanRepeat() {
        return canRepeat;
    }

    public void setCanRepeat(int canRepeat) {
        this.canRepeat = canRepeat;
    }

    public int getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(int minDuration) {
        this.minDuration = minDuration;
    }

    public int getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }

    public int getMinAdvance() {
        return minAdvance;
    }

    public void setMinAdvance(int minAdvance) {
        this.minAdvance = minAdvance;
    }

    public int getMaxAdvance() {
        return maxAdvance;
    }

    public void setMaxAdvance(int maxAdvance) {
        this.maxAdvance = maxAdvance;
    }

    public int getMaxLate() {
        return maxLate;
    }

    public void setMaxLate(int maxLate) {
        this.maxLate = maxLate;
    }

    public int getMinLate() {
        return minLate;
    }

    public void setMinLate(int minLate) {
        this.minLate = minLate;
    }

    public int getMaxEndBook() {
        return maxEndBook;
    }

    public void setMaxEndBook(int maxEndBook) {
        this.maxEndBook = maxEndBook;
    }

    public int getMinEndBook() {
        return minEndBook;
    }

    public void setMinEndBook(int minEndBook) {
        this.minEndBook = minEndBook;
    }

    public int getBeginNotice() {
        return beginNotice;
    }

    public void setBeginNotice(int beginNotice) {
        this.beginNotice = beginNotice;
    }

    public int getEndNotice() {
        return endNotice;
    }

    public void setEndNotice(int endNotice) {
        this.endNotice = endNotice;
    }

    public int getMinAllowPeople() {
        return minAllowPeople;
    }

    public void setMinAllowPeople(int minAllowPeople) {
        this.minAllowPeople = minAllowPeople;
    }

    public int getMaxAllowPeople() {
        return maxAllowPeople;
    }

    public void setMaxAllowPeople(int maxAllowPeople) {
        this.maxAllowPeople = maxAllowPeople;
    }
}
