package com.comichentai.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class ComicDto extends BasicDto {

    /** */
    private String title;

    /** */
    private String coverTitle;

    /** */
    private String author;

    /** */
    private String introduction;

    /** */
    private String contentTitle;

    public ComicDto() {

    }

    public ComicDto(String title, String coverTitle, String author, String introduction, String contentTitle) {

        this.title = title;
        this.coverTitle = coverTitle;
        this.author = author;
        this.introduction = introduction;
        this.contentTitle = contentTitle;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverTitle() {
        return coverTitle;
    }

    public void setCoverTitle(String coverTitle) {
        this.coverTitle = coverTitle;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getContentTitle() {
        return contentTitle;
    }

    public void setContentTitle(String contentTitle) {
        this.contentTitle = contentTitle;
    }

}
    