package com.lids.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value={"openRuleName","info"})
public class Area {

    private String name;
    private String openRuleName;
    private int isLeaf;
    private int hasRoom;
    private String paren;
    private String info;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOpenRuleName() {
        return openRuleName;
    }

    public void setOpenRuleName(String openRuleName) {
        this.openRuleName = openRuleName;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getHasRoom() {
        return hasRoom;
    }

    public void setHasRoom(int hasRoom) {
        this.hasRoom = hasRoom;
    }

    public String getParen() {
        return paren;
    }

    public void setParen(String paren) {
        this.paren = paren;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
