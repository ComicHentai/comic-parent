package com.comichentai.dataobject;

import com.comichentai.page.PageDo;

import java.util.List;

/**
 * Created by Dintama on 2016/2/20.
 */
public class ComicDo extends BasicDo{

    private String title;         /*漫画名*/
    private String coverTitle;    /*封面存储路径*/
    private String author;        /*漫画作者*/
    private String introduction;  /*漫画简介*/
    private String contentTitle;  /*漫画内容存储地址*/
    private List<String> classifieds;   /*漫画标签*/

    public ComicDo(){

    }

    public ComicDo(Long created, Long updated, Integer status, Integer isDeleted, String title, String coverTitle, String author, String introduction, String contentTitle) {
        super(created, updated, status, isDeleted);
        this.title = title;
        this.coverTitle = coverTitle;
        this.author = author;
        this.introduction = introduction;
        this.contentTitle = contentTitle;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public Long getCreated() {
        return super.getCreated();
    }

    @Override
    public void setCreated(Long created) {
        super.setCreated(created);
    }

    @Override
    public Long getUpdated() {
        return super.getUpdated();
    }

    @Override
    public void setUpdated(Long updated) {
        super.setUpdated(updated);
    }

    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Integer status) {
        super.setStatus(status);
    }

    @Override
    public Integer getIsDeleted() {
        return super.getIsDeleted();
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        super.setIsDeleted(isDeleted);
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
