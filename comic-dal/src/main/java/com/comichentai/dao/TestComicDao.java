package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import com.comichentai.dataobject.TestComicDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
@MybatisRepository
public interface TestComicDao {

    int insertTestComic(TestComicDo testComicDo);

    int updateTestComic(TestComicDo testComicDo);

    int batchUpdateTestComic(@Param("data") TestComicDo testComicDo, @Param("idList") List<Integer> idList);

    int deleteTestComic(@Param("id") Integer id);

    int batchDeleteTestComic(@Param("idList") List<Integer> idList);

    TestComicDo selectTestComicById(@Param("id") Integer id);

    List<TestComicDo> selectTestComicListByIds(@Param("idList") List<Integer> idList);

    List<TestComicDo> selectTestComicListByQuery(TestComicDo query);

    int selectTestComicCountByQuery(TestComicDo query);

}
