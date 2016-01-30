package com.comichentai.dao;

import com.comichentai.dataobject.ComicDo;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface ComicDao {

    ComicDo selectComicById(Integer id);

    List<ComicDo> selectComicListByIds(List<Integer> idList);

}
