package com.lids.po;

public class Space {

    private int id;
    private String name;
    private String spaceTypeName;
    private String areaName;
    private int isOpen;
    private int isOccupied;
    private int isArriving;
    private int isStepOut;

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

    public String getSpaceTypeName() {
        return spaceTypeName;
    }

    public void setSpaceTypeName(String spaceTypeName) {
        this.spaceTypeName = spaceTypeName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }

    public int getIsOccupied() {
        return isOccupied;
    }

    public void setIsOccupied(int isOccupied) {
        this.isOccupied = isOccupied;
    }

    public int getIsArriving() {
        return isArriving;
    }

    public void setIsArriving(int isArriving) {
        this.isArriving = isArriving;
    }

    public int getIsStepOut() {
        return isStepOut;
    }

    public void setIsStepOut(int isStepOut) {
        this.isStepOut = isStepOut;
    }

}
