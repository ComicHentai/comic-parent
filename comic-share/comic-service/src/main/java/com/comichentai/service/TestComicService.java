package com.comichentai.service;

import com.comichentai.dto.TestComicDto;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface TestComicService {

    TestComicDto getComicById(Integer id);

    List<TestComicDto> getComicListByIdList(List<Integer> idList);

}
