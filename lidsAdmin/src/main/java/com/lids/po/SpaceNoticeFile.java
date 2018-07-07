package com.lids.po;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class SpaceNoticeFile {

    private int id;
    private String type;
    private String fileName;
    private String fileLink;
    private int spaceNoticeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setFileLink(String fileLink) {
        this.fileLink = fileLink;
    }

    public int getSpaceNoticeId() {
        return spaceNoticeId;
    }

    public void setSpaceNoticeId(int spaceNoticeId) {
        this.spaceNoticeId = spaceNoticeId;
    }
}
