package com.comichentai.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class CategoryDo extends BasicDo {

    /** */
    private String classifiedId;

    /** */
    private String targetId;

    /** */
    private String targetType;

    public CategoryDo() {

    }

    public CategoryDo(String classifiedId, String targetId, String targetType) {

        this.classifiedId = classifiedId;
        this.targetId = targetId;
        this.targetType = targetType;


    }

    public String getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(String classifiedId) {
        this.classifiedId = classifiedId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

}
    