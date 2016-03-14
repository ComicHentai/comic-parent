package com.comichentai.business.impl;

import com.comichentai.bo.CategoryBusiness;
import com.comichentai.dto.CategoryDto;
import com.comichentai.dto.ClassifiedDto;
import com.comichentai.dto.ComicDto;
import com.comichentai.dto.SpecialDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.service.CategoryService;
import com.comichentai.service.ClassifiedService;
import com.comichentai.service.ComicService;
import com.comichentai.service.SpecialService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dintama on 2016/3/14.
 */
@Service("categoryBusiness")
public class CategoryBusinessImpl implements CategoryBusiness {

    @Resource(name = "classifiedService")
    private ClassifiedService classifiedService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "categoryService")
    private CategoryService categoryService;

    @Override
    public ResultSupport<CategoryDto> getComicByClassified(Integer classifiedId) {
        /*获取Key*/
        ResultSupport<ClassifiedDto> classifiedById = classifiedService.getClassifiedById(classifiedId);
        if(!classifiedById.isSuccess()){
            return classifiedById.castToReturnFailed(CategoryDto.class);
        }
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setClassifiedId(classifiedId);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(CategoryDto.class);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            if(tmp.getTargetType() == 0) {  //如果是漫画的
                idList.add(tmp.getTargetId());
            }
        }
        ResultSupport<List<ComicDto>> comicListByIdList = comicService.getComicListByIdList(idList);
        if(!comicListByIdList.isSuccess()){
            return comicListByIdList.castToReturnFailed(CategoryDto.class);
        }
        Map<ClassifiedDto, List<ComicDto>> result = new ConcurrentHashMap<>();
        result.put(classifiedById.getModule(), comicListByIdList.getModule());
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setComicByClassfied(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }

    @Override
    public ResultSupport<CategoryDto> getSpecialByClassified(Integer classifiedId) {
        /*获取Key*/
        ResultSupport<ClassifiedDto> classifiedById = classifiedService.getClassifiedById(classifiedId);
        if(!classifiedById.isSuccess()){
            return classifiedById.castToReturnFailed(CategoryDto.class);
        }
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setClassifiedId(classifiedId);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(CategoryDto.class);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            if(tmp.getTargetType() == 1) {  //如果是专辑的
                idList.add(tmp.getTargetId());
            }
        }
        ResultSupport<List<SpecialDto>> specialListByIdList = specialService.getSpecialListByIdList(idList);
        if(!specialListByIdList.isSuccess()){
            return specialListByIdList.castToReturnFailed(CategoryDto.class);
        }
        Map<ClassifiedDto, List<SpecialDto>> result = new ConcurrentHashMap<>();
        result.put(classifiedById.getModule(), specialListByIdList.getModule());
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setSpecialByClassfied(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }

    @Override
    public ResultSupport<CategoryDto> getComicClassified(Integer comicId) {
        /*获取Key*/
        ResultSupport<ComicDto> comicById = comicService.getComicById(comicId);
        if(!comicById.isSuccess()){
            return comicById.castToReturnFailed(CategoryDto.class);
        }
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTargetId(comicId);
        categoryDto.setTargetType(0);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(CategoryDto.class);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            if(tmp.getTargetType() == 0){
                idList.add(tmp.getClassifiedId());
            }
        }
        ResultSupport<List<ClassifiedDto>> classifiedListByIdList = classifiedService.getClassifiedListByIdList(idList);
        if(!classifiedListByIdList.isSuccess()){
            return classifiedListByIdList.castToReturnFailed(CategoryDto.class);
        }
        Map<ComicDto, List<ClassifiedDto>> result = new ConcurrentHashMap<>();
        result.put(comicById.getModule(), classifiedListByIdList.getModule());
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setComic(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }

    @Override
    public ResultSupport<CategoryDto> getSpecialClassified(Integer specialId) {
        /*获取Key*/
        ResultSupport<SpecialDto> specialById = specialService.getSpecialById(specialId);
        if(!specialById.isSuccess()){
            return specialById.castToReturnFailed(CategoryDto.class);
        }
        /*整理得到一个CategoryDto*/
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setTargetId(specialId);
        categoryDto.setTargetType(1);
        ResultSupport<List<CategoryDto>> categoryListByQuery = categoryService.getCategoryListByQuery(categoryDto);
        if(!categoryListByQuery.isSuccess()){
            return categoryListByQuery.castToReturnFailed(CategoryDto.class);
        }
        List<CategoryDto> categoryDtos = categoryListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(CategoryDto tmp : categoryDtos){
            if(tmp.getTargetType() == 1){
                idList.add(tmp.getClassifiedId());
            }
        }
        ResultSupport<List<ClassifiedDto>> classifiedListByIdList = classifiedService.getClassifiedListByIdList(idList);
        if(!classifiedListByIdList.isSuccess()){
            return classifiedListByIdList.castToReturnFailed(CategoryDto.class);
        }
        Map<SpecialDto, List<ClassifiedDto>> result = new ConcurrentHashMap<>();
        result.put(specialById.getModule(), classifiedListByIdList.getModule());
        CategoryDto categoryDto1 = new CategoryDto();
        categoryDto1.setSpecial(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", categoryDto1);
    }
}
