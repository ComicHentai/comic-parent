package com.comichentai.service;

import com.comichentai.dto.ComicDto;

import java.util.List;

/**
 * Created by Dintama on 2016/2/28.
 */
public interface ComicService {


    /* 获取欢迎页漫画 */
    List<ComicDto> getWelcomeComicListPage(Integer userId);

    /* 通过漫画ID获取漫画信息 */
    ComicDto getComicById(Integer userId, Integer comicId);

    /* 通过漫画名称或作者名获取漫画信息 */
    List<ComicDto> getComicByTitleOrAuthor(String titleOrAuthor);

    /* 通过用户ID获取漫画信息 */
    List<ComicDto> getComicByUserId(Integer userId);

    /* 新增漫画 */
    void addComic(ComicDto comicDto);

    /* 用户收藏漫画 */
    void addComicFromUser(Integer userId, Integer comicId);


    /* 修改漫画 */
    void modifyComic(ComicDto comicDto);

    /* 删除漫画 */
    void removeComicById(Integer id);

    /* 用户删除收藏漫画 */
    void removeComicFromUser(Integer userId, List<Integer> comicIds);

}
