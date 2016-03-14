package com.comichentai.business.impl;

import com.comichentai.bo.FavoriteBusiness;
import com.comichentai.dto.ComicDto;
import com.comichentai.dto.FavoriteDto;
import com.comichentai.dto.SpecialDto;
import com.comichentai.dto.UserInfoDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.service.ComicService;
import com.comichentai.service.FavoriteService;
import com.comichentai.service.SpecialService;
import com.comichentai.service.UserInfoService;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Dintama on 2016/3/14.
 */
@Service("favoriteBusiness")
public class FavoriteBusinessImpl implements FavoriteBusiness {

    @Resource(name = "userInfoService")
    private UserInfoService userInfoService;

    @Resource(name = "comicService")
    private ComicService comicService;

    @Resource(name = "specialService")
    private SpecialService specialService;

    @Resource(name = "favoriteService")
    private FavoriteService favoriteService;

    @Override
    public ResultSupport<FavoriteDto> getUserFavoriteComics(Integer userId) {
        /*获取Key*/
        ResultSupport<UserInfoDto> userInfoById = userInfoService.getUserInfoById(userId);
        if(!userInfoById.isSuccess()){
            return userInfoById.castToReturnFailed(FavoriteDto.class);
        }
        /*整理得到一个FavoriteDto*/
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(userId);
        favoriteDto.setTargetType(0);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(favoriteDto);
        if(!favoriteListByQuery.isSuccess()){
            return favoriteListByQuery.castToReturnFailed(FavoriteDto.class);
        }
        List<FavoriteDto> favoriteDtos = favoriteListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(FavoriteDto tmp : favoriteDtos){
            if(tmp.getTargetType() == 0){
                idList.add(tmp.getTargetId());
            }
        }
        ResultSupport<List<ComicDto>> comicListByIdList = comicService.getComicListByIdList(idList);
        if(!comicListByIdList.isSuccess()){
            return comicListByIdList.castToReturnFailed(FavoriteDto.class);
        }
        Map<UserInfoDto, List<ComicDto>> result = new ConcurrentHashMap<>();
        result.put(userInfoById.getModule(), comicListByIdList.getModule());
        FavoriteDto favoriteDto1 = new FavoriteDto();
        favoriteDto1.setFavoriteComic(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", favoriteDto1);
    }

    @Override
    public ResultSupport<FavoriteDto> getUserFavoriteSpecials(Integer userId) {
        /*获取Key*/
        ResultSupport<UserInfoDto> userInfoById = userInfoService.getUserInfoById(userId);
        if(!userInfoById.isSuccess()){
            return userInfoById.castToReturnFailed(FavoriteDto.class);
        }
        /*整理得到一个FavoriteDto*/
        FavoriteDto favoriteDto = new FavoriteDto();
        favoriteDto.setUserId(userId);
        favoriteDto.setTargetType(1);
        ResultSupport<List<FavoriteDto>> favoriteListByQuery = favoriteService.getFavoriteListByQuery(favoriteDto);
        if(!favoriteListByQuery.isSuccess()){
            return favoriteListByQuery.castToReturnFailed(FavoriteDto.class);
        }
        List<FavoriteDto> favoriteDtos = favoriteListByQuery.getModule();
        List<Integer> idList = new LinkedList<>();
        for(FavoriteDto tmp : favoriteDtos){
            if(tmp.getTargetType() == 1){
                idList.add(tmp.getTargetId());
            }
        }
        ResultSupport<List<SpecialDto>> specialListByIdList = specialService.getSpecialListByIdList(idList);
        if(!specialListByIdList.isSuccess()){
            return specialListByIdList.castToReturnFailed(FavoriteDto.class);
        }
        Map<UserInfoDto, List<SpecialDto>> result = new ConcurrentHashMap<>();
        result.put(userInfoById.getModule(), specialListByIdList.getModule());
        FavoriteDto favoriteDto1 = new FavoriteDto();
        favoriteDto1.setFavoriteSpecial(result);
        return ResultSupport.getInstance(true, "[关联查询成功]", favoriteDto1);
    }
}
