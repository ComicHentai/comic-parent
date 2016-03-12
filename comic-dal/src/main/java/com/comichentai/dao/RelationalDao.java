package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by Dintama on 2016/3/12.
 */
@MybatisRepository
public interface RelationalDao {

    List<Integer> selectComicIdsByClassifiedId(@Param("id")Integer id);

    List<Integer> selectClassifiedIdsByComicId(@Param("id")Integer id);

}
