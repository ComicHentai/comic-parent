package com.comichentai.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class SpecialDto extends BasicDto {

    /** */
    private String title;

    /** */
    private String userId;

    public SpecialDto() {

    }

    public SpecialDto(String title, String userId) {

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
    