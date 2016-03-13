package com.comichentai.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class JumpDo extends BasicDo {

    /** */
    private String specialId;

    /** */
    private String comicId;

    public JumpDo() {

    }

    public JumpDo(String specialId, String comicId) {

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
    