package com.lids.po;

import java.util.Date;

/**
 * @author yaoyou
 * @description 预定记录实体类
 */
public class BookingRecord {

    private int id;
    private int userId;
    private int spaceId;
    private String spaceName;
    private int isRoom;
    private Date date;
    private Date beginTime;
    private Date endTime;
    private int isActive;
    private int hasCheckIn;
    private int hasDefault;
    private int userCount;
    private String application;
    private Date bookingTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }

    public String getSpaceName() {
        return spaceName;
    }

    public void setSpaceName(String spaceName) {
        this.spaceName = spaceName;
    }

    public int getIsRoom() {
        return isRoom;
    }

    public void setIsRoom(int isRoom) {
        this.isRoom = isRoom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public int getHasCheckIn() {
        return hasCheckIn;
    }

    public void setHasCheckIn(int hasCheckIn) {
        this.hasCheckIn = hasCheckIn;
    }

    public int getHasDefault() {
        return hasDefault;
    }

    public void setHasDefault(int hasDefault) {
        this.hasDefault = hasDefault;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public Date getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Date bookingTime) {
        this.bookingTime = bookingTime;
    }
}
