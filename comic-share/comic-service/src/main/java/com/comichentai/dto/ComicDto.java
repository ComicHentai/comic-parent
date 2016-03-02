package com.comichentai.dto;

import com.comichentai.enums.IsDeleted;

import java.util.List;

/**
 * Created by Dintama on 2016/2/28.
 */
public class ComicDto extends BasicDto {

    private String title;         /*漫画名*/
    private String coverTitle;    /*封面存储路径*/
    private String author;        /*漫画作者*/
    private String introduction;  /*漫画简介*/
    private String contentTitle;  /*漫画内容存储地址*/
    private List<String> classifieds;   /*漫画标签*/

    public ComicDto() {
    }

    public ComicDto(Long created, Long updated, Integer status, IsDeleted isDeleted, String title, String coverTitle, String author, String introduction, String contentTitle) {
        super(created, updated, status, isDeleted);
        this.title = title;
        this.coverTitle = coverTitle;
        this.author = author;
        this.introduction = introduction;
        this.contentTitle = contentTitle;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (coverTitle != null ? coverTitle.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (introduction != null ? introduction.hashCode() : 0);
        result = 31 * result + (contentTitle != null ? contentTitle.hashCode() : 0);
        result = 31 * result + (classifieds != null ? classifieds.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ComicDto comicDto = (ComicDto) o;

        if (title != null ? !title.equals(comicDto.title) : comicDto.title != null) return false;
        if (coverTitle != null ? !coverTitle.equals(comicDto.coverTitle) : comicDto.coverTitle != null) return false;
        if (author != null ? !author.equals(comicDto.author) : comicDto.author != null) return false;
        if (introduction != null ? !introduction.equals(comicDto.introduction) : comicDto.introduction != null) return false;
        return contentTitle != null ? contentTitle.equals(comicDto.contentTitle) : comicDto.contentTitle == null;
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

    public List<String> getClassifieds() {
        return classifieds;
    }

    public void setClassifieds(List<String> classifieds) {
        this.classifieds = classifieds;
    }
}
