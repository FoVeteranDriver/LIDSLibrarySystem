package com.lids.po;

public class File {

    private int id;
    private String fileName;
    private String fileLink;
    private int noticeId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setNoticeId(int noticeId) {
        this.noticeId = noticeId;
    }

}
