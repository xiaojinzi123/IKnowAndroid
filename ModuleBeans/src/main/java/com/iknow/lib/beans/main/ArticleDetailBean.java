package com.iknow.lib.beans.main;

import java.util.List;

public class ArticleDetailBean {

    private String content;
    private int id;
    private String publishTime;
    private String shortDesc;
    private String title;
    private int type;
    private List<String> thumbnailImageUrls;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<String> getThumbnailImageUrls() {
        return thumbnailImageUrls;
    }

    public void setThumbnailImageUrls(List<String> thumbnailImageUrls) {
        this.thumbnailImageUrls = thumbnailImageUrls;
    }

}
