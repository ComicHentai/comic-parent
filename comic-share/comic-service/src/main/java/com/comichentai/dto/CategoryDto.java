package com.comichentai.dto;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 实体DTO
 * Created by hope6537 by Code Generator
 */
public class CategoryDto extends BasicDto {

    /**
     * 分类ID
     */
    private Integer classifiedId;

    /**
     * 目标ID
     */
    private Integer targetId;

    /**
     * 目标类型 0-漫画 1-专辑
     */
    private Integer targetType;

    /**
     * 获取漫画详细信息
     * */
    private Map<String, Object> relation = new ConcurrentHashMap<>();



    public CategoryDto() {

    }

    public CategoryDto(Integer classifiedId, Integer targetId, Integer targetType) {

        this.classifiedId = classifiedId;
        this.targetId = targetId;
        this.targetType = targetType;

    }

    public Integer getClassifiedId() {
        return classifiedId;
    }

    public void setClassifiedId(Integer classifiedId) {
        this.classifiedId = classifiedId;
    }

    public Integer getTargetId() {
        return targetId;
    }

    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }

    public Integer getTargetType() {
        return targetType;
    }

    public void setTargetType(Integer targetType) {
        this.targetType = targetType;
    }

    public Map<String, Object> getRelation() {
        return relation;
    }

    public void setRelation(Map<String, Object> relation) {
        this.relation = relation;
    }
}

