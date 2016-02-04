import com.comichentai.helper.SpringTestHelper;
import com.comichentai.service.TestComicService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by hope6537 on 16/2/4.
 */
@ContextConfiguration("classpath:spring/spring-service-impl-context.xml")
public class TestComicServiceImplTest extends SpringTestHelper {

    @Autowired
    private TestComicService testComicService;

    @Before
    public void init() {
        logger.info(testComicService.toString());
    }

    @Test
    public void test1() {

    }

}
