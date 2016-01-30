package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.ComicDao;
import com.comichentai.dataobject.ComicDo;
import com.comichentai.dto.ComicDto;
import com.comichentai.service.ComicService;
import com.google.common.collect.Lists;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hope6537 on 16/1/30.
 */
public class ComicServiceImpl implements ComicService {

    @Resource(name = "comicDao")
    private ComicDao comicDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public ComicDto getComicById(Integer id) {
        checkNotNull(id, "id cannot be null");
        ComicDo comicDo = comicDao.selectComicById(id);
        ComicDto comicDto = mappingConverter.doMap(comicDo, ComicDto.class);
        return comicDto;
    }

    @Override
    public List<ComicDto> getComicListByIdList(List<Integer> idList) {
        checkNotNull(idList, "idList cannot be null");
        checkArgument(idList.size() > 0, "idList cannot be empty");
        List<ComicDo> comicDo = comicDao.selectComicListByIds(idList);
        return comicDo.parallelStream().map(o -> mappingConverter.doMap(o,ComicDto.class)).collect(Collectors.toList());
    }
}
