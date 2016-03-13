package com.comichentai.dataobject;

/**
 * 实体DO
 * Created by hope6537 by Code Generator
 */
public class FavoriteDo extends BasicDo {

    /** */
    private String userId;

    /** */
    private String targetId;

    /** */
    private String targetType;

    public FavoriteDo() {

    }

    public FavoriteDo(String userId, String targetId, String targetType) {

        this.userId = userId;
        this.targetId = targetId;
        this.targetType = targetType;


    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
    