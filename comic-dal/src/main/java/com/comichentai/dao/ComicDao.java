package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import com.comichentai.dataobject.ComicDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Dintama on 2016/2/20.
 */
@MybatisRepository
public interface ComicDao {

    List<ComicDo> selectWelcomeComicListPage();     //首页漫画搜索

    ComicDo selectComicById(@Param("id") int id);

    List<String> selectComicClassifieds(@Param("id")int id);

    List<ComicDo> selectComicByTitle(@Param("title")String title);

    List<ComicDo> selectComicByAuthor(@Param("author")String author);

    List<ComicDo> selectComicByUserId(@Param("userId")int userId);

    void insertComic(ComicDo comicDo);

    void insertComicFromUser(@Param("userId")Integer userId, @Param("comicId")Integer comicId);

    void updateComic(ComicDo comicDo);

    void deleteComicById(@Param("id")int id);

    void deleteComicFromUser(@Param("userId")Integer userId, @Param("comicIds")List<Integer> comicIds);



}
