package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.ComicDao;
import com.comichentai.dataobject.ComicDo;
import com.comichentai.dto.ComicDto;
import com.comichentai.service.ComicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Dintama on 2016/2/29.
 */
@Service("comicService")
public class ComicServiceImpl implements ComicService {

    @Resource(name = "comicDao")
    private ComicDao comicDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;


    @Override
    public List<ComicDto> getWelcomeComicListPage(Integer userId) {
        List<ComicDo> comicDos = comicDao.selectWelcomeComicListPage();
        return comicDos.parallelStream().map(o -> mappingConverter.doMap(o, ComicDto.class)).collect(Collectors.toList());
    }

    @Override
    public ComicDto getComicById(Integer userId, Integer comicId) {
        checkNotNull(comicId, "comicId cannot be null");
        ComicDo comicDo = comicDao.selectComicById(comicId);
        List<String> classified = comicDao.selectComicClassifieds(comicId);
        if(!classified.isEmpty())
            comicDo.setClassifieds(classified);
        return mappingConverter.doMap(comicDo, ComicDto.class);
    }

    @Override
    public List<ComicDto> getComicByTitleOrAuthor(String titleOrAuthor) {
        checkNotNull(titleOrAuthor, "title or author cannot be null");
        List<ComicDo> comicDos = comicDao.selectComicByAuthor(titleOrAuthor);
        comicDos.containsAll(comicDao.selectComicByTitle(titleOrAuthor));
        return comicDos.parallelStream().map(o -> mappingConverter.doMap(o, ComicDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ComicDto> getComicByUserId(Integer userId) {
        checkNotNull(userId, "userId cannot be null");
        List<ComicDo> comicDos = comicDao.selectComicByUserId(userId);
        return comicDos.parallelStream().map(o -> mappingConverter.doMap(o, ComicDto.class)).collect(Collectors.toList());
    }

    @Override
    public void addComic(ComicDto comicDto) {
        checkNotNull(comicDto, "comicDto cannot be null");
        ComicDo comicDo = mappingConverter.doMap(comicDto, ComicDo.class);
        comicDao.insertComic(comicDo);
    }

    @Override
    public void addComicFromUser(Integer userId, Integer comicId) {
        checkNotNull(userId, "userId cannot be null");
        checkNotNull(comicId, "comicId cannot be null");
        comicDao.insertComicFromUser(userId, comicId);
    }

    @Override
    public void modifyComic(ComicDto comicDto) {
        checkNotNull(comicDto, "comicDto cannot be null");
        ComicDo comicDo = mappingConverter.doMap(comicDto, ComicDo.class);
        comicDao.updateComic(comicDo);
    }

    @Override
    public void removeComicById(Integer id) {
        checkNotNull(id, "id cannot be null");
        comicDao.deleteComicById(id);
    }

    @Override
    public void removeComicFromUser(Integer userId, List<Integer> comicIds) {
        checkNotNull(userId, "userId cannot be null");
        checkArgument(comicIds.size() > 0, "comicIds cannot be empty");
        comicDao.deleteComicFromUser(userId, comicIds);
    }
}
