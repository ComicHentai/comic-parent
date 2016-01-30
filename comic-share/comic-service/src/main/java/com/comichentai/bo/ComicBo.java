package com.comichentai.bo;

import com.comichentai.dto.ComicDto;
import com.comichentai.entity.Response;
import com.comichentai.entity.ResultSupport;

import java.util.List;

/**
 * Created by hope6537 on 16/1/30.
 */
public interface ComicBo {

    ResultSupport<List<ComicDto>> getComicInfoById(Integer... id);

}
