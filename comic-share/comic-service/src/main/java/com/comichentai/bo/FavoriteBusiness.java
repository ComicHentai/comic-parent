package com.comichentai.bo;

import com.comichentai.dto.FavoriteDto;
import com.comichentai.entity.ResultSupport;

/**
 * Created by Dintama on 2016/3/14.
 */
public interface FavoriteBusiness {

    /**
     * 获取用户收藏漫画
     *
     *@param favoriteDto query-用户ID
     *@return ResultSupport.module = 用户收藏漫画
     * */
    ResultSupport<FavoriteDto> getUserFavoriteComics(FavoriteDto favoriteDto);


    /**
     * 获取用户收藏专辑
     *
     * @param favoriteDto query-用户ID
     * @return ResultSupport.module = 用户收藏专辑
     * */
    ResultSupport<FavoriteDto> getUserFavoriteSpecials(FavoriteDto favoriteDto);

}
