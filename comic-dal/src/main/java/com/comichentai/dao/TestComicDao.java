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

    TestComicDo selectComicById(@Param("id") Integer id);

    List<TestComicDo> selectComicListByIds(@Param("idList") List<Integer> idList);

}
