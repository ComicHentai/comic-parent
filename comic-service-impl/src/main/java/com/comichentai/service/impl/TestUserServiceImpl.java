package com.comichentai.service.impl;


import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.TestUserDao;
import com.comichentai.dto.TestUserDto;
import com.comichentai.entity.ResultSupport;
import com.comichentai.service.TestUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hope6537 on 16/2/15.
 */
@Service("testUserService")
public class TestUserServiceImpl implements TestUserService {

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Resource(name = "testUserDao")
    private TestUserDao testUserDao;

    @Override
    public ResultSupport<TestUserDto> getTestUserById(Integer id) {
        ResultSupport<TestUserDto> result;
        try {
            checkNotNull(id, "参数不能为空");
            TestUserDto testUserDto = mappingConverter.doMap(testUserDao.selectTestUserById(id), TestUserDto.class);
            checkNotNull(testUserDto, "无结果");
            result = ResultSupport.getInstance(true,"查询成功",testUserDto);
        } catch (Exception e) {
            result = ResultSupport.getInstance(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public ResultSupport<TestUserDto> getTestUserListByIds(Integer... ids) {
        return null;
    }
}
