package com.comichentai.service.impl;

import com.comichentai.convert.impl.DozerMappingConverter;
import com.comichentai.dao.TestComicDao;
import com.comichentai.dataobject.TestComicDo;
import com.comichentai.dto.TestComicDto;
import com.comichentai.service.TestComicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by hope6537 on 16/1/30.
 */
@Service(value = "testComicService")
public class TestComicServiceImpl implements TestComicService {

    @Resource(name = "testComicDao")
    private TestComicDao testComicDao;

    @Resource(name = "mappingConverter")
    private DozerMappingConverter mappingConverter;

    @Override
    public TestComicDto getComicById(Integer id) {
        checkNotNull(id, "id cannot be null");
        TestComicDo comicDo = testComicDao.selectComicById(id);
        TestComicDto testComicDto = mappingConverter.doMap(comicDo, TestComicDto.class);
        return testComicDto;
    }

    @Override
    public List<TestComicDto> getComicListByIdList(List<Integer> idList) {
        checkNotNull(idList, "idList cannot be null");
        checkArgument(idList.size() > 0, "idList cannot be empty");
        List<TestComicDo> comicDo = testComicDao.selectComicListByIds(idList);
        return comicDo.parallelStream().map(o -> mappingConverter.doMap(o,TestComicDto.class)).collect(Collectors.toList());
    }
}
