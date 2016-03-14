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
     * 通过分类获取漫画
     * */
    private Map<ClassifiedDto,  List<ComicDto>> comicByClassfied = new ConcurrentHashMap<>();


    /**
     * 通过分类获取专辑
     * */
    private Map<ClassifiedDto, List<SpecialDto>> specialByClassfied = new ConcurrentHashMap<>();


    /**
     * 获取漫画详细信息
     * */
    private Map<ComicDto, List<ClassifiedDto>> comic = new ConcurrentHashMap<>();

    /**
     * 获取分类详细信息
     * */
    private Map<SpecialDto, List<ClassifiedDto>> special = new ConcurrentHashMap<>();



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

    public Map<ClassifiedDto, List<ComicDto>> getComicByClassfied() {
        return comicByClassfied;
    }

    public void setComicByClassfied(Map<ClassifiedDto, List<ComicDto>> comicByClassfied) {
        this.comicByClassfied = comicByClassfied;
    }

    public Map<ClassifiedDto, List<SpecialDto>> getSpecialByClassfied() {
        return specialByClassfied;
    }

    public void setSpecialByClassfied(Map<ClassifiedDto, List<SpecialDto>> specialByClassfied) {
        this.specialByClassfied = specialByClassfied;
    }

    public Map<ComicDto, List<ClassifiedDto>> getComic() {
        return comic;
    }

    public void setComic(Map<ComicDto, List<ClassifiedDto>> comic) {
        this.comic = comic;
    }

    public Map<SpecialDto, List<ClassifiedDto>> getSpecial() {
        return special;
    }

    public void setSpecial(Map<SpecialDto, List<ClassifiedDto>> special) {
        this.special = special;
    }
}
    