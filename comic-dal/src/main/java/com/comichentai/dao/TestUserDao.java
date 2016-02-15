package com.comichentai.dao;

import com.comichentai.annotation.MybatisRepository;
import com.comichentai.dataobject.TestUserDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by hope6537 on 16/2/15.
 */
@MybatisRepository
public interface TestUserDao {

    TestUserDo selectTestUserById(@Param("id") Integer id);

    List<TestUserDo> selectTestUserListByIds(@Param("idList") Integer... idList);

}
