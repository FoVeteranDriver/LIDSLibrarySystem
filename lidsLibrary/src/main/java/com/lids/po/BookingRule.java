package com.lids.po;

import java.sql.Time;

public class BookingRule {

    private int id;
    private String bookingRuleName;
    private String userTypeName;
    private String spaceTypeName;
    private String minDuration;
    private String maxDuration;
    private String minAdvance;
    private String maxAdvance;
    private Time reserveTime;
    private String needPermission;

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

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getSpaceTypeName() {
        return spaceTypeName;
    }

    public void setSpaceTypeName(String spaceTypeName) {
        this.spaceTypeName = spaceTypeName;
    }

    public String getMinDuration() {
        return minDuration;
    }

    public void setMinDuration(String minDuration) {
        this.minDuration = minDuration;
    }

    public String getMaxDuration() {
        return maxDuration;
    }

    public void setMaxDuration(String maxDuration) {
        this.maxDuration = maxDuration;
    }

    public String getMinAdvance() {
        return minAdvance;
    }

    public void setMinAdvance(String minAdvance) {
        this.minAdvance = minAdvance;
    }

    public String getMaxAdvance() {
        return maxAdvance;
    }

    public void setMaxAdvance(String maxAdvance) {
        this.maxAdvance = maxAdvance;
    }

    public Time getReserveTime() {
        return reserveTime;
    }

    public void setReserveTime(Time reserveTime) {
        this.reserveTime = reserveTime;
    }

    public String getNeedPermission() {
        return needPermission;
    }

    public void setNeedPermission(String needPermission) {
        this.needPermission = needPermission;
    }
}
