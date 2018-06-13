package com.lids.po;

import java.util.List;

public class Notice {

    private int id;
    private String noticeTitle;
    private String noticeText;
    private String publisher;
    private String imageFile;
    private List<File> affixFiles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeText() {
        return noticeText;
    }

    public void setNoticeText(String noticeText) {
        this.noticeText = noticeText;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public List<File> getAffixFiles() {
        return affixFiles;
    }

    public void setAffixFiles(List<File> affixFiles) {
        this.affixFiles = affixFiles;
    }
}
