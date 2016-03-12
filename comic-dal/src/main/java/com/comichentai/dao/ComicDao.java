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

    List<ComicDo> selectComicListPage();

    ComicDo selectComicByIds(@Param("ids")Integer... ids);

    ComicDo selectComicById(@Param("id")Integer id);

    List<ComicDo> selectComicByTitle(@Param("title") String title);

    void insertComic(ComicDo comicDo);

    void updateComic(ComicDo comicDo);

    void deleteComicByIds(@Param("ids") Integer... ids);



}
