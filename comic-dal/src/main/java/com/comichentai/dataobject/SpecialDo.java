package com.comichentai.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class SpecialDo extends BasicDo {

    /** */
    private String title;

    /** */
    private String userId;

    public SpecialDo() {

    }

    public SpecialDo(String title, String userId) {

        this.title = title;
        this.userId = userId;


    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
    