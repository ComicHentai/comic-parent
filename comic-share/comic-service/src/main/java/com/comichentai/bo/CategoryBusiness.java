package com.comichentai.bo;

import com.comichentai.dto.CategoryDto;
import com.comichentai.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/14.
 */
public interface CategoryBusiness {


    /**
     * 通过分类获取漫画。
     *
     *@param classifiedId 分类ID
     *@return ResultSupport.module = Map<分类，漫画>
     * */
    ResultSupport<CategoryDto> getComicByClassified(Integer classifiedId);


    /**
     * 通过分类获取专辑。
     *
     * @param classifiedId 分类ID
     * @return ResultSupport.module = Map<分类，专辑>
     * */
    ResultSupport<CategoryDto> getSpecialByClassified(Integer classifiedId);


    /**
     * 获取漫画详细信息
     *
     * @param comicId 漫画ID
     * @return ResultSupport.module = 漫画详细信息
     * */
    ResultSupport<CategoryDto> getComicClassified(Integer comicId);


    /**
     * 获取专辑分类信息
     *
     * @param specialId 专辑ID
     * @return ResultSupport.module = 专辑分类详细信息
     * */
    ResultSupport<CategoryDto> getSpecialClassified(Integer specialId);


}

