package com.iknow.lib.beans;

import java.util.List;

public class ArticleBean {

    private int id;
    private int type;
    private String publishTime;
    private String title;
    private String shortDesc;
    private List<String> thumbnailImageUrls;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public List<String> getThumbnailImageUrls() {
        return thumbnailImageUrls;
    }

    public void setThumbnailImageUrls(List<String> thumbnailImageUrls) {
        this.thumbnailImageUrls = thumbnailImageUrls;
    }

}
