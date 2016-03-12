package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.ClassifiedDao;
import com.comichentai.dao.ComicDao;
import com.comichentai.dao.RelationalDao;
import com.comichentai.dataobject.ComicDo;
import com.comichentai.dto.ComicDto;
import com.comichentai.service.ComicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dintama on 2016/3/12.
 */
@Service("comicService")
public class ComicServiceImpl implements ComicService {

    @Resource(name = "comicDao")
    private ComicDao comicDao;

    @Resource(name = "relationalDao")
    private RelationalDao relationalDao;

    @Resource(name = "classifiedDao")
    private ClassifiedDao classifiedDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public List<ComicDto> getWelcomeComicListPage(Integer userId) {
        List<ComicDo> comicDo = comicDao.selectComicListPage();
        return comicDo.parallelStream().map(o -> mappingConverter.doMap(o, ComicDto.class)).collect(Collectors.toList());
    }

    @Override
    public ComicDto getComicById(Integer userId, Integer comicId) {
        checkNotNull(comicId, "comicId cannot be null");
        ComicDo comicDo = comicDao.selectComicById(comicId);
        List<Integer> ids = relationalDao.selectClassifiedIdsByComicId(comicId);
        List<String> titles = classifiedDao.selectClassifiedListByIds((Integer[])ids.toArray());
        comicDo.setClassifieds(titles);
        ComicDto comicDto = mappingConverter.doMap(comicDo, ComicDto.class);
        return comicDto;
    }

    @Override
    public List<ComicDto> getComicByTitle(String title) {
        checkNotNull(title, "title cannot be null");
        List<ComicDo> comicDo = comicDao.selectComicByTitle(title);
        return comicDo.parallelStream().map(o -> mappingConverter.doMap(o, ComicDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ComicDto> getComicByUserId(Integer userId) {
        return null;
    }

    @Override
    public void addComic(ComicDto comicDto) {
        checkNotNull(comicDto, "comic cannot be null");
        ComicDo comicDo = mappingConverter.doMap(comicDto, ComicDo.class);
        comicDao.insertComic(comicDo);
    }

    @Override
    public void addComicFromUser(Integer userId, Integer comicId) {

    }

    @Override
    public void modifyComic(ComicDto comicDto) {
        checkNotNull(comicDto, "comic cannot be null");
        ComicDo comicDo = mappingConverter.doMap(comicDto, ComicDo.class);
        comicDao.updateComic(comicDo);
    }

    @Override
    public void removeComicByIds(Integer... id) {
        checkNotNull(id, "id cannot be null");
        comicDao.deleteComicByIds(id);
    }

    @Override
    public void removeComicFromUser(Integer userId, List<Integer> comicIds) {

    }
}
