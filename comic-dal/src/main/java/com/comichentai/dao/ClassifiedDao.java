package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import com.comichentai.dataobject.ClassifiedDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MybatisRepository
public interface ClassifiedDao {
    ClassifiedDo selectClassifiedById(@Param("id") Integer id);

    List<String> selectClassifiedListByIds(@Param("ids") Integer... ids);

    List<ClassifiedDo> selectClassifiedByTitle(@Param("title") String title);

    void insertClassified(ClassifiedDo classifiedDo);

    void updateClassified(ClassifiedDo classifiedDo);

    void deleteClassifiedByIds(@Param("ids") Integer... ids);
}