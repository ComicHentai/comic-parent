import com.alibaba.fastjson.JSON;
import com.comichentai.dto.ComicDto;
import com.comichentai.enums.IsDeleted;
import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.ComicService;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Dintama on 2016/2/29.
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class ComicServiceImplTest extends SpringTestHelper{

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ComicService comicService;

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

    @Before
    public void init() {
        logger.info(comicService.toString());
        ComicDto comicDto1 = new ComicDto(1L, 1L, 1, IsDeleted.NO, "1", "1", "1", "1", "1");
        ComicDto comicDto2 = new ComicDto(2L, 2L, 2, IsDeleted.NO, "2", "2", "2", "2", "2");
        comicService.addComic(comicDto1);
        comicService.addComic(comicDto2);
        comicService.addComicFromUser(1, 1);
        comicService.addComicFromUser(1, 2);
        comicService.addComicFromUser(2, 1);
        comicService.addComicFromUser(2, 2);
    }

    @Test
    public void testGetWelcomeComicListPage(){
        logger.debug("debugging");
        String comic = JSON.toJSONString(comicService.getWelcomeComicListPage(1));
        logger.error(comic);
    }

    @Test
    public void testGetComicById(){
        logger.debug("debugging");
        String comic = JSON.toJSONString(comicService.getComicById(1, 1));
        logger.error(comic);
    }

    @Test
    public void testGetComicByTitleOrAuthor(){
        logger.debug("debugging");
        String comic = JSON.toJSONString(comicService.getComicByTitleOrAuthor("1"));
        logger.error(comic);
    }

    @Test
    public void testGetComicByUserId(){
        logger.debug("debugging");
        String comic = JSON.toJSONString(comicService.getComicByUserId(1));
        logger.error(comic);
    }

    @Test
    public void testModifyComic(){
        logger.debug("debugging");
        ComicDto comicDto = new ComicDto(1L, 1L, 1, IsDeleted.NO, "1", "1", "1", "1", "1");
        comicDto.setId(1);
        comicService.modifyComic(comicDto);
    }

    @Test
    public void testRemoveComicById(){
        logger.debug("debugging");
        comicService.removeComicById(1);
    }

    @Test
    public void removeComicFromUser(){
        logger.debug("debugging");
        List<Integer> ids = new LinkedList<>();
        ids.add(1);
        comicService.removeComicFromUser(1, ids);
    }
}
