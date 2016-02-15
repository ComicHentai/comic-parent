package com.comichentai.service;

import com.comichentai.dto.TestUserDto;
import com.comichentai.entity.ResultSupport;

/**
 * Created by hope6537 on 16/2/15.
 */
public interface TestUserService {

    ResultSupport<TestUserDto> getTestUserById(Integer id);

    ResultSupport<TestUserDto> getTestUserListByIds(Integer... ids);

}
