package com.comichentai.bo;

import com.comichentai.dto.TestComicDto;
import com.comichentai.entity.ResultSupport;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface TestComicBo {

    ResultSupport<List<TestComicDto>> getComicInfoById(Integer... id);

}
