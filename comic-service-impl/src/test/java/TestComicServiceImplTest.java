import com.alibaba.fastjson.JSON;
import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.TestComicService;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;

/**
 * Created by hope6537 on 16/2/4.
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class TestComicServiceImplTest extends SpringTestHelper {

    @Autowired
    private TestComicService testComicService;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before
    public void init() {
        logger.info(testComicService.toString());
    }

    @Test
    public void testGetComicById() {
        String comic = JSON.toJSONString(testComicService.getComicById(1));
        logger.error(comic);
    }

    @Test
    public void testGetComicListByIdList() {
        logger.debug("asdasdqwaaqw啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
        String comicList = JSON.toJSONString(testComicService.getComicListByIdList(Lists.newArrayList(1, 2, 3, 4)));
        logger.error(comicList);
    }

    @Test
    public void testDubbo() throws IOException {
        pro();
    }

    static void pro() throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/spring-dubbo-service-impl.xml");
        context.start();
        System.in.read();
    }

}
