package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import com.comichentai.dataobject.TestComicDo;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
@MybatisRepository
public interface TestComicDao {

    TestComicDo selectComicById(Integer id);

    List<TestComicDo> selectComicListByIds(List<Integer> idList);

}
