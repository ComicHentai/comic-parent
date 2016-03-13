package com.comichentai.dto;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class JumpDto extends BasicDto {

    /** */
    private String specialId;

    /** */
    private String comicId;

    public JumpDto() {

    }

    public JumpDto(String specialId, String comicId) {

        this.specialId = specialId;
        this.comicId = comicId;


    }

    public String getSpecialId() {
        return specialId;
    }

    public void setSpecialId(String specialId) {
        this.specialId = specialId;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

}
    