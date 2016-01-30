package com.comichentai.service;

import com.comichentai.dto.ComicDto;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface ComicService {

    ComicDto getComicById(Integer id);

    List<ComicDto> getComicListByIdList(List<Integer> idList);

}
