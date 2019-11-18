package com.iknow.lib.beans;

/**
 * 文章对象
 * time   : 2019/04/11
 *
 * @author : xiaojinzi 30212
 */
public class Article {

    /**
     * id
     */
    private String id;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 缩略图
     */
    private String imageThumbnail;

    /**
     * 描述
     */
    private String desc;

    public String getArticleId() {
        return id;
    }

    public void setArticleId(String articleId) {
        this.id = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}
